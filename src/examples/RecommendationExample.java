package examples;

import framework.*;

import java.io.*;
import java.util.Random;

public class RecommendationExample {

    private BufferedReader reader;
    private BufferedWriter writer;

    public void run() {
        try {
            reader = new BufferedReader(new FileReader(new File("recommendation_example.txt")));
            writer = new BufferedWriter(new FileWriter(new File("recommendation_output.txt")));
        } catch(IOException ex) {
            ex.printStackTrace();
            return;
        }

        Task<String, Character, Character> recommendationTask =
                new Task.Builder<String, Character, Character>()
                    .supply(this::get)
                    .map(new Mapper<>(this::mapper))
                    .reduce(new Reducer<>(this::reducer))
                    .build();

        recommendationTask.execute();

        try {
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KeyValue<Character, Character> mapper(String input) {
        String[] parts = input.split("_");
        return new KeyValue<>(parts[0].charAt(0), parts[1].charAt(0));
    }

    public void reducer(Intermediary<Character, Character> intermediary) {
        try {
            String format = "%s alanlar %s de alıyor !\n";

            int[] freq = new int[26];
            int maxCount = 0, mostFreqIdx = 0;

            for(Character val : intermediary.getValueList()) {
                int idx = val - 'a';

                freq[idx]++;
                if(freq[idx] > maxCount) {
                    maxCount = freq[idx];
                    mostFreqIdx = idx;
                }
            }

            writer.write(String.format(format, intermediary.getKey(), (char) (mostFreqIdx + 'a')));
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    // Supplier
    public String get() {
        try {
            return reader.readLine();
        } catch(IOException ex) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static void prepareRecommendationExample(int lineCount, int sourceSize, int recoSize) {
        if(sourceSize < 1 || sourceSize > 26)
            sourceSize = 26;
        if(recoSize < 1 || recoSize > 26)
            recoSize = 26;

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(new File("recommendation_example.txt")))) {
            Random rnd = new Random();

            for(int i = 0; i < lineCount; i++) {
                char ch_left = (char) (rnd.nextInt(sourceSize) + 'A');
                char ch_right = (char) (rnd.nextInt(recoSize) + 'a');

                writer.write(ch_left + "_" + ch_right + "\n");
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
