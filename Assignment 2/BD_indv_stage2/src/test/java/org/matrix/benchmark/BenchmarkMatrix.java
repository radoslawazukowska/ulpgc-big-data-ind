package org.matrix.benchmark;

import org.matrix.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 1)
@Measurement(iterations = 1)
public class BenchmarkMatrix {
    @Param({"2500"})
    private int size;

    private double [][] a;
    private double [][] b;
    private Random random;

    @Setup(Level.Trial)
    public void setUpMatrices() {
        random = new Random(1234);
        a = new double[size][size];
        b = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                a[i][j] = random.nextDouble();
                b[i][j] = random.nextDouble();
            }
        }
    }

    @Benchmark
    public void testNaive() {
        NaiveMatrixMultiplication.multiply(a, b);
    }

    @Benchmark
    public void testStrassen() {
        StrassenMatrixMultiplication.multiply(a, b);
    }

    @Benchmark
    public void testLoop() {
        LoopUnrollingMatrixMultiplication.multiply(a, b);
    }
}