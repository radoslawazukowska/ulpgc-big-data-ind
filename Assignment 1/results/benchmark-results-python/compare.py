import json
import matplotlib.pyplot as plt

# Helper function to load JSON benchmark data
def load_benchmark_data(file_path):
    with open(file_path, 'r') as f:
        data = json.load(f)
    return data

# Load benchmark results from two JSON files
data1 = load_benchmark_data('benchmark-results-python.json')
data2 = load_benchmark_data('benchmark-results-python-with-gc-on.json')

# Extract the matrix size and mean execution time from both datasets
def extract_benchmark_info(data):
    sizes = []
    times_mean = []
    times_min = []
    times_max = []
    
    for benchmark in data['benchmarks']:
        sizes.append(benchmark['params']['size'])  # Assuming 'size' is the parameter key for matrix size
        times_mean.append(benchmark['stats']['mean'])  # Mean execution time
        times_min.append(benchmark['stats']['min'])    # Minimum execution time
        times_max.append(benchmark['stats']['max'])    # Maximum execution time
    
    return sizes, times_mean, times_min, times_max


# Extract benchmark information from both files
sizes1, times_mean1, times_min1, times_max1 = extract_benchmark_info(data1)
sizes2, times_mean2, times_min2, times_max2 = extract_benchmark_info(data2)

# Ensure both datasets have the same matrix sizes for comparison
if sizes1 != sizes2:
    raise ValueError("Matrix sizes do not match between the two benchmark files!")

# Plot the mean execution times from both files
plt.figure(figsize=(10, 6))
plt.plot(sizes1, times_mean1, label='with disabled garbage collector', marker='o')
plt.plot(sizes2, times_mean2, label='with garbage collector', marker='x')
plt.xlabel('Matrix Size')
plt.ylabel('Mean Execution Time (seconds)')
# plt.title('Mean Matrix Multiplication Time Comparison')
plt.legend()
plt.show()

# Plot the min and max execution times for both files
plt.figure(figsize=(10, 6))
plt.plot(sizes1, times_min1, label='Min Time (File 1)', marker='o', linestyle='--')
plt.plot(sizes2, times_min2, label='Min Time (File 2)', marker='x', linestyle='--')
plt.plot(sizes1, times_max1, label='Max Time (File 1)', marker='o', linestyle=':')
plt.plot(sizes2, times_max2, label='Max Time (File 2)', marker='x', linestyle=':')
plt.xlabel('Matrix Size')
plt.ylabel('Execution Time (seconds)')
plt.title('Min and Max Execution Time Comparison')
plt.legend()
plt.show()