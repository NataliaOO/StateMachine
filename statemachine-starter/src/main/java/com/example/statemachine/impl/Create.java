package com.example.statemachine.impl;

import com.example.statemachine.api.State;

public final class Create {
    public static <C> State<C> state(java.util.function.Consumer<StdState<C>> init) {
        StdState<C> s = new StdState<>();
        init.accept(s);
        return s;
    }
}
