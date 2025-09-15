package com.example.shop;

import com.example.statemachine.api.State;
import com.example.statemachine.api.StateHolder;

public class ShopContext implements StateHolder<ShopContext> {
    private Long userId;
    private State<ShopContext> state;

    public ShopContext userId(Long id) { this.userId = id; return this; }

    @Override public State<ShopContext> getState() { return state; }
    @Override public void setState(State<ShopContext> state) { this.state = state; }
    public ShopContext state(State<ShopContext> s) { this.state = s; return this; }
}