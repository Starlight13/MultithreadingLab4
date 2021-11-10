package sameWords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class SearchSameWordsDocTask extends RecursiveTask<WordOccurrenceTable> {

    private static final int LINES_PER_TASK = 1000;
    private List<Line> lines;
    private String currentFileName;

    public SearchSameWordsDocTask(List<Line> lines, String currentFileName) {
        this.lines = lines;
        this.currentFileName = currentFileName;
    }

    @Override
    protected WordOccurrenceTable compute() {
        if (lines.size() > LINES_PER_TASK) {
            List<SearchSameWordsDocTask> list = new ArrayList<>(SearchSameWordsDocTask.invokeAll(createSubTasks()));
            WordOccurrenceTable table = list.get(0).join();
            for (int i = 1; i < list.size(); i++) {
                table.merge(list.get(i).join());
            }
            return table;
        } else {
            return calculate();
        }
    }

    private Collection<SearchSameWordsDocTask> createSubTasks() {
        List<SearchSameWordsDocTask> list = new ArrayList<>();
        list.add(new SearchSameWordsDocTask(lines.subList(0, lines.size() / 2), currentFileName));
        list.add(new SearchSameWordsDocTask(lines.subList(lines.size() / 2, lines.size()), currentFileName));
        return list;
    }

    private WordOccurrenceTable calculate() {
        WordOccurrenceTable table = new WordOccurrenceTable();

        for (int i = 0; i < lines.size(); i++) {
            int finalI = i;
            Arrays.stream(lines.get(i).getLine().split(" ")).forEach(word -> {
                table.add(word.replaceAll("\\W", "").toLowerCase(), new Occurrence(lines.get(finalI).getLineNumber(), currentFileName));
            });
        }

        return table;
    }
}
