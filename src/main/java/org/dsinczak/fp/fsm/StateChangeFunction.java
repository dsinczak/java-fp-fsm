package org.dsinczak.fp.fsm;


import org.dsinczak.fp.func.OrElse;
import org.dsinczak.fp.func.PartialFunction;

public interface StateChangeFunction<S, D> extends PartialFunction<Message<D>, State<S, D>> {

    // Consequence of lacking higher kinded types
    default StateChangeFunction<S, D> orElse(StateChangeFunction<S, D> next) {
        var orElse = new OrElse<>(this, next);

        return new StateChangeFunction<S, D>() {
            @Override
            public State<S, D> apply(Message<D> message) {
                return orElse.apply(message);
            }

            @Override
            public boolean isDefinedAt(Message<D> message) {
                return orElse.isDefinedAt(message);
            }
        };
    }
}
