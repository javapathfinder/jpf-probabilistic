/*
 * Copyright (C) 2020  Yash Dhamija
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

package probabilistic.test;

import java.util.HashSet;
import java.util.Set;

/**
 * This app determines the similarity of two integer sets using the Jaccard technique.
 *
 * @author Yash Dhamija
 */
public class MinHash {
	private MinHash() {}

	/**
	 * Determines the similarity of the given sets using the given number of hash functions.
	 * 
	 * @param args[0] number of hash functions to use
	 * @param args[1] size of the first set
	 * @param args[2..] element of the first set followed by elements of the second set
	 */
	public static void main(String[] args) {
		if (args.length < 4) {
			System.out.println("Usage: MinHash <num hash functions> <size first set> <elements of first set> <elements of second set>");
			System.exit(0);
		}
		Set<Integer> first = new HashSet<Integer>();
		Set<Integer> second = new HashSet<Integer>();
		
		int size = Integer.parseInt(args[1]); // size of first set
		
		int i = 2;
		for (; i < 2 + size; i++) {
			first.add(Integer.parseInt(args[i]));
		}
		for (; i < args.length; i++) {
			second.add(Integer.parseInt(args[i]));
		}
		
		(new probabilistic.examples.MinHash(Integer.parseInt(args[0]))).jaccardSimilarity(first, second);
	}
}
