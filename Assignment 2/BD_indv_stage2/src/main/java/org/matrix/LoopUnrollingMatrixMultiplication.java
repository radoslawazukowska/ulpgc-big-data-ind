package org.matrix;

public class LoopUnrollingMatrixMultiplication {
    public static double[][] multiply(double[][] A, double[][] B) {
        int n = A.length;
        double[][] C = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                C[i][j] = 0;
                int k;
                for (k = 0; k < n - 4; k += 4) {
                    C[i][j] += A[i][k] * B[k][j];
                    C[i][j] += A[i][k + 1] * B[k + 1][j];
                    C[i][j] += A[i][k + 2] * B[k + 2][j];
                    C[i][j] += A[i][k + 3] * B[k + 3][j];
                }
                for (; k < n; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        return C;
    }
}
