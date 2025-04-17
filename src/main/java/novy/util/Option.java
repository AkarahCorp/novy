package novy.util;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

public sealed interface Option<T> {
    record Some<T>(T value) implements Option<T> { }
    record None<T>() implements Option<T> { }

    static <T> Option<T> of(T value) {
        return new Some<>(value);
    }

    static <T> Option<T> empty() {
        return new None<>();
    }

    default T orElseThrow() {
        return switch (this) {
            case Option.Some<T>(var value) -> value;
            case Option.None<T> ignored -> throw new NoSuchElementException();
        };
    }

    default Option<T> inspect(Consumer<T> consumer) {
        switch (this) {
            case Option.Some<T>(var value) -> consumer.accept(value);
            case Option.None<T> ignored -> {}
        }
        return this;
    }

    default <U> Option<U> map(Functions.F1To1<T, U> function) {
        return switch(this) {
            case Some<T>(T value) -> Option.of(function.apply(value));
            case None<T> ignored -> Option.empty();
        };
    }

    default <U> Option<U> and(Functions.F0To1<Option<U>> other) {
        return switch (this) {
            case Some<T>(T ignored) -> other.apply();
            case None<T> ignored -> Option.empty();
        };
    }

    default <U> Option<U> and(Option<U> other) {
        return switch (this) {
            case Some<T>(T ignored) -> other;
            case None<T> ignored -> Option.empty();
        };
    }

    default <U> Option<U> flatMap(Functions.F1To1<T, Option<U>> function) {
        return switch (this) {
            case Some<T>(T value) -> function.apply(value);
            case None<T> ignored -> Option.empty();
        };
    }

    default Option<T> filter(Functions.F1To1<T, Boolean> predicate) {
        return switch (this) {
            case Some<T>(T value) -> {
                if(!predicate.apply(value)) {
                    yield Option.empty();
                }
                yield this;
            }
            case None<T> value -> value;
        };
    }

    default Option<T> or(Functions.F0To1<Option<T>> other) {
        return switch (this) {
            case Some<T>(T value) -> Option.of(value);
            case None<T> ignored -> other.apply();
        };
    }

    default Option<T> or(Option<T> other) {
        return switch (this) {
            case Some<T>(T value) -> Option.of(value);
            case None<T> ignored -> other;
        };
    }

    default <E extends Exception> Result<T, E> okOr(E e) {
        return switch(this) {
            case Some<T>(T value) -> Result.ok(value);
            case None<T> ignored -> Result.err(e);
        };
    }
}
