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
 * Given two n x n boolean matrices A and B, this program finds a witness matrix W for P=AB, such that each 
 * entry W(i, j) is a witness for P(i, j), if any, and 0 if there is no witness.
 * 
 * @author Yash Dhamija
 */
public class BooleanProductWitnessMatrix {

	/**
	 * 
	 * @param args[0] is the dimension for two square matrices
	 * @param args[1..] the elements of these matrices
	 */
	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Usage: BooleanProductWitnessMatrix <dimension of square matrix> <elements of matrices>");
			System.exit(0);
		}
		
		int n = Integer.parseInt(args[0]);
		if (args.length != 2 * (n * n) + 1) {
			System.out.println("Invalid numbers of elements provided");
			System.exit(0);
		}
		
		int[][] A = new int[n][n];
		int[][] B = new int[n][n];
		
		int i = 1;
		for (;i <= n * n; i = i + n) {
			int r = (i - 1) / n;
			for(int j = 0; j < n; j++)
				A[r][j] = Integer.parseInt(args[i + j]);
		}

		for (int m = i; m < args.length; m = m + n) {
			int r = (m - i) / n;
			for(int j = 0; j < n; j++)
				B[r][j] = Integer.parseInt(args[m + j]);
		}
		
		probabilistic.examples.BooleanProductWitnessMatrix.witnessMatrix(A, B);
	}
}
