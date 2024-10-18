#include "matrix.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>


int main(int argc, char *argv[]) {
    if (argc != 2) {
        fprintf(stderr, "Usage: %s <size>\n", argv[0]);
        return 1;
    }
    
    int n = atoi(argv[1]);
    srand(time(NULL));

    double **A = allocate_and_init_matrix(n);
    double **B = allocate_and_init_matrix(n);
    double **C = malloc(n * sizeof(double *));
    for (int i = 0; i < n; i++) {
        C[i] = malloc(n * sizeof(double));
    }

    matrix_multiply(A, B, C, n);

    free_matrix(A, n);
    free_matrix(B, n);
    free_matrix(C, n);

    return 0;
}