package framework;

import java.util.*;
import java.util.function.Function;

class Combiner<K extends Comparable<K>, V> {

    private Function<Intermediary<K, V>, KeyValue<K, V>> reducerFunction;

    public Combiner(Function<Intermediary<K, V>, KeyValue<K, V>> reducerFunction) {
        this.reducerFunction = reducerFunction;
    }

    List<KeyValue<K, V>> combine(List<KeyValue<K, V>> mapperInput) {
        List<KeyValue<K, V>> keyValueList = new ArrayList<>();

        KeyValue<K, V> keyValue = mapperInput.get(0);
        List<V> values = new ArrayList<>();
        values.add(keyValue.getValue());

        for(int i = 1; i < mapperInput.size(); i++) {
            KeyValue<K, V> keyValueTemp = mapperInput.get(i);
            if(keyValue.getKey().equals(keyValueTemp.getKey())) {
                values.add(keyValueTemp.getValue());
            } else {
                Intermediary<K, V> reducerInput = new Intermediary<>(keyValue.getKey(), new ArrayList<>(values));

                values.clear();
                keyValue = keyValueTemp;
                values.add(keyValue.getValue());

                KeyValue<K, V> output = reducerFunction.apply(reducerInput);
                keyValueList.add(output);
            }
        }

        Intermediary<K, V> reducerInput = new Intermediary<>(keyValue.getKey(), new ArrayList<>(values));
        KeyValue<K, V> output = reducerFunction.apply(reducerInput);
        keyValueList.add(output);

        return keyValueList;
    }
}
