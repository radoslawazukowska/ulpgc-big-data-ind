package org.matrix.benchmark;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class MainThreads {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BenchmarkThreads.class.getSimpleName())
                .forks(1)
                .output("results/benchmark_threads.tx")
                .build();
        new Runner(opt).run();
    }
}
