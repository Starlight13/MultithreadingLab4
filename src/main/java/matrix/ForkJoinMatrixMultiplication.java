package matrix;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinMatrixMultiplication {

    public int[][] multiplication(int[][] a, int[][] b, int rowsPerTask) throws Exception {
        Matrix.check(a, b);
        int[][] result = Matrix.zeroMatrix(a.length, b[0].length);

        long start = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        forkJoinPool.invoke(new MatrixMultiplicationTask(a, b, result, rowsPerTask, 0));

        long finish = System.currentTimeMillis();
        double t = (finish - start) / 1000.0;
        System.out.println("Time Linear with Fork join: " + t + " sec.");
        return result;
    }
}
