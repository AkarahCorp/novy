package novy.util;

import java.util.function.Function;
import java.util.function.Supplier;

public sealed interface Result<T, E extends Exception> {
    record Ok<T, E extends Exception>(T value) implements Result<T, E> {}
    record Err<T, E extends Exception>(E value) implements Result<T, E> {}

    static <T, E extends Exception> Result<T, E> ok(T value) {
        return new Ok<>(value);
    }

    static <T, E extends Exception> Result<T, E> err(E value) {
        return new Err<>(value);
    }

    static <T> Result<T, Exception> tryFunction(Supplier<T> supplier) {
        try {
            return Result.ok(supplier.get());
        } catch (Exception e) {
            return Result.err(e);
        }
    }

    default <U> Result<U, E> map(Function<T, U> function) {
        return switch (this) {
            case Ok(T value) -> Result.ok(function.apply(value));
            case Err(E value) -> Result.err(value);
        };
    }

    default <F extends Exception> Result<T, F> mapErr(Function<E, F> function) {
        return switch (this) {
            case Ok(T value) -> Result.ok(value);
            case Err(E value) -> Result.err(function.apply(value));
        };
    }

    default T getOrThrow() {
        return switch(this) {
            case Ok(T value) -> value;
            case Err(E value) -> throw new RuntimeException(value);
        };
    }
}
