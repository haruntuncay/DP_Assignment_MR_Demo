package framework;

import java.util.*;

class Combiner<K extends Comparable<K>, V> {

    private final Map<K, List<V>> table;
    private final List<Intermediary<K, V>> keysToValuesList;


    Combiner() {
        this.table = new HashMap<>();
        this.keysToValuesList = new ArrayList<>();
    }

    void combine(List<KeyValue<K, V>> keyValueList) {
        for(KeyValue<K, V> keyValue : keyValueList) {
            K key = keyValue.getKey();

            if(!table.containsKey(key)) {
                table.put(key, new ArrayList<>());
            }

            table.get(key).add(keyValue.getValue());
        }

        for(Map.Entry<K, List<V>> entry : table.entrySet()) {
            keysToValuesList.add(new Intermediary<>(entry.getKey(), entry.getValue()));
        }
    }

    List<Intermediary<K, V>> getKeysToValuesList() {
        return keysToValuesList;
    }
}
