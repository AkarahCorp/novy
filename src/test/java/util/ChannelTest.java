package util;

import novy.util.Channel;
import org.junit.Test;

public class ChannelTest {
    @Test
    public void crossThread() throws InterruptedException {
        var channel = Channel.<Integer>create();

        var sender = channel.a();
        var receiver = channel.b();

        var t1 = Thread.ofVirtual().start(() -> {
            sender.send(10);
        });

        var t2 = Thread.ofVirtual().start(() -> {
            var value = receiver.receive();
            assert value == 10;
        });

        t1.join();
        t2.join();
    }
}
