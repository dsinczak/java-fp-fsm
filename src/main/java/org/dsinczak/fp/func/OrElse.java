package org.dsinczak.fp.func;

public final class OrElse<T,R> implements PartialFunction<T,R> {

    private PartialFunction<T,R> first;
    private PartialFunction<T,R> second;

    public OrElse(PartialFunction<T, R> first, PartialFunction<T, R> second) {
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

    public OrElse<T, R> orElse(PartialFunction<T, R> partialFunction) {
        return new OrElse<>(this, partialFunction);
    }
}
