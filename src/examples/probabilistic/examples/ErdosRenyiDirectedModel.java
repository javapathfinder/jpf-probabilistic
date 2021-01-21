/*
 * Copyright (C) 2020  Maeve Wildes and Xiang Chen
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

import java.util.Arrays;

import probabilistic.Choice;

/**
 * Generates a random directed graph using the approach described in
 * Paul Erdős and Alfréd Rényi. On Random Graphs I, Publicationes Mathematicae, 6: 290–297, 1959.
 * 
 * @author Maeve Wildes
 * @author Xiang Chen
 */
public class ErdosRenyiDirectedModel {
	private boolean[][] matrix; // adjacency matrix

	/**
	 * Initializes this random directed graph with the given number of vertices and
	 * the probability of there being an edge between vertices.
	 * 
	 * @param vertices number of vertices of this graph
	 * @param probability probability of there being an edge between vertices
	 */
	public ErdosRenyiDirectedModel(int vertices, double probability) {
		this.matrix = new boolean[vertices][vertices];
		double[] choices = { probability, 1 - probability };
		for (int i = 0; i < vertices; i++) {
			for (int j = 0; j < vertices; j++) {
				this.matrix[i][j] = Choice.make(choices) == 0;		
			}
		}
	}

	/**
	 * Depth first search, starting from the given vertex, setting the
	 * states in the given array when visited. 
	 * 
	 * @param start vertex at which the depth first search starts
	 * @param visited updated with states visited during the depth first search
	 */
	private void dephFirstSearch(int start, boolean[] visited) {
		visited[start] = true;
		for (int i = 0; i < matrix.length; i++) {
			if (this.matrix[start][i] && !visited[i]) { 
				dephFirstSearch(i, visited); 
			} 
		}
	}

	/**
	 * Tests whether this random directed graph is connected.
	 * 
	 * @return true if this random directed graph is connected, false otherwise.
	 */
	public boolean isConnected() {
		int vertices = matrix.length; // number of vertices
		boolean[] visited = new boolean[vertices]; // has vertex been visited by depth first search
		for (int i = 0; i < vertices; i++) {
			Arrays.fill(visited, false);
			this.dephFirstSearch(i, visited);
			for (int j = 0; j < visited.length; j++) {
				if (!visited[j]) {
					return false;
				}
			}
		}
		return true;
	}
}
