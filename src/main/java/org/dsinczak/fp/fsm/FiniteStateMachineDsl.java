package org.dsinczak.fp.fsm;

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

}
