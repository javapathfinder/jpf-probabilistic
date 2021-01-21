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

package probabilistic.examples;

import java.util.ArrayList;
import probabilistic.UniformChoice;

/**
 * A Monte Carlo algorithm for computing the min cut for a undirected graph.
 * 
 * @author Yash Dhamija
 */
public class KargersMinimumCut {
	// an array of arraylist, vertices will represent indices
	private static ArrayList<Integer>[] graph;
	private static int numVertices;
	private static int numEdges;
	private static ArrayList<int[]> edgeList;

	/**
	 * Computes the minimum cut for the provided graph.
	 * 
	 * @param trials   the number of trials
	 * @param vertices the number of vertices
	 * @param edges    the adjacency matrix
	 * @return the minimum cut
	 */
	public static int minCut(int trials, int vertices, int[][] edges) {
		int result = 0;

		for (int t = 1; t <= trials; t++) {
			int currentResult = 0;
			int finalVertex = 0;
			setUp(trials, vertices, edges);

			while (numVertices > 2) {
				// pick an edge at random
				int x = UniformChoice.make(edgeList.size());
				int[] edge = edgeList.get(x);
				edgeList.set(x, edgeList.get(edgeList.size() - 1));
				edgeList.remove(edgeList.size() - 1);

				if (graph[edge[0]] != null && graph[edge[1]] != null) {
					finalVertex = edge[0];
					// merge the endpoints of that edge
					merge(edge[0], edge[1]);
				}
			}
			for (int incidentVertex : graph[finalVertex]) {
				if (graph[incidentVertex] != null) {
					currentResult++;
				}
			}
			if (t == 1)
				result = currentResult;
			else
				result = (currentResult < result) ? currentResult : result;
		}
		return result;
	}

	/**
	 * Constructs the undirected graph.
	 * 
	 * @param trials   the number of trials
	 * @param vertices the number of vertices
	 * @param edges    the adjacency matrix
	 */
	private static void setUp(int trials, int vertices, int[][] edges) {
		numVertices = vertices;
		graph = new ArrayList[numVertices];
		edgeList = new ArrayList<>();

		// constructing the graph
		for (int[] item : edges) {
			edgeList.add(item);
			if (graph[item[0]] == null)
				graph[item[0]] = new ArrayList<Integer>();
			graph[item[0]].add(item[1]);
			if (graph[item[1]] == null)
				graph[item[1]] = new ArrayList<Integer>();
			graph[item[1]].add(item[0]);
		}
	}

	/**
	 * Merges the two given nodes, which are connected by a single edge.
	 * 
	 * @param source a node
	 * @param dest   a node
	 */
	private static void merge(int source, int dest) {
		for (int i : graph[dest])
			if (graph[i] != null && source != i) {
				graph[source].add(i);
				graph[i].add(source);
				// add new edges formed to edgeList
				edgeList.add(new int[] { source, i });
			}

		graph[dest] = null;
		--numVertices;
	}
}
