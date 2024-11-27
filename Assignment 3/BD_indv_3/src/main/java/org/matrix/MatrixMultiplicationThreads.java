package org.matrix;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MatrixMultiplicationThreads {
    private static ExecutorService executorService;

    public static double[][] multiply(double[][] A, double[][] B) {
        executorService = Executors.newFixedThreadPool(16);
        return multiplication(A, B);
    }

    public static double[][] multiply(double[][] A, double[][] B, int numberThreads) {
        executorService = Executors.newFixedThreadPool(numberThreads);
        return multiplication(A, B);
    }

    private static double[][] multiplication(double[][] A, double[][] B) {
        int size = A.length;
        double[][] C = new double[size][size];
        for (int i = 0; i < size; i++) submit(A, B, size, C, i);
        try {
            executorService.shutdown();
            executorService.awaitTermination(1000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return C;
    }

    private static void submit(double[][] A, double[][] B, int size, double[][] C, int i) {
        executorService.submit(() -> {
            for (int k = 0; k < size; k++)
                for (int j = 0; j < size; j++)
                    C[i][j] += A[i][k] * B[k][j];
        });
    }
}
