package novy.util;

public interface Tuple {
    record Of2<A, B>(A a, B b) { }
    record Of3<A, B, C>(A a, B b, C c) { }

    static <A, B> Tuple.Of2<A, B> of(A a, B b) {
        return new Tuple.Of2<>(a, b);
    }

    static <A, B, C> Tuple.Of3<A, B, C> of(A a, B b, C c) {
        return new Tuple.Of3<>(a, b, c);
    }
}
