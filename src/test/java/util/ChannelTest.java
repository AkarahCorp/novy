package util;

import novy.util.Channel;
import org.junit.Test;

public class ChannelTest {
    @Test
    public void crossThread() throws InterruptedException {
        var channel = Channel.<Integer>create();

        var t1 = Thread.ofVirtual().start(() -> channel.send(10));

        var t2 = Thread.ofVirtual().start(() -> {
            assert channel.receive() == 10;
        });

        t1.join();
        t2.join();
    }
}
