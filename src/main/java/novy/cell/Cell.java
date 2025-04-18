package novy.cell;

import novy.util.Fn;

public class Cell<T> {
    T inner;

    private Cell(T value) {
        this.inner = value;
    }

    public static <T> Cell<T> create(T value) {
        return new Cell<>(value);
    }

    public T get() {
        return this.inner;
    }

    public Cell<T> map(Fn.F1<T, T> function) {
        this.inner = function.apply(this.inner);
        return this;
    }

    public Cell<T> set(T value) {
        this.inner = value;
        return this;
    }
}
