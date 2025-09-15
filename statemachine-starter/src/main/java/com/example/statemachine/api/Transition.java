package com.example.statemachine.api;

import java.util.function.Function;

public interface Transition<C> {
    Event getEvent();
    Function<C, Boolean> getCondition();        // guard (может быть null)
    State<C> getNextState();
    Action<C> getTransitionAction();
}
