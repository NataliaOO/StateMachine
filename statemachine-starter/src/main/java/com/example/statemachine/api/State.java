package com.example.statemachine.api;

import java.util.List;

public interface State<C> extends Named {
    Action<C> getEntryAction();            // null если нет
    Action<C>  getExitAction();             // null если нет
    List<Transition<C>> getTransitions();       // пусто, если нет
}
