package novy.cell;

import novy.util.Fn;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mutex<T> {
    T inner;
    Lock lock = new ReentrantLock();

    private Mutex(T inner) {
        this.inner = inner;
    }

    public static <T> Mutex<T> create(T inner) {
        return new Mutex<>(inner);
    }

    public Mutex<T> map(Fn.F1<T, T> function) {
        this.lock.lock();
        try {
            this.inner = function.apply(this.inner);
        } finally {
            this.lock.unlock();
        }
        return this;
    }

    public Mutex<T> set(T value) {
        this.lock.lock();
        this.inner = value;
        this.lock.unlock();
        return this;
    }

    public T get() {
        this.lock.lock();
        var t = this.inner;
        this.lock.unlock();
        return t;
    }
}
