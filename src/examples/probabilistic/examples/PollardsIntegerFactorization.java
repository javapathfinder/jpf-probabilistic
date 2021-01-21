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
 * A randomized algorithm for factoring presented in
 * John Pollard. Theorems of factorization and primality testing. 
 * Proceedings of the Cambridge Philosophical Society. 76 (3): 521-528, November 1974.
 * 
 * @author Maeve Wildes
 */
public class PollardsIntegerFactorization {
	private PollardsIntegerFactorization() {}
	
	/**
	 * Returns a factor of the given integer.
	 * 
	 * @param n an integer
	 * @return a factor of n
	 */
	public static int factor(int n) {
		List<Integer> pseudoRandomSequence = new ArrayList<Integer>();
		pseudoRandomSequence.add(UniformChoice.make(n));
		for (int i = 1; i < n; i++) {
			pseudoRandomSequence.add(((int) Math.pow(pseudoRandomSequence.get(i - 1), 2) - 1) % n);
			for (int j = 0; j < i; j++) {
				int x = pseudoRandomSequence.get(i) - pseudoRandomSequence.get(j);
				int factor = gcd(x, n);
				if (factor != 1) {
					return factor;
				}
			}
		}
		return 0;
	}
	
	/**
	 * Returns the greatest common divisor of the given integers.
	 * 
	 * @param a an integer
	 * @param b an integer
	 * @return the greatest common divisor of a and b
	 */
	private static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		}
		else {
			return gcd(b, a % b);
		}
	}
}