# DP_Assignment_MR_Demo

This project's goal is to simulate a really simple map-reduce framework. It's main entry point is the `Task` class, which can be obtained using a `TaskBuilder`.
A simple example of how TaskBuilder can be used is provided below.

```
// Task <I, K, V>
// I -> type parameter of the input that will be passed to mapper
// K -> type parameter of the Key that mapper will produce
// V -> type parameter of the Value that mapper will produce

Task<String, Character, Character> recommendationTask =
          new Task.Builder<String, Character, Character>()
              .supply(Supplier<I>)
              .map(Mapper<I, K, V>)
              .reduce(Reducer<K, V>)
              .build();

      recommendationTask.execute();
 ```
 
 Two simple examples, one simple `RecommendationTask` and one simple `WordCount` task, is included in the `src/examples` directory.
 
 `RecommendationTask` takes `recommendation_example.txt` as input, which provides pairs in form `A_b` that designates a person who bought item `A` had also bought item `b`.
 As a result, it outputs which items are frequently bought together. The output is written to `recommendation_output.txt` file.
 
 `WordCount` example does what its name suggests. Given `word_count_example.txt` as input, returns the number of times each word occurs.
 The output is written to `word_count_output.txt` file.
