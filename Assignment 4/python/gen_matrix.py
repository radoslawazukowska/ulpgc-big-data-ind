import sys
import random

def generate_matrix(size, output_file="matrix.txt"):
    """
    Generate a random square matrix of the given size and write it to a file.
    Each row starts with the row number, followed by the matrix values.
    """
    with open(output_file, "w") as f:
        for row in range(size):
            row_values = [random.randint(-10, 10) for _ in range(size)]
            f.write(f"{row} " + " ".join(map(str, row_values)) + "\n")

if __name__ == "__main__":
    # Check for the command-line argument
    if len(sys.argv) != 2:
        print("Usage: python script.py <size>")
        sys.exit(1)

    try:
        # Parse the size of the matrix from the command line
        size = int(sys.argv[1])
        if size <= 0:
            raise ValueError("Matrix size must be a positive integer.")

        # Generate the matrix
        generate_matrix(size)
        print(f"Matrix of size {size}x{size} written to 'matrix.txt'.")

    except ValueError as e:
        print(f"Error: {e}")
        sys.exit(1)
