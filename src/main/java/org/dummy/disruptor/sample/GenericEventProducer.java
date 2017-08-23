/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dummy.disruptor.sample;

import com.lmax.disruptor.RingBuffer;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Davide Artasensi
 */
public abstract class GenericEventProducer implements EventProducer {

    private static final Logger LOGGER = LogManager.getLogger();

    public void produce(final RingBuffer<ValueEvent> ringBuffer, final int count) throws InterruptedException {
        produce(ringBuffer, count, -1, TimeUnit.NANOSECONDS);
    }

    public void produce(final RingBuffer<ValueEvent> ringBuffer, final int count, final long delay, final TimeUnit delayTimeUnit) throws InterruptedException {
        LOGGER.debug("Starting production of {} events", count);
        for (int i = 0; i < count; i++) {
            final long seq = ringBuffer.next();
            final ValueEvent valEvent = ringBuffer.get(seq);
            valEvent.setValue(i);
            ringBuffer.publish(seq);

            if (delay >= 0) {
                delayTimeUnit.sleep(delay);
            }
        }
        LOGGER.debug("Completed production of {} events", count);
    }
}
