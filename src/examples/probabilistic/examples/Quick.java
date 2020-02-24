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

package probabilistic.examples;

import java.util.Vector;
import java.util.List;
import probabilistic.Choice;

/**
 * This class Quick contains the method sort which is a randomized version of
 * quicksort.
 * 
 * @author Xin Zhang
 * @author Franck van Breugel
 */
public class Quick {
	private Quick() {}

	/**
	 * Sorts the given list.
	 * 
	 * @param <T> type of elements of the list.
	 * @param list the list to be sorted.
	 * @pre. list != null and list contains no duplicates.
	 */
	public static <T extends Comparable<? super T>> void sort(List<T> list) {
		if (list.size() > 1) {
			double[] p = new double[list.size()];
			for (int i = 0; i < list.size(); i++) {
				p[i] = 1.0 / list.size();
			}
			int pivot = Choice.make(p);
			T middle = list.get(pivot);
			List<T> smaller = new Vector<T>(list.size());
			List<T> larger = new Vector<T>(list.size());
			for (T element : list) {
				if (element.compareTo(middle) < 0) {
					smaller.add(element);
				} else if (element.compareTo(middle) > 0) {
					larger.add(element);
				}
			}

			Quick.sort(smaller);
			Quick.sort(larger);

			list.clear();
			list.addAll(smaller);
			list.add(middle);
			list.addAll(larger);
		}
	}
}
