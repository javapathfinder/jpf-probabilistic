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

package probabilistic.examples;

import probabilistic.Choice;

/**
 * Making a fair coin flip from a biased coin.
 * 
 * @author Yash Dhamija
 */
public class FairBiasedCoin {

	/**
	 * This method returns the result, 0 or 1, of a fair coin flip based on a biased
	 * coin that returns 0 with certain probability p which is specified by the
	 * user.
	 * 
	 * @param a real number between 0 and 1, exclusive of boundaries.
	 * @return 0 or 1, both with probability 0.5.
	 */
	public static int flip(double p) {
		double[] choices = { p, 1 - p };
		int x = Choice.make(choices);
		int y = Choice.make(choices);

		while ((x == 0 && y == 0) || (x == 1 && y == 1)) {
			x = Choice.make(choices);
			y = Choice.make(choices);
		}

		if (x == 0 && y == 1)
			return 0;
		else
			return 1;
	}
}