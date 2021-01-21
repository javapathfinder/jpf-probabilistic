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
 * Attempts to find for a hypergraph a 2-colouring (if possible) such that no edge is monochromatic.
 * 
 * @author Yash Dhamija
 */
public class HypergraphColouring {
	private HypergraphColouring() {}
	
	/**
	 * For the graph given by the command line arguments, provides a 2-colouring (if possible) such that no edge is monochromatic.
	 * 
	 * @param args[0] the number of vertices of the hypergraph
	 * @param args[1] the number of edges of the hypergraph
	 * @param args[2..] the edges of the hypergraph
	 */
	public static void main(String[] args) {
		if (args.length < 4) {
			System.out.println("Usage: java HypergraphColouring <number of vertices> <number of edges> <edges>");
			System.exit(0);
		}
		
		int edges = Integer.parseInt(args[1]);
		if (args.length - 2 != edges) {
			System.out.println("Wrong number of edges given!");
			System.exit(0);
		}
		
		// an array of edges, a single edge (element) is represented by an arraylist of vertices
		List<Integer>[] edgeList = new ArrayList[edges];
		for (int i = 2; i < args.length; i++) {
			char[] edge = args[i].toCharArray();
			edgeList[i - 2] = new ArrayList<Integer>();
			for (char c : edge) {
				edgeList[i - 2].add(Character.getNumericValue(c));
			}
		}
			
		probabilistic.examples.HypergraphColouring hype = new probabilistic.examples.HypergraphColouring();
		hype.colour(Integer.parseInt(args[0]), edges, edgeList);
	}
}
