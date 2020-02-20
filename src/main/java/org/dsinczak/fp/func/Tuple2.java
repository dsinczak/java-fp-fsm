package org.dsinczak.fp.func;

import java.util.Objects;

// I still don't understand why Java does not provide Tuples
public class Tuple2<A, B> {
    private A a;
    private B b;

    public static <A,B> Tuple2<A,B> of(A a, B b) {
        return new Tuple2<>(a, b);
    }

    public Tuple2(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple2)) return false;
        Tuple2<?, ?> tuple2 = (Tuple2<?, ?>) o;
        return Objects.equals(getA(), tuple2.getA()) &&
                Objects.equals(getB(), tuple2.getB());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getA(), getB());
    }

    @Override
    public String toString() {
        return "Tuple2{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }
}
