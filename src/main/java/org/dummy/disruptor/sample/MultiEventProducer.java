/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dummy.disruptor.sample;

import com.lmax.disruptor.RingBuffer;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Davide Artasensi
 */
public class MultiEventProducer extends GenericEventProducer {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void start(RingBuffer<ValueEvent> ringBuffer, int count) {
        final Runnable fastProducer = () -> {
            try {
                produce(ringBuffer, count, -1, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                LOGGER.catching(ex);
            }
        };
        final Runnable slowProducer = () -> {
            try {
                produce(ringBuffer, count, 1, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                LOGGER.catching(ex);
            }
        };

        LOGGER.debug("Starting fastProducer");
        new Thread(fastProducer).start();

        LOGGER.debug("Starting slowProducer");
        new Thread(slowProducer).start();
    }
}
