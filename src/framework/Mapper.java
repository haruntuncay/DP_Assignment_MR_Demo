package framework;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class Mapper<I, K extends Comparable<K>, V> {

    private Function<I, KeyValue<K, V>> mapperFunction;

    public Mapper(Function<I, KeyValue<K, V>> mapperFunction) {
        this.mapperFunction = mapperFunction;
    }

    List<KeyValue<K, V>> map(Supplier<I> inputSupplier)  {
        List<KeyValue<K, V>> keyValueList = new ArrayList<>();

        I input;
        while((input = inputSupplier.get()) != null) {
            keyValueList.add(mapperFunction.apply(input));
        }

        return keyValueList;
    }
}
