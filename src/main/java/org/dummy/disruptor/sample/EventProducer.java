/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dummy.disruptor.sample;

import com.lmax.disruptor.RingBuffer;

/**
 *
 * @author Davide Artasensi
 */
public interface EventProducer {

    public void start(final RingBuffer<ValueEvent> ringBuffer, final int count);
}
