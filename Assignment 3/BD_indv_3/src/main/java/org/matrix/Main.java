package org.matrix;

import java.util.Random;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public class Main {
    public static void main(String[] args) {
        int size = 4;
        double[][] A = generate_random_matrix(size);
        double[][] B = generate_random_matrix(size);

        double[][] C1 = MatrixMultiplicationThreads.multiply(A, B);
        System.out.println("Finished multiplication with threads");
        double[][] C2 = MatrixMultiplicationParralelStreams.multiply(A, B);

        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        Runtime runtime = Runtime.getRuntime();

        System.out.println("Finished multiplication with parallel streams");
        System.out.println("System Information:");
        System.out.println("Operating System: " + osBean.getName() + " " + osBean.getVersion());
        System.out.println("Architecture: " + osBean.getArch());
        System.out.println("Available Processors: " + osBean.getAvailableProcessors());
        System.out.println("Total Memory (MB): " + (runtime.totalMemory() / 1024 / 1024));
        System.out.println("Free Memory (MB): " + (runtime.freeMemory() / 1024 / 1024));
        System.out.println("Active Thread Count: " + Thread.activeCount());
    }

    private static double[][] generate_random_matrix(int size) {
        double[][] matrix = new double[size][size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = random.nextDouble();
            }
        }
        return matrix;
    }

    private static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}