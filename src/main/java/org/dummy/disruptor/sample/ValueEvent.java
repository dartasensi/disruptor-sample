/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dummy.disruptor.sample;

import com.lmax.disruptor.EventFactory;

/**
 *
 * @author Davide Artasensi
 */
public final class ValueEvent {

    private int value;

    public final static EventFactory EVENT_FACTORY = () -> new ValueEvent();

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
