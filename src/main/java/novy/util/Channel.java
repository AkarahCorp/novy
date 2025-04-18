package novy.util;

import java.util.ArrayDeque;
import java.util.concurrent.locks.ReentrantLock;

public class Channel<T> {
    ArrayDeque<T> data;
    ReentrantLock lock;

    private Channel(ArrayDeque<T> data, ReentrantLock lock) {
        this.data = data;
        this.lock = lock;
    }

    public void send(T value) {
        this.lock.lock();
        try {
            this.data.push(value);
        } finally {
            this.lock.unlock();
        }
    }

    public Option<T> tryReceive() {
        this.lock.lock();
        try {
            return Option.of(this.data.removeFirst());
        } catch(Exception e) {
            return Option.empty();
        } finally {
            this.lock.unlock();
        }
    }

    public T receive() {
        while(true) {
            switch (this.tryReceive()) {
                case Option.Some(T value) -> {
                    return value;
                }
                case Option.None() -> Thread.yield();
            }
        }
    }

    public static <T> Channel<T> create() {
        return new Channel<>(new ArrayDeque<>(), new ReentrantLock());
    }
}
