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

import probabilistic.UniformChoice;

/**
 * An implementation of a randomized algorithm to determine if an array of integers
 * has a majority element (number of occurrences is more than half of the array)
 * described in
 * Rajeev Motwani and ‎Prabhakar Raghavan. Randomized Algorithms. Cambridge University
 * Press. 1995.
 *
 * This is a Monte Carlo algorithm, with probability of error less than 1/2.
 * 
 * @author Maeve Wildes
 */
public class HasMajorityElement {
	private HasMajorityElement() {}

	/**
	 * Returns whether a majority element of the given integer array has been found.
	 * 
	 * @param a an integer array
	 * @return true if a majority element of the given array has been found,
	 * false otherwise
	 */
	public static boolean majority(int[] a) {
		int candidate = a[UniformChoice.make(a.length)];
		int count = 0;
		for (int element : a) {
			if (element == candidate) {
				count++;
			}
		}
		return count > a.length / 2;
	}
}
