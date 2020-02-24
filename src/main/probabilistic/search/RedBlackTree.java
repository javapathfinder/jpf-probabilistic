/*
 * Copyright (C) 2013  Franck van Breugel and Steven Xu
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

package probabilistic.search;

/**
 * A distribution is implemented by means of a red-black tree.
 * A node of the tree contains an element and its weight.
 * The weights are the keys.  Hence, a preorder traversal
 * visits the nodes by increasing weight.  Whenever we
 * have to add weights of a subtree, we use a preorder
 * traversal to minimize rounding errors.
 *
 * The implementation of the red-black tree is directly based on the 
 * algorithms described in Chapter 14 of <BR>
 * Thomas H. Cormen, Charles E. Leiserson and Ronald L. Rivest.
 * <I>Introduction to Algorithms</I>.
 * The MIT Press, Cambridge, Massachussets, USA. 1990.
 *
 * @author Franck van Breugel
 * @author Steven Xu
 */
public class RedBlackTree<T> implements Distribution<T> {
	/**
	 * This class represents a node of a red-black tree.  A node has 
	 * an element and the weight of the element.  We also keep 
	 * track of the sum of the weights of the subtree rooted at 
	 * a node.  A node has a left child, a right child, and a parent.
	 * A node is either red or black.
	 */
	private static class Node<T> {
		T element;      // element stored in this node
		double weight;  // weight of element stored in this node
		double sum;     // sum of weights of subtree rooted at this node
		Node<T> left;   // left child of this node
		Node<T> right;  // right child of this node
		Node<T> parent; // parent of this node
		boolean isRed;  // whether this node is red

		/**
		 * Initializes a leaf.
		 */
		private Node() {}

		/**
		 * Initializes this node with the given element, weight and 
		 * colour.  The left and right child of this node are black leaves.
		 *
		 * @param element the element of this node.
		 * @pre. element != null
		 * @param weight the weight of this node.
		 * @pre. weight > 0
		 * @param isRed whether this node is red.
		 */
		private Node(T element, double weight, boolean isRed) {
			super();
			this.element = element;
			this.weight = weight;
			this.sum = weight;
			this.left = RedBlackTree.LEAF;
			this.right = RedBlackTree.LEAF;
			this.parent = null;
			this.isRed = isRed;
		}
	}

	/**
	 * This node represents all the leaves of a tree.
	 */
	private static final Node LEAF = new Node();

	/**
	 * The root of this tree.
	 */
	private Node<T> root;

	/**
	 * Initializes this tree consisting of a single black leaf.
	 */
	public RedBlackTree() {
		this.root = RedBlackTree.LEAF;
	}

	/**
	 * Tests whether this tree consists of a single leaf.
	 *
	 * @return true if this tree consists of a single leaf, false otherwise.
	 */
	public boolean isEmpty() {
		return this.root == RedBlackTree.LEAF;
	}

	/**
	 * Adds the given element with the given weight to this tree.
	 *
	 * @param element the element to be added to this tree.
	 * @pre. element != null
	 * @param weight the weight of the element.
	 * @pre. weight &gt; 0
	 */
	public void add(T element, double weight) {
		Node<T> parent = null;
		Node<T> current = this.root;
		while (current != RedBlackTree.LEAF) {
			parent = current;
			if (weight < current.weight) {
				current = current.left;
			} else {
				current = current.right;
			}
		}

		Node<T> node = new Node<T>(element, weight, true);
		if (parent == null) {
			this.root = node;
		} else {
			node.parent = parent;
			if (weight < parent.weight) {
				parent.left = node;
			} else {
				parent.right = node;
			}
		}

		// update the weight sums of all ancestors of node
		current = node.parent;
		while (current != null) {
			current.sum = current.left.sum + current.weight + current.right.sum;
			current = current.parent;
		}

		while (node != this.root && node.parent.isRed) {
			Node<T> grandparent = node.parent.parent;
			if (node.parent == grandparent.left) {
				Node<T> aunt = grandparent.right;
				if (aunt.isRed) {
					node.parent.isRed = false;
					aunt.isRed = false;
					grandparent.isRed = true;
					node = grandparent;
				} else {
					if (node == node.parent.right) {
						node = node.parent;
						this.leftRotate(node);
					}
					node.parent.isRed = false;
					grandparent.isRed = true;
					this.rightRotate(grandparent);
				}
			} else {
				Node<T> aunt = grandparent.left;
				if (aunt.isRed) {
					node.parent.isRed = false;
					aunt.isRed = false;
					grandparent.isRed = true;
					node = grandparent;
				} else {
					if (node == node.parent.left) {
						node = node.parent;
						this.rightRotate(node);
					}
					node.parent.isRed = false;
					grandparent.isRed = true;
					this.leftRotate(grandparent);
				}
			}
		}
		this.root.isRed = false;
	}

	/**
	 * Performs a left rotation on the subtree rooted at the given node.
	 * Implements the algorithm described on page 266 of <BR>
	 * Thomas H. Cormen, Charles E. Leiserson and Ronald L. Rivest.
	 * <I>Introduction to Algorithms</I>.
	 * The MIT Press, Cambridge, Massachussets, USA. 1990.
	 *
	 * @param node root of the subtree to be rotated to the left.
	 * @pre. node.right != RedBlackTree.LEAF
	 */
	private void leftRotate(Node<T> node) {
		Node<T> parent = node.parent;
		Node<T> child = node.right;
		node.right = child.left;
		if (child.left != RedBlackTree.LEAF) {
			child.left.parent = node;
		}
		child.parent = parent;
		if (parent == null) {
			this.root = child;
		} else {
			if (node == parent.left) {
				parent.left = child;
			} else {
				parent.right = child;
			}
		}
		child.left = node;
		node.parent = child;

		// update the sums of node and child
		node.sum = node.left.sum + node.weight + node.right.sum;
		child.sum = child.left.sum + child.weight + child.right.sum;
	}

	/**
	 * Performs a right rotation on the subtree rooted at the given node.
	 * Implements the algorithm described on page 266 of<BR>
	 * Thomas H. Cormen, Charles E. Leiserson and Ronald L. Rivest.
	 * <I>Introduction to Algorithms</I>.
	 * The MIT Press, Cambridge, Massachussets, USA. 1990.
	 *
	 * @param node root of the subtree to be rotated to the right.
	 * @pre. node.right != RedBlackTree.LEAF
	 */
	private void rightRotate(Node<T> node) {
		Node<T> parent = node.parent;
		Node<T> child = node.left;
		node.left = child.right;
		if (child.right != RedBlackTree.LEAF) {
			child.right.parent = node;
		}
		child.parent = parent;
		if (parent == null) {
			this.root = child;
		} else {
			if (node == parent.right) {
				parent.right = child;
			} else {
				parent.left = child;
			}
		}
		child.right = node;
		node.parent = child;

		// update the sums of node and child
		node.sum = node.left.sum + node.weight + node.right.sum;
		child.sum = child.left.sum + child.weight + child.right.sum;
	}

	/**
	 * Removes a random element from this tree.  The probability that a 
	 * particular element is removed is proportional its weight.
	 *
	 * @pre. this tree contains an internal node.
	 * @return the removed element and its weight.
	 */
	public Weighted<T> remove() {
		// choose random number in [0, sum of root)
		double choice = this.root.sum * Math.random();

		// locate node corresponding to the random choice
		Node<T> current = this.root;
		boolean found = false;
		while (!found) {
			if (choice < current.left.sum) {
				current = current.left;
			} else if (choice > current.left.sum + current.weight) {
				choice = choice - (current.left.sum + current.weight);
				current = current.right;
			} else {
				found = true;
			}
		}
		T element = current.element;
		double weight = current.weight;

		// remove current
		Node<T> removed = current;
		if (current.left != RedBlackTree.LEAF && current.right != RedBlackTree.LEAF) {
			removed = removed.right;
			while (removed.left != RedBlackTree.LEAF) {
				removed = removed.left;
			}
		}
		Node<T> child;
		if (removed.left != RedBlackTree.LEAF) {
			child = removed.left;
		} else {
			child = removed.right;
		}

		Node<T> parent = removed.parent;
		removed.parent = null;
		if (child != RedBlackTree.LEAF) {
			child.parent = parent;
		}
		if (parent == null) {
			this.root = child;
		} else {
			if (parent.left == removed) {
				parent.left = child;
			} else {
				parent.right = child;
			}
		}

		if (current != removed) {
			current.element = removed.element;
			current.weight = removed.weight;
		}

		// update the sums of parent and its ancestors
		Node<T> node = parent;
		while (node != null) {
			node.sum = node.left.sum + node.weight + node.right.sum;
			node = node.parent;
		}

		if (!removed.isRed) {
			Node<T> fix = child;
			while (fix != this.root && !fix.isRed) {
				if (parent.left == fix) {
					Node<T> sibling = parent.right;
					if (sibling.isRed) {
						sibling.isRed = false;
						parent.isRed = true;
						this.leftRotate(parent);
						sibling = parent.right;
					}
					if (!sibling.left.isRed && !sibling.right.isRed) {
						sibling.isRed = true;
						fix = parent;
						parent = fix.parent;
					} else {
						if (!sibling.right.isRed) {
							sibling.left.isRed = false;
							sibling.isRed = true;
							this.rightRotate(sibling);
							sibling = parent.right;
						}
						sibling.isRed = parent.isRed;
						parent.isRed = false;
						sibling.right.isRed = false;
						this.leftRotate(parent);
						fix = this.root;
					}
				} else {
					Node<T> sibling = parent.left;
					if (sibling.isRed) {
						sibling.isRed = false;
						parent.isRed = true;
						this.rightRotate(parent);
						sibling = parent.left;
					}
					if (!sibling.left.isRed && !sibling.right.isRed) {
						sibling.isRed = true;
						fix = parent;
						parent = fix.parent;
					} else {
						if (!sibling.left.isRed) {
							sibling.right.isRed = false;
							sibling.isRed = true;
							this.leftRotate(sibling);
							sibling = parent.left;
						}
						sibling.isRed = parent.isRed;
						parent.isRed = false;
						sibling.left.isRed = false;
						this.rightRotate(parent);
						fix = this.root;
					}
				}
			}
			fix.isRed = false;
		}

		return new Weighted<T>(element, weight);
	}
	
	/**
	 * Removes an element with maximal weight from this tree.
	 *
	 * @pre. this tree is nonempty.
	 * @return the removed element and its weight.
	 */
	public Weighted<T> removeMax() {
		// locate a node with maximal weight
		Node<T> current = this.root;
		while (current.right != RedBlackTree.LEAF) {
			current = current.right;
		}
		T element = current.element;
		double weight = current.weight;

		// remove current
		Node<T> removed = current;
		if (current.left != RedBlackTree.LEAF && current.right != RedBlackTree.LEAF) {
			removed = removed.right;
			while (removed.left != RedBlackTree.LEAF) {
				removed = removed.left;
			}
		}
		Node<T> child;
		if (removed.left != RedBlackTree.LEAF) {
			child = removed.left;
		} else {
			child = removed.right;
		}

		Node<T> parent = removed.parent;
		removed.parent = null;
		if (child != RedBlackTree.LEAF) {
			child.parent = parent;
		}
		if (parent == null) {
			this.root = child;
		} else {
			if (parent.left == removed) {
				parent.left = child;
			} else {
				parent.right = child;
			}
		}

		if (current != removed) {
			current.element = removed.element;
			current.weight = removed.weight;
		}

		// update the sums of parent and its ancestors
		Node<T> node = parent;
		while (node != null) {
			node.sum = node.left.sum + node.weight + node.right.sum;
			node = node.parent;
		}

		if (!removed.isRed) {
			Node<T> fix = child;
			while (fix != this.root && !fix.isRed) {
				if (parent.left == fix) {
					Node<T> sibling = parent.right;
					if (sibling.isRed) {
						sibling.isRed = false;
						parent.isRed = true;
						this.leftRotate(parent);
						sibling = parent.right;
					}
					if (!sibling.left.isRed && !sibling.right.isRed) {
						sibling.isRed = true;
						fix = parent;
						parent = fix.parent;
					} else {
						if (!sibling.right.isRed) {
							sibling.left.isRed = false;
							sibling.isRed = true;
							this.rightRotate(sibling);
							sibling = parent.right;
						}
						sibling.isRed = parent.isRed;
						parent.isRed = false;
						sibling.right.isRed = false;
						this.leftRotate(parent);
						fix = this.root;
					}
				} else {
					Node<T> sibling = parent.left;
					if (sibling.isRed) {
						sibling.isRed = false;
						parent.isRed = true;
						this.rightRotate(parent);
						sibling = parent.left;
					}
					if (!sibling.left.isRed && !sibling.right.isRed) {
						sibling.isRed = true;
						fix = parent;
						parent = fix.parent;
					} else {
						if (!sibling.left.isRed) {
							sibling.right.isRed = false;
							sibling.isRed = true;
							this.leftRotate(sibling);
							sibling = parent.left;
						}
						sibling.isRed = parent.isRed;
						parent.isRed = false;
						sibling.left.isRed = false;
						this.rightRotate(parent);
						fix = this.root;
					}
				}
			}
			fix.isRed = false;
		}

		return new Weighted<T>(element, weight);
	}
}
