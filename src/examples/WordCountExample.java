package examples;

import framework.*;

import java.io.*;
import java.util.Random;

public class WordCountExample {

    private BufferedReader reader;
    private BufferedWriter writer;

    public void run() {
        try {
            reader = new BufferedReader(new FileReader(new File("word_count_example.txt")));
            writer = new BufferedWriter(new FileWriter(new File("word_count_output.txt")));
        } catch(IOException ex) {
            ex.printStackTrace();
            return;
        }

        Task<String, String, Integer> wordCountTask =
                new Task.Builder<String, String, Integer>()
                        .supply(this::get)
                        .map(new Mapper<>(this::mapper))
                        .reduce(new Reducer<>(this::reducer))
                        .build();

        wordCountTask.execute();

        try {
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KeyValue<String, Integer> mapper(String input) {
        return new KeyValue<>(input, 1);
    }

    public void reducer(Intermediary<String, Integer> intermediary) {
        try {
            String format = "%s -> %d\n";
            int freq = intermediary.getValueList().size();

            writer.write(String.format(format, intermediary.getKey(), freq));
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

    public static void prepareWordCountExample(int lineCount) {
        String[] strings = {"kalem", "silgi", "defter", "kalemlik", "canta", "masa", "sandalye", "televizyon"};

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(new File("word_count_example.txt")))) {
            Random rnd = new Random();

            for(int i = 0; i < lineCount; i++) {
                String el = strings[rnd.nextInt(strings.length)];
                writer.write(el + "\n");
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
