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

import java.lang.Math;

import probabilistic.UniformChoice;

/**
 * Given a graph, finds a perfect matching.
 * 
 * @author Maeve Wildes
 */
public class PerfectMatching {
	
	/**
	 * Represents a graph.
	 */
	public static class Graph {
		int V;
		int E;
		int [][] adjMatrix;
		
		public Graph(int V, int E, int [][] M) {
			this.V=V;
			this.E=E;
			this.adjMatrix=M;
		}
	}
	
	/**
	 * this is a monte carlo algorithm with probability of failure (1/2)^t where t is number of
	 * trials.
	 * This algorithm takes as input a graph with at least one perfect matching
	 *  in adjacency matrix form, and finds a perfect matching. 
	 *  
	 * @param graph
	 * @param trials, the number of repetitions
	 * @return the adjacency matrix of the perfect matching
	 * 
	 * DISCLAIMER: this algorithm so far only works with small graphs, otherwise the determinant
	 * becomes larger than 2^31 and overflow occurs. To fix this, one could alter this code to
	 * use BigInteger or long instead of int when calculating the determinant.
	 */
	public static int[][] get(Graph graph, int trials){
		for (int i = 0; i < trials; i++) {
			int[][] matching = matching(graph);
			if (matching != null) {
				return matching;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param graph
	 * @return
	 */
	private static int[][] matching(Graph graph) {

		/*
		 * choose a random weight w(i,j) between 1 and |E| for each edge (i,j)
		 * define the Tutte matrix T for the graph: for each edge (i,j), T(i,j) = 2^w(i,j)
		 * and T(j,i)=-2^w(i,j) 
		 * 
		 * note that in the original algorithm, w(i,j) is chosen randomly from {1, 2|E|}, 
		 * leading to a higher success rate. however, this creates overflow as the determinate
		 * is often larger than 2^31 
		 */		
		for (int i=0; i<graph.V; i++) {
			for (int j=i; j<graph.V; j++) {
				if (graph.adjMatrix[i][j] == 1) {
					int x = UniformChoice.make(graph.E)+1;
					graph.adjMatrix[i][j] = (int)Math.pow(2, x);
					graph.adjMatrix[j][i] = -(int)Math.pow(2,x);
				}
			}
		}
		//calculate the determinant of the Tutte matrix
		int det = determinant(graph.adjMatrix, graph.V);
		int w = 0;
		//System.out.println(det);
		//if det=0, the algorithm fails. you must try again
		if (det ==0) {
			return null;
		}
		/*calculate the weight of the perfect matching: this weight is w such that 2^2w
		 * is the largest power of 2 that divides det(T)
		 */
		while(det%Math.pow(2, 2*w) ==0) {
			w++;

		}
		w--;
		int [][] matching = new int [graph.V][graph.V];
		/*
		 * for each edge (i,j), compute r(i,j)=det(cofactor(T))*2^w(i,j) / 2^(2*w)
		 * (i,j) is in the matching iff r(i,j) is odd
		 */
		for (int i=0; i<graph.V; i++ ) {
			for (int j=i ; j<graph.V; j++) {
				if (graph.adjMatrix[i][j] != 0) {
					int [][] cofactor = new int [graph.V-1][graph.V-1];
					getCofactor(graph.adjMatrix, cofactor, i, j, graph.V);
					int det2 = determinant(cofactor, graph.V-1);
					int r = Math.abs((int)(det2 * graph.adjMatrix[i][j] /Math.pow(2, 2*w)));
					//if r is odd, add it to the matching 
					if (r%2 !=0) {
						//							System.out.println(i + " " + j);
						matching[i][j]=1;
						matching[j][i]=1;
					}
				}

			}
		}
		return matching;
	}
	
	/**
	 * Recursive method to find the determinant of a nxn matrix
	 * 
	 * @param matrix
	 * @param n
	 * @return
	 */
	private static int determinant(int [][] matrix, int n) {
		int det = 0; // determinant
		if (n==1) {
			return matrix[0][0];
		}
		int [][] cofactor = new int[n-1][n-1];
		int sign = 1;
		for (int col = 0; col<n; col++) {
			getCofactor(matrix, cofactor, 0, col, n);
			det += sign*matrix[0][col]*determinant(cofactor, n-1);

			sign = sign*(-1);
		}

		return det;
	}
	
	/**
	 * method to find the cofactor (the matrix obtained when row p and column q are deleted) 
	 * of a nxn matrix M. 
	 */
	private static void getCofactor(int [][] M, int [][] cofactor, int p, int q, int n) { 
		int i = 0, j = 0; 
		for (int row = 0; row < n; row++) { 
			for (int col = 0; col < n; col++)  {    
				if (row != p && col != q)  { 
					cofactor[i][j++] = M[row][col]; 
					if (j == n - 1) 
					{ 
						j = 0; 
						i++; 
					} 
				} 
			} 
		} 
	} 
}
