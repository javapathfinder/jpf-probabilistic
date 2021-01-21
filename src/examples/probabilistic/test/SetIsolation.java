/*
 * Copyright (C) 2020  Yash Dhamija and Xiang Chen
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

import java.util.Arrays;

/**
 * Calls DisjointSets.check.
 *
 * @author Yash Dhamija
 * @author Xiang Chen
 */
public class SetIsolation {
	private SetIsolation() {}

	/**
	 * Ghost field that captures whether the sample is good.
	 */
	@SuppressWarnings("unused")
	private static boolean isGoodSample = false;

	/**
	 * Calls DisjointSets.check on the input specified as command line arguments.
	 * For example, the command line arguments 21, 4, 4, 5, 10, 15, 20, 4, 11, 17, 19 
	 * capture that the universe has size 21 and hence contains the integers 0, ..., 20,
	 * the subsets have size 4, the first subset contains 5, 10, 15, 20 and the second
	 * subset contains 4, 11, 17, 19.
	 * 
	 * @param args[0] size of the universe
	 * @param args[1] size of the subsets
	 * @param args[2 ..] elements of the two subsets
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: java SetIsolation <size of universe> <size of subsets> <integers of first subset> <integers of second subset>");
			System.exit(0);
		}

		int sizeOfUniverse = Integer.parseInt(args[0]);
		int sizeOfSubset = Integer.parseInt(args[1]);
		if (sizeOfSubset != args.length - sizeOfSubset - 2) {
			System.out.println("Subsets are not of the same size");
			System.exit(0);
		}
		
		int[] first = new int[sizeOfSubset];
		int[] second = new int[sizeOfSubset];
		for (int i = 0; i < sizeOfSubset; i++) {
			first[i] = Integer.parseInt(args[i + 2]);
			if (first[i] < 0 || first[i] >= sizeOfUniverse) {
				System.out.println("First subset is not a subset of the universe");
				System.exit(0);
			}
		}
		int[] sorted = Arrays.copyOf(first, first.length);
		Arrays.sort(sorted);
		for (int i = 0; i < sizeOfSubset; i++) {
			second[i] = Integer.parseInt(args[i + sizeOfSubset + 2]);
			if (second[i] < 0 || second[i] >= sizeOfUniverse) {
				System.out.println("Second subset is not a subset of the universe");
				System.exit(0);
			}
			if (Arrays.binarySearch(sorted, second[i]) >= 0) {
				System.out.println("Subsets are not disjoint");
				System.exit(0);
			}
		}

		isGoodSample = probabilistic.examples.SetIsolation.check(sizeOfUniverse, first, second);
	}
}
