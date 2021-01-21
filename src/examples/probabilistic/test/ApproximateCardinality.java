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

/**
 *  Estimates the cardinality of a union of sets.
 *  
 *  @author Yash Dhamija
 */
public class ApproximateCardinality {

	/**
	 * @param number of samples, number of sets, followed by sets
	 * For e.g, 4 3 1245, 876,11234 determines three sets;
	 * {1,2,4,5}, {8,7,6}, {1,1,2,3,4}. For simplicity elements of sets are digits [0-9]
	 */
	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Usage: ApproximateCardinality <int number of sets> <sets separated by comma>");
			System.exit(0);
		}
		
		int n = Integer.parseInt(args[1]);
		HashSet<Integer>[] sets = new HashSet[n];
		
		for(int i = 2; i < args.length; i++) {
			char[] c = args[i].toCharArray();
			sets[i-2] = new HashSet<Integer>();
			for (int j = 0; j < c.length; j++)
				sets[i-2].add((int) c[j]);
		}
		
		probabilistic.examples.ApproximateCardinality<Integer> estimator = new probabilistic.examples.ApproximateCardinality<Integer>();
		estimator.getCardinality(sets, Integer.parseInt(args[0]));
	}
}
