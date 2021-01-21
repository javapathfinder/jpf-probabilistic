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
 * Calls FermatPrimalityTest.isPrime.
 * 
 * @author Franck van Breugel
 */
public class FermatPrimalityTest {
	private FermatPrimalityTest() {}
	
	/**
	 * Checks if the given number is prime.
	 * 
	 * @param args[0] the number to be checked to be prime
	 * @param args[1] the number of trails
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: java FermatPrimalityTest <number> <trials>");
			System.exit(0);
		}
		
		probabilistic.examples.FermatPrimalityTest.isPrime(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
	}
}
