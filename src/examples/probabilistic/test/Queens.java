/*
 * Copyright (C) 2020  Maeve Wildes and Xiang Chen
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
 * Calls Queens.place.
 * 
 * @author Maeve Wildes
 * @author Xiang Chen
 */
public class Queens {
	private Queens() {}
	
	/**
	 * Ghost field that captures whether the queens problem was solved successfully.
	 */
	@SuppressWarnings("unused")
	private static boolean success = false; 

	public static void main (String [] args) {
		if (args.length != 1) {
			System.out.println("Usage: java Queens <size of the board>");
			System.exit(0);
		}

		int size = Integer.parseInt(args[0]);
		boolean[][] board = new boolean[size][size];
		success = probabilistic.examples.Queens.place(0, board);			
	}
}
