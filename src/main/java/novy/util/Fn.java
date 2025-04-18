package novy.util;

public interface Fn {
    record NoResult() {}

    static NoResult nr() {
        return new NoResult();
    }

    @FunctionalInterface
    interface F0<O> {
        O apply();

        default <P> F0<P> compose(F1<O, P> f) {
            return () -> f.apply(this.apply());
        }
    }

    @FunctionalInterface
    interface F1<T1, O> {
        O apply(T1 t1);

        default <P> F1<T1, P> compose(F1<O, P> f) {
            return (t1) -> f.apply(this.apply(t1));
        }
    }

    @FunctionalInterface
    interface F2<T1, T2, O> {
        O apply(T1 t1, T2 t2);

        default F1<T2, O> apply(T1 t1) {
            var subject = this;
            return t2 -> subject.apply(t1, t2);
        }

        default <P> F2<T1, T2, P> compose(F1<O, P> f) {
            return (t1, t2) -> f.apply(this.apply(t1, t2));
        }
    }

    @FunctionalInterface
    interface F3<T1, T2, T3, O> {
        O apply(T1 t1, T2 t2, T3 t3);

        default F2<T2, T3, O> apply(T1 t1) {
            var subject = this;
            return (t2, t3) -> subject.apply(t1, t2, t3);
        }

        default <P> F3<T1, T2, T3, P> compose(F1<O, P> f) {
            return (t1, t2, t3) -> f.apply(this.apply(t1, t2, t3));
        }
    }

    @FunctionalInterface
    interface F4<T1, T2, T3, T4, O> {
        O apply(T1 t1, T2 t2, T3 t3, T4 t4);

        default F3<T2, T3, T4, O> apply(T1 t1) {
            var subject = this;
            return (t2, t3, t4) -> subject.apply(t1, t2, t3, t4);
        }

        default <P> F4<T1, T2, T3, T4, P> compose(F1<O, P> f) {
            return (t1, t2, t3, t4) -> f.apply(this.apply(t1, t2, t3, t4));
        }
    }

    @FunctionalInterface
    interface F5<T1, T2, T3, T4, T5, O> {
        O apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);

        default F4<T2, T3, T4, T5, O> apply(T1 t1) {
            var subject = this;
            return (t2, t3, t4, t5) -> subject.apply(t1, t2, t3, t4, t5);
        }

        default <P> F5<T1, T2, T3, T4, T5, P> compose(F1<O, P> f) {
            return (t1, t2, t3, t4, t5) -> f.apply(this.apply(t1, t2, t3, t4, t5));
        }
    }
}
