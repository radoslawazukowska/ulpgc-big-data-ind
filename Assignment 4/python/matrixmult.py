from mrjob.job import MRJob

class MRMatrixMultiplication(MRJob):
    def mapper(self, _, line):
        # Parse the line to identify the row and elements of the matrix
        row_index, *values = map(int, line.split())
        num_cols = len(values)

        # Emit key-value pairs for matrix A and matrix B
        for col_index, value in enumerate(values):
            for k in range(num_cols):  # num_cols is also the size of the square matrix
                # Emit for matrix multiplication
                yield (row_index, k), ('A', col_index, value)
                yield (k, col_index), ('B', row_index, value)

    def reducer(self, key, values):
        # Separate and organize values into dicts for A and B
        a_values = {}
        b_values = {}

        for matrix, idx, value in values:
            if matrix == 'A':
                a_values[idx] = value
            elif matrix == 'B':
                b_values[idx] = value

        # Calculate the dot product for the current (row, col) position
        result = sum(a_values.get(k, 0) * b_values.get(k, 0) for k in set(a_values) & set(b_values))
        yield key, result

if __name__ == "__main__":
    MRMatrixMultiplication.run()
