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

import probabilistic.UniformChoice;

/**
 * The nuts and bolts problem is defined as follows: You are given a collection
 * of n bolts of different widths, and n corresponding nuts. You can test
 * whether a given nut and bolt fit together, from which you learn whether the
 * nut is too large, too small, or an exact match for the bolt. The differences
 * in size between pairs of nuts or bolts are too small to see by eye, so you
 * cannot compare the sizes of two nuts or two bolts directly. You are to match
 * each bolt to each nut.
 * 
 * Reference: [ABFKNO94] Noga Alon, Manuel Blum, Amos Fiat, Sampath Kannan, Moni
 * Naor, and Rafail Ostrovsky. Matching nuts and bolts. In Daniel Dominic
 * Sleator, editor, Proceedings of the Fifth Annual ACM-SIAM Symposium on
 * Discrete Algorithms. 23-25 January 1994, Arlington, Virginia, USA, pages
 * 690-696. ACM/SIAM, 1994.
 * 
 * @author Zainab Fatmi
 */
public class MatchingNutsAndBolts {
	private int[] nuts;
	private int[] bolts;

	/**
	 * Initializes the values of the widths of the n nuts and bolts.
	 * 
	 * @param nuts  An array of length n containing the widths of nuts.
	 * @param bolts An array of length n containing the widths of bolts.
	 */
	public MatchingNutsAndBolts(int[] nuts, int[] bolts) {
		this.nuts = nuts;
		this.bolts = bolts;
	}

	/**
	 * Rearranges the nuts array and bolts array, such that matching nuts and bolts
	 * have the same index.
	 */
	public void match() {
		match(0, this.nuts.length);
	}

	/**
	 * Rearranges sub-arrays of the nuts and bolts arrays, such that matching nuts
	 * and bolts within the sub-arrays have the same index.
	 * 
	 * @param low  The beginning index of the sub-array (inclusive).
	 * @param high The end index of the sub-array (exclusive).
	 */
	private void match(int low, int high) {
		int n = high - low;
		if (n > 1) {
			int p = partition(low, high, n);
			match(low, p);
			match(p + 1, high);
		}
	}

	/**
	 * Partitions a sub-array of the nuts and bolts arrays using a random pivot,
	 * such that all values within the sub-array that are smaller than the pivot
	 * appear before the pivot and all values larger than the pivot appear after the
	 * pivot.
	 * 
	 * @param low  The beginning index of the sub-array (inclusive).
	 * @param high The end index of the sub-array (exclusive).
	 * @param n    The amount of values in the sub-array.
	 * @return The index of the randomly chosen pivot.
	 */
	private int partition(int low, int high, int n) {

		// Randomly pick a nut
		int pivotIndex = UniformChoice.make(n) + low;
		int pivotNut = this.nuts[pivotIndex];
		int pivotBolt = 0; // we haven't found the matching bolt yet

		// Partition the bolts according to whether they are smaller than,
		// bigger than, or equal to the chosen nut
		int[] temp = new int[n];
		int j = 0;
		int k = n - 1;

		// we can only compare nuts to bolts and bolts to nuts, we cannot compare nuts
		// with nuts and bolts with bolts
		for (int i = low; i < high; i++) {
			if (this.bolts[i] < pivotNut) {
				// put the smaller bolts before the matching bolt
				temp[j++] = this.bolts[i];
				if (j >= temp.length) {
					System.out.println(low + " " + high + " " + pivotIndex);
					System.out.println(j + " " + temp.length + " " + pivotNut);
					for (int ii = 0; ii < n; ii++) {
						System.out.print(temp[ii] + ", ");
					}
				}
			} else if (this.bolts[i] > pivotNut) {
				// put the larger bolts after the matching bolt
				temp[k--] = this.bolts[i];
			} else {
				// we found the matching bolt
				pivotBolt = this.bolts[i];
			}
		}
		pivotIndex = j;
		temp[pivotIndex] = pivotBolt;

		for (int i = low; i < high; i++) {
			this.bolts[i] = temp[i - low];
		}

		// Partition the nuts according to whether they are smaller or
		// bigger than the matching bolt
		j = 0;
		k = n - 1;

		for (int i = low; i < high; i++) {
			if (this.nuts[i] < pivotBolt) {
				temp[j++] = this.nuts[i];
			} else if (this.nuts[i] > pivotBolt) {
				temp[k--] = this.nuts[i];
			}
		}
		temp[pivotIndex] = pivotNut;

		for (int i = low; i < high; i++) {
			this.nuts[i] = temp[i - low];
		}
		return pivotIndex + low;
	}
}
