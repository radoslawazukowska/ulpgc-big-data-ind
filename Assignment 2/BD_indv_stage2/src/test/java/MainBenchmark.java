import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.matrix.benchmark.BenchmarkMatrix;

public class MainBenchmark {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BenchmarkMatrix.class.getSimpleName())
                .forks(1)
                .addProfiler("gc")
                .addProfiler("stack")
                .output("benchmark_results_up_to1500.txt")
                .build();
        new Runner(opt).run();
    }
}
