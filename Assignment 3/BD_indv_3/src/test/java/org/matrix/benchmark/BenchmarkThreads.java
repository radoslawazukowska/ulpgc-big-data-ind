package org.matrix.benchmark;

import org.matrix.MatrixMultiplicationThreads;
import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
public class BenchmarkThreads {
    @Param({"1", "2", "4", "8", "16", "24", "32", "40"})
    private int nThreads;

    private double [][] A;
    private double [][] B;

    @Setup(Level.Trial)
    public void setup() {
        Random random = new Random(1234);
        int size = 512;
        A = new double[size][size];
        B = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                A[i][j] = random.nextDouble();
                B[i][j] = random.nextDouble();
            }
        }
    }

    @Benchmark
    public void testThreads() {
        MatrixMultiplicationThreads.multiply(A, B, nThreads);
    }
}
