import matplotlib.pyplot as plt

with open('formatted_results.csv', 'r', encoding="utf-8") as file:
    header = file.readline().strip().split(',')
    
    data = {'Loop': {}, 'Naive': {}, 'Stassen': {}}
    
    for line in file:
        values = line.strip().split(',')
        if values[0] not in data.keys():
            data[values[0]] = {'size':[], 'score':[], 'error':[], 'units':[]}
        
        print(values)
        data[values[0]]['size'].append(values[1])
        data[values[0]]['score'].append(values[4])
        if(len(values)>6):
            data[values[0]]['error'].append(values[6])
        data[values[0]]['units'].append(values[-1])
# Print each line's data
for column, values in data.items():
    print(f'{column}: {values}')
