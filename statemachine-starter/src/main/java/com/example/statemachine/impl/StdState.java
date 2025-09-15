package com.example.statemachine.impl;

import com.example.statemachine.api.Action;
import com.example.statemachine.api.State;
import com.example.statemachine.api.Transition;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StdState<C> implements State<C> {
    private String name;
    private String description;
    private Action<C> entry;
    private Action<C> exit;
    private final List<Transition<C>> transitions = new ArrayList<>();

    public StdState<C> name(String n) { this.name = n; return this; }
    public StdState<C> description(String d) { this.description = d; return this; }

    public StdState<C> entryAction(Consumer<StdAction<C>> init) {
        StdAction<C> a = new StdAction<>();
        init.accept(a);
        this.entry = a;
        return this;
    }

    public StdState<C> exitAction(Consumer<StdAction<C>> init) {
        StdAction<C> a = new StdAction<>();
        init.accept(a);
        this.exit = a;
        return this;
    }

    public StdState<C> transition(Consumer<StdTransition<C>> init) {
        StdTransition<C> t = new StdTransition<>();
        init.accept(t);
        transitions.add(t);
        return this;
    }

    @Override public String getName() { return name; }
    @Override public String getDescription() { return description; }
    @Override public Action<C> getEntryAction() { return entry; }
    @Override public Action<C> getExitAction()  { return exit; }
    @Override public List<Transition<C>> getTransitions() { return transitions; }

    @Override public String toString() {
        return "State{name='" + name + "', description='" + description + "'}";
    }
}