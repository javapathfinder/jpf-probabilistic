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
 * This problem is known as 'set-balancing', or 'two-colouring a family of
 * vectors'. Given an n x n matrix A, all of whose entries are 0 or 1, find a
 * column vector b E {-1, +1}^n minimizing the infinity norm of (A * b). An
 * example of an application is in experiments when dividing a set of subjects
 * into two subsets that have roughly the same characteristics (i.e. the control
 * group and the treatment group).
 * 
 * @author Zainab Fatmi
 */
public class SetBalancing {
	private int[][] A; // a square (n x n) matrix, all of whose entries are 0 or 1.
	private int n; // the length of the matrix, A.

	/**
	 * Initializes the values of the n x n array.
	 * 
	 * @param A a square (n x n) matrix, all of whose entries are 0 or 1.
	 */
	public SetBalancing(int[][] A) {
		this.A = A;
		this.n = A.length;
	}

	/**
	 * For k trials, with probability at least 1 - (2/n)^k, we find a vector b for
	 * which the infinity norm of (A * b) <= 4 * sqrt(n * ln(n)).
	 * 
	 * @param k The number of trials, >= 1.
	 * @return The vector b of length n, all of whose entries are -1 or 1.
	 */
	public int[] divide(int k) {
		int[] b = divide();
		for (int i = 1; i < k; i++) {
			int[] temp = divide();
			if (infinityNorm(multiply(A, temp)) < infinityNorm(multiply(A, b))) {
				b = temp; // minimize the infinity norm of A * b
			}
		}
		return b;
	}

	/**
	 * With probability at least (1 - 2/n), we find a vector b for which the
	 * infinity norm of A * b <= 4 sqrt(n * ln(n)).
	 * 
	 * @return The vector b of length n, all of whose entries are -1 or 1.
	 */
	private int[] divide() {
		int[] b = new int[n];
		for (int i = 0; i < b.length; i++) {
			b[i] = UniformChoice.make(2);
			if (b[i] == 0) {
				b[i] = -1; // random uniform choice on distribution set {-1,1}
			}
		}
		return b;
	}

	/**
	 * Multiplies a square matrix of size n x n and a vector of size n, and returns the
	 * result.
	 * 
	 * @param matrix An n x n matrix.
	 * @param vector An array of size n.
	 * @return matrix * vector.
	 */
	public int[] multiply(int[][] matrix, int[] vector) {
		int n = vector.length;
		int[] result = new int[n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) 
				result[i] += matrix[i][j] * vector[j];

		return result;
	}

	/**
	 * Finds the infinity-norm (the maximum magnitude of all elements) of a vector.
	 * 
	 * @param vector An array of integers.
	 * @return The infinity norm of the vector.
	 */
	public int infinityNorm(int[] vector) {
		int n = vector.length;
		int max = Math.abs(vector[0]);
		for (int i = 1; i < n; i++) {
			if (Math.abs(vector[i]) > max) {
				max = Math.abs(vector[i]);
			}
		}
		return max;
	}
}
