package org.dsinczak.fp.func;

@FunctionalInterface
public interface Predicate2<T,P> {

    /**
     * Evaluates this predicate on the given arguments.
     *
     * @param t the input argument
     * @param p the input argument
     * @return {@code true} if the input arguments matches the predicate,
     * otherwise {@code false}
     */
    boolean test(T t, P p);
}
