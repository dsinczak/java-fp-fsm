package org.dsinczak.fp.fsm;

import java.util.Objects;

public class State<S, D> {
    S stateType;
    D stateData;

    State(S stateType, D stateData) {
        this.stateType = stateType;
        this.stateData = stateData;
    }

    public State<S,D> using(D nextStateData) {
        return new State<>(stateType, nextStateData);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State<?, ?> state = (State<?, ?>) o;
        return Objects.equals(stateType, state.stateType) &&
                Objects.equals(stateData, state.stateData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stateType, stateData);
    }

    @Override
    public String toString() {
        return "State{" +
                "stateType=" + stateType +
                ", stateData=" + stateData +
                '}';
    }
}