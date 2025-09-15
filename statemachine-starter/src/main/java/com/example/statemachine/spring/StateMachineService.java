package com.example.statemachine.spring;

import com.example.statemachine.api.Event;
import com.example.statemachine.api.State;
import com.example.statemachine.api.StateHolder;
import com.example.statemachine.impl.StateMachine;

public class StateMachineService {
    public <C extends StateHolder<C>> State<C> process(Event event, C context) {
        return StateMachine.process(event, context);
    }
}
