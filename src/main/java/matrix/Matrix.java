package matrix;

public class Matrix {

    public static void check(int[][] a, int[][] b) throws Exception{
        if (a[0].length != b.length || a.length != b[0].length) {
            throw new Exception("EXCEPTION");
        }
    }

    public static int[][] randomMatrix(int rowSize, int columnSize, boolean isTesting) {
        int matrix[][] = new int[rowSize][columnSize];
        for (int i = 0; i < rowSize; i++) {
            matrix[i] = new int[columnSize];
            if (isTesting) {
                for (int l = 0; l < columnSize; l++) {
                    matrix[i][l] = 1;
                }
            } else {
                for (int l = 0; l < columnSize; l++) {
                    matrix[i][l] = (int) (Math.random() * (9 - 1)) + 1;
                }
            }
        }
        return matrix;
    }

    public static int[][] zeroMatrix(int rowSize, int columnSize) {
        int matrix[][] = new int[rowSize][columnSize];
        for (int i = 0; i < rowSize; i++) {
            matrix[i] = new int[columnSize];
            for (int l = 0; l < columnSize; l++) {
                matrix[i][l] = 0;
            }
        }
        return matrix;
    }

    public static void print(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int l = 0; l < matrix[i].length; l++) {
                System.out.printf("%7d", matrix[i][l]);
            }
            System.out.println();
        }
    }

    public static boolean checkIfCorrect(int[][] matrix, int matrixSize) {
        for (int i = 0; i < matrix.length; i++) {
            for (int l = 0; l < matrix[i].length; l++) {
                if (matrix[i][l] != matrixSize) return false;
            }
        }
        return true;
    }
}
