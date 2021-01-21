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

package probabilistic.examples;

import probabilistic.Coin;

/**
 * A game tree is a binary tree of even height.  The internal nodes at odd distance
 * from the root are OR nodes and the internal nodes at even distances from the root
 * are AND nodes.  The leaves are labelled with booleans.  These are the values of the
 * leaves.  The value of an OR node is the disjunction of the values of its children.
 * The value of an AND node is the conjunction of the values of its children.
 * 
 * @author Franck van Breugel
 */
public class GameTree {
	private boolean[] leaf; // values of the leaves of the tree

	/**
	 * Initializes the values of the leaves of this tree.
	 * 
	 * @param leaf values of the leaves of this tree.
	 * @pre. leaf.length = Math.pow(2, height) for some height > 0 and height % 2 == 0.
	 */
	public GameTree(boolean[] leaf) {
		this.leaf = leaf;
	}

	/**
	 * Constant that is used avoid rounding errors when computing log and pow.
	 */
	private static final double EPSILON = 1e-10;

	/**
	 * Returns the value of this tree.
	 * 
	 * @return the value of this tree.
	 */
	public boolean evaluate() {
		final int height = (int) (Math.log(leaf.length) / Math.log(2) + EPSILON);
		return this.evaluate(0, height);
	}

	/**
	 * Returns the value of the subtree rooted at the node at the given level corresponding to the  
	 * segment of leaf starting at the given index.
	 * 
	 * @param index the first index of the relevant segment of leaf.
	 * @param level the level of the tree.
	 * @param random the Random object to randomize the order of the evaluation of the subtrees.
	 * @return the value of the subtree rooted at the node at the given level corresponding to the  
	 * segment of leaf starting at the given index.
	 */
	private boolean evaluate(int index, int level) {
		if (level == 0) { // leaf
			return leaf[index];
		} else if (level % 2 == 0) { // AND node
			if (Coin.flip() == 1) { // left subtree first
				if (this.evaluate(index, level - 1)) {
					return this.evaluate(index + (int) (Math.pow(2, level - 1) + EPSILON), level - 1);
				} else {
					return false;
				}
			} else { // right subtree first
				if (this.evaluate(index + (int) (Math.pow(2, level - 1) + EPSILON), level - 1)) {
					return this.evaluate(index, level - 1);
				} else {
					return false;
				}
			}
		} else { // OR node
			if (Coin.flip() == 1) { // left subtree first
				if (this.evaluate(index, level - 1)) {
					return true;
				} else {
					return this.evaluate(index + (int) (Math.pow(2, level - 1) + EPSILON), level - 1);
				}
			} else { // right subtree first
				if (this.evaluate(index + (int) (Math.pow(2, level - 1) + EPSILON), level - 1)) {
					return true;
				} else {
					return this.evaluate(index, level - 1);
				}
			}
		}
	}
}
