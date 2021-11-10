package searchWord;

import sameWords.Line;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class SearchStatementDocTask extends RecursiveTask<OccurrenceTable> {

    private static final int LINES_PER_TASK = 100;
    private List<Line> lines;
    private String currentFileName;

    public SearchStatementDocTask(List<Line> lines, String currentFileName) {
        this.lines = lines;
        this.currentFileName = currentFileName;
    }

    @Override
    protected OccurrenceTable compute() {
        if (lines.size() > LINES_PER_TASK) {
            List<SearchStatementDocTask> list = new ArrayList<>(SearchStatementDocTask.invokeAll(createSubTasks()));
            OccurrenceTable table = list.get(0).join();
            for (int i = 1; i < list.size(); i++) {
                table.merge(list.get(i).join());
            }
            return table;
        } else {
            return calculate();
        }
    }

    private Collection<SearchStatementDocTask> createSubTasks() {
        List<SearchStatementDocTask> list = new ArrayList<>();
        list.add(new SearchStatementDocTask(lines.subList(0, lines.size() / 2), currentFileName));
        list.add(new SearchStatementDocTask(lines.subList(lines.size() / 2, lines.size()), currentFileName));
        return list;
    }

    private OccurrenceTable calculate() {
        OccurrenceTable table = new OccurrenceTable();
        List<String> words = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            List<String> a = Arrays.stream(lines.get(i).getLine().split(" "))
                    .map(el -> el.replaceAll("\\W", ""))
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());
            words.addAll(a);
        }

        for (int i = 0; i < words.size(); i++) {
            for (int l = 0; l < SearchStatement.SEARCHED_STATEMENT.length; l++) {
                if (words.get(i).equals(SearchStatement.SEARCHED_STATEMENT[l])) {
                    table.add(currentFileName, words.get(i));
                }
            }
        }

        return table;
    }
}
