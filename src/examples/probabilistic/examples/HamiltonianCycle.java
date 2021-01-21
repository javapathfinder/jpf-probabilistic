package probabilistic.examples;

import probabilistic.Choice;
import probabilistic.UniformChoice;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Given an undirected graph, a hamiltonian cycle is found (if one exists) with probability 1 - 2 / n.
 * 
 * @author Maeve Wildes
 */
public class HamiltonianCycle {
	
	/**
	 * Attempts to find a hamiltonian cycle in the given graph.
	 * 
	 * @param vertices the number of vertices of the graph
	 * @param adjacent the adjacency list of the graph
	 * @return true if a hamiltonian cycle in the given graph has been found,
	 * false otherwise
	 */
	public static List<Integer> find(int vertices, List<LinkedList<Integer>> adjacent) {
		// visited.get(v): the vertices adjacent to vertex v that have been visited
		List<LinkedList<Integer>> visited = new ArrayList<LinkedList<Integer>>();
		for (int v = 0; v < vertices; v++) {
			visited.add(new LinkedList<Integer>());
		}
		
		// choose a random node as head
		int head = UniformChoice.make(vertices);
		List<Integer> path = new LinkedList<Integer>();
		path.add(head);
		
		// repeat until all vertices adjacent to head are visited 
		while (!adjacent.get(head).isEmpty()) { 
			// the number of nodes that were visited from the current head
			int number = visited.get(head).size();
			double[] choices = new double[3];
			choices[0] = 1 / (double) vertices;
			choices[1] = number / (double) vertices;
			choices[2] = 1 - (choices[0] + choices[1]);
			int choice = Choice.make(choices);
			// reverse the path with probability 1 / vertices
			if (choice == 0) {
				reverse(path);
				head = path.get(path.size() - 1);
			}
			// choose a random node that has been visited from head previously with probability number / (double) vertices
			else if (choice == 1) {
				// number > 0
				assert number > 0;
				int next = visited.get(head).get(UniformChoice.make(number));
				// check if a hamiltonian cycle was found
				if (isHamiltonian(path, next, vertices)) {
					return path;
				}
				// if the next is not yet in the path, add it
				else if (!path.contains(next)) {
					head = extend(path, next);
				}
				// else, next is already in the path, so perform a rotation
				else {
					head = rotate(path, next);
				}
			}
			// choose a random node that has not been visited from the head previously with probability 1 - ((1 + number) / (double) vertices)
			else {
				int index = UniformChoice.make(adjacent.get(head).size()); 
				int nextNode = adjacent.get(head).get(index);
				// mark this node as visited from head
				adjacent.get(head).removeFirstOccurrence(nextNode);
				visited.get(head).add(nextNode);
				visited.get(nextNode).add(head);
				adjacent.get(nextNode).removeFirstOccurrence(head);
				// check if a hamiltonian cycle was found
				if (isHamiltonian(path, nextNode, vertices)) {
					//System.out.println(path.toString());
					return path;
				}
				// if the nextNode is not yet in the path, add it
				else if (!path.contains(nextNode)) {
					head = extend(path, nextNode);
				}
				// else, nextNode is already in the path, so perform a rotation
				else {
					head = rotate(path, nextNode);
				}
			}
		}
		/* if the loop exits, the algorithm failed to find a hamiltonian cycle because 
		 * one does not exist, or because the unused edge list ran out before one was found:
		 * this even happens with probability 2/n
		 */
		//System.out.println("FAIL");
		return null;
	}
	
	/**
	 * Reverses the given path.
	 * 
	 * @param path a list to be reversed
	 */
	private static void reverse(List<Integer> path) {
		for (int i = 0; i < path.size() / 2; i++) {
			int temp = path.get(i);
			path.set(i, path.get(path.size() - 1 - i));
			path.set(path.size() - 1 - i, temp);
		}
	}
	
	/**
	 * Checks whether the given path starts in v and contains a hamiltonian cycle.
	 * 
	 * @param path the path to be checked
	 * @param v the newest node to be added
	 * @param n number of vertices in the graph
	 * @return true if the given path starts in v and contains a hamiltonian cycle,
	 * false otherwise
	 */
	private static boolean isHamiltonian(List<Integer> path, int v, int n) {
		
		if (v == path.get(0)) {
			for (int i = 0; i < n; i++) {
				if (!path.contains(i)) {
					return false;
				}
			}
			path.add(v);
			return true;
		}
		return false;
	}
	
	/**
	 * Extends the given path with the given node.
	 * 
	 * @param path the current path
	 * @param v the node to be added
	 * @return the new head of the path
	 */
	private static int extend(List<Integer> path, int v) {
		path.add(v);
		return v;
	}
	/**
	 * suppose that P=v1, v2, ..., vk is the current path, and (vk, vi) is an edge in the graph.
	 * then a call to rotate(path, vi) yields the following path:
	 * P'=v1, v2, ..., vi, vk, v(k-1), ..., v(i+2), v(i+1)
	 * 
	 * @param path the current path
	 * @param v the node on which the rotation occurs
	 * @return the new head of the path
	 */
	private static int rotate(List<Integer> path, int v) {
		int j = path.indexOf(v);
		int head = path.get(j + 1);
		for (int i = j + 1; i < (path.size() - (j + 1)) / 2 + j + 1; i++) {
			int temp = path.get(i);
			path.set(i, path.get(path.size() - 1 - i + (j + 1)));
			path.set(path.size() - 1 - i + (j + 1), temp);
		}
		return head;
	}
}
