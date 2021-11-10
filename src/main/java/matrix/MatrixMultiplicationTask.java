package matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class MatrixMultiplicationTask extends RecursiveAction {

    int[][] a;
    int[][] b;
    int[][] c;
    int rowsPerTask;
    int startIndex;

    public MatrixMultiplicationTask(int[][] a, int[][] b, int[][] c, int rowsPerTask, int startIndex) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.rowsPerTask = rowsPerTask;
        this.startIndex = startIndex;
    }

    @Override
    protected void compute() {
        if (a.length > rowsPerTask) {
            ForkJoinTask.invokeAll(createSubTasks()).forEach(ForkJoinTask::join);
        } else {
            calculate();
        }

    }

    private Collection<MatrixMultiplicationTask> createSubTasks() {
        List<MatrixMultiplicationTask> list = new ArrayList<>();
        int startIndex1 = startIndex;
        int startIndex2 = startIndex + b.length / a.length;
        list.add(new MatrixMultiplicationTask(Arrays.copyOfRange(a, 0, a.length / 2), b, Arrays.copyOfRange(c, 0, c.length / 2), rowsPerTask, startIndex1));
        list.add(new MatrixMultiplicationTask(Arrays.copyOfRange(a, a.length / 2, a.length), b, Arrays.copyOfRange(c, c.length / 2, c.length), rowsPerTask, startIndex2));

        return list;
    }

    private void calculate() {
        for (int i = 0; i < a.length; i++) {
            for (int l = 0; l < a[i].length; l++) {
                int k = (startIndex + i) - l + ((startIndex + i) - l < 0 ? b.length : 0);
                for (int j = 0; j < b[l].length; j++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
    }

}
