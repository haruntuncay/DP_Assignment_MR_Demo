package framework;

import java.util.List;

public class Intermediary<K, V> {

    private final K key;
    private final List<V> valueList;

    public Intermediary(K key, List<V> valueList) {
        this.key = key;
        this.valueList = valueList;
    }

    public K getKey() {
        return key;
    }

    public List<V> getValueList() {
        return valueList;
    }
}
