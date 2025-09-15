package com.example.statemachine.impl;

import com.example.statemachine.api.Event;

public class StdEvent implements Event {
    private String name;
    private String description;

    public StdEvent name(String name) { this.name = name; return this; }
    public StdEvent description(String d) { this.description = d; return this; }

    @Override public String getName() { return name; }
    @Override public String getDescription() { return description; }

    @Override public String toString() { return "Event{name='" + name + "', description='" + description + "'}"; }
}
