package util;

import novy.cell.Mutex;
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
}
