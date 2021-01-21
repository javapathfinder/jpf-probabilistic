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

import probabilistic.Choice;
import probabilistic.Coin;

/**
 * This algorithm takes as input a d-regular graph (a graph on n vertices with nd/2 edges) and finds an independent set.
 * This is a Monte Carlo algorithm and will find an independent set with probability n(1-epsilon)/6 for epsilon small.
 * 
 * @author Maeve Wildes
 */
public class IndependentSet {
	private IndependentSet() {}

	/**
	 * Represents a graph.
	 */
	private static class Graph {
		private int vertices;
		private int edges; 
		private List<Edge> edge;
		private List<Integer> vertex;

		/**
		 * Initializes this graph with the given number of vertices and edges.
		 * 
		 * @param vertices the number of vertices of this graph
		 * @param edges the number of edges of this graph
		 */
		private Graph(int vertices, int edges) {
			this.vertices = vertices;
			this.edges = edges;
			this.edge = new ArrayList<Edge>(edges);
			this.vertex = new ArrayList<Integer>(vertices);
		}		
	}

	/**
	 * Represent an edge of a graph.
	 */
	private static class Edge { 
		private int source;
		private int destination;

		/**
		 * Initializes this edge with the given source and destination.
		 * 
		 * @param source the source of this edge
		 * @param destination the destination of this edge
		 */
		private Edge(int source, int destination) {
			this.source = source;
			this.destination = destination;
		}
	}

	/**
	 * Returns an independent set for the graph with the given number of vertices, the
	 * given number of edges, where the edges are specified by the given edge list.
	 * 
	 * @param vertices the number of vertices of the graph
	 * @param edges the number of edges of the graph
	 * @param edgeList the edges of the graph
	 * @return an independent set for the graph
	 */
	public static List<Integer> getIndependentSet(int vertices, int edges, int[][] edgeList) {
		Graph graph = createGraph(vertices, edges, edgeList);
		double degree = 2 * graph.edges / graph.vertices;
		for (int v = 0; v < graph.vertices; v++) {
			// delete each vertex with probability 1-1/d 
			if (Choice.make(new double[] {1 / degree, 1 - (1 / degree)}) == 1) {
				deleteVertex(graph, v);
			}		
		}

		while (graph.edges > 0) {
			if (Coin.flip() == 0) {
				deleteVertex(graph, graph.edge.get(0).source);
			} else {
				deleteVertex(graph, graph.edge.get(0).destination);
			}
		}
		return graph.vertex;
	}

	/**
	 * Deletes the given vertex from the given graph.
	 * 
	 * @param graph the graph
	 * @param v the vertex
	 */
	private static void deleteVertex(Graph graph, int v) {
		graph.vertex.remove(Integer.valueOf(v));
		for (int j = 0; j < graph.edges; j++) {
			if (graph.edge.get(j).source == v || graph.edge.get(j).destination == v) {
				graph.edge.remove(j);
				graph.edges--;
				j--;
			}
		}
		graph.vertices--;
	}

	/**
	 * Generates a graph with the given number of vertices, the given number of edges, 
	 * where the edges are specified by the given edge list.
	 * 
	 * @param vertices the number of vertices of the graph
	 * @param edges the number of edges of the graph
	 * @param edgeList the edges of the graph
	 * @return the generated graph
	 */
	private static Graph createGraph(int vertices, int edges, int[][] edgeList) {
		Graph graph = new Graph(vertices, edges);

		for(int e = 0; e < edges; e++) {
			int[] edge = edgeList[e];
			graph.edge.add(e, new Edge(edge[0], edge[1]));
		}

		for (int v = 0; v < vertices; v++) {
			graph.vertex.add(v);
		}
		return graph;
	}

	/**
	 * Checks whether the given set is independent for the graph with the given number of vertices, 
	 * the given number of edges, where the edges are specified by the given edge list.
	 * 
	 * @param set a set of vertices of the graph
	 * @param vertices the number of vertices of the graph
	 * @param edges the number of edges of the graph
	 * @param edgeList the edges of the graph
	 * @return true if the given set is independent for the graph, false otherwise
	 */
	@SuppressWarnings("unused")
	private static boolean isIndependent(List<Integer> set, int vertices, int edges, int[][] edgeList) {
		Graph graph = createGraph(vertices, edges, edgeList);
		for (int i = 0; i < set.size(); i++) {
			for (int j = i + 1; j < set.size(); j++){
				for (int k = 0; k < graph.edge.size(); k++) {
					if (graph.edge.get(k).source == set.get(i) && graph.edge.get(k).destination == set.get(j) 
							|| graph.edge.get(k).source == set.get(j) && graph.edge.get(k).destination == set.get(i)) {
						return false;
					}
				}
			}
		}
		return true;			
	}
}
