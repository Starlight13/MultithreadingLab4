package sameWords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SearchSameWordsDirTask extends RecursiveTask<WordOccurrenceTable> {

    private File currentFile;
    public ForkJoinPool forkJoinPool;

    public SearchSameWordsDirTask(File currentFile) {
        this.currentFile = currentFile;
    }

    @Override
    protected WordOccurrenceTable compute() {
        if (currentFile.isDirectory()) {
            List<SearchSameWordsDirTask> list = new ArrayList<>(SearchSameWordsDirTask.invokeAll(createSubTasks()));
            WordOccurrenceTable table = list.get(0).join();
            for (int i = 1; i < list.size(); i++) {
                table.merge(list.get(i).join());
            }
            return table;
        } else {
            return calculate();
        }
    }

    private Collection<SearchSameWordsDirTask> createSubTasks() {
        List<SearchSameWordsDirTask> tasks = new ArrayList<>();
        File[] files = currentFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            tasks.add(new SearchSameWordsDirTask(files[i]));
        }
        return tasks;
    }

    private WordOccurrenceTable calculate() {
        List<Line> lines = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
            String line = reader.readLine();
            int numberOfLine = 1;

            while (line != null) {
                lines.add(new Line(line, numberOfLine++));
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("No file on path: " + currentFile.getAbsolutePath());
            return new WordOccurrenceTable();
        }
        this.forkJoinPool = ForkJoinPool.commonPool();

        return forkJoinPool.invoke(new SearchSameWordsDocTask(lines, currentFile.getName()));
    }
}
