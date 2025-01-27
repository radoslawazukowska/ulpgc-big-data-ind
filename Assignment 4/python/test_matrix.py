import pytest
from matrixmult import MRMatrixMultiplication
from gen_matrix import generate_matrix

@pytest.mark.parametrize("size", [200, 250, 300])  # Adjust sizes as needed
def test_matrix_multiplication_benchmark(benchmark, size):
    # Generate random input matrix
    generate_matrix(size)
    file = "matrix.txt"

    # Define the benchmarking function
    def run_mr_job():
        mr_job = MRMatrixMultiplication(args=[file])
        with mr_job.make_runner() as runner:
            runner.run()
            list(mr_job.parse_output(runner.cat_output()))  # Force full execution

    # Benchmark the MapReduce job
    benchmark(run_mr_job)
