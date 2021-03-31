package framework;

import java.util.List;

public class Intermediary<K extends Comparable<K>, V> implements Comparable<Intermediary<K, V>> {

    private K key;
    private List<V> valueList;

    public Intermediary() { }

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

    @Override
    public int compareTo(Intermediary<K, V> o) {
        return this.key.compareTo(o.key);
    }
}
