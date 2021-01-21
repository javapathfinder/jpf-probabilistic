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
 * Attempts to find an independent set of a graph.
 * 
 * @author Yash Dhamija
 */
public class IndependentSet {
	private IndependentSet() {}

	/**
	 * For the graph given by the command line arguments, attempts to find an independent set.
	 *  
	 * @param args[0] the number of vertices of the graph
	 * @param args[1] the number of edges of the graph
	 * @param args[2..] the edges of the graph
	 */
	public static void main(String[] args) {
		if (args.length < 4) {
			System.out.println("Usage: java IndependentSet <number of vertices> <number of edges> <edges>");
			System.exit(0);
		}
		int edges = Integer.parseInt(args[1]);
		if (args.length - 2 != 2 * edges) {
			System.out.println("Invalid input: Wrong number of edges provided");
			System.exit(0);
		}
		
		int[][] edgeList = new int[edges][2];
		for (int i = 2, j = 0; i < args.length && j < edges; i++, j++)
			edgeList[j] = new int[] {Integer.parseInt(args[i]), Integer.parseInt(args[++i])};	
		
		probabilistic.examples.IndependentSet.getIndependentSet(Integer.parseInt(args[0]), edges, edgeList);
	}
}
