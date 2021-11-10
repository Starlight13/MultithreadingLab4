package stat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class StDeviationTask extends RecursiveTask<Double> {

    private List<String> words;
    private double avgLettersPerWord;

    public StDeviationTask(List<String> words, double avgLettersPerWord) {
        this.words = words;
        this.avgLettersPerWord = avgLettersPerWord;
    }

    @Override
    protected Double compute() {
        if (words.size() > Statistic.WORDS_PER_TASK) {
            return LetterCountTask.invokeAll(createSubTasks())
                    .stream()
                    .mapToDouble(ForkJoinTask::join)
                    .sum();
        } else {
            return processing(words);
        }
    }

    private Collection<StDeviationTask> createSubTasks() {
        List<StDeviationTask> dividedTasks = new ArrayList<>();

        dividedTasks.add(new StDeviationTask(words.subList(0, words.size() / 2), avgLettersPerWord));
        dividedTasks.add(new StDeviationTask(words.subList(words.size() / 2, words.size()), avgLettersPerWord));

        return dividedTasks;
    }

    private Double processing(List<String> words) {
        return words.stream().mapToDouble( word -> Math.pow(word.length() - avgLettersPerWord, 2)).sum();
    }
}
