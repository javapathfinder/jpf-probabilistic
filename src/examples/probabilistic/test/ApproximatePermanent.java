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
 * A Monte-Carlo algorithm for estimating the permanent of a 0-1 matrix.
 * 
 * @author Yash Dhamija
 */
public class ApproximatePermanent {

	/**
	 * @param number of trials (500 would be good enough for up till 3x3 matrix,
	 * for matrices with size 4,5,6 10000 would be a good input).
	 * A a 00-1 matrix, whose rows are passed as strings 
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: ApproximatePermanent <int number of trials> <string rows of matrix>");
			System.exit(0);
		}
		
		int n = args.length-1;
		int[][] A = new int[n][n];
		
		for(int i = 1; i < args.length; i++) {
			char[] c = args[i].toCharArray();
			for (int j = 0; j < c.length; j++) {
				A[i-1][j] = Character.getNumericValue(c[j]);
			}	
		}
		
		probabilistic.examples.ApproximatePermanent.getPermanent(A, Integer.parseInt(args[0]));
	}
}
