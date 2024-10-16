from matrix import matrix_multiplication
from random import random
import pytest
import gc


def generate_random_matrix(n):
    return [[random() for _ in range(n)] for _ in range(n)]


@pytest.mark.parametrize("size", [16, 32, 64, 128, 256, 512, 1024])
def test_matrix_multiplication(benchmark, size):
    A = generate_random_matrix(size)
    B = generate_random_matrix(size)
    benchmark(matrix_multiplication, A, B)
