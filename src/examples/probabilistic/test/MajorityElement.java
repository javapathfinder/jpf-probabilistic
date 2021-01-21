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
 * Finds the majority element in a list of integers and its multiplicity.
 * 
 * @author Yash Dhamija
 */
public class MajorityElement {
	private MajorityElement() {}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: java MajorityElement <integer elements of the list>");
		} else {
			List<Integer> list = new ArrayList<Integer>();
			for(String s : args) {
				list.add(Integer.parseInt(s));
			}

			probabilistic.examples.MajorityElement<Integer> sequence = new probabilistic.examples.MajorityElement<Integer>();
			sequence.majority(list);
			sequence.getMultiplicity();
		}
	}
}
