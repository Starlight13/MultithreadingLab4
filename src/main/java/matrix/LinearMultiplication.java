package matrix;

public class LinearMultiplication {

    public int[][] multiplication(int[][] a, int[][] b, int threadCount) throws Exception {
        Matrix.check(a, b);
        Result result = new Result(Matrix.zeroMatrix(a.length, b[0].length));

        long start = System.currentTimeMillis();
        if (threadCount <= 0) threadCount = a.length;
        LinearThread[] linearThread = new LinearThread[threadCount];

        int rowsPerThread = a.length / threadCount;
        for (int i = 0; i < threadCount; i++) {
            linearThread[i] = new LinearThread(b, rowsPerThread, rowsPerThread * i, rowsPerThread * (i + 1));
            for (int l = 0; l < rowsPerThread; l++) {
                linearThread[i].setRows(a[rowsPerThread * i + l], result.getMatrix()[rowsPerThread * i + l], l);
            }
            linearThread[i].start();
        }
        for (int i = 0; i < linearThread.length; i++) {
            linearThread[i].join();
        }
        long finish = System.currentTimeMillis();
        double t = (finish - start) / 1000.0;
        System.out.println("Linear algorithm time: " + t + " sec.");
        return result.getMatrix();
    }
}
