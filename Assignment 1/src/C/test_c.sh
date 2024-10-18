#!/bin/bash

sizes=(16 32 64 128 256 512 1024)

for size in "${sizes[@]}"
do
    perf stat -o ../../results/benchmark-results-c/perf_output_$size.txt ./matrix_multiplication $size
done
