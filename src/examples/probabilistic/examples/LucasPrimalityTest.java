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
import java.util.HashSet;
import java.util.Set;

import probabilistic.UniformChoice;

/**
 * The Lucas test, a probabilistic primality test. The algorithm always returns
 * false on a composite number.
 * 
 * @author Zainab Fatmi
 */
public class LucasPrimalityTest {
	private LucasPrimalityTest() {}

	/**
	 * @param n an integer
	 * @pre. n > 1
	 * @param k the security parameter (determines the accuracy of the test)
	 * @pre. k >= 1
	 */
	public static boolean isPrime(int n, int k) {
		// The algorithm only accepts odd numbers as input, so deal with even numbers.
		if (n == 2) {
			return true;
		} else if (n % 2 == 0) {
			return false;
		}
		
		for (int j = 0; j < k; j++) {
			/*
			 * Pick a randomly in the range [2, n-1]. If a^(n-1) is not congruent to 1 (mod
			 * n) then return COMPOSITE.
			 */
			int a = UniformChoice.make(n - 2) + 2;
			if (mod(a, n - 1, n) != 1) {
				return false;
			} else {
				/*
				 * For all prime factors q of n-1: if a^[(n-1)/q] is not congruent to 1 (mod n)
				 * then: if we checked this equality for all prime factors of n-1 then return
				 * PRIME.
				 */
				boolean prime = true;
				Integer[] factors = primeFactors(n - 1);
				for (int q : factors) {
					if (mod(a, (n - 1) / q, n) == 1) {
						prime = false;
					}
				}
				if (prime) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Determines the prime factors of the given integer.
	 * 
	 * @param n an integer
	 * @pre. n > 1
	 * @return an array containing the distinct prime factors of n.
	 */
	private static Integer[] primeFactors(int n) {
		Set<Integer> factors = new HashSet<Integer>();
		while (n % 2 == 0) { // even
			factors.add(2);
			n /= 2;
		}
		int root = (int) Math.sqrt(n);
		for (int i = 3; i <= root; i += 2) { // odd
			while (n % i == 0) {
				factors.add(i);
				n /= i;
			}
		}
		if (n > 2) {
			factors.add(n);
		}
		return factors.toArray(new Integer[0]);
	}
	
	/**
	 * Computes and returns the modulus of an integer raised to an exponent.
	 * 
	 * @param a the base.
	 * @param k the exponent.
	 * @param n the modulus.
	 * @return (a^k) mod n.
	 */
	private static int mod(int a, int k, int n) {
		return BigInteger.valueOf(a).modPow(BigInteger.valueOf(k), BigInteger.valueOf(n)).intValue();
	}
}
