package util;

import novy.cell.Mutex;
import novy.collections.Vec;
import org.junit.Test;

public class MutexTest {
    @Test
    public void mapMutex() {
        var mutex = Mutex.create(10);
        mutex.map(x -> x * 2);
        assert mutex.get() == 20;
    }

    @Test
    public void getMutex() {
        var mutex = Mutex.create(10);
        assert mutex.get() == 10;
    }

    @Test
    public void setMutex() {
        var mutex = Mutex.create(10);
        mutex.set(20);
        assert mutex.get() == 20;
    }

    @Test
    public void testMultithreaded() throws InterruptedException {
        var mutex = Mutex.create(0);
        var handles = Vec.<Thread>create();
        for(int i = 0; i < 200; i++) {
            var handle = Thread.ofVirtual().start(() -> mutex.map(x -> x + 1));
            handles.push(handle);
        }
        for(int i = 0; i < 200; i++) {
            handles.get(i).orElseThrow().join();
        }
        assert mutex.get() == 200;
    }
}
