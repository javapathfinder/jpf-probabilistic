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

import java.util.LinkedList;
import java.util.List;

import probabilistic.Choice;
import probabilistic.UniformChoice;

/**
 * Attempts to find a path from the given source vertex to the given target vertex
 * in the given directed graph.
 * 
 * @author Maeve Wildes
 */
public class DirectedReachability {
	private DirectedReachability() {}

	/**
	 * Checks whether there is a path from the given source vertex to the given target vertex
	 * in the given directed graph.
	 * 
	 * @param adjacent the adjacency list of the directed graph
	 * @param source the source vertex
	 * @param target the target vertex
	 */
	public static boolean findPath(List<List<Integer>> adjacent, int source, int target) {
		int vertices = adjacent.size();
		double probability = Math.pow(2, -Math.log(Math.pow(vertices, vertices)) / Math.log(2));
		double[] choices = { probability, 1 - probability };
		
		List<Integer> path = new LinkedList<Integer>();
		int current = source;
		while (path.size() < vertices) {
			path.add(current);
			if (adjacent.get(current).size() == 0) {
				path.clear();
				current = source;
				if (Choice.make(choices) == 0) {
					return false;
				}
			} else {
				int choice = UniformChoice.make(adjacent.get(current).size());
				int next = adjacent.get(current).get(choice);
				if (next == target) {
					return true;
				}
				current = next;
			}
		}

		if (Choice.make(choices) == 0) {
			return false;
		} else {
			path.clear();
			return findPath(adjacent, source, target);
		}
	}
}
