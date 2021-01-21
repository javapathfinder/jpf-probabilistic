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

package probabilistic.test;

/**
 * This app calls RandomizedBinarySearch.search.
 * 
 * @author Yash Dhamija
 * @author Xiang Chen
 */
public class RandomizedBinarySearch {
	private RandomizedBinarySearch() {}
	
	/**
	 * Searches for a value in an array.
	 * 
	 * @param args the value to be searched for, followed by the values of the array to be searched 
	 * @pre. the array is sorted
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: java RandomizedBinarySearch <value> <elements of array separated by spaces>");
			System.exit(0);
		}

		int value = Integer.parseInt(args[0]);
		int[] array = new int[args.length-1];
		for(int i = 1; i < args.length; i++) {
			array[i - 1] = Integer.parseInt(args[i]);
		}
		probabilistic.examples.RandomizedBinarySearch.search(array, value);
		isWorse(array, value, probabilistic.examples.RandomizedBinarySearch.iterations);
	}
	
	/**
	 * Test whether the deterministic version of binary search takes fewer iterations
	 * than the given number of iterations for finding the given value in the given array. 
	 * 
	 * @param array the array to be searched 
	 * @pre. array is sorted
	 * @param value the value to be searched for
	 * @param iterations the number of iterations to compare with
	 * @return true if deterministic version of binary search takes fewer iterations
	 * than the given number of iterations for finding the given value in the given array,
	 * false otherwise
	 */
	private static boolean isWorse(int[] array, int value, int iterations) {
		if (array.length == 0) {
			return false;
		}
		int left = 0;
		int right = array.length - 1;
		while (left < right) {
			iterations--;
			int pivot = (left + right) / 2;
			int middle = array[pivot];
			if (value == middle) {
				return iterations > 0;
			}			
			if(value < middle) {
				right = pivot - 1;
			} else {
				left = pivot + 1;
			}
		}
		return iterations > 0;
	}
}
