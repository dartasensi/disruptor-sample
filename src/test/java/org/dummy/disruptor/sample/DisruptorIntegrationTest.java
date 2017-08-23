/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dummy.disruptor.sample;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.TimeoutException;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Davide Artasensi
 */
public class DisruptorIntegrationTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private Disruptor<ValueEvent> disruptor;

    private WaitStrategy waitStrategy;

    @Before
    public void setUp() throws Exception {
        waitStrategy = new BusySpinWaitStrategy();

        // set log4j 2
        System.setProperty("org.apache.logging.log4j.simplelog.StatusLogger.level", "TRACE");
    }

    private void createDisruptor(final ProducerType producerType, final EventConsumer eventConsumer) {
        final ThreadFactory threadFactory = DaemonThreadFactory.INSTANCE;
        disruptor = new Disruptor<>(ValueEvent.EVENT_FACTORY, 16, threadFactory, producerType, waitStrategy);
        disruptor.handleEventsWith(eventConsumer.getEventHandlers());
    }

    @Test
    public void whenMultiProdSingleCons_thenOutputInFifoOrder() {
        LOGGER.info("Testing whenMultiProdSingleCons_thenOutputInFifoOrder");

        final EventConsumer evnConsumer = new SingleEventConsumerPrinter();
        final EventProducer evnProducer = new MultiEventProducer();
        createDisruptor(ProducerType.MULTI, evnConsumer);
        final RingBuffer<ValueEvent> ringBuffer = disruptor.start();

        evnProducer.start(ringBuffer, 64);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex) {
            LOGGER.error(ex.getMessage());
        }

        disruptor.halt();
        disruptor.shutdown();
        LOGGER.info("Tested whenMultiProdSingleCons_thenOutputInFifoOrder");

    }

    @Test
    public void whenSingleProdMultiSlowCons_thenOutputInFifoOrder() {
        LOGGER.info("Testing whenSingleProdMultiSlowCons_thenOutputInFifoOrder");

        final EventConsumer evnConsumer = new MultiEventConsumerPrinter(2, 500, TimeUnit.MILLISECONDS);
        final EventProducer evnProducer = new SingleEventProducer();
        createDisruptor(ProducerType.SINGLE, evnConsumer);
        final RingBuffer<ValueEvent> ringBuffer = disruptor.start();

        evnProducer.start(ringBuffer, 64);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex) {
            LOGGER.error(ex.getMessage());
        }

        disruptor.halt();
        disruptor.shutdown();
        LOGGER.info("Tested whenSingleProdMultiSlowCons_thenOutputInFifoOrder");
    }

}
