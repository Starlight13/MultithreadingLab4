package stat;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class Statistic {

    public static final int WORDS_PER_TASK = 30000;

    private List<String> words;
    private ForkJoinPool forkJoinPool;

    public Statistic(File file) {
        forkJoinPool = ForkJoinPool.commonPool();
        getDataFromFile(file);
    }

    public void calculateOneThread() {
        long start = System.currentTimeMillis();

        System.out.println("Words: " + words.size());

        int lettersInText = words.stream().mapToInt(String::length).sum();
        double avgLettersPerWord = (double) lettersInText / words.size();

        System.out.println("Letters: " + lettersInText);
        System.out.println("Avg letters/word: " + avgLettersPerWord);

        double deviation = Math.sqrt(words.stream().mapToDouble(word -> Math.pow(word.length() - avgLettersPerWord, 2)).sum() / words.size());

        System.out.println("St. deviation: " + deviation);

        HistogramTable histogramTable = new HistogramTable();
        words.forEach(word -> {
            char[] letters = word.toLowerCase().toCharArray();
            for (int i = 0; i < letters.length; i++) {
                histogramTable.add(letters[i]);
            }
        });

        System.out.println(histogramTable);

        long finish = System.currentTimeMillis();
        double t = (finish - start) / 1000.0;
        System.out.println("One thread time: " + t + " sec.\n\n\n");
    }

    public void calculateMultiThreads() {
        long start = System.currentTimeMillis();

        System.out.println("Words: " + words.size());

        LetterCountTask countTask = new LetterCountTask(words);
        int lettersInText = forkJoinPool.invoke(countTask);
        double middleLettersPerWord = (double) lettersInText / words.size();

        System.out.println("Letters: " + lettersInText);
        System.out.println("Avg letters/word: " + middleLettersPerWord);

        StDeviationTask middleCurvatureDeviationTask = new StDeviationTask(words, middleLettersPerWord);
        double deviation = Math.sqrt(forkJoinPool.invoke(middleCurvatureDeviationTask) / words.size());

        System.out.println("St. deviation: " + deviation);

        HistogramTask histogramTask = new HistogramTask(words);
        HistogramTable histogramTable = forkJoinPool.invoke(histogramTask);

        System.out.println(histogramTable);

        long finish = System.currentTimeMillis();
        double t = (finish - start) / 1000.0;
        System.out.println("Multiple threads time: " + t + " sec.\n\n\n");
    }

    private void getDataFromFile(File file) {
        this.words = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();

            while (line != null) {
                words.addAll(Arrays.stream(line.split(" ")).map(world -> world.replaceAll("\\W", "")).collect(Collectors.toList()));
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("No such file...");
            System.exit(0);
        }
    }
}
