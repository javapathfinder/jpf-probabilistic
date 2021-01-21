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

package probabilistic.examples;

import java.util.ArrayList;
import probabilistic.UniformChoice;

/**
 * A full, endogenous random binary treap where each node contains a key and a priority value.
 * It is a binary tree w.r.t key value and a heap w.r.t priority values.
 * It is called random treap as the priorities for the elements are chosen at random (here from a continuous distribution).
 * 
 * @author Yash Dhamija
 */
public class RandomizedTreap {

	public class TreapNode {
		public TreapNode left, right, parent;
		public int priority, element;

		/*
		 * dumy leaf node, can be identified as null left and right pointers
		 */
		public TreapNode() {
			this(Integer.MIN_VALUE, Integer.MIN_VALUE, null, null, null);
		}    

		/** Constructor **/
		public TreapNode(int ele, int priority, TreapNode left, TreapNode right, TreapNode parent) {
			element = ele;
			this.left = left;
			this.right = right;
			this.priority = priority;
			this.parent = parent;
		}    
	}

	private TreapNode root;
	//elements of arraylist represent the pool of priorities to choose from
	private ArrayList<Integer> priorities;
	private final int MAX_SIZE;
	private int size;
	private final int MAX_PRIORITY;

	/*
	 * Constructor
	 * @param size of the treap
	 */
	public RandomizedTreap(int sz) {
		MAX_SIZE = sz;
		MAX_PRIORITY = 3*MAX_SIZE;
		size = 0;
		root = null;
		priorities = new ArrayList<>();

		for (int i = 0; i < MAX_PRIORITY; i++)
			priorities.add(i);
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean isFull() {
		return size == MAX_SIZE;
	}

	public int size() {
		return size;
	}

	public TreapNode root() {
		return root;
	}

	/*
	 * inserts the element if not already present in the tree
	 * with distinct priority
	 */
	public void insert(int ele) {
		if (!isFull()) {
			int pIndex = UniformChoice.make(priorities.size());
			int priority = priorities.get(pIndex);
			//swap this index with last element in the list and remove it
			priorities.set(pIndex, priorities.get(priorities.size()-1));
			priorities.remove(priorities.size() - 1);
			insert(ele, priority);
			size++;
		}		 
	}

	/*
	 * recursive sub-routine for insert
	 */
	private void insert(int ele, int priority) {
		//where to put this element
		TreapNode loc = find(ele);

		//insert if ele not already present, i.e, null or empty leaf node is returned
		if (loc == null || (loc.left == null && loc.right == null)) {
			TreapNode right = new TreapNode();
			TreapNode left = new TreapNode();
			
			// this element becomes the root
			if(loc == null ) {
				loc = new TreapNode(ele,priority, left, right, null);
				root = loc;
			} else {
				loc.element = ele;
				loc.priority = priority;
				loc.right = right;
				loc.left = left;
			}
			left.parent = loc;
			right.parent = loc;

			upTreap(loc);
		}		
	}

	/*
	 * maintains the heap-order property after an insertion by
	 * performing rotations
	 */
	private void upTreap(TreapNode t) {
		while (t.parent != null && t.parent.priority < t.priority) {
			//if left child, perform right rotation
			if (t == t.parent.left) {
				rightRotate(t.parent);	
			}	
			//else is right child, perform left rotation
			else {
				leftRotate(t.parent);
			}	
		}
	}

	//fix issues with rotation
	private void leftRotate(TreapNode t) {
		TreapNode y = t.right, T2  = y.left, tParent = t.parent;
		t.right  = T2; T2.parent = t;
		y.left = t; t.parent = y;
		y.parent = tParent;

		if (tParent == null) {
			root = y;			
		} else {
			if (tParent.left == t)
				tParent.left = y;
			else 
				tParent.right = y;
		}

	}

	private void rightRotate(TreapNode t) {
		TreapNode x = t.left, T2 = x.right, tParent = t.parent;
		x.right = t; t.parent = x; x.parent = tParent;
		t.left = T2; T2.parent = t;		

		if (tParent == null) {
			root = x;			
		}
		else {
			if (tParent.left == t)
				tParent.left = x;
			else 
				tParent.right = x;
		}
	}

	/*
	 * Returns the node with element equals ele if present, otherwise returns the 
	 * location of leaf where this element shall be inserted
	 */
	public TreapNode find(int ele) {
		if (root == null)
			return null;
		return find(ele, root);
	}	

	private TreapNode find(int ele, TreapNode T) {
		// T is a leaf
		if (T.left == null && T.right == null)
			return T;
		//T is an internal node
		else {
			if (ele  < T.element)
				return find(ele, T.left);
			else if (ele > T.element)
				return find(ele, T.right);
			else
				return T;
		}
	}

	/*
	 * prints the in-order traversal of nodes
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		inOrder(sb, root);
		sb.append(" }");		
		return sb.toString();
	}

	private void inOrder(StringBuilder sb, TreapNode t) {
		// t is internal node
		if (t.left != null && t.right != null) {
			if (t.left != null)
				inOrder(sb, t.left);
			sb.append(t.element + ", " + t.priority + "\n");
			if (t.right != null)
				inOrder(sb, t.right);
		}
	}
}
