package org.ulpgc;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class MainBenchmark {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(DistributedMatrixMultiplication.class.getSimpleName())
                .forks(1)
                .output("benchmark1000-1.txt")
                .build();
        new Runner(opt).run();
    }
}
