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
 * This app inserts into a random treap.
 * 
 * @author Yash Dhamija
 */
public class RandomizedTreap {

	/**
	 * Inserts the command line arguments into a random treap.
	 *  
	 * @param args integers to be inserted into the treap
	 */
	public static void main(String[] args) {
		probabilistic.examples.RandomizedTreap treap = new probabilistic.examples.RandomizedTreap(args.length);
		for (String s : args) {
			treap.insert(Integer.parseInt(s));
		}
	}
}
