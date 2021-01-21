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
 * This app calls the Routing class.
 * 
 * @author Yash Dhamija
 */
public class RandomizedRouting {
	private RandomizedRouting() {}

	/**
	 * Processes the command line arguments and calls Routing.main.
	 * 
	 * @param args[0] the dimension of the cube
	 * @pre. args[0] >= 1
	 * @param args[1..] permutation of the nodes
	 */
	public static void main(String[] args) {
		if (args.length < 3 || Math.pow(2, Integer.parseInt(args[0])) + 1 != args.length) { 
			System.out.println("Usage: java RandomizedRouting <n, dimension of the cube> <2^n permutations>");
			System.exit(0);
		}
		
		int n = Integer.parseInt(args[0]);
		int[] permutations = new int[(int) Math.pow(2, n)];
		for (int i = 1; i < args.length; i++) {
			permutations[i - 1] = Integer.parseInt(args[i], 2);
		}
		
		probabilistic.examples.RandomizedRouting.main(n, permutations);
	}
}
