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

package probabilistic.test;

/**
 * This app finds two square roots of an integer a mod p, where p is a prime number.
 * 
 * @author Yash Dhamija
 */
public class QuadraticResidue {
	private QuadraticResidue() {}

	/**
	 * Given a prime p and a quadratic residue a in Z*(p), finds the square roots of a modulo p.
	 * 
	 * @param args[0] a
	 * @param args[1] p
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java QuadraticResidue <int a to square root> <int p to define field we are in>");
			System.exit(0);
		}
		probabilistic.examples.QuadraticResidue.getRoots(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
	}
}
