package org.matrix;

public class StrassenMatrixMultiplication {
    // from https://www.javatpoint.com/strassens-matrix-multiplication
    public static double[][] multiply(double[][] A, double[][] B) {
        int n = A.length;
        double[][] C = new double[n][n];

        if (n==1) {
            C[0][0] = A[0][0] * B[0][0];
        } else if (n < 127) {
            C = NaiveMatrixMultiplication.multiply(A, B);
        } else {
            double [][] A00 = new double[n/2][n/2];
            double [][] A01 = new double[n/2][n/2];
            double [][] A10 = new double[n/2][n/2];
            double [][] A11 = new double[n/2][n/2];
            double [][] B00 = new double[n/2][n/2];
            double [][] B01 = new double[n/2][n/2];
            double [][] B10 = new double[n/2][n/2];
            double [][] B11 = new double[n/2][n/2];

            splitMatrix(A, A00, 0, 0);
            splitMatrix(A, A01, 0, n/2);
            splitMatrix(A, A10, n/2, 0);
            splitMatrix(A, A11, n/2, n/2);
            splitMatrix(B, B00, 0, 0);
            splitMatrix(B, B01, 0, n/2);
            splitMatrix(B, B10, n/2, 0);
            splitMatrix(B, B11, n/2, n/2);

            double[][] P1 = multiply(A00, subtractMatrix(B01,B11));
            double[][] P2 = multiply(addMatrix(A00, A01), B11);
            double[][] P3 = multiply(addMatrix(A10, A11), B00);
            double[][] P4 = multiply(A11, subtractMatrix(B10, B00));
            double[][] P5 = multiply(addMatrix(A00, A11), addMatrix(B00, B11));
            double[][] P6 = multiply(subtractMatrix(A01, A11), addMatrix(B10, B11));
            double[][] P7 = multiply(subtractMatrix(A00, A10), addMatrix(B00, B01));

            double[][] C00 = addMatrix(subtractMatrix(addMatrix(P5, P4), P2), P6);
            double[][] C01 = addMatrix(P1, P2);
            double[][] C10 = addMatrix(P3, P4);
            double[][] C11 = subtractMatrix(subtractMatrix(addMatrix(P5,P1), P3), P7);

            mergeMatrix(C00,C,0,0);
            mergeMatrix(C01,C,0,n/2);
            mergeMatrix(C10,C,n/2,0);
            mergeMatrix(C11,C,n/2,n/2);
        }
        return C;
    }

    private static void mergeMatrix(double[][] child, double[][] parent, int rowOffset, int colOffset) {
        for (int i=0; i<child.length; i++)
            for (int j=0; j<child.length; j++)
                parent[rowOffset + i][colOffset + j] = child[i][j];
    }

    private static double[][] subtractMatrix(double[][] A, double[][] B) {
        int n = A.length;
        double[][] C = new double[n][n];
        for(int i=0; i<n; i++)
            for (int j=0; j<n; j++)
                C[i][j] = A[i][j] - B[i][j];
        return C;
    }

    private static void splitMatrix(double[][] parent, double[][] child, int rowOffset, int colOffset) {
        for (int i=0; i<child.length; i++)
            for (int j=0; j<child.length; j++)
                child[i][j] = parent[rowOffset+i][colOffset+j];
    }

    private static double[][] addMatrix(double[][] A, double[][] B) {
        int n = A.length;
        double[][] C = new double[n][n];
        for(int i=0; i<n; i++)
            for (int j=0; j<n; j++)
                C[i][j] = A[i][j] + B[i][j];
        return C;
    }
}
