package util;

import novy.collections.Vec;
import novy.util.Fn;
import org.junit.Test;

public class FnTest {
    @Test
    public void testApplications() {
        Fn.F3<Integer, Integer, Integer, Integer> fn = (a, b, c) -> a + b + c;
        assert fn.apply(1).apply(2).apply(3) == 6;
    }

    @Test
    public void noResultTest() {
        var vec = Vec.create(1, 2, 3);
        vec.forEach(x -> Fn.nr());
    }
}
