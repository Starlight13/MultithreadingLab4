package searchWord;

import sameWords.Line;
import sameWords.WordOccurrenceTable;

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
//            List<WordOccurrence> occurring = list.get(0).join();
//            for (int i = 1; i < list.size(); i++) {
//                occurring.addAll(list.get(i).join());
//            }
//            return occurring;
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



//        List<WordOccurrence> occurring = new ArrayList<>();
//
        OccurrenceTable table = new OccurrenceTable();
        List<String> words = new ArrayList<>();
//        List<SpecialLine> wordsInLine = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            List<String> a = Arrays.stream(lines.get(i).getLine().split(" "))
                    .map(el -> el.replaceAll("\\W", ""))
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());

//            wordsInLine.add(new SpecialLine(lines.get(i).getLineNumber(), a.size()));
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

//
//        WordOccurrence wordOccurring = new WordOccurrence();
//        wordOccurring.setFileName(this.currentFileName);
//        boolean flag = false;
//        for (int i = 0; i < words.size(); i++) {
//            for (int l = 0; l < SearchStatement.SEARCHED_STATEMENT.length; l++) {
//                if (words.get(i).equals(SearchStatement.SEARCHED_STATEMENT[l])) {
//                    if (!flag) {
//                        int sum = 0;
//                        for (int j = 0; j < wordsInLine.size(); j++) {
//                            sum += wordsInLine.get(j).getWordsInLine();
//                            if (i < sum) {
//                                wordOccurring.setLine(wordsInLine.get(j).getRealLine());
//                                break;
//                            }
//                        }
//                    }
//                    wordOccurring.addPercentage(SearchStatement.PERCENTAGES[l]);
//                    if (l + 1 < SearchStatement.SEARCHED_STATEMENT.length && i + 1 < words.size()) {
//                        i++;
//                    }
//                    flag = true;
//                } else {
//                    if (flag) {
//                        occurring.add(wordOccurring);
//                        wordOccurring = new WordOccurrence();
//                        wordOccurring.setFileName(this.currentFileName);
//                        flag = false;
//                        break;
//                    }
//                }
//            }
//            if (flag) {
//                occurring.add(wordOccurring);
//                wordOccurring = new WordOccurrence();
//                wordOccurring.setFileName(this.currentFileName);
//                flag = false;
//            }
//        }
//        return occurring;
    }
}
