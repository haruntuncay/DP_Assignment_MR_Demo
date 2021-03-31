package framework;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Task<I, K extends Comparable<K>, V> {

    private Supplier<I> supplier;
    private Mapper<I, K, V> mapper;
    private Combiner<K, V> combiner;
    private Reducer<K, V> reducer;
    private Function<I, KeyValue<K, V>> mapperFunction;
    private Function<Intermediary<K, V>, KeyValue<K, V>> reducerFunction;
    private Consumer<KeyValue<K, V>> resultConsumer;

    private Task() {}

    public void execute() {
        if(supplier == null)
            throw new RuntimeException("supplier can't be null");
        if(mapperFunction == null)
            throw new RuntimeException("mapper can't be null");
        if(reducerFunction == null)
            throw new RuntimeException("reducer can't be null");

        this.mapper = new Mapper<>(mapperFunction);
        this.combiner = new Combiner<>(reducerFunction);
        this.reducer = new Reducer<>(reducerFunction);

        List<KeyValue<K, V>> mapperOutput = mapper.map(supplier);
        mapperOutput.sort(Comparator.comparing(KeyValue::getKey));

        List<KeyValue<K, V>> combinerOutput = combiner.combine(mapperOutput);
        combinerOutput.sort(Comparator.comparing(KeyValue::getKey));

        List<KeyValue<K, V>> reducerOutput = reducer.reduce(combinerOutput);
        reducerOutput.sort(Comparator.comparing(KeyValue::getKey));

        for(KeyValue<K, V> result : reducerOutput) {
            resultConsumer.accept(result);
        }
    }

    public static class Builder<I, K extends Comparable<K>, V> {

        private final Task<I, K, V> task;

        public Builder() {
            this.task = new Task<>();
        }

        public Builder<I, K, V> supply(Supplier<I> supplier) {
            task.supplier = supplier;
            return this;
        }

        public Builder<I, K, V> map(Function<I, KeyValue<K, V>> mapperFunction) {
            task.mapperFunction = mapperFunction;
            return this;
        }

        public Builder<I, K, V> reduce(Function<Intermediary<K, V>, KeyValue<K, V>> reducerFunction) {
            task.reducerFunction = reducerFunction;
            return this;
        }

        public Builder<I, K, V> consume(Consumer<KeyValue<K, V>> resultConsumer) {
            task.resultConsumer = resultConsumer;
            return this;
        }

        public Task<I, K, V> build() {
            return task;
        }
    }
}
