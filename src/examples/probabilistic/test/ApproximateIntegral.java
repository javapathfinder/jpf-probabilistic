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
 * Integrates functions.
 * 
 * @author Franck van Breugel
 */
public class ApproximateIntegral {
	private ApproximateIntegral() {}
	
	/**
	 * Given a function f from the unit interval to the unit interval, returns its integral.
	 * 
	 * @param args[0] a string representation of the function using the exp4j format
	 * @param args[1] the number of repeats
	 * @param args[2] the granularity of the samples in the unit interval
	 */
	public static void main(String[] args ) {
		probabilistic.examples.ApproximateIntegral.integrate(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
	}
}
