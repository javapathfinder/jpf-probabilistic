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
 * This app calls PolyRoot.get.
 * 
 * @author Yash Dhamija
 */
public class RandomizedPolynomialFactorization {
	private RandomizedPolynomialFactorization() {}

	/**
	 * Finds the roots a and b of a polynomial f(x) = x^2 + ax + b
     * over the field Zp, where p is a prime number.
	 * 
	 * @param args[0] the prime p
	 * @param args[1] the coefficient a of the polynomial f(x) = x^2 + ax + b
	 * @param args[2] the coefficient b of the polynomial f(x) = x^2 + ax + b
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Usage: java RandomizedPolynomialFactorization <prime number> <first coefficient> <second coefficient>");
			System.exit(0);
		}
		probabilistic.examples.RandomizedPolynomialFactorization.get(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
	}
}
