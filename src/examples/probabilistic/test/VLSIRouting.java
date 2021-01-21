/*
 * Copyright (C) 2020  Franck van Breugel
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
 * This app calls Wiring.getDirections.
 * 
 * @author Franck van Breugel
 */
public class VLSIRouting {
	private VLSIRouting() {}

	/**
	 * 
	 * @param args[0] the number of columns of the gate array
	 * @param args[1] the number of rows of the gate array
	 * @param args[2] the number of nets
	 * @param args[3..] x- and y-coordinates of the endpoints of the nets
	 */
	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Usage: java VLSIRouting <number of columns> <number of rows> <number of nets> <end-points of nets>");
		} else {		
			int columns = Integer.parseInt(args[0]);
			int rows = Integer.parseInt(args[1]);
			int number = Integer.parseInt(args[2]);
			int[][] endPoints = new int[number][4];
			int i = 3;
			for (int n = 0; n < number; n++) {
				for (int j = 0; j < 4; j++) {
					endPoints[n][j] = Integer.parseInt(args[i]);
					i++;
				}
			}

			probabilistic.examples.VLSIRouting.getDirections(columns, rows, endPoints);
		}
	}
}
