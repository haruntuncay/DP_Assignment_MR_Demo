package framework;

import java.util.function.Consumer;

public class Reducer<K, V> {

    private final Consumer<Intermediary<K, V>> consumer;

    public Reducer(Consumer<Intermediary<K, V>> consumer) {
        this.consumer = consumer;
    }

    void reduce(Intermediary<K, V> intermediary){
        consumer.accept(intermediary);
    }
}
