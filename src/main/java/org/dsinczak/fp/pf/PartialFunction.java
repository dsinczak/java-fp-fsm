package org.dsinczak.fp.pf;

import java.util.Optional;
import java.util.function.Function;

public interface PartialFunction<T,R> extends Function<T,R> {
    /**
     * Applies this function to the given argument and returns the result.
     *
     * @param t the argument
     * @return the result of function application
     *
     */
    R apply(T t);

    /**
     * Tests if a value is contained in the function's domain.
     *
     * @param value a potential function argument
     * @return true, if the given value is contained in the function's domain, false otherwise
     */
    boolean isDefinedAt(T value);

    /**
     * Lifts this partial function into a total function that returns an {@code Optional} result.
     *
     * @return a function that applies arguments to this function and returns {@code Optional.of(result)}
     *         if the function is defined for the given arguments, and {@code Optional.empty()} otherwise.
     */
    default Function<T, Optional<R>> lift() {
        return t -> isDefinedAt(t)?Optional.of(apply(t)):Optional.empty();
    }
}
