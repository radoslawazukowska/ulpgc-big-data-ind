package matrix;

public class Matrix {
	public static double[][] multiply(double[][] a, double[][]b) {
		int n = a.length;
		double[][] c = new double[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				c[i][j] = 0;
				for (int k = 0; k < n; k++) {
					c[i][j] += a[i][k] * b[k][j];
				}
			}
		}
		return c;
	}
}
