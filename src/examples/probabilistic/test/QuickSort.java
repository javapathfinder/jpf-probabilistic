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

package probabilistic.test;

import java.util.ArrayList;
import java.util.List;

/**
 * This app sorts the command line arguments.
 * 
 * @author Xin Zhang
 * @author Franck van Breugel
 */
public class QuickSort {
	private QuickSort() {}
	
	/**
	 * Sorts the integers given as command line arguments.
	 * 
	 * @param args the integers to be sorted
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		for (String value : args) {
			list.add(Integer.parseInt(value));
		}
		probabilistic.examples.QuickSort.sort(list);
	}
}
