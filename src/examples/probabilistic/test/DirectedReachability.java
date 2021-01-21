/*
 * Copyright (C) 2020  Yash Dhamija, Xiang Chen and Franck van Breugel
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Attempts to find a path in a directed graph.
 * 
 * @author Yash Dhamija
 * @author Xiang Chen
 * @author Franck van Breugel
 */
public class DirectedReachability {
	private DirectedReachability() {}

	/**
	 * Ghost field that captures whether findPath returns the incorrect result.
	 */
	@SuppressWarnings("unused")
	private static boolean incorrect = false;

	/**
	 * Attempts to find a path from the given source vertex to the given target vertex
	 * in the given directed graph
	 * 
	 * @param args[0] the number of vertices of the directed graph
	 * @param args[1] the source vertex
	 * @param args[2] the target vertex
	 * @param args[3..] the edges (source-target pairs)
	 */
	public static void main(String[] args) {		
		if (args.length < 3) {
			System.out.println("Usage: java DirectedReachability <number of vertices>\n"
					+ "<source vertex>\n"
					+ "<target vertex>\n"
					+ "<edges>");
			System.exit(0);
		}

		int vertices = Integer.parseInt(args[0]);
		int source = Integer.parseInt(args[1]);
		int target = Integer.parseInt(args[2]);

		if (source < 0 || source > vertices - 1 || target < 0 || target > vertices - 1) {
			System.out.println("Wrong source or target provided.");
			System.exit(0);
		}

		List<List<Integer>> adjacent = new ArrayList<List<Integer>>(vertices);
		for (int v = 0; v < vertices; v++) {
			adjacent.add(new LinkedList<Integer>());
		}
		for (int i = 3; i < args.length; i = i + 2) {
			int u = Integer.parseInt(args[i]);
			int v = Integer.parseInt(args[i + 1]);
			adjacent.get(u).add(v);
		}

		boolean[] visited = new boolean[vertices];
		incorrect = probabilistic.examples.DirectedReachability.findPath(adjacent, source, target) != depthFirstSearch(adjacent, source, target, visited);
	}

	/**
	 * Checks whether the given target vertex is reachable from the given start vertex in the given directed graph
	 * via vertices that have not been visited yet.
	 * 
	 * @param adjacent adjacent the adjacency list of the directed graph
	 * @param start the start vertex
	 * @param target the target vertex
	 * @param visited whether a vertex has been visited
	 * @return true if the given target vertex is reachable from the given start vertex in the given directed graph
	 * via vertices that have not been visited yet, false otherwise
	 */
	private static boolean depthFirstSearch(List<List<Integer>> adjacent, int start, int target, boolean[] visited) {
		visited[start] = true;
		if (start == target) {
			return true;
		}
		for (Integer vertex : adjacent.get(start)) {
			if (!visited[vertex]) { 
				if (depthFirstSearch(adjacent, vertex, target, visited)) {
					return true;
				}
			} 
		}
		return false;
	}
}
