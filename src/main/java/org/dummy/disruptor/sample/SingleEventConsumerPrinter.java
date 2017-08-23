/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dummy.disruptor.sample;

import com.lmax.disruptor.EventHandler;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Davide Artasensi
 */
public class SingleEventConsumerPrinter implements EventConsumer {

    private long delay;
    private TimeUnit delayTimeUnit;

    private static final Logger LOGGER = LogManager.getLogger();

    public SingleEventConsumerPrinter() {
        this(-1, TimeUnit.NANOSECONDS);
    }

    public SingleEventConsumerPrinter(final long delay, final TimeUnit delayTimeUnit) {
        this.delay = delay;
        this.delayTimeUnit = delayTimeUnit;
    }

    @Override
    public EventHandler<ValueEvent>[] getEventHandlers() {
        final EventHandler<ValueEvent> eventHandler = (event, sequence, endOfBatch) -> print(event.getValue(), sequence);

        return new EventHandler[]{eventHandler};
    }

    private void print(final int id, final long sequenceId) {
        LOGGER.info("Id: {} - Sequence: {}", id, sequenceId);
    }

}
