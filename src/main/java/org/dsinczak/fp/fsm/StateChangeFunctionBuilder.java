package org.dsinczak.fp.fsm;

import org.dsinczak.fp.func.Predicate2;

import java.util.function.BiFunction;

public class StateChangeFunctionBuilder<S, D> {

    public final <MT, DT> StateChangeFunctionBuilder<S, D> matchMessage(
            final Class<MT> messageType,
            final Class<DT> dataType,
            final Predicate2<MT, DT> predicate,
            BiFunction<MT, DT, State<S, D>> apply
    ) {

        return this;
    }

    public final <MT, DT> StateChangeFunctionBuilder<S, D> matchMessage(
            final Class<MT> messageType,
            final Class<DT> dataType,
            final BiFunction<MT, DT, State<S, D>> apply
    ) {

        return this;
    }

    public final <MT> StateChangeFunctionBuilder<S, D> matchMessage(
            final Class<MT> messageType,
            final Predicate2<MT, D> predicate,
            BiFunction<MT, D, State<S, D>> apply
    ) {

        return this;
    }

    public final <MT> StateChangeFunctionBuilder<S, D> matchMessage(
            final Class<MT> messageType,
            BiFunction<MT, D, State<S, D>> apply
    ) {

        return this;
    }

    public final StateChangeFunctionBuilder<S, D> matchMessage(
            final Predicate2<Object, D> predicate,
            BiFunction<Object, D, State<S, D>> apply
    ) {

        return this;
    }

    public final <MT, DT> StateChangeFunctionBuilder<S, D> matchMessageEquals(
            MT message,
            Class<DT> dataType,
            BiFunction<MT, DT, State<S,D>> apply
    ) {

        return this;
    }

    public final <MT> StateChangeFunctionBuilder<S, D> matchMessageEquals(
            MT message,
            BiFunction<MT, D, State<S,D>> apply
    ) {

        return this;
    }

    public final StateChangeFunctionBuilder<S, D> matchAnyMessage(
            BiFunction<Object, D, State<S,D>> apply
    ) {

        return this;
    }

    public StateChangeFunction<S, D> build() {
        return new StateChangeFunction<S, D>() {
            @Override
            public State<S, D> apply(Message<D> message) {
                return null;
            }

            @Override
            public boolean isDefinedAt(Message<D> value) {
                return false;
            }
        };
    }
}
