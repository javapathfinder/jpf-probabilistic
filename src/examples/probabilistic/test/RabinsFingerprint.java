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
 * This class verifies the equality of strings using finger-prints.
 * 
 * @author Yash Dhamija 
 */
public class RabinsFingerprint {
	private RabinsFingerprint() {}

	/**
	 * Verifies the equality of the given strings.
	 * 
	 * @param args[0] 1 / bound on error
	 * @param args[1] a string
	 * @param args[2] a string
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Usage: java RabinsFingerprint <integer bound on error probability> <string> <string>");
			System.exit(0);
		}
		
		probabilistic.examples.RabinsFingerprint f = new probabilistic.examples.RabinsFingerprint();
		f.verifyEquality(args[1], args[2], Integer.parseInt(args[0]));
	}
}
