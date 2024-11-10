package org.matrix;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        double[][] matrixA = generateRandomMatrix(12810);
        double[][] matrixB = generateRandomMatrix(12810);
        double[][] matrixC = StrassenMatrixMultiplication.multiply(matrixA, matrixB);
//        System.out.print("Matrix A:\n");
//        printMatrix(matrixA);
//        System.out.print("Matrix B:\n");
//        printMatrix(matrixB);
//        System.out.print("Matrix C:\n");
//        printMatrix(matrixC);
    }

    private static double[][] generateRandomMatrix(int size) {
        Random random = new Random();
        double[][] a = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                a[i][j] = random.nextDouble();
            }
        }
        return a;
    }

    public static void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%.2f ", matrix[i][j]);
            }
            System.out.println();
        }
    }
}