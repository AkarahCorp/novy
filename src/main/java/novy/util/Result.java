package novy.util;

public sealed interface Result<T, E extends Exception> {
    record Ok<T, E extends Exception>(T value) implements Result<T, E> {}
    record Err<T, E extends Exception>(E value) implements Result<T, E> {}

    static <T, E extends Exception> Result<T, E> ok(T value) {
        return new Ok<>(value);
    }

    static <T, E extends Exception> Result<T, E> err(E value) {
        return new Err<>(value);
    }

    static <T> Result<T, Exception> tryFunction(Fn.F0<T> supplier) {
        try {
            return Result.ok(supplier.apply());
        } catch (Exception e) {
            return Result.err(e);
        }
    }

    default <U> Result<U, E> map(Fn.F1<T, U> function) {
        return switch (this) {
            case Ok(T value) -> Result.ok(function.apply(value));
            case Err(E value) -> Result.err(value);
        };
    }

    default <F extends Exception> Result<T, F> mapErr(Fn.F1<E, F> function) {
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

    default Option<T> ok() {
        return switch(this) {
            case Ok(T value) -> Option.of(value);
            case Err(E value) -> Option.empty();
        };
    }
}
