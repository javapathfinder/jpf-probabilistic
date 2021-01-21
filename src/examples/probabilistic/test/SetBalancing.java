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
 * This app calls SetBalancing.
 * 
 * @author Yash Dhamija
 */
public class SetBalancing {
	private SetBalancing() {}

	/**
	 * Given a matrix A, all of whose entries are 0 or 1, finds a column vector b, all whose 
	 * entries are -1 or 1, that minimizes the infinity norm of A * b.
	 * 
	 * @param args[0] the number of trials of the algorithm to be run
	 * @param args[1] the size of the matrix
	 * @param args[2..] the elements of the matrix
	 */
	public static void main (String[] args) {
		if (args.length < 3 || Math.pow(Integer.parseInt(args[1]), 2) + 2 != args.length) {
			System.out.println("Usage: java SetBalancing <number of trials> <size of matrix> <elements of matrix>");
			System.exit(0);
		}
		
		int trials = Integer.parseInt(args[0]);
		int size = Integer.parseInt(args[1]);
		int[][] matrix = new int[size][size];
		for (int i = 2; i < args.length; i++) {
			matrix[(i - 2) / size][(i - 2) % size] = Integer.parseInt(args[i]);
		}
		
		probabilistic.examples.SetBalancing balance = new probabilistic.examples.SetBalancing(matrix);
		balance.infinityNorm(balance.multiply(matrix, balance.divide(trials)));
	}
}
