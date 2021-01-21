/*
 * Copyright (C) 2020  Yash Dhamija
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

package probabilistic.test;

/**
 * Given a n x n matrix A and a column vector p, this app calls
 * LatticeApproximation.solve to find a column vector q minimizing the
 * infinity norm of A * (p - q).
 * 
 * @author Yash Dhamija
 */
public class LatticeApproximation {
	private LatticeApproximation() {}

	/**
	 * Given a n x n matrix A and a column vector p, finds a column vector q
	 * minimizing the infinity norm of A * (p - q).
	 * 
	 * @param args[0] the dimension of the matrix and vector
	 * @param args[1..dimension] the elements of the vector
	 * @param args[dimension + 1...] the elements of the matrix (row by row)
	 */
	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Usage: java LatticeApproximation <dimension of matrix/vector> <elements of vector> <elements of matrices>");
			System.exit(0);
		}
		int n = Integer.parseInt(args[0]);
		if (args.length - 1 != n * n + n) {
			System.out.println("Invalid arguments");
			System.exit(0);
		}
		
		double[] vector = new double[n];
		for (int i = 1; i < n; i++) {
			vector[i - 1] = Double.parseDouble(args[i]);
		}
		
		int i = n + 1;
		int[][] matrix = new int[n][n];
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				matrix[r][c] = Integer.parseInt(args[i]);
				i++;
			}
		}
		
		(new probabilistic.examples.LatticeApproximation(matrix, vector)).solve(1);
	}
}
