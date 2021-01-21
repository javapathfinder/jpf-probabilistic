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
 * The Miller-Rabin probabilistic primality test. For prime n, this algorithm
 * always returns true. The probability that the algorithm returns true on a
 * composite input n is at most 1/2, i.e. the probability of error with k trials
 * <= (1/2)^k.
 * 
 * Reference: Algorithm Primality3 in "Randomized Algorithms" by R. Motwani and
 * P. Raghavan.
 * 
 * @author Zainab Fatmi
 */
public class MillerRabinPrimalityTest {
	private MillerRabinPrimalityTest() {}

	/**
	 * Tests whether the given integer is a prime number. If true is returned, the
	 * probability of error is <= (1/2)^k.
	 * 
	 * @param n an integer n > 1.
	 * @param k the number of trials.
	 * @return true if the integer is a prime number, false otherwise.
	 */
	public static boolean isPrime(int n, int k) {
		boolean isPrime = true;
		for (int i = 0; i < k & isPrime; i++) {
			isPrime &= millerRabin(n);
		}
		return isPrime;
	}

	/**
	 * For prime n, this primality testing algorithm always returns true. The
	 * probability that the algorithm returns true on a composite input n is at most
	 * 1/2.
	 * 
	 * @param n an integer n > 1.
	 * @return true if the integer is probably prime, false otherwise.
	 */
	private static boolean millerRabin(int n) {
		// The algorithm only accepts odd numbers as input, so deal with even numbers
		if (n == 2) {
			return true;
		} else if (n % 2 == 0) {
			return false;
		}

		// 1. Compute r and R such that n - 1 = 2^r * R , and R is odd
		int r = 0;
		int R = n - 1;
		while (R % 2 == 0) {
			r++;
			R = R / 2;
		}

		// 2. Choose a uniformly at random from Zn\{0}
		int a = UniformChoice.make(n - 1) + 1;

		// 3. For i = 0 to r compute bi = a^(2^i * R)
		double[] b = new double[r + 1];
		for (int i = 0; i <= r; i++) {
			// Note: I compute the mod of b directly, since that is all we need
			b[i] = mod(a, (int) (Math.pow(2, i) * R), n);
		}

		// 4. If a^(n-1) = br not congruent to 1 (mod n) then return COMPOSITE
		// 5. If a^(R) = b0 congruent to 1 (mod n) then return PRIME
		if (b[r] != 1) {
			return false;
		} else if (b[0] == 1) {
			return true;
		}

		// 6. Let j = max{i | bi not congruent 1 (mod n)}
		int j = -1;
		for (int i = 0; i <= r; i++) {
			if (b[i] != 1) {
				j = i;
			}
		}

		// 7. If bj congruent -1 (mod n) then return PRIME
		// else return COMPOSITE
		if (b[j] == (n - 1)) {
			return true;
		} else {
			return false;
		}
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