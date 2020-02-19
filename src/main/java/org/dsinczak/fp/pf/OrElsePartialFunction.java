package org.dsinczak.fp.pf;

import io.vavr.PartialFunction;

public final class OrElsePartialFunction<T,R> implements PartialFunction<T,R> {

    private PartialFunction<T,R> first;
    private PartialFunction<T,R> second;

    public OrElsePartialFunction(PartialFunction<T, R> first, PartialFunction<T, R> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public R apply(T t) {
        if (first.isDefinedAt(t)) {
            return first.apply(t);
        } else if (second.isDefinedAt(t)) {
            return second.apply(t);
        } else {
            throw new IllegalArgumentException("Partial function not defined for " + t + " check isDefinedAt before apply");
        }
    }

    @Override
    public boolean isDefinedAt(T value) {
        return first.isDefinedAt(value) || second.isDefinedAt(value);
    }

    public OrElsePartialFunction<T, R> orElse(PartialFunction<T, R> partialFunction) {
        return new OrElsePartialFunction<>(this, partialFunction);
    }
}
