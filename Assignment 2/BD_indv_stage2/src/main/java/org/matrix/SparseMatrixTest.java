package org.matrix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SparseMatrixTest {
    public static void main(String[] args) {
        String filePath = "data/0.91.mtx";
        try {
            SparseMatrixArray matrix = readMatrixArrayFromFile(filePath);
            SparseMatrixArray matrixC = matrix.multiply(matrix);
            matrixC.print();
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    public static SparseMatrix readMatrixFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            if (!line.startsWith("%")) {
                break;
            }
        }

        String[] parts = line.trim().split("\\s+");
        int rows = Integer.parseInt(parts[0]);
        int cols = Integer.parseInt(parts[1]);
        int nonZeroEntries = Integer.parseInt(parts[2]);

        SparseMatrix matrix = new SparseMatrix(nonZeroEntries, rows, cols);

        while ((line = reader.readLine()) != null) {
            parts = line.trim().split("\\s+");
            int row = Integer.parseInt(parts[0]) - 1;
            int col = Integer.parseInt(parts[1]) - 1;
            int value = 1;
            matrix.insert(row, col, value);
        }

        reader.close();
        return matrix;
    }

    public static SparseMatrixArray readMatrixArrayFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            if (!line.startsWith("%")) {
                break;
            }
        }

        String[] parts = line.trim().split("\\s+");
        int rows = Integer.parseInt(parts[0]);
        int cols = Integer.parseInt(parts[1]);
        int nonZeroEntries = Integer.parseInt(parts[2]);

        SparseMatrixArray matrix = new SparseMatrixArray(rows, cols);

        while ((line = reader.readLine()) != null) {
            parts = line.trim().split("\\s+");
            int row = Integer.parseInt(parts[0]) - 1;
            int col = Integer.parseInt(parts[1]) - 1;
            int value = 1;
            matrix.insert(row, col, value);
        }

        reader.close();
        return matrix;
    }
}
