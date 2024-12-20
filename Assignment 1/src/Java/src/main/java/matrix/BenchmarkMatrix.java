package matrix;

import java.util.Random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class BenchmarkMatrix {
    @Param({"100", "200", "300", "400", "500", "600", "700", "800", "900", "1000"})
    private int size;

    private double [][] a;
    private double [][] b;

    @Setup(Level.Trial)
    public void setUpMatrices() {
        Random random = new Random();
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
    @BenchmarkMode(Mode.SampleTime)
    public void testMatrixMultiplication() {
        Matrix.multiply(a, b);
    }
}
