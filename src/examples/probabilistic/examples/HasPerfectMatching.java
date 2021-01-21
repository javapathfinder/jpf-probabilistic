/*
 * Copyright (C) 2020  Maeve Wildes
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
 * Given a bipartite graph, determines whether or not it has a perfect matching. 
 * This is a Monte Carlo algorithm and the probability of error is (1/2)^t, where 
 * t is the number of trials
 *
 * @author Maeve Wildes
 */
public class HasPerfectMatching {
	
	/** 
	 * 
	 * 
	 * @param vertices the number of vertices in the graph
	 * @param trials the number of repetitions of the algorithm
	 * @param matrix
	 */
	public static boolean search(int vertices, int trials, int[][] matrix) {
		int determinant = determinant(matrix, vertices);
		if (determinant != 0) {
			return true;
		} else {
			boolean found = false;
			for (int t = 0; t < trials && !found; t++) {
				// set each entry matrix[r][c] to matrix[r][c] * x[r][c], where x[r][c] is assigned a number 
				// between 1 and 2n uniformly at random
				for (int r = 0; r < vertices; r++) {
					for (int c = 0; c < vertices; c++) {
						if (matrix[r][c] != 0) {
							matrix[r][c] *= UniformChoice.make(vertices) + 1;
						}
					}
				}
				determinant = determinant(matrix, vertices);
				found = determinant != 0;
			}
			return found;
		}

	}

	// recursive method to find the determinant of a nxn matrix
	/**
	 * 
	 * @param matrix
	 * @param n
	 * @return
	 */
	private static int determinant(int[][] matrix, int n) {
		int det = 0; // determinant
		if (n == 1) {
			return matrix[0][0];
		}
		int[][] cofactor = new int[n-1][n-1];
		int sign = 1;
		for (int col = 0; col < n; col++) {
			getCofactor(matrix, cofactor, 0, col, n);
			det += sign * matrix[0][col] * determinant(cofactor, n - 1);
			sign *= -1;
		}

		return det;
	}
	
	/**
	 * 
	 * @param matrix
	 * @param cofactor
	 * @param curRow
	 * @param curCol
	 * @param n
	 */
	private static void getCofactor(int [][] matrix, int [][] cofactor, int curRow, int curCol, int n) {
		for (int row=1; row<n; row ++) {
			int col=0;
			for (int k =0; k<n-1; k++) {
				if (k != curCol) {
					cofactor[row-1][k]=matrix[row][col];
					col++;
				}
				else {
					cofactor[row-1][k]=matrix[row][col+1];
					col=col+2;
				}
			}
		}
	}
}
