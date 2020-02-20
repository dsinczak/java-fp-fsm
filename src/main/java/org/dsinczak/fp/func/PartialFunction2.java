package org.dsinczak.fp.func;

import java.util.Optional;
import java.util.function.BiFunction;

public interface PartialFunction2<T, P, R> extends BiFunction<T, P, R> {
    /**
     * Applies this function to the given argument and returns the result.
     *
     * @param t the argument
     * @param p the argument
     * @return the result of function application
     */
    R apply(T t, P p);

    /**
     * Tests if a values are contained in the function's domain.
     *
     * @param t a potential function argument
     * @param p a potential function argument
     * @return true, if the given value is contained in the function's domain, false otherwise
     */
    boolean isDefinedAt(T t, P p);

    /**
     * Lifts this partial function into a total function that returns an {@code Optional} result.
     *
     * @return a function that applies arguments to this function and returns {@code Optional.of(result)}
     * if the function is defined for the given arguments, and {@code Optional.empty()} otherwise.
     */
    default BiFunction<T, P, Optional<R>> lift() {
        return (t, p) -> isDefinedAt(t, p) ? Optional.of(apply(t, p)) : Optional.empty();
    }

    /**
     * Folds partial function from two arguments to one where argument is tuple of
     * arguments.
     *
     * @return tupled partial function
     */
    default PartialFunction<Tuple2<T, P>, R> tupled() {
        return new PartialFunction<>() {
            @Override
            public R apply(Tuple2<T, P> tp) {
                return PartialFunction2.this.apply(tp.getA(), tp.getB());
            }

            @Override
            public boolean isDefinedAt(Tuple2<T, P> tp) {
                return PartialFunction2.this.isDefinedAt(tp.getA(), tp.getB());
            }
        };
    }
}
