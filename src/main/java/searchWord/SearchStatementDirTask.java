package searchWord;

import sameWords.Line;
import sameWords.Occurrence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SearchStatementDirTask extends RecursiveTask<OccurrenceTable> {

    private File currentFile;
    private ForkJoinPool forkJoinPool;

    public SearchStatementDirTask(File currentFile) {
        this.currentFile = currentFile;
    }

    @Override
    protected OccurrenceTable compute() {
        if (currentFile.isDirectory()) {
            List<SearchStatementDirTask> list = new ArrayList<>(SearchStatementDirTask.invokeAll(createSubTasks()));
            OccurrenceTable table = list.get(0).join();
            for (int i = 1; i < list.size(); i++) {
                table.merge(list.get(i).join());
            }
            return table;
        } else {
            return calculate();
        }
    }

    private Collection<SearchStatementDirTask> createSubTasks() {
        List<SearchStatementDirTask> tasks = new ArrayList<>();
        File[] files = currentFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            tasks.add(new SearchStatementDirTask(files[i]));
        }
        return tasks;
    }

    private OccurrenceTable calculate() {
        List<Line> lines = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
            String line = reader.readLine();
            int numberOfLine = 1;

            while (line != null) {
                lines.add(new Line(line, numberOfLine++));
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("EXCEPTION!!!  No file on path: " + currentFile.getAbsolutePath());
            return new OccurrenceTable();
        }
        this.forkJoinPool = ForkJoinPool.commonPool();

        return forkJoinPool.invoke(new SearchStatementDocTask(lines, currentFile.getName()));
    }
}
