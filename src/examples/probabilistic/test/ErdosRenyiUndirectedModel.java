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

/*
 * This app creates a random undirected graph.
 * 
 * @author Yash Dhamija
 * @author Xiang Chen
 */
public class ErdosRenyiUndirectedModel {
	private ErdosRenyiUndirectedModel() {}
	
	/**
	 * Creates a random undirected graph.
	 * 
	 * @param args[0] the number of vertices of the graph 
	 * @param args[1] the probability of there being an edge between any two vertices.
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java ErdosRenyiUndirectedModel <number of vertices> <probability of an edge between any two vertices>");
			System.exit(0);
		}
		probabilistic.examples.ErdosRenyiUndirectedModel graph = new probabilistic.examples.ErdosRenyiUndirectedModel(Integer.parseInt(args[0]), Double.parseDouble(args[1]));
		graph.isConnected();
	}
}
