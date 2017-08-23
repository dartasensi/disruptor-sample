/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dummy.disruptor.sample;

import com.lmax.disruptor.EventHandler;
import java.util.List;

/**
 *
 * @author Davide Artasensi
 */
public interface EventConsumer {

    public EventHandler<ValueEvent>[] getEventHandlers();

}
