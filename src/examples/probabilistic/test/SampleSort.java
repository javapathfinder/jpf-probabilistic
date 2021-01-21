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

package probabilistic.test;

/**
 * A divide-and-conquer sorting algorithm often used in parallel
 * processing (multiprocessor) systems. It significantly improves the
 * expected number of comparisons required to sort the input sequence, 
 * when compared to QuickSort.
 * 
 * @author Yash Dhamija
 */
public class SampleSort {
	private SampleSort() {}

	/**
	 * Sorts the given integers, using the given number of processors with the given minimum threshold.
	 * 
	 * @param args[0] the minimum threshold
	 * @param args[1] the number of processors
	 * @param args[2..] the integers to be sorted
	 */
	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Usage: java SampleSort <int k minmum thresholds> <int p number of processors, p <= k> <integers to sort>");
			System.exit(0);
		}
		
		int threshold = Integer.parseInt(args[0]);
		int processors = Integer.parseInt(args[1]);
		int[] array = new int[args.length - 2];
		for (int i = 2; i < args.length; i++) {
			array[i-2] = Integer.parseInt(args[i]);
		}
		
		(new probabilistic.examples.SampleSort(threshold, processors)).sort(array);
	}
}
