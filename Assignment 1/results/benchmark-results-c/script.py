import os

def extract_user_times_from_file(file_path):
    """Extract user time from a given performance output file."""
    user_times = []
    with open(file_path, 'r') as file:
        for line in file:
            if "seconds user" in line:
                user_time = float(line.split()[0])  # Get the first word (user time) as a float
                user_times.append(user_time)
    return user_times

def calculate_average_user_time(base_directory):
    """Calculate and print the average user time for each batch."""
    for batch in range(100, 1001, 100):
        batch_directory = os.path.join(base_directory, str(batch))
        if not os.path.exists(batch_directory):
            print(f"Directory for {batch} not found.")
            continue
        
        all_user_times = []
        
        for filename in os.listdir(batch_directory):
            file_path = os.path.join(batch_directory, filename)
            if os.path.isfile(file_path):
                user_times = extract_user_times_from_file(file_path)
                all_user_times.extend(user_times)  # Collect all user times
        
        if all_user_times:
            average_user_time = sum(all_user_times) / len(all_user_times)
            print(f"{batch}, {average_user_time:.9f}")
        else:
            print(f"No user time found in batch {batch}.")

# Specify the base directory containing the batch folders
base_directory = 'Assignment 1/results/benchmark-results-c'  # Replace with the actual path
calculate_average_user_time(base_directory)
