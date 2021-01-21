/*
 * Copyright (C) 2020  Maeve Wildes
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

import java.util.ArrayList;
import java.util.List;

import probabilistic.UniformChoice;

/**
 * Finds a generator of the group Z* mod p = Z mod p \ {0}.
 * A generator is an element of the group with order p-1.
 * 
 * @author Maeve Wildes
 */
public class GroupGenerator {
	private GroupGenerator() {}
	
	/**
	 * Returns a generator of the group Z* mod p = Z mod p \ {0}.
	 * 
	 * @param p a prime
	 * @return a generator for the group
	 */
	public static int find (int p) {
		if (p == 2) {
			return 1;
		} else {
		// the array group holds the members of Z*(p) = {x | 1<= x <= p and gcd(p, x) = 1}
		int[] group = new int[p - 1];
		for (int i = 0; i < p - 1; i++) {
			group[i] = i + 1;
		}
		
		List<Integer> primeFactors = primeFactors(p - 1);
		int repetitions = p * (int) Math.log(p);
		for (int i = 0; i < repetitions; i++) {
			// select a random element from the group and determine whether it is a generator
			int x = group[UniformChoice.make(p - 1)];
			if (isGenerator(x, primeFactors, p)) {
				return x;
			}
		}
		return 0; // failure to find a generator
		}
	}
	/**
	 * Checks whether the given integer x is a generator, that is, its order is p - 1. 
	 * If for each prime factor pi of p - 1, x^((p - 1) / pi) = 1 (mod p), then x is a generator.
	 * 
	 * @param x the integer to be check 
	 * @param primeFactors th prime factors of p - 1
	 * @param p the prime
	 * @return true if the given integer x has order p - 1, false otherwise
	 */
	private static boolean isGenerator(int x, List<Integer> primeFactors, int p) {
		for (int i = 0; i < primeFactors.size(); i++) {
			int power = (int) Math.pow(x, ((p - 1) / primeFactors.get(i)));
			if (power % p == 1) {
				return false;
			}
		}
		return  true;	
	}
	/**
	 * Returns the distinct prime factors of given integer.
	 * 
	 * @param n an integer
	 * @return the distinct prime factors of given integer
	 */
	private static List<Integer> primeFactors(int n) {
		List<Integer> primeFactors = new ArrayList<Integer>();
		if (n % 2 == 0) {
			primeFactors.add(2);
		}
		while (n % 2 == 0) {
			n /= 2;
		}
		
		for (int i = 3; i <= Math.sqrt(n); i += 2) {
			if (n % i == 0) {
				primeFactors.add(i);
			}
			while(n % i == 0) {
				n /= i;
			}
		}
		if (n > 2) {
			primeFactors.add(n);
		}
		return primeFactors;
	}
}
