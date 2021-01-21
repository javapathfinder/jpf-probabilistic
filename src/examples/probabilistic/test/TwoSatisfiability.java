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
 * This app calls TwoSatisfiability.
 * 
 * @author Yash Dhamija
 */
public class TwoSatisfiability {
	private TwoSatisfiability() {}

	/**
	 * Attempts to find a satisfying assignment for the given boolean expression.
	 * 
	 * @param args[0] the number of variables
	 * @param args[1..] the boolean expression: {{1, -2}, {3, 1}, {-1, -2}} represents
	 * (x1 || !x2) && (x3 || x1) && (!x1 || !x2)
	 */
	public static void main(String[] args) {
		if ((args.length - 1) % 2 != 0) {
			System.out.println("Usage: java TwoSatisfiability <number of variables> <boolean expression>");
			System.exit(0);
		}
		
		int numberOfVariables = Integer.parseInt(args[0]);
		int expressions = (args.length - 1) / 2;
		int[][] input = new int[expressions][2];
		int j = 1;
		for (int i = 1; i < args.length; i++) {
			input[i-j++] = new int[]{ Integer.parseInt(args[i]), Integer.parseInt(args[++i]) };
		}
		
		probabilistic.examples.TwoSatisfiability.find(input, numberOfVariables);
	}
}
