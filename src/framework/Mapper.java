package framework;

import java.util.function.Function;

public class Mapper<I, K, V> {

    private final Function<I, KeyValue<K, V>> function;

    public Mapper(Function<I, KeyValue<K, V>> function) {
        this.function = function;
    }

    KeyValue<K, V> map(I input) {
        return function.apply(input);
    }
}
