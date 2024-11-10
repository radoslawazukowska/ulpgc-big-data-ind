package org.matrix;

import java.util.ArrayList;

public class SparseMatrixArray {
    ArrayList<ArrayList<Integer>> data;
    int row, col;
    int len;

    public SparseMatrixArray(int row, int column) {
        this.row = row;
        this.col = column;
        this.data = new ArrayList<>();
        len = 0;
    }

    public SparseMatrixArray(int size) {
        this.row = size;
        this.col = size;
        this.data = new ArrayList<>();
        this.len = 0;
    }

    public void insert(int r, int c, int val) {
        if (r > row || c > col) {
            System.err.println("Wrong entry");
        } else {
            ArrayList<Integer> entry = new ArrayList<>();
            entry.add(r);
            entry.add(c);
            entry.add(val);
            data.add(entry);
            len++;
        }
    }

    public SparseMatrixArray transpose() {
        SparseMatrixArray result = new SparseMatrixArray(this.col); // Initialize the result matrix with the transposed dimensions
        result.len = this.len;

        // Create an array to count the number of non-zero elements per column in the original matrix
        int[] count = new int[this.col];
        for (int i = 0; i < this.len; i++) {
            ArrayList<Integer> entry = data.get(i);
            count[entry.get(1)]++; // Increment the count for the column index
        }

        // Create an array that holds the index positions for each column in the result matrix
        int[] index = new int[this.col + 1];
        index[0] = 0;
        for (int i = 1; i <= this.col; i++) {
            index[i] = index[i - 1] + count[i - 1];
        }

        // Now, populate the result matrix
        for (int i = 0; i < this.len; i++) {
            ArrayList<Integer> entry = data.get(i);
            int colIndex = entry.get(1); // Get the column index from the original matrix
            int rpos = index[colIndex]++; // Get the correct position for the transposed entry

            // Create a new entry for the result matrix and add it to the data
            ArrayList<Integer> transposedEntry = new ArrayList<>();
            transposedEntry.add(entry.get(1)); // Row becomes the column
            transposedEntry.add(entry.get(0)); // Column becomes the row
            transposedEntry.add(entry.get(2)); // Value remains the same

            // Add the transposed entry to the result matrix
            result.data.add(rpos, transposedEntry);
        }

        return result;
    }

    public SparseMatrixArray multiply(SparseMatrixArray other) {
        if (col != other.row) {
            System.err.println("Invalid dimensions");
            return null;
        }
        other = other.transpose();
        int apos, bpos;
        SparseMatrixArray result = new SparseMatrixArray(row, other.row);

        for (apos = 0; apos < len;) {
            ArrayList<Integer> rowA = data.get(apos);
            int r = rowA.get(0);
            for (bpos = 0; bpos < other.len;) {
                ArrayList<Integer> rowB = other.data.get(bpos);
                int c = rowB.get(0);
                int tempa = apos;
                int tempb = bpos;
                int sum = 0;
                while (tempa < len && data.get(tempa).get(0) == r
                        && tempb < other.len && other.data.get(tempb).get(0) == c) {

                    if (data.get(tempa).get(1) < other.data.get(tempb).get(1))
                        tempa++;

                    else if (data.get(tempa).get(1) > other.data.get(tempb).get(1))
                        tempb++;
                    else
                        sum += data.get(tempa++).get(2) * other.data.get(tempb++).get(2);
                }

                if (sum != 0)
                    result.insert(r, c, sum);

                while (bpos < other.len && other.data.get(bpos).get(0) == c)
                    bpos++;
            }
            while (apos < len && data.get(apos).get(0) == r)
                apos++;
        }
        return result;
    }

    public void print() {
        for (int i = 0; i < this.len; i++) {
            ArrayList<Integer> entry = data.get(i);
            System.out.println("Row " + entry.get(0) + " Col " + entry.get(1) + " Val " + entry.get(2));
        }
    }
}
