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

import probabilistic.Choice;

/**
 * The lattice approximation problem is an extension of the set-balancing
 * problem. Given an n x n matrix A, all of whose entries are 0 or 1, and a
 * column vector p with n entries all in the interval [0,1]. Find a column
 * vector q E {0,1}^n minimizing the infinity norm of A*(p-q). It has
 * applications in finding an integer approximation of real valued solutions to
 * linear programming relaxations.
 * 
 * @author Zainab Fatmi
 */
public class LatticeApproximation {
	private int[][] A;
	private double[] p;

	/**
	 * Initializes the values of the n x n array and real-valued vector.
	 * 
	 * @param A a square (n x n) matrix, all of whose entries are 0 or 1.
	 * @param p a column vector p with n entries all in the interval [0,1]
	 */
	public LatticeApproximation(int[][] A, double[] p) {
		this.A = A;
		this.p = p;
	}

	/**
	 * Using randomized rounding, find an integer vector q approximating p, while
	 * minimizing the infinity norm of A*(p-q).
	 * 
	 * @param k the number of iterations, >= 1.
	 * @return q, a vector of length n: the integer approximation.
	 */
	public int[] solve(int k) {
		int[] q = solve();
		for (int i = 1; i < k; i++) {
			int[] temp = solve();
			if (infinityNorm(multiply(A, subtract(p, temp))) < infinityNorm(multiply(A, subtract(p, q)))) {
				q = temp; // minimize the infinity norm of A*(p-q)
			}
		}
		return q;
	}

	/**
	 * Using randomized rounding, find an integer vector q approximating p.
	 * 
	 * @return a vector, q, of length n: the integer approximation.
	 */
	private int[] solve() {
		int n = p.length;
		int[] q = new int[n];
		for (int i = 0; i < n; i++) {
			// randomized rounding: set q to 1 with probability p[i]
			q[i] = Choice.make(new double[] { 1 - p[i], p[i] });
		}
		return q;
	}

	/**
	 * Subtracts two arrays and returns the result.
	 * 
	 * @param p an array of size n.
	 * @param q an array of size n.
	 * @return (p - q)
	 */
	private double[] subtract(double[] p, int[] q) {
		int n = p.length;
		double[] result = new double[n];
		for (int i = 0; i < n; i++) {
			result[i] = p[i] - q[i];
		}
		return result;
	}

	/**
	 * Multiplies a square matrix of size n x n and a vector of size n, and returns
	 * the result.
	 * 
	 * @param matrix a n x n matrix.
	 * @param vector an array of size n.
	 * @return matrix * vector.
	 */
	private double[] multiply(int[][] matrix, double[] vector) {
		int n = vector.length;
		double[] result = new double[n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				result[i] += matrix[i][j] * vector[j];
			}
		}
		return result;
	}

	/**
	 * Finds the infinity-norm (the maximum magnitude of all elements) of a vector.
	 * 
	 * @param vector an array.
	 * @return the infinity norm of the array.
	 */
	private double infinityNorm(double[] vector) {
		int n = vector.length;
		double max = Math.abs(vector[0]);
		for (int i = 1; i < n; i++) {
			if (Math.abs(vector[i]) > max) {
				max = Math.abs(vector[i]);
			}
		}
		return max;
	}
}
