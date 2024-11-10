package org.matrix.benchmark;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class MainBenchmarkSparse {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BenchmarkSparse.class.getSimpleName())
                .forks(1)
                .addProfiler("gc")
                .addProfiler("stack")
                .output("results/benchmark_sparse_results_sparsity.txt")
                .build();
        new Runner(opt).run();
    }
}
