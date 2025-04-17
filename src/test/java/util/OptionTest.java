package util;

import novy.util.Option;
import org.junit.Test;

import java.util.NoSuchElementException;

public class OptionTest {
    @Test
    public void unwrapSome() {
        var opt = Option.of(10);
        assert opt.orElseThrow() == 10;
    }

    @Test(expected = NoSuchElementException.class)
    public void unwrapNone() {
        var opt = Option.empty();
        opt.orElseThrow();
    }

    @Test
    public void mapOption() {
        var opt = Option.of(10);
        opt = opt.map(x -> x * 2);
        assert opt instanceof Option.Some<Integer>;
        assert opt.orElseThrow() == 20;
    }

    @Test
    public void mapNone() {
        var opt = Option.<Integer>empty();
        opt = opt.map(x -> x * 2);
        assert opt instanceof Option.None<Integer>;
    }
}
