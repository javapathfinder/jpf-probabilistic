/*
 * Copyright (C) 2020  Zainab Fatmi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You can find a copy of the GNU General Public License at
 * <http://www.gnu.org/licenses/>.
 */

package probabilistic.examples;

import probabilistic.UniformChoice;

/**
 * A Monte-Carlo algorithm for estimating the permanent of a 0-1 matrix.
 * 
 * @author Zainab Fatmi
 */
public class ApproximatePermanent {

	/**
	 * Approximates the permanent of the matrix, A, by calculating the mean of n
	 * independent samples of estimates; i.e. E[det(A)^2] = per(A).
	 * 
	 * @param A a square 0-1 matrix
	 * @param n the number of samples/trials (Note: the number of trials needed for
	 *          an ε,δ-approximation is less than C*3^(n/2)*ε^(-1)*ln(1/δ), where C
	 *          is a constant)
	 * @return the approximate permanent of the given matrix A
	 */
	public static int getPermanent(int[][] A, int n) {
		double sum = 0;
		for (int i = 0; i < n; i++) {
			sum += getPermanent(A);
		}
		return (int) Math.round(sum / n);
	}

	/**
	 * Returns an estimate of the permanent of a square matrix.
	 * 
	 * @param matrix a square 0-1 matrix
	 * @return the estimated permanent of the given matrix
	 */
	private static int getPermanent(int[][] matrix) {
		int n = matrix.length;
		int[][] B = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int r = UniformChoice.make(2);
				if (r == 0) {
					B[i][j] = matrix[i][j];
				} else {
					B[i][j] = -matrix[i][j];
				}
			}
		}
		int det = getDeterminant(B);
		return det * det;
	}

	/**
	 * Calculates the determinant of an integer matrix, using Laplace expansion.
	 * Reference: https://gist.github.com/Cellane/398372
	 *
	 * @param matrix matrix of which we need to know determinant
	 * @return the determinant of the given matrix
	 */
	public static int getDeterminant(int[][] matrix) {
		int n = matrix.length;
		if (n == 1) {
			return matrix[0][0];
		} else if (n == 2) {
			return (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
		}
		int m = matrix[0].length;
		int minor[][];
		int det = 0;
		for (int i = 0; i < m; i++) {
			minor = new int[n - 1][n - 1];
			for (int j = 1; j < n; j++) {
				for (int k = 0; k < m; k++) {
					if (k < i) {
						minor[j - 1][k] = matrix[j][k];
					} else if (k > i) {
						minor[j - 1][k - 1] = matrix[j][k];
					}
				}
			}
			det += matrix[0][i] * Math.pow(-1, i) * getDeterminant(minor);
		}
		return det;
	}
}
