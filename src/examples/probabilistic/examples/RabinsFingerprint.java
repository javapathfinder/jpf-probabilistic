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
import java.util.ArrayList;
import java.util.Arrays;

import probabilistic.UniformChoice;

/**
 * A class to verify the equality of strings using finger-prints.
 * 
 * Applications: comparing databases for consistency, without transmitting the
 * entire database. One party finds a fingerprint of their data, transmits the
 * prime number and fingerprint to the other party, who find the fingerprint of
 * their data using the prime and compares it to the received fingerprint. If
 * the size of the database is N, the number of transmitted bits is O(log(t) +
 * log(log(N))).
 * 
 * @author Zainab Fatmi
 */
public class RabinsFingerprint {

	/**
	 * Verifies the equality of the given strings, with an error probability of at
	 * most O(1/t).
	 * 
	 * @param string1 the first string.
	 * @param string2 the second string.
	 * @param t a large integer to bound the error probability.
	 * @return true if they are equal, false otherwise.
	 */
	public boolean verifyEquality(String string1, String string2, int t) {
		return isConsistent(findFingerprint(string1, t), string2);
	}

	/**
	 * Finds a fingerprint of the data, using a random prime number.
	 * 
	 * @param data a string containing the binary data.
	 * @param t    a large integer to bound the error probability.
	 * @return an array containing binary representations of the prime number used
	 *         (first element) and the fingerprint of the data (second element).
	 */
	public String[] findFingerprint(String data, int t) {

		double n = Math.log10(data.length()) / Math.log10(2);
		double temp = t * n;
		int tau = (int) Math.ceil(temp * (Math.log10(temp) / Math.log10(2)));
		Integer[] primes = sieveOfEratosthenes(tau);

		int i = UniformChoice.make(primes.length);
		int p = primes[i];

		BigInteger m = new BigInteger(data, 2);
		BigInteger h = m.mod(BigInteger.valueOf(p));
		return new String[] { Integer.toBinaryString(p), h.toString(2) };
	}

	/**
	 * Compares a prime number and fingerprint with data to determine whether they
	 * are consistent. The probability of error is at most O(1/t).
	 * 
	 * @param message an array containing binary representations of a prime number
	 *                (first element) and a fingerprint (second element).
	 * @param data    a string containing the binary data.
	 * @return true if the fingerprint of the data (using the given prime number)
	 *         and the given fingerprint are equal, false otherwise.
	 */
	public boolean isConsistent(String[] message, String data) {
		int p = Integer.parseUnsignedInt(message[0], 2);
		BigInteger fingerprint = new BigInteger(message[1], 2);

		BigInteger m = new BigInteger(data, 2);
		BigInteger h = m.mod(BigInteger.valueOf(p));

		return fingerprint.equals(h);
	}

	/**
	 * Sieve of Eratosthenes is a simple algorithm for finding all prime numbers up
	 * to any given limit.
	 * 
	 * @param tau the integer limit.
	 * @return an array containing all primes smaller than tau.
	 */
	private Integer[] sieveOfEratosthenes(int tau) {
		ArrayList<Integer> primes = new ArrayList<Integer>();
		boolean[] A = new boolean[tau + 1];
		Arrays.fill(A, true);
		int n = (int) Math.sqrt(tau);

		for (int i = 2; i <= n; i++) { // from 2 to sqrt(tau)
			if (A[i] == true) {
				primes.add(i);
				int k = i;
				for (int j = k * k; j <= tau; j += k) {
					A[j] = false;
				}
			}
		}
		for (int i = n + 1; i <= tau; i++) { // from after sqrt(tau) to tau
			if (A[i] == true) {
				primes.add(i);
			}
		}
		return primes.toArray(new Integer[primes.size()]);
	}
}
