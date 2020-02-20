package org.dsinczak.fp.fsm;

import java.util.function.BiConsumer;

public class StateChangeHandlerBuilder<S> {

    public StateChangeHandlerBuilder<S> matchState(S from, S to, Runnable apply) {
        return this;
    }

    public StateChangeHandlerBuilder<S> matchState(S from, S to, BiConsumer<S, S> apply) {
        return this;
    }

    public StateChangeConsumer<S> build() {
        return new StateChangeConsumer<S>() {
            @Override
            public Void apply(S s, S s2) {
                return null;
            }

            @Override
            public boolean isDefinedAt(S s, S s2) {
                return false;
            }
        };
    }

}
