package framework;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Mapper<I, K, V> {

    private final Function<I, KeyValue<K, V>> function;
    private List<KeyValue<K, V>> keyValueList;

    public Mapper(Function<I, KeyValue<K, V>> function) {
        this.function = function;
        this.keyValueList = new ArrayList<>();
    }

    void map(I input) {
        keyValueList.add(function.apply(input));
    }

    List<KeyValue<K, V>> getKeyValueList() {
        return keyValueList;
    }
}
