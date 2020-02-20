package org.dsinczak.fp.func;


public abstract class PartialFunctions {
    private PartialFunctions() {
    }

    public static <T, R> PartialFunction<T,R> empty() {
        return new PartialFunction<>() {
            @Override
            public R apply(T t) {
                throw new IllegalStateException("This function has no domain. It cannot be applied to "+t);
            }

            @Override
            public boolean isDefinedAt(T value) {
                return false;
            }
        };
    }
}
