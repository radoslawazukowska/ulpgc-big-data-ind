#include <stdio.h>
#include <stdlib.h>
#include "matrix.h"

void matrix_multiply(double **A, double **B, double **C, int n) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            C[i][j] = 0.0;
            for (int k = 0; k < n; k++) {
                C[i][j] += A[i][k] * B[k][j];
            }
        }
    }
}

double **allocate_and_init_matrix(int n) {
    double **matrix = malloc(n * sizeof(double *));
    for (int i = 0; i < n; i++) {
        matrix[i] = malloc(n * sizeof(double));
        for (int j = 0; j < n; j++) {
            matrix[i][j] = (double)rand() / RAND_MAX;
        }
    }
    return matrix;
}

void free_matrix(double **matrix, int n) {
    for (int i = 0; i < n; i++) {
        free(matrix[i]);
    }
    free(matrix);
}