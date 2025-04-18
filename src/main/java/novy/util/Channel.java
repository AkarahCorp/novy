package novy.util;

import java.util.ArrayDeque;
import java.util.concurrent.locks.ReentrantLock;

public interface Channel {
    class ChannelShared<T> {
        ArrayDeque<T> data;
        ReentrantLock lock;

        private ChannelShared(ArrayDeque<T> data, ReentrantLock lock) {
            this.data = data;
            this.lock = lock;
        }
    }

    class Sender<T> {
        ChannelShared<T> shared;

        private Sender(ChannelShared<T> shared) {
            this.shared = shared;
        }

        public void send(T value) {
            this.shared.lock.lock();
            try {
                this.shared.data.push(value);
            } finally {
                this.shared.lock.unlock();
            }
        }
    }

    class Receiver<T> {
        ChannelShared<T> shared;

        private Receiver(ChannelShared<T> shared) {
            this.shared = shared;
        }

        public Option<T> tryReceive() {
            this.shared.lock.lock();
            try {
                return Option.of(this.shared.data.removeFirst());
            } catch(Exception e) {
                return Option.empty();
            } finally {
                this.shared.lock.unlock();
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
    }

    static <T> Tuple.Of2<Sender<T>, Receiver<T>> create() {
        var buf = new ChannelShared<T>(new ArrayDeque<>(), new ReentrantLock());
        return Tuple.of(new Sender<>(buf), new Receiver<>(buf));
    }
}
