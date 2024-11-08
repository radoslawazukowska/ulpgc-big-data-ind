package org.matrix;

public class NaiveMatrixMultiplication {
    public static double[][] multiply(double[][] A, double[][]B) {
        int n = A.length;
        double[][] C = new double[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                C[i][j] = 0;
                for (int k = 0; k < n; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        return C;
    }
}
