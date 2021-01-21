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
 * This app aproximates the value of pi.
 * 
 * @author Franck van Breugel
 */
public class ApproximatePI {
	private ApproximatePI() {}
	
	/**
	 * Approximates pi.
	 *  
	 * @param args[0] the number of randomly chosen points
	 * @param args[1] the size of the grid from which the points are chosen
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Use: java ApproximatePI <number of points> <size of grid>");
		} else {
			probabilistic.examples.ApproximatePI.get(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		}
	}
}
