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
 * This app calls RabinKarpPatternMatching.find.
 * 
 * @author Yash Dhamija
 */
public class RabinKarpPatternMatching {
	private RabinKarpPatternMatching() {}
	
	/**
	 * Finds an occurrence (if any) of the given pattern in the given string.
	 * 
	 * @param args[0] the string
	 * @param args[1] the pattern
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java RabinKarpPatternMatching <string> <pattern>");
			System.exit(0);
		}
		
		probabilistic.examples.RabinKarpPatternMatching.find(args[0], args[1]);
	}
}
