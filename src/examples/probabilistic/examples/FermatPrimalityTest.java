/*
 * Copyright (C) 2020  Zainab Fatmi
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

import java.math.BigInteger;

import probabilistic.UniformChoice;

/**
 * The Fermat probabilistic primality test. When the algorithm declares a number
 * as composite, it is definitely composite. However, if the algorithm declares
 * a number as prime, there is no proof that it is prime.
 * 
 * Reference: Algorithm 2.149 in the "Handbook of Applied Cryptography" by A.
 * Menezes, P. van Oorschot and S. Vanstone.
 * 
 * @author Zainab Fatmi
 */
public class FermatPrimalityTest {
	private FermatPrimalityTest() {}

	/**
	 * Attempts to determine whether the given number is prime.
	 * 
	 * @param n an integer 
	 * @pre. n > 1
	 * @param t the security parameter (number of trials)
	 * @pre. t >= 1
	 */
	public static boolean isPrime(int n, int t) {
		if (n == 2) {
			return true;
		} else if (n % 2 == 0) {
			return false;
		}

		for (int i = 0; i < t; i++) {
			// 1. Choose a random integer, 1 < a < n-1
			int a = UniformChoice.make(n - 2) + 2;
			
			// 2. Compute r = a^(n-1) (mod n)
			int r = BigInteger.valueOf(a).modPow(BigInteger.valueOf(n - 1), BigInteger.valueOf(n)).intValue();
			
			// 3. if r != 1, return composite
			if (r != 1) {
				return false;
			}
		}
		return true;
	}
}
