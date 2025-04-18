package novy.collections;

import novy.util.Fn;
import novy.util.Option;
import novy.util.Tuple;

public final class HashMap<K, V> {
    java.util.HashMap<K, V> inner = new java.util.HashMap<>();

    @SafeVarargs
    public static <K, V> HashMap<K, V> create(Tuple.Of2<K, V>... entries) {
        var hm = new HashMap<K, V>();
        for(var entry : entries) {
            hm.inner.put(entry.a(), entry.b());
        }
        return hm;
    }

    @SafeVarargs
    public final HashMap<K, V> extend(Tuple.Of2<K, V>... entries) {
        for(var entry : entries) {
            this.inner.put(entry.a(), entry.b());
        }
        return this;
    }

    public Option<V> get(K key) {
        if(!this.inner.containsKey(key)) {
            return Option.empty();
        }
        return Option.of(this.inner.get(key));
    }

    public HashMap<K, V> insert(K key, V value) {
        this.inner.put(key, value);
        return this;
    }

    public Option<Tuple.Of2<K, V>> entry(K key) {
        if(!this.inner.containsKey(key)) {
            return Option.empty();
        }
        return Option.of(Tuple.of(key, this.inner.get(key)));
    }

    public Vec<K> keys() {
        var keys = Vec.<K>create();
        for(var key : this.inner.keySet()) {
            keys.push(key);
        }
        return keys;
    }

    public boolean hasKey(K key) {
        return this.inner.containsKey(key);
    }

    public boolean hasValue(V value) {
        return this.inner.containsValue(value);
    }

    public Vec<Tuple.Of2<K, V>> entries() {
        var entries = Vec.<Tuple.Of2<K, V>>create();
        for(var entry : this.inner.entrySet()) {
            entries.push(Tuple.of(entry.getKey(), entry.getValue()));
        }
        return entries;
    }

    public HashMap<K, V> map(K key, Fn.F2<K, V, V> function) {
        if(this.inner.containsKey(key)) {
            this.inner.put(key, function.apply(key, this.inner.get(key)));
        }
        return this;
    }

    public HashMap<K, V> map(Fn.F2<K, V, V> function) {
        this.inner.replaceAll((k, v) -> function.apply(k, this.inner.get(k)));
        return this;
    }
}
