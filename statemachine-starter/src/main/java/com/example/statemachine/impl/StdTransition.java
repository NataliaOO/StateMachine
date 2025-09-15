package com.example.statemachine.impl;

import com.example.statemachine.api.Action;
import com.example.statemachine.api.Event;
import com.example.statemachine.api.State;
import com.example.statemachine.api.Transition;

import java.util.function.Function;
import java.util.function.Supplier;

public class StdTransition<C> implements Transition<C> {
    private Event event;
    private Function<C, Boolean> condition;
    private Supplier<State<C>> nextStateSupplier;
    private Action<C> transitionAction;

    public StdTransition<C> event(Event e) { this.event = e; return this; }
    public StdTransition<C> condition(Function<C, Boolean> cond) { this.condition = cond; return this; }
    public StdTransition<C> nextState(State<C> s) { this.nextStateSupplier = () -> s; return this; }
    public StdTransition<C> nextState(Supplier<State<C>> sup) { this.nextStateSupplier = sup; return this; }
    public StdTransition<C> transitionAction(Action<C> a) { this.transitionAction = a; return this; }

    @Override public Event getEvent() { return event; }
    @Override public Function<C, Boolean> getCondition() { return condition; }
    @Override public State<C> getNextState() { return nextStateSupplier == null ? null : nextStateSupplier.get(); }
    @Override public Action<C> getTransitionAction() { return transitionAction; }
}
