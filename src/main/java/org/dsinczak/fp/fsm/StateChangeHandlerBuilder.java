package org.dsinczak.fp.fsm;

import org.dsinczak.fp.func.PartialFunctionBuilder;
import org.dsinczak.fp.func.Tuple2;

import java.util.function.BiConsumer;

public class StateChangeHandlerBuilder<S> {

    private PartialFunctionBuilder<Tuple2<S, S>, Void> builder = new PartialFunctionBuilder<>();

    public StateChangeHandlerBuilder<S> matchState(S from, S to, Runnable apply) {
        builder.match(Tuple2.class,
                t -> (from == null || from.equals(t.getA())) && (to == null || to.equals(t.getB())),
                t -> {
                    apply.run();
                    return null;
                }
        );
        return this;
    }

    @SuppressWarnings("unchecked")
    public StateChangeHandlerBuilder<S> matchState(S from, S to, BiConsumer<S, S> apply) {
        builder.match(Tuple2.class,
                t -> (from == null || from.equals(t.getA())) && (to == null || to.equals(t.getB())),
                t -> {
                    apply.accept((S) t.getA(), (S) t.getB());
                    return null;
                }
        );
        return this;
    }

    public StateChangeConsumer<S> build() {
        var pft = builder.build();
        return new StateChangeConsumer<S>() {
            @Override
            public Void apply(S from, S to) {
                return pft.apply(Tuple2.of(from, to));
            }

            @Override
            public boolean isDefinedAt(S from, S to) {
                return pft.isDefinedAt(Tuple2.of(from, to));
            }
        };
    }

}
