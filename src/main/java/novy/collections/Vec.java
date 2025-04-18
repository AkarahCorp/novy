package novy.collections;

import novy.util.Fn;
import novy.util.Option;

import java.util.ArrayList;

public final class Vec<T> {
    ArrayList<T> inner = new ArrayList<>();

    private Vec() {}

    @SafeVarargs
    public static <T> Vec<T> create(T... arguments) {
        var vec = new Vec<T>();
        vec.inner.ensureCapacity(arguments.length);
        for(var arg : arguments) {
            vec.push(arg);
        }
        return vec;
    }

    public int len() {
        return this.inner.size();
    }

    public Vec<T> push(T value) {
        this.inner.add(value);
        return this;
    }

    public Option<T> pop() {
        if(this.len() == 0) {
            return Option.empty();
        }
        return Option.of(this.inner.removeLast());
    }

    public Option<T> get(int index) {
        if(index >= this.len() || index < 0) {
            return Option.empty();
        }
        return Option.of(this.inner.get(index));
    }

    public Vec<T> insert(int index, T value) {
        this.inner.add(index, value);
        return this;
    }

    public Option<T> remove(int index) {
        if(index >= this.len() || index < 0) {
            return Option.empty();
        }
        return Option.of(this.inner.remove(index));
    }

    public Vec<T> reversed() {
        var vec = Vec.<T>create();
        vec.inner.ensureCapacity(this.len());
        for(int i = this.len() - 1; i >= 0; i--) {
            vec.push(this.inner.get(i));
        }
        return vec;
    }

    public void forEach(Fn.F1<T, Fn.NoResult> function) {
        for(var element : this.inner) {
            function.apply(element);
        }
    }
}
