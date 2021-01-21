/*
 * Copyright (C) 2020  Franck van Breugel
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
 * This app calls SetCover.find.
 * 
 * @author Franck van Breugel
 */
public class SetCover {
	
	/**
	 * Given given a matrix A, the elements of which are either 0 or 1, 
	 * determines if there exists a vector x such that the dot product 
	 * of each row of A with x is positive while minimizing x.
	 * 
	 * @param args[0] the number of trials to be run
	 * @param args[1] the size of the matrix
	 * @param args[2..] the elements of the matrix
	 */
	public static void main(String[] args) {
		
		int trials = Integer.parseInt(args[0]);
		int size = Integer.parseInt(args[1]);
		int[][] matrix = new int[size][size];
		int i = 2;
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				matrix[r][c] = Integer.parseInt(args[i]);
				i++;
			}
		}
		
		probabilistic.examples.SetCover.find(matrix, trials);
	}
}
