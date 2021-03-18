package framework;

import java.util.function.Supplier;

public class Task<I, K, V> {

    private Supplier<I> supplier;
    private Mapper<I, K, V> mapper;
    private Combiner<K, V> combiner;
    private Reducer<K, V> reducer;

    private Task() {
        this.combiner = new Combiner<>();
    }

    public void execute() {
        if(supplier == null)
            throw new RuntimeException("supplier can't be null");
        if(mapper == null)
            throw new RuntimeException("mapper can't be null");
        if(reducer == null)
            throw new RuntimeException("reducer can't be null");

        I input;
        while((input = supplier.get()) != null) {
            KeyValue<K, V> keyValue = mapper.map(input);
            combiner.combine(keyValue);
        }

        for(Intermediary<K, V> intermediary : combiner) {
            reducer.reduce(intermediary);
        }
    }

    public static class Builder<I, K, V> {

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
