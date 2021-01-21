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

import probabilistic.UniformChoice;

/**
 * Finds the Kth element in an array.  Implements algorithms described in
 * C.A.R. Hoare.  Algorithm 63: Partition and Algorithm 65: Find.  Communications of the ACM, 4(7): 321-322, July 1961.
 * 
 * @author Franck van Breugel
 */
public class QuickSelect {
	private static double[] A; // array of elements
	private static int M; // index of the first cell of the array to be considered
	private static int N; // index of the last cell of the array to be considered
	private static int F; // index of the randomly chosen cell
	private static int I; // index used in the partition
	private static int J; // index used in the partition
	private static double X; // value of the randomly chosen cell
	
	/**
	 * Partitions the array A and possibly swaps some of its values such that for X the value of
	 * a cell of A randomly chosen between M and N (both inclusive), we have that
	 * A[R] <= X for all M <= R <= J,
	 * A[R} == X for all J < R < I, and
	 * A[R] >= X for all I <= R <= N.
	 */
	private static void partition() {
		F = M + UniformChoice.make(N - M + 1);
		X = A[F];	
		I = M;
		J = N;
		boolean goToUp = true;
		while (goToUp) {
			up();
			down();
			if (I < J) {
				exchange(I, J);
				I++;
				J--;
			} else {
				goToUp = false;
			}
		}
		if (I < F) {
			exchange(I, F);
			I++;
		} else if (F < J) {
			exchange(F, J);
			J--;
		}	
	}
	
	/** 
	 * Determines the value of I.
	 */
	private static void up() {
		for(; I <= N; I++) {
			if (X < A[I]) {
				return;
			}
		}
		I = N;
	}
	
	/** 
	 * Determines the value of J.
	 */
	private static void down() {
		for(; J >= M; J--) {
			if (A[J] < X) {
				return ;
			}
		}
		J = M;
	}
	
	/**
	 * Swaps the values of the array A at the given indices.
	 * 
	 * @param I an index. 
	 * @param J an index. 
	 */
	private static void exchange(int I, int J) {
		double temp = A[I];
		A[I] = A[J];
		A[J] = temp;
	}
	
	/**
	 * Swaps elements of the array A, with indices greater than or equal to M and smaller than or equal to N, such that the 
	 * element at index K is the Kth element.  We assume that 0 <= M <= K <= N <= A.length - 1.
	 * 
	 * @param K an index.
	 */
	public static void find(int K) {
		if (M < N) {
			partition();
			if (K <= J) {
				N = J;
				find(K);
			}
			else if (I <= K) {
				M = I;
				find(K);
			}
		}
	}
	
	/**
	 * Initialize the variables A, M and N.  We assume that 0 <= M <= N <= A.length - 1.
	 * 
	 * @param A the array.
	 * @param M an index.
	 * @param N an index.
	 */
	public static void init(double[] A, int M, int N) {
		QuickSelect.A = A;
		QuickSelect.M = M;
		QuickSelect.N = N;
	}
}
