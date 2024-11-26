package org.matrix.benchmark;

import org.matrix.MatrixMultiplicationParralelStreams;
import org.matrix.MatrixMultiplicationThreads;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 1)
@Measurement(iterations = 1)
public class BenchmarkMatrix {
    @Param({"10000"})
    private int size;

    private double [][] A;
    private double [][] B;

    @Setup(Level.Trial)
    public void setup() {
        Random random = new Random(1234);
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
        MatrixMultiplicationThreads.multiply(A, B);
    }

    @Benchmark
    public void testParallelStreams() {
        MatrixMultiplicationParralelStreams.multiply(A, B);
    }
}
