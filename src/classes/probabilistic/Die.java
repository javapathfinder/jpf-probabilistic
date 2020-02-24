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

/**
 * The class <code>Die</code> contains the static method <code>roll</code>. This
 * method returns an integer in the interval [0, 5], each with probability 1/6.
 * 
 * @see probabilistic.Choice
 * @author Xin Zhang
 * @author Franck van Breugel
 */
public class Die {
	private Die() {}

	/**
	 * Returns an integer in the interval [0, 5], each with probability 1/6.
	 * 
	 * @return an integer in the interval [0, 5], each with probability 1/6.
	 */
	public static int roll() {
		double[] p = { 1.0 / 6.0, 1.0 / 6.0, 1.0 / 6.0, 1.0 / 6.0, 1.0 / 6.0, 1.0 / 6.0 };
		return Choice.make(p);
	}
}
