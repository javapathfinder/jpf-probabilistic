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
 * The class <code>Coin</code> contains the static method <code>flip</code>.
 * This method returns either 0 or 1, both with probability 0.5.
 * 
 * @see probabilistic.Choice
 * @author Xin Zhang
 * @author Franck van Breugel
 */
public class Coin {
	private Coin() {}

	/**
	 * Returns either 0 or 1, both with probability 0.5.
	 * 
	 * @return either 0 or 1, both with probability 0.5.
	 */
	public static int flip() {
		double[] p = { 0.5, 0.5 };
		return Choice.make(p);
	}
}
