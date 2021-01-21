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
 * This class checks whether a number is prime.
 * 
 * @author Yash Dhamija
 * @author Zainab Fatmi
 */
public class SolovayStrassenPrimalityTest {
	private SolovayStrassenPrimalityTest() {}
	
	/**
	 * Determines whether the given number is prime, using the Solovay-Strassen
	 * primality test.
	 * 
	 * @param args[0] the integer n > 1 to check.
	 * @param args[1] the number of trials.
	 */
	public static void main(String[] args) {
		final int ARGS = 2;
		if (args.length != ARGS) {
			System.out.println("Usage: java probabilistic.test.SolovayStrassenPrimalityTest <number> <trials>");
		} else {
			int n = Integer.parseInt(args[0]);
			int k = Integer.parseInt(args[1]);

			probabilistic.examples.SolovayStrassenPrimalityTest.isPrime(n, k);
		}
	}
}
