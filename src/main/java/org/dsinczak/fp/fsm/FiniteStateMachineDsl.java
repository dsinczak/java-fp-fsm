package org.dsinczak.fp.fsm;

import org.dsinczak.fp.func.Predicate2;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class FiniteStateMachineDsl<S, D> extends FiniteStateMachine<S, D> {

    final void startWith(S state, D data) {
        super.initState(state, data);
    }

    final void when(S state, StateChangeFunction<S, D> stateChangeFunction) {
        super.registerStateChangeFunction(state, stateChangeFunction);
    }

    final void when(S state, StateChangeFunctionBuilder<S, D> builder) {
        super.registerStateChangeFunction(state, builder.build());
    }

    final void onStateChange(StateChangeConsumer<S> stateChangeHandler) {
        super.registerStateChangeHandler(stateChangeHandler);
    }

    final void onStateChange(StateChangeHandlerBuilder<S> builder) {
        super.registerStateChangeHandler(builder.build());
    }

    final void whenUnhandled(StateChangeFunction<S, D> stateChangeFunction) {
        super.registerUnhandledStateChangeFunction(stateChangeFunction);
    }

    final void whenUnhandled(StateChangeFunctionBuilder<S, D> builder) {
        super.registerUnhandledStateChangeFunction(builder.build());
    }

    /////////////////////////////////////////////////////////////////////////
    // STATE CHANGE FUNCTION MATCHERS

    final <MT, DT extends D> StateChangeFunctionBuilder<S, D> matchMessage(
            Class<MT> messageType,
            Class<DT> dataType,
            Predicate2<MT, DT> predicate,
            BiFunction<MT, DT, State<S,D>> apply
    ) {
        return new StateChangeFunctionBuilder<S,D>().matchMessage(messageType, dataType, predicate, apply);
    }

    final <MT, DT extends D> StateChangeFunctionBuilder<S, D> matchMessage(
            Class<MT> messageType,
            Class<DT> dataType,
            BiFunction<MT, DT, State<S,D>> apply
    ) {
        return new StateChangeFunctionBuilder<S,D>().matchMessage(messageType, dataType, apply);
    }

    final <MT> StateChangeFunctionBuilder<S, D> matchMessage(
            Class<MT> messageType,
            Predicate2<MT, D> predicate,
            BiFunction<MT, D, State<S,D>> apply
    ) {
        return new StateChangeFunctionBuilder<S,D>().matchMessage(messageType, predicate, apply);
    }

    final <MT> StateChangeFunctionBuilder<S, D> matchMessage(
            Class<MT> messageType,
            BiFunction<MT, D, State<S,D>> apply
    ) {
        return new StateChangeFunctionBuilder<S,D>().matchMessage(messageType, apply);
    }

    final StateChangeFunctionBuilder<S, D> matchMessage(
            Predicate2<Object, D> predicate,
            BiFunction<Object, D, State<S,D>> apply
    ) {
        return new StateChangeFunctionBuilder<S,D>().matchMessage(predicate, apply);
    }

    final <MT, DT> StateChangeFunctionBuilder<S, D> matchMessageEquals(
            MT message,
            Class<DT> dataType,
            BiFunction<MT, DT, State<S,D>> apply
    ) {
        return new StateChangeFunctionBuilder<S,D>().matchMessageEquals(message, dataType, apply);
    }

    final <MT> StateChangeFunctionBuilder<S, D> matchMessageEquals(
            MT message,
            BiFunction<MT, D, State<S,D>> apply
    ) {
        return new StateChangeFunctionBuilder<S,D>().matchMessageEquals(message, apply);
    }

    final StateChangeFunctionBuilder<S, D> matchAnyMessage(
            BiFunction<Object, D, State<S,D>> apply
    ) {
        return new StateChangeFunctionBuilder<S,D>().matchAnyMessage(apply);
    }

    /////////////////////////////////////////////////////////////////////////
    // STATE CHANGE HANDLER MATCHERS

    public StateChangeHandlerBuilder<S> matchState(S from, S to, Runnable apply) {
        return new StateChangeHandlerBuilder<S>().matchState(from, to, apply);
    }

    public StateChangeHandlerBuilder<S> matchState(S from, S to, BiConsumer<S, S> apply) {
        return new StateChangeHandlerBuilder<S>().matchState(from, to, apply);
    }

}
