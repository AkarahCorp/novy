package util;

import novy.util.Tuple;
import org.junit.Test;

public class TupleTest {
    @Test
    public void extension() {
        var tuple = Tuple.of(10, 12);
        var extended = tuple.extend(14).extend(16);
        assert extended.equals(Tuple.of(10, 12, 14, 16));
    }

    @Test
    public void unpack() {
        var tuple = Tuple.of(1, 2, 3);
        record Mapped(int a, int b, int c) {}

        var mapped = tuple.unpack(Mapped::new);
        assert mapped.equals(new Mapped(1, 2, 3));
    }
}
