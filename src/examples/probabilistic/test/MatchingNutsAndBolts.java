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
 * This app calls NutsAndBolts.
 * 
 * @author Yash Dhamija
 */
public class MatchingNutsAndBolts {
	private MatchingNutsAndBolts() {}

	/**
	 * Given a collection of n bolts of different widths, and n corresponding nuts, tests whether 
	 * a given nut and bolt fit together.
	 * 
	 * @param args[0] number of nuts (bolts)
	 * @param args[1..] widths of the nuts followed by widths of bolts
	 */
	public static void  main(String[] args) {

		if (args.length < 3) {
			System.out.println("Usage: java MatchingNutsAndBolts <number of nuts> <widths of nuts> <widths of bolts>");
			System.exit(0);
		}
		
		int size = Integer.parseInt(args[0]);

		// same number of nuts and bolts
		if (args.length - 1 != 2 * size) {
			System.out.println("Wrong number of nuts and bolts");
			System.exit(0);
		}
		
		int[] nuts = new int[size];
		int[] bolts = new int[size];
		int i = 1;
		for (; i < size + 1; i++) {
			nuts[i - 1] = Integer.parseInt(args[i]);
		}
		for (; i < args.length; i++) {
			bolts[i - size - 1] = Integer.parseInt(args[i]);
		}
		
		(new probabilistic.examples.MatchingNutsAndBolts(nuts, bolts)).match();
	}
}
