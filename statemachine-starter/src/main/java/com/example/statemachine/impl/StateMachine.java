package com.example.statemachine.impl;

import com.example.statemachine.api.Event;
import com.example.statemachine.api.State;
import com.example.statemachine.api.StateHolder;
import com.example.statemachine.api.Transition;

import java.util.Objects;

public class StateMachine {
    private StateMachine() { }

    public static <C extends StateHolder<C>> State<C> process(Event event, C context) {
        State<C> current = context.getState();
        if (current == null) return null;

        Transition<C> t = current.getTransitions().stream()
                .filter(tr -> Objects.equals(tr.getEvent(), event))
                .filter(tr -> tr.getCondition() == null || Boolean.TRUE.equals(tr.getCondition().apply(context)))
                .findFirst()
                .orElse(null);

        if (t == null) {
            // нет подходящего перехода — остаёмся
            return current;
        }

        if (current.getExitAction() != null) current.getExitAction().apply(context);
        if (t.getTransitionAction() != null) t.getTransitionAction().apply(context);

        State<C> next = t.getNextState();
        if (next == null) next = current;
        context.setState(next);

        if (next.getEntryAction() != null) next.getEntryAction().apply(context);
        return next;
    }
}
