/*
 * Copyright (C) 2013  Xin Zhang and Franck van Breugel
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

package probabilistic.search;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A distribution is implemented by means of a linked list.
 * A list of the tree contains an element and its weight.
 * The list is sorted by weights to minimize rounding errors.
 *
 * @author Xin Zhang
 * @author Franck van Breugel
 */
public class List<T> implements Distribution<T> {
	private LinkedList<Weighted<T>> list;

	/**
	 * Initializes the list.
	 */
	public List() {
		this.list = new LinkedList<Weighted<T>>();
	}

	/**
	 * Tests whether this list is empty.
	 *
	 * @return true if this list is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	/**
	 * Adds the given element with the given weight to this list.
	 *
	 * @param element the element to be added to this list.
	 * @pre. element != null
	 * @param weight the weight of the element.
	 * @pre. weight &gt; 0
	 */
	public void add(T element, double weight) {
		this.list.add(new Weighted<T>(element, weight));
	}

	/**
	 * Removes a random element from this list.  The probability that a 
	 * particular element is removed is proportional to its weight.
	 *
	 * @pre. this list is nonempty.
	 * @return the removed element and its weight.
	 */
	public Weighted<T> remove() {
		Collections.sort(this.list);
		double sum = 0;
		for (Weighted<T> element : this.list) {
			sum += element.getWeight();
		}
		double choice = sum * Math.random();
		Iterator<Weighted<T>> iterator = this.list.iterator();
		Weighted<T> element = iterator.next();
		sum = element.getWeight();
		while (iterator.hasNext() && sum < choice) {
			element = iterator.next();
			sum += element.getWeight();
		}
		this.list.remove(element);
		return element;
	}
	
	/**
	 * Removes an element with maximal weight from this list.
	 *
	 * @pre. this list is nonempty.
	 * @return the removed element and its weight.
	 */
	public Weighted<T> removeMax() {
		Collections.sort(this.list);
		return this.list.removeLast();
	}
}
