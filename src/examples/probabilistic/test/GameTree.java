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

/**
 * This class calls GameTree class. 
 * 
 * @author Yash Dhamija
 */
public class GameTree {

	/**
	 * Processes the game tree of which the leaves are provided as command line arguments.
	 * 
	 *  @param args the leaves of the game tree
	 *  @pre. args.length = Math.pow(2, height) for some height > 0 and height % 2 == 0
	 */
	public static void main(String[] args) {
		boolean[] leaf = new boolean[args.length];
		for (int i = 0; i < args.length; i++) {
			leaf[i] = Boolean.parseBoolean(args[i]);
		}
		probabilistic.examples.GameTree tree = new probabilistic.examples.GameTree(leaf);
		tree.evaluate();
	}
}
