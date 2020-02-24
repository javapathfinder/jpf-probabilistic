/* Copyright (C) 2013  Franck van Breugel and Steven Xu
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

/**
 * This class represents weighted elements.
 *
 * @author Franck van Breugel
 * @author Steven Xu
 */
public class Weighted<T> implements Comparable<Weighted<T>> {
	private T element;
	private double weight;

	/**
	 * Initializes this weighted element with the given element and weight.
	 *
	 * @param element the element of this weighted element.
	 * @pre. element != null
	 * @param weight the weight of this weighted element.
	 * @pre. weight > 0
	 */
	Weighted(T element, double weight) {
		super();
		this.element = element;
		this.weight = weight;
	}

	/**
	 * Returns the element.
	 *
	 * @return the element.
	 */
	public T getElement() {
		return this.element;
	}

	/**
	 * Returns the weight.
	 *
	 * @return the weight.
	 */
	public double getWeight() {
		return this.weight;
	}

	/**
	 * Compares this weighted element to the other weighted element.
	 *
	 * @param other another weighted element.
	 * @return -1 if the weight of this weighted element is greater than that
	 * of the other weighted element, 1 if the weight of this weighted element 
	 * is smaller than that of the other weighted element, 0 otherwise.
	 */
	public int compareTo(Weighted<T> other) {
		if (this.weight < other.weight) {
			return -1;
		} else if (this.weight > other.weight) {
			return 1;
		} else {
			return 0;
		}
	}

}
