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

import java.util.Arrays;

import probabilistic.Coin;

/**
 * An implementation of Freivalds' Technique to verify the result (C) of a
 * matrix multiplication (AB), i.e. confirm that A * B = C. The algorithm is a
 * Monte Carlo algorithm with one-sided error (there is only a possibility of an
 * error if true is returned). If the algorithm is iterated k times, the
 * probability of error is (1/2)^k.
 * 
 * @author Zainab Fatmi
 */
public class FreivaldsTechnique {
	private int[][] A;
	private int[][] B;
	private int[][] C;
	private int n;

	/**
	 * Initializes the three n x n arrays.
	 * 
	 * @param A an n x n array.
	 * @param B an n x n array.
	 * @param C the resulting A * B array to verify.
	 */
	public FreivaldsTechnique(int[][] A, int[][] B, int[][] C) {
		this.A = A;
		this.B = B;
		this.C = C;
		n = A.length;
	}

	/**
	 * Multiple iterations of Freivalds' algorithm to verify that A * B = C. The
	 * probability of error is (1/2)^k.
	 * 
	 * @param k the number of iterations of the algorithm.
	 * @return true if AB = C, false otherwise.
	 */
	public boolean verify(int k) {
		for (int i = 0; i < k; i++) {
			if (!verify()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Freivalds' algorithm to verify the result of the matrix multiplication A * B
	 * = C.
	 * 
	 * @return true if AB = C, false otherwise.
	 */
	private boolean verify() {
		int[] r = new int[n];
		for (int i = 0; i < n; i++) {
			r[i] = Coin.flip();
		}
		return Arrays.equals(multiply(A, multiply(B, r)), multiply(C, r));
	}

	/**
	 * Multiplies a matrix of size n x n and a vector of size n, and returns the
	 * result.
	 * 
	 * @param matrix an n x n matrix.
	 * @param vector a vector of size n.
	 * @return matrix * vector.
	 */
	private int[] multiply(int[][] matrix, int[] vector) {
		int n = vector.length;
		int[] result = new int[n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				result[i] += matrix[i][j] * vector[j];
			}
		}
		return result;
	}
}
