#!/bin/bash

sizes=(100 200 300 400 500 600 700 800 900 1000)

for size in "${sizes[@]}"; do
    mkdir ../../results/benchmark-results-c/$size
    for i in {1..10}; do
        perf stat -o "../../results/benchmark-results-c/$size/perf_output_sample$i.txt" ./matrix_multiplication $size
    done
done