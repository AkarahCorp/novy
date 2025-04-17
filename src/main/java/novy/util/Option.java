package novy.util;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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

    default <U> Option<U> map(Function<T, U> function) {
        return switch(this) {
            case Some<T>(T value) -> Option.of(function.apply(value));
            case None<T> ignored -> Option.empty();
        };
    }

    default <U> Option<U> and(Supplier<Option<U>> other) {
        return switch (this) {
            case Some<T>(T value) -> other.get();
            case None<T> ignored -> Option.empty();
        };
    }

    default <U> Option<U> and(Option<U> other) {
        return switch (this) {
            case Some<T>(T value) -> other;
            case None<T> ignored -> Option.empty();
        };
    }

    default <U> Option<U> flatMap(Function<T, Option<U>> function) {
        return switch (this) {
            case Some<T>(T value) -> function.apply(value);
            case None<T> ignored -> Option.empty();
        };
    }

    default Option<T> filter(Predicate<T> predicate) {
        return switch (this) {
            case Some<T>(T value) -> {
                if(!predicate.test(value)) {
                    yield Option.empty();
                }
                yield this;
            }
            case None<T> value -> value;
        };
    }

    default Option<T> or(Supplier<Option<T>> other) {
        return switch (this) {
            case Some<T>(T value) -> Option.of(value);
            case None<T> ignored -> other.get();
        };
    }

    default Option<T> or(Option<T> other) {
        return switch (this) {
            case Some<T>(T value) -> Option.of(value);
            case None<T> ignored -> other;
        };
    }
}
