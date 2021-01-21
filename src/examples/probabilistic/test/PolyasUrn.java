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
 * This app calls PolyasUrn.main.
 * 
 * @author Yash Dhamija
 */
public class PolyasUrn {

	/**
	 * Processes the input to pass as argument for Polyasurn.main.
	 * 
	 * @param args[0] the number of selections
	 * @param args[1] the number of black balls
	 * @param args[2] the number of white balls
	 * @param args[3] balls to be added per selection
	 */
	public static void main(String[] args) {
		if (args.length != 4) {
			System.out.println("Usage: java PolyasUrn <number of selections> <number of black balls> <number of white balls> <number of balls to replaced with>");
			System.exit(0);
		}
		
		int n = Integer.parseInt(args[0]);
		int b = Integer.parseInt(args[1]);
		int w = Integer.parseInt(args[2]);
		int c = Integer.parseInt(args[3]);
		
		probabilistic.examples.PolyasUrn.main(b, w, c, n);
	}
}
