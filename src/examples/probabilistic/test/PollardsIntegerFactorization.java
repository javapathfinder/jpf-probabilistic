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
 * This app calls Pollard.factor.
 * 
 * @author Yash Dhamija
 * @author Xiang Chen
 */
public class PollardsIntegerFactorization {
	private PollardsIntegerFactorization() {}
	
	/**
	 * Ghost field that captures whether Pollard's algorithm returns the number itself.
	 */
	@SuppressWarnings("unused")
	private static boolean identical = false;

	/**
	 * Factors an integer.
	 * 
	 * @param args[0] an integer
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java PollardsIntegerFactorization <integer>");
			System.exit(0);
		}
		
		int n = Integer.parseInt(args[0]);
		identical = probabilistic.examples.PollardsIntegerFactorization.factor(n) == n;
	}
}