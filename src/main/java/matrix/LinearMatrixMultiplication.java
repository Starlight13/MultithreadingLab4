package matrix;


public class LinearMatrixMultiplication {

    public int[][] multiplication(int[][] a, int[][] b, int threadsSize) throws Exception {
        Matrix.check(a, b);
        Result result = new Result(Matrix.zeroMatrix(a.length, b[0].length));

        long start = System.currentTimeMillis();
        if (threadsSize <= 0) threadsSize = a.length;
        LinearThread[] linearThread = new LinearThread[threadsSize];

        int rowsPerThread = a.length / threadsSize;
        for (int i = 0; i < threadsSize; i++) {
            linearThread[i] = new LinearThread(b, rowsPerThread, rowsPerThread * i, rowsPerThread * (i + 1));
            for (int l = 0; l < rowsPerThread; l++) {
                linearThread[i].setRows(a[rowsPerThread * i + l], result.getMatrix()[rowsPerThread * i + l], l);
            }
            linearThread[i].start();
        }
        for (LinearThread thread : linearThread) {
            thread.join();
        }
        long finish = System.currentTimeMillis();
        double t = (finish - start) / 1000.0;
        System.out.println("Time Linear: " + t + " sec.");
        return result.getMatrix();
    }
}
