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

import java.util.ArrayList;
import java.util.List;

/**
 * Shuffles the list consisting of the command line arguments.
 * 
 * @author Yash Dhamija
 */
public class FisherYatesShuffle {
	private FisherYatesShuffle() {}

	/**
	 * Shuffles the list consisting of the command line arguments.
	 * 
	 * @param args the elements of the list
	 */
	public static void main(String args[]) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < args.length; i++) {
			list.add(Integer.parseInt(args[i]));
		}
		probabilistic.examples.FisherYatesShuffle.shuffle(list);
	}
}
