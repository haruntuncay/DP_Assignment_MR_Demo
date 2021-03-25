package framework;

import java.util.List;
import java.util.function.Consumer;

public class Reducer<K extends Comparable<K>, V> {

    private final Consumer<Intermediary<K, V>> consumer;

    public Reducer(Consumer<Intermediary<K, V>> consumer) {
        this.consumer = consumer;
    }

    void reduce(List<Intermediary<K, V>> intermediaryValues){
        for(Intermediary<K, V> intermediary : intermediaryValues)
            consumer.accept(intermediary);
    }
}
