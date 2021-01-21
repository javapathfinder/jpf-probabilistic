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

package probabilistic.examples;

import probabilistic.UniformChoice;

/**
 * This app aproximates the value of pi.
 * 
 * @author Franck van Breugel
 */
public class ApproximatePI {
	private ApproximatePI() {}

	/**
	 * Approximates pi by randomly choosing points in a unit square and 
	 * determine the percentage of points that are at distance less than
	 * one from the left lower corner of the square.  This approximates pi/4.
	 *  
	 * @param points the number of randomly chosen points
	 * @param size the size of the grid from which the points are chosen
	 * @return an approximation of pi
	 */
	public static double get(int points, int size) {
		int in = 0; // number of points inside the circle
		int out = 0; // number of points outside the circle
		for (int i = 0; i < points; i++) {
			double x = UniformChoice.make(size) / (double) size;
			double y = UniformChoice.make(size) / (double) size;
			double distance = x * x + y * y;
			if (distance < 1.0) {
				in++;
			} else {
				out++;
			}
		}
		double pi = 4 * (in / (double) (out + in));
		return pi;
	}
}
