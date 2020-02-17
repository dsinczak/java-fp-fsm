package org.dsinczak.fp.fsm;

import io.vavr.PartialFunction;

import java.util.function.BiConsumer;

public class FiniteStateMachineDsl<S, D> extends FiniteStateMachine<S, D> {

    final void startWith(S state, D data) {

    }

    final void when(S state, PartialFunction<Message<S>, State<S, D>> stateChangeFunction) {

    }

    final void when(S state, StateChangeFunctionBuilder<S, D> stateChangeFunction) {

    }

    final void onStateChange(BiConsumer<S, S> stateChangeHandler) {

    }

    final void onStateChange(StateChangeHandlerBuilder<S> stateChangeHandlerBuilder) {

    }

    final void whenUnhandled(StateChangeFunctionBuilder<S, D> stateChangeFunction) {

    }

}
