/*
 * Copyright (C) 2013  Franck van Breugel and Steven Xu
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
 * A finite distribution is a finite collection of elements where each 
 * element has a weight which is a positive real.  A distribution supports 
 * three operations.  One can add an element with a given weight to the 
 * distribution.  One can also remove a random element from the 
 * distribution.  The probability that a particular element is removed 
 * is proportional its weight.  The probability that a particular element 
 * is returned is proportional its weight.  One can test if the collection 
 * is empty.
 *
 * @author Franck van Breugel
 * @author Steven Xu
 */
public interface Distribution<T> {
	/**
	 * Tests whether this distribution is empty.
	 *
	 * @return true if this distribution is empty, false otherwise.
	 */
	public boolean isEmpty();

	/**
	 * Adds the given element with the given weight to this distribution.
	 *
	 * @param element the element to be added to this distribution.
	 * @pre. element != null
	 * @param weight the weight of the element.
	 * @pre. weight &gt; 0
	 */
	public void add(T element, double weight);

	/**
	 * Removes a random element from this distribution.  The probability that a 
	 * particular element is removed is proportional its weight.
	 *
	 * @pre. this distribution is nonempty.
	 * @return the removed element and its weight.
	 */
	public Weighted<T> remove();

	/**
	 * Removes an element with maximal weight from this distribution.
	 *
	 * @pre. this distribution is nonempty.
	 * @return the removed element and its weight.
	 */
	public Weighted<T> removeMax();
}
