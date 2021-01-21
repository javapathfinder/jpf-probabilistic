/*
 * Copyright (C) 2020  Franck van Breugel
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
 * Calls HamiltonianCycle.find.
 * 
 * @author Franck van Breugel
 */
public class HamiltonianCycle {
	private HamiltonianCycle() {}
	
	/**
	 * Calls HamiltonianCycle.find on the graph given as command line arguments.
	 * 
	 * @param args[0] the number of vertices of the graph
	 * @param args[1..] the edges of the graph
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: java HamiltonianCycle <number of vertices> <edges>");
		} else {
			int vertices = Integer.parseInt(args[0]);
			List<LinkedList<Integer>> adjacent = new ArrayList<LinkedList<Integer>>();
			for (int v = 0; v < vertices; v++) {
				adjacent.add(new LinkedList<Integer>());
			}
			for (int i = 1; i < args.length; i = i + 2) {
				int source = Integer.parseInt(args[i]);
				int target = Integer.parseInt(args[i + 1]);

				adjacent.get(source).add(target);
				adjacent.get(target).add(source);
			}
			probabilistic.examples.HamiltonianCycle.find(vertices, adjacent);
		}
	}
}
