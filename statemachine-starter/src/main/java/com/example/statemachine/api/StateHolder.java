package com.example.statemachine.api;

public interface StateHolder<C extends StateHolder<C>> {
    State<C> getState();
    void setState(State<C> state);
}
