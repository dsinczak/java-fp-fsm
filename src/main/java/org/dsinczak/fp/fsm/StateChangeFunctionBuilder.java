package org.dsinczak.fp.fsm;

import org.dsinczak.fp.func.PartialFunctionBuilder;
import org.dsinczak.fp.func.Predicate2;

import java.util.function.BiFunction;

public class StateChangeFunctionBuilder<S, D> {

    private PartialFunctionBuilder<Message<D>, State<S, D>> builder = new PartialFunctionBuilder<>();

    @SuppressWarnings("unchecked")
    public final <MT, DT> StateChangeFunctionBuilder<S, D> matchMessage(
            final Class<MT> messageType,
            final Class<DT> dataType,
            final Predicate2<MT, DT> predicate,
            BiFunction<MT, DT, State<S, D>> apply
    ) {
        builder.match(Message.class,
                message -> messageType.isInstance(message.content)
                        && dataType.isInstance(message.stateData)
                        && predicate.test((MT)message.content, (DT)message.stateData),
                message -> apply.apply((MT)message.content, (DT)message.stateData)
        );
        return this;
    }

    @SuppressWarnings("unchecked")
    public final <MT, DT> StateChangeFunctionBuilder<S, D> matchMessage(
            final Class<MT> messageType,
            final Class<DT> dataType,
            final BiFunction<MT, DT, State<S, D>> apply
    ) {
        builder.match(Message.class,
                message -> messageType.isInstance(message.content)
                        && dataType.isInstance(message.stateData),
                message -> apply.apply((MT)message.content, (DT)message.stateData)
        );
        return this;
    }

    @SuppressWarnings("unchecked")
    public final <MT> StateChangeFunctionBuilder<S, D> matchMessage(
            final Class<MT> messageType,
            final Predicate2<MT, D> predicate,
            BiFunction<MT, D, State<S, D>> apply
    ) {
        builder.match(Message.class,
                message -> messageType.isInstance(message.content)
                        && predicate.test((MT)message.content, (D)message.stateData),
                message -> apply.apply((MT)message.content, (D)message.stateData)
        );
        return this;
    }

    @SuppressWarnings("unchecked")
    public final <MT> StateChangeFunctionBuilder<S, D> matchMessage(
            final Class<MT> messageType,
            BiFunction<MT, D, State<S, D>> apply
    ) {
        builder.match(Message.class,
                message -> messageType.isInstance(message.content),
                message -> apply.apply((MT)message.content, (D)message.stateData)
        );
        return this;
    }

    @SuppressWarnings("unchecked")
    public final StateChangeFunctionBuilder<S, D> matchMessage(
            final Predicate2<Object, D> predicate,
            BiFunction<Object, D, State<S, D>> apply
    ) {
        builder.match(Message.class,
                message -> predicate.test(message.content, (D)message.stateData),
                message -> apply.apply(message.content, (D)message.stateData)
        );
        return this;
    }

    @SuppressWarnings("unchecked")
    public final <MT, DT> StateChangeFunctionBuilder<S, D> matchMessageEquals(
            MT msg,
            Class<DT> dataType,
            BiFunction<MT, DT, State<S, D>> apply
    ) {
        builder.match(Message.class,
                message -> msg.equals(message.content) && dataType.isInstance(message.stateData),
                message -> apply.apply((MT)message.content, (DT)message.stateData)
        );
        return this;
    }

    @SuppressWarnings("unchecked")
    public final <MT> StateChangeFunctionBuilder<S, D> matchMessageEquals(
            MT msg,
            BiFunction<MT, D, State<S, D>> apply
    ) {
        builder.match(Message.class,
                message -> msg.equals(message.content),
                message -> apply.apply((MT)message.content, (D)message.stateData)
        );
        return this;
    }

    @SuppressWarnings("unchecked")
    public final StateChangeFunctionBuilder<S, D> matchAnyMessage(
            BiFunction<Object, D, State<S, D>> apply
    ) {
        builder.match(Message.class,
                message -> true,
                message -> apply.apply(message.content, (D)message.stateData)
        );
        return this;
    }

    public StateChangeFunction<S, D> build() {
        var pf = builder.build();
        return new StateChangeFunction<>() {
            @Override
            public State<S, D> apply(Message<D> message) {
                return pf.apply(message);
            }

            @Override
            public boolean isDefinedAt(Message<D> message) {
                return pf.isDefinedAt(message);
            }
        };
    }
}
