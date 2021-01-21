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
 * The Solovay-Strassen probabilistic primality test. This algorithm is always
 * correct when it returns COMPOSITE. The probability that the algorithm returns
 * PRIME when n is composite is at most 1/2, i.e. the probability of error with
 * k trials <= (1/2)^k.
 * 
 * Reference: Algorithm Primality1 in "Randomized Algorithms" by R. Motwani and
 * P. Raghavan.
 * 
 * @author Zainab Fatmi
 */
public class SolovayStrassenPrimalityTest {
	private SolovayStrassenPrimalityTest() {}

	/**
	 * Tests whether the given integer is a prime number. If true is returned, the
	 * probability of error is <= (1/2)^k.
	 */
	public static boolean isPrime(int n, int k) {
		boolean isPrime = true;
		for (int i = 0; i < k & isPrime; i++) {
			isPrime &= solovayStrassen(n);
		}
		return isPrime;
	}

	/**
	 * This primality testing algorithm is always correct when it returns false. The
	 * probability that the algorithm returns true when n is composite is at most
	 * 1/2.
	 * 
	 * @param n an integer n > 1.
	 * @return true if the integer is probably prime, false otherwise.
	 */
	private static boolean solovayStrassen(int n) {
		// The algorithm only accepts odd numbers as input, so deal with even numbers
		if (n == 2) {
			return true;
		} else if (n % 2 == 0) {
			return false;
		}

		// 1. Choose a uniformly at random from Zn\{0}
		int a = UniformChoice.make(n - 1) + 1;

		// 2. Compute gcd(a, n).
		// 3. If gcd(a, n) not equal 1 then return COMPOSITE.
		if (gcd(a, n) != 1) {
			return false;
		}

		// 4 Compute [a|n] and a^((n-1)/2) (mod n).
		// 5. If [a|n] congruent a^((n-1)/2) (mod n) then return PRIME
		// else return COMPOSITE.
		if (mod(jacobi(a, n), 1, n) == mod(a, (n - 1) / 2, n)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Computes the Jacobi Symbol, [a|n].
	 * 
	 * Reference: Algorithm 2.149 in the "Handbook of Applied Cryptography" by A.
	 * Menezes, P. van Oorschot and S. Vanstone.
	 * 
	 * @param a an integer 0 <= a < n.
	 * @param n an odd integer n >= 3.
	 * @return the value of the Jacobi Symbol [a|n].
	 */
	private static int jacobi(int a, int n) {
		if (a == 0) {
			return 0;
		} else if (a == 1) {
			return 1;
		}

		// a = (2^e) * a1, where a1 is odd
		int e = 0;
		int a1 = a;
		while (a1 % 2 == 0) {
			e++;
			a1 = a1 / 2;
		}

		int s;
		if (e % 2 == 0) { // if e is even
			s = 1;
		} else if (n % 8 == 1 || n % 8 == 7) {
			s = 1;
		} else { // if (n % 8 == 3 || n % 8 == 5) {
			s = -1;
		}

		if (n % 4 == 3 && a1 % 4 == 3) {
			s = -s;
		}

		if (a1 == 1) {
			return s;
		} else {
			int n1 = n % a1;
			return s * jacobi(n1, a1);
		}
	}
	
	/**
	 * Returns the greatest common divisor of two integers.
	 * 
	 * @param a first integer.
	 * @param n second integer.
	 * @return the greatest common divisor of a and n.
	 */
	private static int gcd(int a, int n) {
		return BigInteger.valueOf(a).gcd(BigInteger.valueOf(n)).intValue();
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

