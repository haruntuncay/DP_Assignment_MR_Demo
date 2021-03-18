package framework;

import java.util.*;

class Combiner<K, V> implements Iterable<Intermediary<K, V>>{

    private final Map<K, List<V>> table;

    Combiner() {
        this.table = new HashMap<>();
    }

    void combine(KeyValue<K, V> keyValue) {
        K key = keyValue.getKey();

        if(!table.containsKey(key)) {
            table.put(key, new ArrayList<>());
        }

        table.get(key).add(keyValue.getValue());
    }

    @Override
    public Iterator<Intermediary<K, V>> iterator() {
        return new CombinerIterator();
    }

    private class CombinerIterator implements Iterator<Intermediary<K, V>> {

        private final Iterator<Map.Entry<K, List<V>>> tableIterator;

        private CombinerIterator() {
            tableIterator = table.entrySet().iterator();
        }

        @Override
        public boolean hasNext() {
            return tableIterator.hasNext();
        }

        @Override
        public Intermediary<K, V> next() {
            Map.Entry<K, List<V>> mapEntry = tableIterator.next();
            return new Intermediary<>(mapEntry.getKey(), mapEntry.getValue());
        }
    }
}
