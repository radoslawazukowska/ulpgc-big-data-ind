package matrix;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkRunner {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(matrix.BenchmarkMatrix.class.getSimpleName()) // Specify which benchmark to run
                .forks(1) // Run the benchmark in a separate JVM fork
                .build();

        new Runner(opt).run();
    }
}
