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
 * The Rabin�Karp randomized string-matching algorithm to find an occurrence (if
 * any) of a given pattern/substring in a text, using finger-printing
 * techniques. Runs in O(n + m) time.
 * 
 * @author Zainab Fatmi
 */
public class RabinKarpPatternMatching {

	/**
	 * The base of the character values. Java uses 16-bit Unicode values.
	 */
	private static final BigInteger base = BigInteger.valueOf(65535);

	/**
	 * Finds the first occurrence (if any) of the given pattern (substring) in the
	 * text.
	 * 
	 * @param text    the string within which to look for the pattern
	 * @param pattern the pattern to which the string is to be matched
	 * @return the index of the first occurrence of the specified pattern in the
	 *         text, or -1 if the text does not contain the pattern
	 */
	public static int find(String text, String pattern) {
		int n = text.length();
		int m = pattern.length();
		int tau; // to limit failure of the fingerprint to O(1/n)
		if (n > Math.sqrt(Integer.MAX_VALUE)) {
			tau = Integer.MAX_VALUE;
		} else {
			tau = n * n * m * (int) (Math.log(n * n * m) / Math.log(2));
		}
		// pick a random prime number, p, less than tau
		ArrayList<Integer> primes = sieveOfEratosthenes(tau);
		BigInteger p = BigInteger.valueOf(primes.get(UniformChoice.make(primes.size())));

		// test the first substring of length m
		int patternPrint = fingerprint(pattern, p);
		String candidate = text.substring(0, m);
		int textPrint = fingerprint(candidate, p);
		BigInteger offset = getOffset(m, p);
		if (textPrint == patternPrint && candidate.equals(pattern)) {
			// a false match of fingerprints occurs O(1/n) times, thus the strings are
			// tested for quality O(1/n) times
			return 0;
		}
		// test subsequent substrings of length m
		for (int i = 1; i <= n - m; i++) {
			candidate = text.substring(i, i + m);
			textPrint = fingerprint(textPrint, offset, p, text, i, i + m);
			if (textPrint == patternPrint && candidate.equals(pattern)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns the offset of the first character in a string of length m.
	 * 
	 * @param m the length of the string
	 * @param p the prime value used in the finger-printing function
	 * @return the offset (base)^(m - 1) of the first character in the string
	 */
	private static BigInteger getOffset(int m, BigInteger p) {
		BigInteger offset = BigInteger.ONE;
		for (int i = 1; i < m; i++) {
			offset = offset.multiply(base).mod(p);
		}
		return offset;
	}

	/**
	 * Computes the fingerprint of the data, using the given prime number.
	 * 
	 * @param data  a string
	 * @param prime a prime number
	 * @return the fingerprint of the data
	 */
	private static int fingerprint(String data, BigInteger prime) {
		int n = data.length();
		if (n == 1) {
			return data.charAt(0) % prime.intValue();
		}
		BigInteger a = BigInteger.valueOf(data.charAt(0));
		BigInteger b = BigInteger.valueOf(data.charAt(1));
		a = a.multiply(base).mod(prime).add(b).mod(prime);
		for (int i = 2; i < n; i++) {
			b = BigInteger.valueOf(data.charAt(i));
			a = a.multiply(base).mod(prime).add(b).mod(prime);
		}
		return a.intValue();
	}

	/**
	 * Computes the rolling fingerprint of the substring of the text beginning at
	 * index (begin) and ending at index (end - 1).
	 * 
	 * @param fingerprint the fingerprint of the previous substring of the text,
	 *                    beginning at index (begin-1) and ending at index (end-2)
	 * @param offset      the offset of the first character in the substring =
	 *                    (base)^(end-begin-1)
	 * @param prime       a prime number
	 * @param text        the string containing the substring
	 * @param begin       the beginning index of the substring (inclusive)
	 * @param end         the ending index of the substring (exclusive)
	 * @return the fingerprint of the substring of the text, beginning at index
	 *         (begin) and ending at index (end-1).
	 */
	private static int fingerprint(int fingerprint, BigInteger offset, BigInteger prime, String text, int begin, int end) {
		BigInteger old = BigInteger.valueOf(text.charAt(begin - 1)).multiply(offset);
		BigInteger last = BigInteger.valueOf(text.charAt(end - 1));
		BigInteger result = BigInteger.valueOf(fingerprint).subtract(old).multiply(base).add(last).mod(prime);
		return result.intValue();
	}

	/**
	 * Sieve of Eratosthenes is a simple algorithm for finding all prime numbers up
	 * to any given limit.
	 * 
	 * @param tau the integer limit.
	 * @return an list of all primes smaller than tau.
	 */
	private static ArrayList<Integer> sieveOfEratosthenes(int tau) {
		ArrayList<Integer> primes = new ArrayList<Integer>();
		boolean[] A = new boolean[tau + 1];
		Arrays.fill(A, true);
		int n = (int) Math.sqrt(tau);

		for (int i = 2; i <= n; i++) { // from 2 to sqrt(tau)
			if (A[i]) {
				primes.add(i);
				int k = i;
				for (int j = k * k; j <= tau; j += k) {
					A[j] = false;
				}
			}
		}
		for (int i = n + 1; i <= tau; i++) { // from after sqrt(tau) to tau
			if (A[i]) {
				primes.add(i);
			}
		}
		return primes;
	}
}
