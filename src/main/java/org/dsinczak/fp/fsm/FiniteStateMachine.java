package org.dsinczak.fp.fsm;

import io.vavr.PartialFunction;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @param <S> state type
 * @param <D> data type
 */
public abstract class FiniteStateMachine<S, D> {
    private State<S, D> currentState;
    private State<S, D> nextState;

    private Map<S, PartialFunction<Message<D>, State<S,D>>> stateFunctions = new HashMap<>();
}

class Message<D> {
    Object content;
    D stateData;
}

class State<S, D> {
    S stateType;
    D stateData;
}
