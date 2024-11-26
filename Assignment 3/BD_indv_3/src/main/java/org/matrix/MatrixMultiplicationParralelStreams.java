package org.matrix;

import java.util.stream.IntStream;

public class MatrixMultiplicationParralelStreams {
    public static double[][] multiply(double[][] A, double[][] B) {
        int size = A.length;
        double[][] C = new double[size][size];
        IntStream.range(0, size).parallel().forEach(i -> {
            for (int k = 0; k < size; k++)
                for (int j = 0; j < size; j++)
                    C[i][j] += A[i][k] * B[k][j];
        });
        return C;
    }
}
