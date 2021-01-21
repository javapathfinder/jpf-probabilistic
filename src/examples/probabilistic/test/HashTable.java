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

/**
 * Calls methods on a hash table.
 * 
 * @author Yash Dhamija
 */
public class HashTable {
	private HashTable() {}

	/**
	 * Processes the given command line argument as a series of operations to perform on
	 * a hash table.  It supports insertion, find, and delete.
	 * 
	 * @param args[0] the number of insertions
	 * @param args[1] the number of search queries
	 * @param args[2] the number of deletions
	 * @param args[3..] the queries
	 */
	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Usage: java HashTable <number of insertions> <number of searches> <number of deletions> <queries>..");
			System.exit(0);
		}
		
		int i = Integer.parseInt(args[0]);
		int s = Integer.parseInt(args[1]);
		int d = Integer.parseInt(args[2]);
		
		if (args.length != 3 + (2 * i) + s + d) {
			System.out.println("Invalid number of queries provided");
			System.exit(0);
		}
		
		int[][] insertions = new int[i][2];
		
		for (int m = 3, k = 0; m < 3 + 2 * i; k++) {
			insertions[k][0] = Integer.parseInt(args[m++]);
			insertions[k][1] = Integer.parseInt(args[m++]);
		}
		
		int max = Integer.MIN_VALUE;
		for (int[] k : insertions) {
			if (k[0] > max) {
				max = k[0];
			}
		}
			
		probabilistic.examples.HashTable<Integer> ht = new probabilistic.examples.HashTable<>(max, 2 * i);
		
		for (int[] k : insertions) {
			ht.insert(k[0], k[1]);
		}
		
		for (int m = 3 + 2 * i; m < 3 + 2 * i + s; m++) {
			ht.find(Integer.parseInt(args[m]));
		}
		
		for (int m = 3 + 2 * i + s; m < args.length; m++) {
			ht.delete(Integer.parseInt(args[m]));
		}
	}
}
