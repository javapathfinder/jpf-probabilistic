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

import java.util.ArrayList;

import probabilistic.UniformChoice;

/**
 * boolean product witness matrix
 * 
 * given two n x n boolean matrices A and B, this program finds a witness matrix W for P=AB, such that each 
 * entry W(i, j) is a witness for P(i, j), if any, and 0 if there is no witness.
 * A witness for P(i, j) is an index k in {1,...,n} such that A(i, k) = B(k, j) = 1
 * 
 * @author Maeve Wildes
 */
public class BooleanProductWitnessMatrix {
	/**
	 * 
	 * @param A, a n x n matrix
	 * @param B, a n x n matrix
	 * @return W, the witness matrix
	 */
	public static int [][] witnessMatrix(int[][] first, int[][] second) {
		int n = first.length;
		int[][] product = multiply(first, second);
		for (int i = 0 ; i < product.length; i++)
			for (int j = 0; j < product[0].length; j++)
				product[i][j] = -product[i][j];

		//for t from 0 to floor(logn)
		for (int t = 0; t < (int) (Math.log(n) / Math.log(2)); t++) {
			int r =(int) Math.pow(2, t);
			//repeat ceil(3.77logn) times
			for (int i = 0; i < (int)Math.ceil(3.77*(Math.log(n)/Math.log(2))); i++) {
				ArrayList<Integer> R = new ArrayList<Integer>(r);
				ArrayList<Integer> choices = new ArrayList<Integer>(n);			
				
				for (int m = 1; m <= n; m++)
					choices.add(m);
				
				for (int j = 0; j < r; j++) {
					//choose random indices {1,..,n} for R
					int x = UniformChoice.make(choices.size());
					R.add(choices.get(x));
					choices.set(x, choices.get(choices.size()-1));
					choices.remove(choices.size()-1);				
				}
				
				// R2 is a vector such that R2[k-1] = 1 if k in R, R2[k-1] = 0 otherwise
				int [] R2 = new int[n];
				for (int j=1; j<=n; j++) {
					if (R.contains(j)) {
						R2[j-1] = 1;
					}
					else {
						R2[j-1] = 0;
					}
				}
				int [][] Ar = augmentA(first, R2);
				int [][] Br = augmentB(second, R2);
				int [][] Z = multiply(Ar, Br);
				for (int p=0; p<n; p++)
					for (int q=0; q<n; q++)
						if (product[p][q] < 0 )
							if (isWitness(p, q, Z[p][q], first, second))
								product[p][q] = Z[p][q];
			}
		}
		// if witnesses are not found at this point, use brute force. this ensures a las vegas algorithm
		for (int i=0; i<n; i++)
			for (int j=0; j<n; j++)
				if (product[i][j] < 0) {
					//use brute force
					for (int k=0; k<n; k++)
						if (first[i][k] == 1 && second[k][j] == 1)
							product[i][j] = k+1;
					if (product[i][j] < 0)
						product[i][j] = 0;
				}
//		printMatrix(W);
		return product;
		
	}
	/**
	 * Multiplies two n x n matrices, returns the result
	 * 
	 * @param matrix1 an n x n matrix.
	 * @param matrix2 an n x n matrix
	 * @return matrix1 * matrix2.
	 */
	public static int[][] multiply(int[][] matrix1, int[][] matrix2) {
		int n = matrix1.length;
		int[][] result = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				result[i][j] = 0;
				for (int k=0; k<n; k++)
					result[i][j] += matrix1[i][k] * matrix2[k][j];
			}
		return result;
	}
	/**
	 * augments an n x n matrix A to A' as follows:
	 * A'(i, k) = k*R(k)*A(i, k)
	 * @param matrix, an n x n matrix
	 * @param R, a vector of length n
	 * @return the result A'
	 */
	public static int [][] augmentA(int [][] matrix, int [] R){
		int n = R.length;
		int [][] result = new int[n][n];
		for (int i=0; i<n; i++)
			for (int k=0; k<n; k++)
				result[i][k]= (k+1) * R[k] * matrix[i][k];
		return result;
	}
	/**
	 * augments an n x n matrix B to B' as follows:
	 * B'(i, k) = R(i)*B(i, k)
	 * @param matrix, an n x n matrix
	 * @param R, a vector o length n
	 * @return the result B'
	 */
	public static int [][] augmentB(int [][] matrix, int [] R){
		int n = R.length;
		int [][] result = new int[n][n];
		for (int i=0; i<n; i++) {
			for (int k=0; k<n; k++) {
				result[i][k]= R[i] * matrix[i][k];
			}
		}
		return result;
	}
	/**
	 * determines if 'witness' is a witness for P(i, j) where P=AB: 
	 * it is a witness if witness is an index in {1,...,n} such that A(i, k) = B(k, j) = 1
	 * 
	 */
	public static boolean isWitness(int i, int j, int witness, int [][] A, int [][] B) {
		if (witness == 0) 
			return false;
		if (A[i][witness-1] == 1 && B[witness-1][j] == 1)
			return true;
		else
			return false;
	}
	
	public static void printMatrix(int [][] m) {
		for (int i=0; i<m.length; i++) {
			for (int j=0; j<m.length; j++)
				System.out.print(m[i][j]+ " ");
			System.out.println();
		}
		System.out.println();
	}
}
