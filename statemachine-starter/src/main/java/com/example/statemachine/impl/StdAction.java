package com.example.statemachine.impl;

import com.example.statemachine.api.Action;

import java.util.function.Function;

public class StdAction<C> implements Action<C> {
    private String name;
    private String description;
    private Function<C, C> target;

    public StdAction<C> name(String name) { this.name = name; return this; }
    public StdAction<C> description(String d) { this.description = d; return this; }
    public StdAction<C> activity(Function<C, C> fn) { this.target = fn; return this; }

    @Override public String getName() { return name; }
    @Override public String getDescription() { return description; }
    @Override public C apply(C c) { return target == null ? c : target.apply(c); }

    @Override public String toString() { return "Action{name='" + name + "', description='" + description + "'}"; }
}
