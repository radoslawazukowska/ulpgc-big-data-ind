#ifndef MATRIX_H
#define MATRIX_H

double **allocate_and_init_matrix(int n);
void free_matrix(double **matrix, int n);
void matrix_multiply(double **A, double **B, double **C, int n);

#endif