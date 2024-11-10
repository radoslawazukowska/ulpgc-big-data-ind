package org.matrix.benchmark;

import org.matrix.*;

import java.util.concurrent.TimeUnit;
import java.io.IOException;

import org.openjdk.jmh.annotations.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class BenchmarkSparse {
//    @Param({"112", "207", "292", "425", "511"})
//    private String size;
    @Param({"0.91", "0.96", "0.970", "0.971", "0.982", "0.986"})
    private String sparsity;

    private SparseMatrixArray sparseMatrixArray;
    private SparseMatrix sparseMatrix;

    @Setup(Level.Trial)
    public void setUpMatrices() {
        try {
            String filePath = "data/" + sparsity + ".mtx";
            sparseMatrix = SparseMatrixTest.readMatrixFromFile(filePath);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    @Benchmark
    public void testMatrix() {
        sparseMatrix.multiply(sparseMatrix);
    }
}