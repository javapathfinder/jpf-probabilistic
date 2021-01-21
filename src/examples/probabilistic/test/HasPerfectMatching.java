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
 * This app calls PerfectMatchingIdentity.search.
 *
 * @author Yash Dhamija
 */
public class HasPerfectMatching {
	private HasPerfectMatching() {}

	/**
	 * Determines whether or not the given graph has a perfect matching.
	 * 
	 * @param args[0] the number of vertices of the graph
	 * @param args[1] the number of trials of the algorithm
	 * @param args[2..] the edges of the graph
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: java HasPerfectMatching <number of vertices> <number of trials> <edges>");
			System.exit(0);
		}
		int vertices = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);
		
		int[][] matrix = new int[vertices][vertices];
		for (int i = 2; i < args.length; i = i + 2) {
			int source = Integer.parseInt(args[i]);
			int destination = Integer.parseInt(args[i + 1]);
			matrix[source][destination] = 1;
			matrix[destination][source] = 1;
		}
		
		probabilistic.examples.HasPerfectMatching.search(vertices, trials, matrix);
	}
}
