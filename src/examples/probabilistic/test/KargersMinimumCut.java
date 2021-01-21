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

/**
 * This app calls KargersMinimumCut.minCut.
 * 
 * @author Yash Dhamija
 * @author Xiang Chen
 */
public class KargersMinimumCut {

	private static boolean falsePositive = false;

	/**
	 * Parses input as arguments for MinCut.
	 * 
	 * @param args An array of string, where first element represents the number of
	 *             trials, second is num of vertices, third for number of edges, and
	 *             then followed by edges. For e.g, 4 5 5 0 1 0 2 1 2 1 3 2 3 means
	 *             4 trials, 5 vertices and 5 edges, 0->1, 0->2 ...
	 */
	public static void main(String[] args) {
		if (args.length < 5) {
			System.out.println(
					"Usage: java probabilistic.test.KargersMinimumCut <number of trials> <number of vertices> <number of edges> <edge>+");
			System.exit(0);
		}
		int edges = Integer.parseInt(args[2]);
		if (args.length - 3 != 2 * edges) {
			System.out.println("Invalid input: Wrong number of edges provided");
			System.exit(0);
		}

		int[][] edgeList = new int[edges][2];

		for (int i = 3, j = 0; i < args.length && j < edges; i++, j++) {
			edgeList[j] = new int[] { Integer.parseInt(args[i]), Integer.parseInt(args[++i]) };
		}

		// int result1 = MinCutNormal.minCut(Integer.parseInt(args[0]),
		// Integer.parseInt(args[1]), edgeList);

		int result2 = probabilistic.examples.KargersMinimumCut.minCut(Integer.parseInt(args[0]), Integer.parseInt(args[1]), edgeList);

		falsePositive = !(result2 == 3);
	}
}
