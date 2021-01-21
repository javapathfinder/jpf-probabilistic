/*
 * Copyright (C) 2020  Yash Dhamija and Xiang Chen
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

/*
 * A Las Vegas randomized binary search algorithm. 
 *  
 * @author Yash Dhamija
 * @author Xiang Chen
 */
public class RandomizedBinarySearch {
	private RandomizedBinarySearch() {}
	
	/**
	 * Ghost field that captures the number of iterations of this randomized algorithm.
	 */
	@SuppressWarnings("unused")
	public static int iterations;
	
	/**
	 * Returns the index of the given value in the given sorted array if the array contains the value.
	 * Returns -1 otherwise.
	 *
	 * @param array the array to be searched
	 * @pre. array is sorted
	 * @param value the value to be searched for
	 * @return the index of the value in the array if present, -1 otherwise
	 */
	public static int search(int[] array, int value) {
		iterations = 0; 
		if (array.length == 0) {
			return -1;
		}
		int left = 0;
		int right = array.length - 1;
		while (left < right) {
			iterations++;
			int range = right - left + 1;
			int pivot = UniformChoice.make(range) + left;
			int middle = array[pivot];
			if (value == middle) {
				return pivot;
			}			
			if(value < middle) {
				right = pivot - 1;
			} else {
				left = pivot + 1;
			}
		}
		return -1;
	}
}