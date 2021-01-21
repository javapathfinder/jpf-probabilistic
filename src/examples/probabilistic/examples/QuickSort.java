/*
 * Copyright (C) 2011  Xin Zhang, Franck van Breugel, and Xiang Chen
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

import java.util.ArrayList;
import java.util.List;
import probabilistic.UniformChoice;

/**
 * This class QuickSort contains the method sort which is a randomized version of
 * quicksort.
 * 
 * @author Xin Zhang
 * @author Franck van Breugel
 * @author Xiang Chen
 */
public class QuickSort {
	private QuickSort() {}

	/**
	 * Ghost field that captures whether smaller or larger is empty.
	 */
	@SuppressWarnings("unused")
	private static boolean isEmpty = true;
	
	/**
	 * Sorts the given list.
	 * 
	 * @param <T> type of elements of the list.
	 * @param list the list to be sorted.
	 * @pre. list != null and list contains no duplicates.
	 */
	public static <T extends Comparable<? super T>> void sort(List<T> list) {
		if (list.size() > 1) {
			int pivot = UniformChoice.make(list.size());
			T middle = list.get(pivot);
			List<T> smaller = new ArrayList<T>(list.size());
			List<T> larger = new ArrayList<T>(list.size());
			for (T element : list) {
				if (element.compareTo(middle) < 0) {
					smaller.add(element);
				} else if (element.compareTo(middle) > 0) {
					larger.add(element);
				}
			}

			isEmpty = smaller.isEmpty() || larger.isEmpty();

			QuickSort.sort(smaller);
			QuickSort.sort(larger);

			list.clear();
			list.addAll(smaller);
			list.add(middle);
			list.addAll(larger);
		}
	}
}
