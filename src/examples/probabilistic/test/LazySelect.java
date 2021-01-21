/*
 * Copyright (C) 2020  Yash Dhamija and Zainab Fatmi
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
 * This class selects the k-th smallest element from a set.
 * 
 * @author Yash Dhamija
 * @author Zainab Fatmi
 */
public class LazySelect {

	/**
	 * Selects the kth smallest element in the set.
	 * 
	 * @param args[0]      the number to select.
	 * @param args[1..n+1] the elements of the set (of size n).
	 */
	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		int n = args.length - 1;
		if (k < 1 || k > n) {
			throw new IllegalArgumentException("k must be an integer in the range [1,n]");
		}
		int[] set = new int[n];
		for (int i = 0; i < n; i++)
			set[i] = Integer.parseInt(args[i + 1]);
			probabilistic.examples.LazySelect select = new probabilistic.examples.LazySelect(set);
		select.kthSmallest(k);
	}
}
