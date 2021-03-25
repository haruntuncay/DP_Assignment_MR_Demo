package framework;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class Task<I, K extends Comparable<K>, V> {

    private Supplier<I> supplier;
    private Mapper<I, K, V> mapper;
    private Reducer<K, V> reducer;

    private Task() {}

    public void execute() {
        if(supplier == null)
            throw new RuntimeException("supplier can't be null");
        if(mapper == null)
            throw new RuntimeException("mapper can't be null");
        if(reducer == null)
            throw new RuntimeException("reducer can't be null");

        I input;
        while((input = supplier.get()) != null) {
            mapper.map(input);
        }

        Combiner<K, V> combiner = new Combiner<>();
        combiner.combine(mapper.getKeyValueList());

        List<Intermediary<K, V>> intermediaries = combiner.getKeysToValuesList();
        Collections.sort(intermediaries);

        reducer.reduce(combiner.getKeysToValuesList());
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

        public Builder<I, K, V> map(Mapper<I, K, V> mapper) {
            task.mapper = mapper;
            return this;
        }

        public Builder<I, K, V> reduce(Reducer<K, V> reducer) {
            task.reducer = reducer;
            return this;
        }

        public Task<I, K, V> build() {
            return task;
        }
    }
}
