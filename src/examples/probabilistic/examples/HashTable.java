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
import java.util.Arrays;
import java.util.HashMap;

import probabilistic.UniformChoice;

/**
 * A randomized hash table with integer key values. Collisions are handled by
 * storing a collection of elements at each index in the hash table. The
 * expected time for a FIND, INSERT or DELETE operation is |S| / n, where n is
 * the capacity of the hash table and |S| is the amount of values stored in the
 * hash table.
 * 
 * @author Zainab Fatmi
 *
 * @param <T> the type of values stored in the hash table.
 */
public class HashTable<T> {
	
	/**
	 * A family of hash functions H = {h : M -> N}, where each h E H is easily
	 * represented and evaluated. For a 2-universal family H and any x != y, the
	 * expected collisions <= |H|/n = p(p-1)/n.
	 */
	private static class UniversalHashing {

		private int a;
		private int b;
		private int m;
		private int n;
		private int p;

		/**
		 * Initializes a random hash function from the 2-universal hash family : ℤm ->
		 * ℤn.
		 * 
		 * @param m the limit of the domain, m >= n.
		 * @param n the limit of the range, n <= m.
		 */
		public UniversalHashing(int m, int n) {
			this.m = m;
			this.n = n;
			this.p = getPrime(m);
			this.a = UniformChoice.make(p-1) + 1; // element of ℤp, a != 0
			this.b = UniformChoice.make(p); // element of ℤp
		}

		/**
		 * Returns a hash of the input.
		 * 
		 * @param x an integer in ℤm.
		 * @return the hash of x, an integer in ℤn.
		 */
		public int hash(int x) {
			BigInteger A = BigInteger.valueOf(this.a);
			BigInteger B = BigInteger.valueOf(this.b);
			BigInteger X = BigInteger.valueOf(x);
			BigInteger P = BigInteger.valueOf(p);
			int f = (A.multiply(X).add(B)).mod(P).intValue(); // ℤm -> ℤp
			int g = f % n; // ℤp -> ℤn
			return g;
		}

		/**
		 * Get the domain ℤm.
		 * 
		 * @return the limit of the domain of the hash function.
		 */
		public int getM() {
			return m;
		}

		/**
		 * Get the range ℤn.
		 * 
		 * @return the limit of the range of the hash function.
		 */
		public int getN() {
			return n;
		}

		/**
		 * Returns a prime number in the interval [m, 2n]. Relies on the well-known
		 * result in number theory called Bertrand's Postulate states that for any
		 * number m, there exists a prime between m and 2m. Uses the concepts of the
		 * Sieve of Eratosthenes, a simple algorithm for finding all prime numbers up to
		 * any given limit.
		 * 
		 * @param m the lower bound on the prime number.
		 * @return the smallest prime number >= m.
		 */
		private int getPrime(int m) {
			if (m == Integer.MAX_VALUE) {
				return m;
			}
			
			int lowerLimit = m;
			int upperLimit = 2 * m;
			boolean[] A = new boolean[upperLimit + 1];
			Arrays.fill(A, true);
			int n = (int) Math.ceil(Math.sqrt(upperLimit));

			for (int i = 2; i <= n; i++) { // from 2 to sqrt(tau)
				if (A[i]) {
					if (i >= lowerLimit) {
						return i;
					}
					for (int j = i * i; j <= upperLimit; j += i) {
						A[j] = false;
					}
				}
			}
			for (int i = n + 1; i <= upperLimit; i++) { // from after sqrt(tau) to tau
				if (A[i] && i >= lowerLimit) {
					return i;
				}
			}
			return Integer.MAX_VALUE;
		}
	}

	/** A random hash function from a 2-universal family of hash functions. */
	private UniversalHashing hashFunction;
	private HashMap<Integer, T>[] table;

	/**
	 * Initialize the hash table with a capacity of n. The maximum value of a key is
	 * the default Integer.MAX_VALUE.
	 * 
	 * @param n the capacity of the hash table;
	 */
	public HashTable(int n) {
		this(Integer.MAX_VALUE, n);
	}

	/**
	 * Initialize the hash table with a capacity of n, and a maximum key value of m.
	 * 
	 * @param m the maximum key value.
	 * @param n capacity of the hash table.
	 */
	public HashTable(int m, int n) {
		hashFunction = new UniversalHashing(m, n);
		table = new HashMap[n];
		for (int i = 0; i < n; i++) {
			table[i] = new HashMap<Integer, T>();
		}
	}

	/**
	 * Insert an element into the hash table, with a distinct key.
	 * 
	 * @param key   <= m. the key of the element, not previously contained in the
	 *              hash table.
	 * @param value the value mapped to the key.
	 */
	public void insert(int key, T value) {
		HashMap<Integer, T> list = table[hashFunction.hash(key)];
		list.put(key, value);
	}

	/**
	 * Returns the value to which the specified key is mapped, or null if this map
	 * contains no mapping for the key.
	 * 
	 * @param key <= m. the key of the element whose associated value is to return.
	 * @return the value mapped to the key, or null if the hash table does not
	 *         contain the key.
	 */
	public T find(int key) {
		HashMap<Integer, T> list = table[hashFunction.hash(key)];
		return list.get(key);
	}

	/**
	 * Deletes the element specified by the given key from the hash table.
	 * 
	 * @param key <= m. the key of the element to be removed.
	 * @return the value previously mapped to the key, or null if the hash table did
	 *         not contain the key.
	 */
	public T delete(int key) {
		HashMap<Integer, T> list = table[hashFunction.hash(key)];
		return list.remove(key);
	}
}
