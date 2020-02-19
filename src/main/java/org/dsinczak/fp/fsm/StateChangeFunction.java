package org.dsinczak.fp.fsm;

import io.vavr.PartialFunction;
import org.dsinczak.fp.pf.OrElsePartialFunction;

public interface StateChangeFunction<S, D> extends PartialFunction<Message<D>, State<S, D>> {

    default StateChangeFunction<S, D> orElse(StateChangeFunction<S, D> next) {
        var orElse = new OrElsePartialFunction<>(this, next);

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
