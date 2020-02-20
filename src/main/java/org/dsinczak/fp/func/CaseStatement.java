package org.dsinczak.fp.func;

import java.util.function.Function;
import java.util.function.Predicate;

class CaseStatement<T, P, R> implements PartialFunction<T, R> {

    Predicate<Object> predicate;
    Function<P, R> apply;

    public CaseStatement(Predicate<Object> predicate, Function<P, R> apply) {
        this.predicate = predicate;
        this.apply = apply;
    }

    @Override
    @SuppressWarnings("unchecked")
    public R apply(T t) {
        // At this point isDefinedAt verified
        // that class can be cast to
        return apply.apply((P) t);
    }

    @Override
    public boolean isDefinedAt(T value) {
        return predicate.test(value);
    }
}
