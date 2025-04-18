package novy.cell;

import novy.util.Fn;

public class Mutex<T> {
    T inner;

    private Mutex(T inner) {
        this.inner = inner;
    }

    public static <T> Mutex<T> create(T inner) {
        return new Mutex<>(inner);
    }

    public Mutex<T> map(Fn.F1<T, T> function) {
        synchronized (this) {
            this.inner = function.apply(this.inner);
        }
        return this;
    }

    public Mutex<T> set(T value) {
        synchronized (this) {
            this.inner = value;
        }
        return this;
    }

    public T get() {
        synchronized (this) {
            return this.inner;
        }
    }
}
