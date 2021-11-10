import matrix.ForkJoinMatrixMultiplication;
import matrix.LinearMatrixMultiplication;
import matrix.Matrix;
import sameWords.Occurrence;
import sameWords.SearchSameWordsDirTask;
import sameWords.WordOccurrenceTable;
import searchWord.SearchStatement;
import stat.Statistic;

import java.io.File;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) throws Exception {
//        testStat();
//
//        boolean isTesting = true;
//        int matrixSize = 2000;
//        int[][] b = Matrix.randomMatrix(matrixSize, matrixSize, isTesting);
//        testMatrix(b, matrixSize, isTesting);
//        testPrevious(b, matrixSize, isTesting);

//        testSearchSameWords();
//
        testSearchStatementInDocuments();
    }

    public static void testStat() {
        Statistic lengthCount = new Statistic(new File("src/main/java/txt/architectingmodernjavaeeapplications.txt"));

        lengthCount.calculateOneThread();
        lengthCount.calculateMultiThreads();
    }

    public static void testMatrix(int[][] b, int matrixSize, boolean isTesting) throws Exception {
        LinearMatrixMultiplication linearMatrixMultiplication = new LinearMatrixMultiplication();

        int[][] resultP1 = linearMatrixMultiplication.multiplication(b,b,100);

        if (isTesting) {
            System.out.println("Linear algorithm is " + Matrix.checkIfCorrect(resultP1, matrixSize));
        }

//        System.out.println("Linear algorithm:");
//        Matrix.print(resultP1);

    }

    public static void testPrevious(int[][] b, int matrixSize, boolean isTesting) throws Exception{
        ForkJoinMatrixMultiplication forkJoinMatrixMultiplication = new ForkJoinMatrixMultiplication();

        int[][] result = forkJoinMatrixMultiplication.multiplication(b, b, 10);

        if (isTesting) {
            System.out.println("Linear algorithm with ForkJoin is " + Matrix.checkIfCorrect(result, matrixSize));
        }

//        Matrix.print(result);
    }

    public static void testSearchSameWords() {
        SearchSameWordsDirTask search = new SearchSameWordsDirTask(new File("src/main/java/sameWords/test"));

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        WordOccurrenceTable table = forkJoinPool.invoke(search);

        System.out.println(table.get("publisher").size() + " occurrences.");
        List<Occurrence> result = table.get("publisher");

        for (Occurrence occurrence : result) {
            System.out.println(occurrence);
        }

//        System.out.println(table.getKeys());
    }

    public static void testSearchStatementInDocuments() {
        String searchedStatement = "code program software application programmer algorithm";
        File file1 = new File("src/main/java/searchWord/test");

        SearchStatement searchStatement = new SearchStatement(searchedStatement, file1);

        System.out.println("Statement: " + searchedStatement);
        SearchStatement.print(searchStatement.search(), 0);
    }
}
