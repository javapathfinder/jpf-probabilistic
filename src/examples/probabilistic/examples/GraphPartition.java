/*
 * Copyright (C) 2020  Maeve Wildes
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
import java.util.List;

import probabilistic.Coin;

/**
 * Partitions the vertices of a graph into two sets so that at least half of 
 * the edges have one end point in the one set and one end point in the other 
 * set.
 * 
 * @author Maeve Wildes
 */
public class GraphPartition {
	private GraphPartition() {}

	/**
	 * An edge of a graph.
	 */
	public static class Edge { 
		private int source;
		private int target;

		/**
		 * Initializes this edge with the given end points.
		 * 
		 * @param source the source of this edge
		 * @param target the target of this edge
		 */
		public Edge(int source, int target) {
			this.source = source;
			this.target = target;
		}
	}

	/**
	 * Returns a subset of the vertices such that this subset and its complement 
	 * partition the vertices so that at least half of the edges have one end point 
	 * in the subset and one end point in its complement.
	 * 
	 * @param vertices the number of vertices of the graph
	 * @param edgeList the edges of the graph
	 * @return a subset of the vertices such that this subset and its complement 
	 * partition the vertices so that at least half of the edges have one end point 
	 * in the subset and one end point in its complement.
	 */
	public static List<Integer> partition(int vertices, List<Edge> edgeList) {
		List<Integer> subset;
		do {
			subset = new ArrayList<Integer>();
			for (int v = 0; v < vertices; v++) {
				// randomly assign each vertex to the subset (or its complement) 
				if (Coin.flip() == 0) {
					subset.add(v);
				}
			}
		} while (!isMajority(edgeList, subset));
		return subset;
	}

	/**
	 * Checks whether the given subset of vertices of the given graph partitions 
	 * the vertices so that at least half of the edges have one end point 
	 * in the subset and one end point in its complement.
	 * 
	 * @param edgeList the edges of the graph
	 * @param subset a set of vertices
	 * @return true if the given subset of vertices of the given graph partitions 
	 * the vertices so that at least half of the edges have one end point 
	 * in the subset and one end point in its complement, false otherwise
	 */
	private static boolean isMajority(List<Edge> edgeList, List<Integer> subset) {
		int count = 0;
		for (int i = 0; i < edgeList.size(); i++) {
			if ((subset.contains(edgeList.get(i).source) && !subset.contains(edgeList.get(i).target)) || 
					(!subset.contains(edgeList.get(i).source) && subset.contains(edgeList.get(i).target))) {
				count ++;
			}
		}
		return count >= edgeList.size() / (double) 2;
	}
}
