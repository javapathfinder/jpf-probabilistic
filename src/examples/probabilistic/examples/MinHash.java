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
import java.util.Set;

import probabilistic.UniformChoice;

/**
 * The minimum-wise independent permutations locality sensitive hashing scheme:
 * a technique for quickly estimating how similar two sets are, by approximating
 * the Jaccard Index between the two sets. Its expected error is O(1/√k).
 * 
 * @author Zainab Fatmi
 */
public class MinHash {

	private double k;
	private static final int prime = 19; // the first prime >= Integer.MAX_VALUE

	/**
	 * Initialize the Min-Hash algorithm.
	 * 
	 * @param k the number of hash functions to use and the number of signatures per
	 *          set to compute.
	 */
	public MinHash(int k) {
		this.k = k;
	}

	/**
	 * Get the the number of hash functions and the number of signatures per set.
	 * 
	 * @return the number of hash functions and the number of signatures per set.
	 */
	public double getK() {
		return k;
	}

	/**
	 * Set the the number of hash functions and the number of signatures per set.
	 * 
	 * @param k the number of hash functions to use and the number of signatures per
	 *          set to compute.
	 */
	public void setK(int k) {
		this.k = k;
	}

	/**
	 * Estimates the Jaccard similarity between 2 sets of integers. The Jaccard
	 * index is defined as the ratio of the number of elements of their intersection
	 * and the number of elements of their union. J(A,B) = (A ∩ B) / (A ∪ B). The
	 * expected error is O(1/√k).
	 * 
	 * @param A the first set of integers.
	 * @param B the second set of integers.
	 * @return J(A,B), the Jaccard similarity coefficient: 0 when the two sets are
	 *         disjoint, 1 when they are equal, and strictly between 0 and 1
	 *         otherwise.
	 */
	public double jaccardSimilarity(Set<Integer> A, Set<Integer> B) {
		BigInteger p = BigInteger.valueOf(prime);
		double matches = 0;

		for (int i = 0; i < k; i++) {
			BigInteger a = BigInteger.valueOf(UniformChoice.make(prime) + 1);
			BigInteger b = BigInteger.valueOf(UniformChoice.make(prime));
			BigInteger signatureA = BigInteger.valueOf(prime);
			BigInteger signatureB = BigInteger.valueOf(prime);

			for (int x : A) {
				BigInteger f = a.multiply(BigInteger.valueOf(x)).add(b).mod(p);
				if (f.compareTo(signatureA) < 0) {
					signatureA = f;
				}
			}
			for (int x : B) {
				BigInteger f = a.multiply(BigInteger.valueOf(x)).add(b).mod(p);
				if (f.compareTo(signatureB) < 0) {
					signatureB = f;
				}
			}
			if (signatureA.equals(signatureB)) {
				matches++;
			}
		}
		return matches / k;
	}
}
