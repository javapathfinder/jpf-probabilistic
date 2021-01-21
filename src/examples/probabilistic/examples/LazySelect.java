/*
 * Copyright (C) 2020  Zainab Fatmi
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

import java.util.Arrays;

import probabilistic.UniformChoice;

/**
 * The use of random sampling for the problem of selecting the kth-smallest
 * element in a set S of n distinct elements. Expected running time = 2n + O(n).
 * 
 * @author Zainab Fatmi
 */
public class LazySelect {
	private int[] S;

	/**
	 * Initializes the values in the set.
	 * 
	 * @param S A array of n distinct elements.
	 */
	public LazySelect(int[] S) {
		this.S = S;
	}

	/**
	 * Returns the kth-smallest element in the set S, using the randomized Lazy
	 * Select algorithm.
	 * 
	 * @param k An integer in the range [1,n].
	 * @return The kth smallest element in the set S.
	 */
	public int kthSmallest(int k) {
		/*
		 * Step 1: Pick n^(3/4) elements from S, chosen independently and uniformly at
		 * random with replacement; call this multiset of elements R.
		 */
		int n = S.length;
		int n_75 = (int) Math.pow(n, 0.75);
		int[] R = new int[n_75];

		for (int i = 0; i < n_75; i++) {
			R[i] = S[UniformChoice.make(n)];
		}

		/*
		 * Step 2: Sort R in O( n^(3/4) 1og n ) steps using any optimal sorting
		 * algorithm.
		 */
		Arrays.sort(R);

		/*
		 * Step 3: Let x = kn^(-1/4). For l = max{floor(x - sqrt(n)), 1} and h =
		 * min{roof(x + sqrt(n)), n^(3/4)}, let a = R(l) and b = R(h). By comparing a
		 * and b to every element of S, determine rs(a) and rs(b).
		 */
		double n_neg25 = Math.pow(n, -0.25);
		double x = k * n_neg25;
		int l = Math.max((int) Math.floor(x - Math.sqrt(n)), 1) - 1;
		int h = Math.min((int) Math.ceil(x + Math.sqrt(n)), n_75) - 1;
		int a = R[l];
		int b = R[h];
		int rankA = 1;
		int rankB = 1;

		for (int i = 0; i < n; i++) {
			if (a > S[i]) {
				rankA++;
			}
			if (b > S[i]) {
				rankB++;
			}
		}

		/*
		 * Step 4: if k < n^(1/4), then P = {y E S | y <= b}; else if k > n - n^(1/4),
		 * let P = {y E S | y >= a}; else if k E [n^(1/4), n - n^(1/4)], let P = {y E S
		 * | a <= y <= b}; Check whether S(k) E P and |P| <= 4n^(3/4) + 2. If not,
		 * repeat Steps 1-3 until such a set P is found.
		 */
		double n_25 = Math.pow(n, 0.25);
		boolean firstCase = false;
		boolean kElementOfP;
		int[] P;

		if (k < Math.ceil(n_25)) {
			firstCase = true;
			P = new int[rankB];
			int j = 0;
			for (int i = 0; i < n; i++) {
				if (S[i] <= b) {
					P[j] = S[i];
					j++;
				}
			}
			kElementOfP = (k <= rankB);
		} else if (k > Math.floor(n - n_25)) {
			P = new int[n - rankA + 1];
			int j = 0;
			for (int i = 0; i < n; i++) {
				if (S[i] >= a) {
					P[j] = S[i];
					j++;
				}
			}
			kElementOfP = (k >= rankA);
		} else {
			P = new int[rankB - rankA + 1];
			int j = 0;
			for (int i = 0; i < n; i++) {
				if (S[i] >= a && S[i] <= b) {
					P[j] = S[i];
					j++;
				}
			}
			kElementOfP = (k >= rankA && k <= rankB);
		}

		if (kElementOfP && P.length <= (4 * n_75 + 2)) {
			/*
			 * Step 5: By sorting P in O(|P| log |P|) steps, identify P(k - rs(a) + 1),
			 * which is S(k).
			 */
			Arrays.sort(P);
			if (firstCase) {
				return P[k - 1];
			} else {
				return P[k - rankA];
			}
		} else {
			return kthSmallest(k);
		}
	}
}
