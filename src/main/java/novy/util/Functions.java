package novy.util;

public interface Functions {
    @FunctionalInterface
    interface F1To0<T1> {
        void apply(T1 t1);
    }

    @FunctionalInterface
    interface F0To1<O1> {
        O1 apply();
    }

    @FunctionalInterface
    interface F1To1<T1, O1> {
        O1 apply(T1 t1);
    }

    @FunctionalInterface
    interface F2To1<T1, T2, O1> {
        O1 apply(T1 t1, T2 t2);
    }
}
