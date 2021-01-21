/*
 * Copyright (C) 2020  Franck van Breugel
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
 * Calls LucasPrimalityTesting.isPrime.
 * 
 * @author Franck van Breugel
 */
public class LucasPrimalityTest {
	private LucasPrimalityTest() {}

	/**
	 * Tests whether a number is prime.
	 * 
	 * @param args[0] the integer to be checked for primality
	 * @param args[1] the security parameter, an integer that determines the accuracy of the test
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java LucasPrimalityTest <integer checked to be prime> <integer used to specify the accuracy>");
		} else {
			int n = Integer.parseInt(args[0]);
			int k = Integer.parseInt(args[1]);
			probabilistic.examples.LucasPrimalityTest.isPrime(n, k);
		}
	}
}
