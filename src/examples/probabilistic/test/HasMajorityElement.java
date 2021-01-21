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
 * This app looks for a majority element.
 * 
 * @author Yash Dhamija
 * @author Xiang Chen
 */
public class HasMajorityElement {
	private HasMajorityElement() {}

	/**
	 * Ghost field that captures whether the randomized algorithm returns the incorrect answer,
	 * that is, an answer different from the deterministic algorithm.
	 */
	@SuppressWarnings("unused")
	private static boolean incorrect = false;

	/**
	 * Tries to find a majority element for the given integers.
	 * 
	 * @param args one or more integers
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: java HasMajorityElement <one or more integers>");
			System.exit(0);
		}

		int[] a = new int[args.length];
		for (int i = 0; i < args.length; i++) {
			a[i] = Integer.parseInt(args[i]);
		}

		incorrect = probabilistic.examples.HasMajorityElement.majority(a) != deterministicMajority(a);
	}

	/**
	 * Checks whether the given integer array has a majority element.
	 * 
	 * @param a an integer array
	 * @return true if the given integer array has a majority element,
	 * false otherwise
	 */
	private static boolean deterministicMajority(int[] a) {
		for (int candidate : a) {
			int count = 0;
			for (int element : a) {
				if (element == candidate) {
					count++;
				}
			}
			if (count > a.length / 2) {
				return true;
			}
		}
		return false;
	}
}
