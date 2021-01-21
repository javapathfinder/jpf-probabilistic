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

import java.util.ArrayList;
import java.util.Arrays;

import probabilistic.UniformChoice;

/**
 * Samplesort is a divide-and-conquer sorting algorithm often used in parallel
 * processing (multiprocessor) systems. Samplesort significantly improves the
 * expected number of comparisons required to sort the input sequence, when
 * compared to QuickSort.
 * 
 * @author Zainab Fatmi
 */
public class SampleSort {
	private int p;
	private int k;

	/**
	 * Initializes the number of processors and the minimum threshold. When the
	 * array size is lower than the threshold, the algorithm stops recursing and
	 * uses quick-sort.
	 * 
	 * @param k >= p, minimum threshold for sample-sort
	 * @param p number of processors/buckets
	 */
	public SampleSort(int k, int p) {
		this.p = p;
		this.k = k;
	}

	/**
	 * Sorts the array in ascending order.
	 * 
	 * @param A the array to be sorted, with minimal (< k) duplicates of elements
	 */
	public void sort(int[] A) {
		sampleSort(A, 0, A.length);
	}

	/**
	 * Sorts the specified sub-array A[begin, end) in place, in ascending order.
	 * 
	 * @param A     the array to be sorted
	 * @param begin the index of the first element to be sorted (inclusive)
	 * @param end   the index of the last element to be sorted (exclusive)
	 */
	private void sampleSort(int[] A, int begin, int end) {
		int n = end - begin;
		if (n < k) {
			// if the size of the array is below the threshold, use any fast sorting
			// algorithm
			Arrays.sort(A, begin, end);
		} else {
			// select (p - 1) splitters randomly and sort them
			int[] splitters = new int[p - 1];
			for (int i = 0; i < p - 1; i++) {
				splitters[i] = A[UniformChoice.make(n) + begin];
			}
			Arrays.sort(A, 0, p - 1);

			// split the array into p buckets (each pair of adjacent splitters defines a
			// bucket)
			@SuppressWarnings("unchecked")
			ArrayList<Integer>[] buckets = new ArrayList[p];
			for (int i = 0; i < p; i++) {
				buckets[i] = new ArrayList<Integer>();
			}
			for (int i = begin; i < end; i++) {
				int j = 0;
				while (j < p - 1 && A[i] > splitters[j]) {
					j++;
				}
				buckets[j].add(A[i]);
			}
			int index = begin;
			for (int i = 0; i < p; i++) {
				for (int j = 0; j < buckets[i].size(); j++) {
					A[index++] = buckets[i].get(j);
				}
			}
			// recurse on each bucket (each processor sorts one bucket)
			for (int i = 0; i < p; i++) {
				end = buckets[i].size() + begin;
				sampleSort(A, begin, end);
				begin = end;
			}
		}
	}
}
