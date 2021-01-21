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
 * Calls verify method of PolynomialIdentities class.
 * 
 * @author Franck van Breugel
 */
public class PolynomialIdentities {

	/**
	 * Verifies whether the given polynomial is identically zero.
	 * 
	 * @param args[0] the polynomial
	 * @param args[1..] the variables of the polynomial
	 */
	public static void main(String[] args) {
		String expression = args[0];
		String[] variables = new String[args.length - 1];
		for (int i = 1; i < args.length; i++) {
			variables[i - 1] = args[i];
		}
		(new probabilistic.examples.PolynomialIdentities(2)).verify(expression, variables);
	}
}
