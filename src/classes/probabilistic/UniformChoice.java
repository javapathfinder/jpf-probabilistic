/*
 * Copyright (C) 2011  Xin Zhang and Franck van Breugel
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

package probabilistic;

import java.util.Arrays;

/**
 * The class <code>UniformChoice</code> contains the static method
 * <code>make</code>. This method, given a positive integer <code>n</code>,
 * returns an integer <code>i</code>, where <code>1 &le; i &lt; n</code>, with
 * equal probability.
 * 
 * @see probabilistic.Choice
 * @author Xin Zhang
 * @author Franck van Breugel
 */
public class UniformChoice {
	private UniformChoice() {}

	/**
	 * Returns an integer in the interval [0, n-1], each with probability 1/n.
	 * 
	 * @param n number of alternatives.
	 * @pre. 1 &le; i &lt; n
	 * @return an integer in the interval [0, n-1], each with probability 1/n.
	 */
	public static int make(int n) {
		double[] p = new double[n];
		Arrays.fill(p, 1.0 / n);
		return Choice.make(p);
	}
}
