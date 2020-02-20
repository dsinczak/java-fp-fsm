package org.dsinczak.fp.pf;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Builder for {@link PartialFunction}, the whole idea is to mimic the behaviour of pattern
 * matching based on types:
 * <pre>
 *     obj match {
 *         case str:String => println("obj is string")
 *         case data:Date  => println("obj is date")
 *     }
 * </pre>
 * This class is basically a clone of akka.japi.pf.PFBuilder
 *
 * @param <T> PF argument type
 * @param <R> PF return type
 */
public class PartialFunctionBuilder<T, R> {

    protected PartialFunction<T, R> pf = null;

    public PartialFunctionBuilder() {
    }

    public <P> PartialFunctionBuilder<T, R> match(final Class<P> type, final Function<P, R> apply) {
        return matchUnchecked(type, apply);
    }

    public <P> PartialFunctionBuilder<T, R> match(final Class<P> type, final Predicate<P> predicate, final Function<P, R> apply) {
        return matchUnchecked(type, predicate, apply);
    }

    public <P> PartialFunctionBuilder<T, R> matchEquals(final P other, final Function<P, R> apply) {
        append(new CaseStatement<T,P,R>(o->o.equals(other), apply));
        return this;
    }

    public <P> PartialFunctionBuilder<T, R> matchAny(final Function<P, R> apply) {
        append(new CaseStatement<T,P,R>(o->true, apply));
        return this;
    }

    public PartialFunction<T, R> build() {
        PartialFunction<T, R> empty = PartialFunctions.empty();
        if (this.pf == null) return empty;
            // This way we always return PF that in the end has no domain
        else return new OrElsePartialFunction<>(pf, empty);
    }

    @SuppressWarnings("unchecked")
    PartialFunctionBuilder<T, R> matchUnchecked(final Class<?> type, final Function<?, R> apply) {
        Predicate<Object> predicate = type::isInstance;
        append(new CaseStatement<T, Object, R>(predicate, (Function<Object, R>) apply));
        return this;
    }

    @SuppressWarnings("unchecked")
    PartialFunctionBuilder<T, R> matchUnchecked(final Class<?> type, final Predicate<?> predicate, final Function<?, R> apply) {
        Predicate<Object> p = o -> {
                    if (!type.isInstance(o)) return false;
                    else return ((Predicate<Object>) predicate).test(o);
                };
        append(new CaseStatement<T, Object, R>(p, (Function<Object, R>) apply));
        return this;
    }

    void append(PartialFunction<T, R> another) {
        if (pf == null) pf = another;
        else pf = new OrElsePartialFunction<>(pf, another);
    }

}
