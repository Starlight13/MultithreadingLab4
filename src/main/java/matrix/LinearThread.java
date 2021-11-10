package matrix;

public class LinearThread extends Thread {

    private int[][] a;
    private int[][] b;
    private int[][] result;

    private int startIndex;
    private int endIndex;

    public LinearThread(int[][] b, int rowsPerIndex, int startIndex, int endIndex) {
        this.a = new int[rowsPerIndex][];
        this.b = b;
        this.result = new int[rowsPerIndex][];
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        for (int i = 0; i < a.length; i++) {
            for (int l = 0; l < a[i].length; l++) {
                int k = (startIndex + i) - l + ((startIndex + i) - l < 0 ? b.length : 0);
                for (int j = 0; j < b[l].length; j++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
    }

    public void setRows(int[] a, int[] result, int index) {
        this.a[index] = a;
        this.result[index] = result;
    }
}
