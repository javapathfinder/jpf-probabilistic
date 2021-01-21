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
 * Calls PerfectMatching.get.
 *  
 *  @author Yash Dhamija
 */
public class PerfectMatching {
	private PerfectMatching() {}

	/**
	 * Finds a perfect matching for the given graph.
	 * 
	 * @param args[0] number of trials run by the algorithm
	 * @param args[1..] elements of the matrix
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: java PerfectMatching <number of trials> <elements of matrix>");
			System.exit(0);
		}
		
		int size = (int) Math.sqrt(args.length - 1);
		int[][] matrix = new int[size][size];
		
		int i = 1;
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				matrix[c][r] = Integer.parseInt(args[i]);
				i++;
			}
		}
		
		probabilistic.examples.PerfectMatching.Graph graph = new probabilistic.examples.PerfectMatching.Graph(size, size, matrix);
		int trials = Integer.parseInt(args[0]);
		probabilistic.examples.PerfectMatching.get(graph, trials);
	}
}
