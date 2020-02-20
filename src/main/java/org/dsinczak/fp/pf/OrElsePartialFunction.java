package org.dsinczak.fp.pf;

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
        } else {
            return second.apply(t);
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
