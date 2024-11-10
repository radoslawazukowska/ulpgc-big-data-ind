package org.matrix;

public class SparseMatrix {
    // from https://www.geeksforgeeks.org/operations-sparse-matrices/
    int[][] data;
    int row, col;
    int len;
    int maxElements;

    public SparseMatrix(int maxElements, int row, int column) {
        this.maxElements = maxElements;
        this.row = row;
        this.col = column;
        data = new int[this.maxElements][3];
        len = 0;
    }

    public SparseMatrix(int maxElements, int size) {
        this.row = size;
        this.col = size;
        this.maxElements = maxElements;
        this.data = new int[this.maxElements][3];
        this.len = 0;
    }

    public void insert(int r, int c, int val) {
        if (r > row || c > col) {
            System.err.println("Wrong entry");
        } else {
            data[len][0] = r;
            data[len][1] = c;
            data[len][2] = val;
            len++;
        }
    }

    public SparseMatrix transpose() {
        SparseMatrix result = new SparseMatrix(this.len+1, this.col);
        result.len = this.len;

        int[] count = new int[this.col];
        for (int i=0; i<this.len; i++)
            count[this.data[i][1]]++;

        int[] index = new int[this.col + 1];
        index[0] = 0;
        for (int i=1; i<=this.col; i++)
            index[i] = index[i-1] + count[i-1];

        for (int i=0; i<this.len; i++) {
            int colIndex = this.data[i][1];
            int rpos = index[colIndex]++;

            result.data[rpos][0] = data[i][1];
            result.data[rpos][1] = data[i][0];
            result.data[rpos][2] = data[i][2];
        }
        return result;
    }

    public SparseMatrix multiply(SparseMatrix other) {
        if (col != other.row) {
            System.err.println("Invalid dimentions");
            return null;
        }
        other = other.transpose();
        int apos, bpos;
        SparseMatrix result = new SparseMatrix(row * col, row, other.row);
        for (apos=0; apos<len;) {
            int r = data[apos][0];
            for (bpos=0; bpos<other.len;) {
                int c = other.data[bpos][0];
                int tempa = apos;
                int tempb = bpos;
                int sum = 0;
                while (tempa < len && data[tempa][0] == r
                        && tempb < other.len && other.data[tempb][0] == c) {

                    if (data[tempa][1] < other.data[tempb][1])
                        tempa++;

                    else if (data[tempa][1] > other.data[tempb][1])
                        tempb++;
                    else
                        sum += data[tempa++][2] * other.data[tempb++][2];
                }

                if (sum != 0)
                    result.insert(r, c, sum);

                while (bpos < other.len && other.data[bpos][0] == c)
                    bpos++;
            }
            while (apos < len && data[apos][0] == r)
                apos++;
        }
        return result;
    }

    public void print() {
        for (int i=0; i<this.len; i++) {
            System.out.println("Row " + data[i][0] + " Col " + data[i][1] + " Val " + data[i][2]);
        }
    }
}
