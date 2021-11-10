package stat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class LetterCountTask extends RecursiveTask<Integer> {

    private List<String> words;

    public LetterCountTask(List<String> words) {
        this.words = words;
    }

    @Override
    protected Integer compute() {
        if (words.size() > Statistic.WORDS_PER_TASK) {
            return LetterCountTask.invokeAll(createSubTasks())
                    .stream()
                    .mapToInt(ForkJoinTask::join)
                    .sum();
        } else {
            return processing(words);
        }
    }

    private Collection<LetterCountTask> createSubTasks() {
        List<LetterCountTask> dividedTasks = new ArrayList<>();

        dividedTasks.add(new LetterCountTask(words.subList(0, words.size() / 2)));
        dividedTasks.add(new LetterCountTask(words.subList(words.size() / 2, words.size())));

        return dividedTasks;
    }

    private Integer processing(List<String> words) {
        return words.stream().mapToInt(String::length).sum();
    }
}
