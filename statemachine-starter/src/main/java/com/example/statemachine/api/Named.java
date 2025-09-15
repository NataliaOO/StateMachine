package com.example.statemachine.api;

public interface Named {
    String getName();
    default String getDescription() { return null; }
}
