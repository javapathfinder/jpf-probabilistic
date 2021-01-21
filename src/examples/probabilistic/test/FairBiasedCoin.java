/*
 * Copyright (C) 2020  Yash Dhamija and Xiang Chen
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
 * This class returns the result of a fair coin flip.
 * 
 * @author Yash Dhamija
 * @author Xiang Chen
 */
public class FairBiasedCoin {

	/**
	 * Creates a fair coin flip using a biased coin.
	 * 
	 * @param args[0] the bias of the coin, a double in the range (0, 1).
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java probabilistic.test.FairBiasedCoin <bias>");
		} else {
			double bias = Double.parseDouble(args[0]);
			probabilistic.examples.FairBiasedCoin.flip(bias);
		}
	}
}
