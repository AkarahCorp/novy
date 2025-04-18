package novy.util;

public interface Tuple {
    record Of2<A, B>(A a, B b) {
        public <T> T unpack(Fn.F2<A, B, T> constructor) {
            return constructor.apply(a, b);
        }

        public <Z> Tuple.Of3<A, B, Z> extend(Z extension) {
            return new Tuple.Of3<>(a, b, extension);
        }
    }

    record Of3<A, B, C>(A a, B b, C c) {
        public <T> T unpack(Fn.F3<A, B, C, T> constructor) {
            return constructor.apply(a, b, c);
        }

        public <Z> Tuple.Of4<A, B, C, Z> extend(Z extension) {
            return new Tuple.Of4<>(a, b, c, extension);
        }
    }

    record Of4<A, B, C, D>(A a, B b, C c, D d) {
        public <T> T unpack(Fn.F4<A, B, C, D, T> constructor) {
            return constructor.apply(a, b, c, d);
        }

        public <Z> Tuple.Of5<A, B, C, D, Z> extend(Z extension) {
            return new Tuple.Of5<>(a, b, c, d, extension);
        }
    }

    record Of5<A, B, C, D, E>(A a, B b, C c, D d, E e) {
        public <T> T unpack(Fn.F5<A, B, C, D, E, T> constructor) {
            return constructor.apply(a, b, c, d, e);
        }
    }

    static <A, B> Tuple.Of2<A, B> of(A a, B b) {
        return new Tuple.Of2<>(a, b);
    }

    static <A, B, C> Tuple.Of3<A, B, C> of(A a, B b, C c) {
        return new Tuple.Of3<>(a, b, c);
    }

    static <A, B, C, D> Tuple.Of4<A, B, C, D> of(A a, B b, C c, D d) {
        return new Tuple.Of4<>(a, b, c, d);
    }

    static <A, B, C, D, E> Tuple.Of5<A, B, C, D, E> of(A a, B b, C c, D d, E e) {
        return new Tuple.Of5<>(a, b, c, d, e);
    }
}
