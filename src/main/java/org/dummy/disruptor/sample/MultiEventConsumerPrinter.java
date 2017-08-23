/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dummy.disruptor.sample;

import com.lmax.disruptor.EventHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Davide Artasensi
 */
public class MultiEventConsumerPrinter implements EventConsumer {

    private int count;
    private long delay;
    private TimeUnit delayTimeUnit;

    private static final Logger LOGGER = LogManager.getLogger();

    public MultiEventConsumerPrinter() {
        this(2, -1, TimeUnit.NANOSECONDS);
    }

    public MultiEventConsumerPrinter(final int count, final long delay, final TimeUnit delayTimeUnit) {
        this.count = count;
        this.delay = delay;
        this.delayTimeUnit = delayTimeUnit;
    }

    @Override
    public EventHandler<ValueEvent>[] getEventHandlers() {
        final EventHandler<ValueEvent> eventHandler = (event, sequence, endOfBatch) -> print(event.getValue(), sequence);
        final EventHandler<ValueEvent> otherEventHandler = (event, sequence, endOfBatch) -> print(event.getValue(), sequence);

        return new EventHandler[]{eventHandler, otherEventHandler};
    }

    private void print(final int id, final long sequenceId) {
        LOGGER.info("Id: {} - Sequence: {}", id, sequenceId);
        if (this.delay >= 0) {
            try {
                this.delayTimeUnit.sleep(this.delay);
            } catch (InterruptedException ex) {
                LOGGER.catching(ex);
            }
        }
    }

}
