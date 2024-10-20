import matplotlib.pyplot as plt

def read_data_from_file(filename):
    sizes = []
    avr_times = []
    
    with open(filename, 'r') as file:
        print(filename)
        for line in file:
            size, avr_time = line.strip().split(',')
            sizes.append(float(size))
            avr_times.append(float(avr_time))
    
    return sizes, avr_times


files = ['c-avr.txt', 'python-avr.txt', 'java-avr.txt']
labels = ['C', 'Python', 'Java']
colors = ['blue', 'orange', 'green']

plt.figure(figsize=(10, 6))

for file, label, color in zip(files, labels, colors):
    sizes, avr_times = read_data_from_file(file)
    plt.plot(sizes, avr_times, label=label, marker='o', color=color)


# plt.title('Average Execution Time Comparison')
plt.xlabel('Size')
plt.ylabel('Average Time (seconds)')
plt.xticks(sizes)
plt.grid(True)
plt.legend()
# plt.yscale('log')
plt.tight_layout()
plt.show()
