import hazelcast
import numpy as np
from concurrent.futures import ThreadPoolExecutor

def distribute_matrix_multiplication(client, matrix_a, matrix_b):
    rows_a, cols_a = len(matrix_a), len(matrix_a[0])
    rows_b, cols_b = len(matrix_b), len(matrix_b[0])

    if cols_a != rows_b:
        raise ValueError("Matrix dimensions do not match for multiplication.")

    # Distributed map to store tasks
    tasks_map = client.get_map("tasks").blocking()

    # Assign each row of matrix_a as a task
    for i in range(rows_a):
        tasks_map.put(i, {"row": matrix_a[i], "matrix_b": matrix_b})

    # Retrieve results
    result = np.zeros((rows_a, cols_b), dtype=int)
    with ThreadPoolExecutor() as executor:
        futures = [executor.submit(process_task, tasks_map.get(i)) for i in range(rows_a)]
        for i, future in enumerate(futures):
            result[i] = future.result()

    return result

def process_task(task):
    """Performs matrix multiplication for a single row."""
    row = task["row"]
    matrix_b = task["matrix_b"]
    cols_b = len(matrix_b[0])

    result_row = [0] * cols_b
    for j in range(cols_b):
        for k in range(len(row)):
            result_row[j] += row[k] * matrix_b[k][j]

    return result_row

def main():
    # Start Hazelcast client
    client = hazelcast.HazelcastClient()

    # Example matrices
    matrix_a = [
        [1, 2, 3],
        [4, 5, 6],
        [7, 8, 9]
    ]
    matrix_b = [
        [9, 8, 7],
        [6, 5, 4],
        [3, 2, 1]
    ]

    # Perform distributed matrix multiplication
    result = distribute_matrix_multiplication(client, matrix_a, matrix_b)

    # Display the result
    print("Result:")
    print(result)

    # Shutdown the client
    client.shutdown()

if __name__ == "__main__":
    main()
