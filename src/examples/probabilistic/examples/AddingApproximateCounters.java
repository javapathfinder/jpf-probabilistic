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

import probabilistic.Choice;

/**
 * Adding Binary Morris Approximate Counters.
 * 
 * @author Zainab Fatmi
 */
public class AddingApproximateCounters {

	/**
	 * The addition of two binary probabilistic Morris counters.
	 * 
	 * @param X an ApproximateCounter, with base 2 and exponent < Integer.MAX_VALUE.
	 * @param Z an ApproximateCounter, with base 2 and exponent < Integer.MAX_VALUE.
	 * @return a new ApproximateCounter with base 2, that represents ≈ X + Z.
	 */
	public static ApproximateCounter add(ApproximateCounter X, ApproximateCounter Z) {
		int exponent;
		int x = X.getExponent();
		int z = Z.getExponent();
		
		int k = Math.max(x, z);
		int l = Math.min(x, z);
		if (allZeroRandomBits(k - l)) {
			if (allZeroRandomBits(l)) {
				exponent = k + 1;
			} else {
				exponent = k;
			}
		} else {
			exponent = k;
		}
		ApproximateCounter result = new ApproximateCounter(1, exponent);
		return result;
	}

	/**
	 * Representation of j random bits, returning true if all j random bits are
	 * equal to zero.
	 * 
	 * @param j the number of random bits.
	 * @return true with probability 2^(-j), and false with probability (1 -
	 *         2^(-j)).
	 */
	private static boolean allZeroRandomBits(int j) {
		double probability = Math.pow(0.5, j);
		return Choice.make(new double[] {probability, 1 - probability}) == 0;
	}
}
