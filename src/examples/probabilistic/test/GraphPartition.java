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

import java.util.ArrayList;
import java.util.List;

/**
 * Calls GraphPartition.partition.
 * 
 * @author Yash Dhamija
 */
public class GraphPartition {
	private GraphPartition() {}

	/**
	 * Calls GraphPartition.partition for the undirected graph given as command line arguments.
	 *  
	 * @param args[0] the number of vertices of the graph
	 * @param args[1..] the edges of the graph
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: java GraphPartition <number of vertices> <edges>");
			System.exit(0);
		}
		if (args.length % 2 == 0) {
			System.out.println("Invalid input: Wrong number of edges provided");
			System.exit(0);
		}
		
		int vertices = Integer.parseInt(args[0]);
		List<probabilistic.examples.GraphPartition.Edge> edgeList = new ArrayList<probabilistic.examples.GraphPartition.Edge>();
		
		for (int i = 1; i < args.length; i = i + 2) {
			edgeList.add(new probabilistic.examples.GraphPartition.Edge(Integer.parseInt(args[i]), Integer.parseInt(args[i + 1])));	
		}

		probabilistic.examples.GraphPartition.partition(vertices, edgeList);
	}
}
