package com.example.statemachine.api;

import java.util.function.Function;

public interface Action<C> extends Named, Function<C, C> { }
