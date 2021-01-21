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

import java.util.List;

import probabilistic.Coin;
import probabilistic.UniformChoice;

/**
* Given a hypergraph, this algorithm provides a 2-colouring (if possible) such that no edge is monochromatic.
* 
* @author Maeve Wildes
*/
public class HypergraphColouring {
	
	/**
	 * Represents a hypergraph.
	 */
	private static class HyperGraph {
		private int V; // number of vertices
		private int E; // number of hyperedges
		private int maxEdgeSize; // the largest edge in the graph
		private Vertex[] vertices;
		private Edge[] edges;
		
		/**
		 * Initializes this hypergraph with the given number of vertices and edges.
		 * 
		 * @param v the number of vertices
		 * @param e the number of edges
		 */
		public HyperGraph(int v, int e) {
			this.V = v;
			this.E = e;
			this.edges = new Edge[E];
			this.vertices = new Vertex[V];
		}
	}
	
	/**
	 * Represents a vertex of hypergraph.
	 */
	private static class Vertex {
		private int colour;
		private int label;
		
		/**
		 * Labels this vertex with the given label.
		 * 
		 * @param label the label of this vertex
		 */
		public Vertex(int label) {
			this.label = label;
		}
	}
	
	/**
	 * Represents an edge of hypergraph.
	 */
	private static class Edge {
		Vertex[] vertices;	
	}
	
	/**
	 * Creates a hypergraph with the given number of vertices and the given number of edges.
	 * The edges of the hypergraph are specified by the given edge list.  Randomly 
	 * colours the vertices.
	 * 
	 * @param vertices the number of vertices of the hypergraph
	 * @param edges the number of edges of the hypergraph
	 * @param edgeList the edges of the hypergraph given as an edge list
	 * @return the created hypergraph if it is monochromatic, null otherwise
	 */
	public HyperGraph colour(int vertices, int edges, List<Integer>[] edgeList) {
		HyperGraph graph = createGraph(vertices, edges, edgeList);

		//randomly initialize the colour of each vertex 
		for (int i = 0; i < graph.V; i++) {
			if (Coin.flip() == 0) {
				graph.vertices[i].colour = 0;			
			} else { 
				graph.vertices[i].colour = 1;
			}
		}
		
		int iterations = 0;

		double maxIterations = (graph.maxEdgeSize * graph.V*graph.V) / 8.0; // if you do more than this number of iterations, with high probability this graph is not 2-colourable
		
		while (monochromatic(graph) != null && iterations < maxIterations) {
			/*
			 * if an edge is monochromatic, randomly select one of its vertices and swap its colour
			 */
			iterations ++;
			Edge e = monochromatic(graph);
			int swap = UniformChoice.make(e.vertices.length);
			if (e.vertices[swap].colour == 0) {
				e.vertices[swap].colour =1;
			}
			else {
				e.vertices[swap].colour = 0;
			}
			
		}
		if (maxIterations < iterations) {
			return null;
		}

		return graph;
	}
	
	/**
	 * Returns an edge in the given graph that is monochromatic.
	 * 
	 * @param graph a hypergraph
	 * @return an edge in the given graph that is monochromatic
	 */
	private Edge monochromatic(HyperGraph graph) {
		for (int i = 0; i < graph.edges.length; i++) {
			if (monochromatic(graph.edges[i])) {
				return graph.edges[i];
			}
		}
		return null;
	}
	
	/**
	 * Checks whether the given edge is monochromatic.
	 * 
	 * @param e an edge of a hypergraph
	 * @return true if the given edge is monochromatic, false otherwise
	 */
	private boolean monochromatic(Edge e) {
		int colour = e.vertices[0].colour;
		for (int i = 1; i < e.vertices.length; i++) {
			if (e.vertices[i].colour != colour) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Creates a hypergraph with the given number of vertices and the given number of edges.
	 * The edges of the hypergraph are specified by the given edge list. 
	 * 
	 * @param vertices the number of vertices of the hypergraph
	 * @param edges the number of edges of the hypergraph
	 * @param edgeList the edges of the hypergraph given as an edge list
	 * @return the created hypergraph 
	 */
	private HyperGraph createGraph(int vertices, int edges, List<Integer>[] edgeList) {		
			HyperGraph graph = new HyperGraph(vertices, edges);
			
			for (int i = 0; i < vertices; i++) {
				graph.vertices[i] = new Vertex(i);
			}
			
			for(int i = 0; i < edges; i++) {
				graph.edges[i] = new Edge();
				graph.edges[i].vertices = new Vertex[edgeList[i].size()];
				int j = 0;
				for (int v : edgeList[i]) {
					graph.edges[i].vertices[j++] = graph.vertices[v];
				}
			}
			graph.maxEdgeSize = graph.edges[0].vertices.length;
			for (int i=0; i<graph.edges.length; i++) {
				if (graph.edges[i].vertices.length > graph.maxEdgeSize) {
					graph.maxEdgeSize = graph.edges[i].vertices.length;
				}
			}
			return graph;
	}
}
