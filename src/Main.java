import examples.RecommendationExample;
import examples.WordCountExample;

public class Main {

    public static void main(String[] args) {
        // This example uses the file "recommendation_example.txt" as its input
        // and finds out which items are usually bought together.
        // Item pairs are given in form A_b and the output is written to "recommendation_output.txt".
        new RecommendationExample().run();

        // Classical word count example that uses "word_count_example.txt" as its input
        // and returns the frequency of each items. Writes its output to "word_count_output.txt".
        new WordCountExample().run();
    }
}
