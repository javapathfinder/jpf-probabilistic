/*
 * Copyright (C) 2020  Yash Dhamija and Franck van Breugel
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
 * The Fisher-Yates shuffle algorithm: given a list generates a 
 * random permutation of the elements of the list.
 * 
 * @author Yash Dhamija
 * @author Franck van Breugel
 */
public class FisherYatesShuffle<T> {
	private static int permutation; // ghost field
	private FisherYatesShuffle() {}
	
	/**
	 * Shuffles the given list.
	 * 
	 * @param <T> type of the element of the list
	 * @param list a list
	 */
	public static <T> void shuffle(List<T> list) {
		List<List<T>> permutations = getPermutations(new ArrayList<T>(list)); // ghost variable
		
		for (int i = list.size() - 1; i > 0; i--) {
			int j = UniformChoice.make(i + 1);
			T temp = list.get(j);
			list.set(j, list.get(i));
			list.set(i, temp);
		}	
		
		permutation = permutations.indexOf(list); // ghost call
	}
	
	/**
	 * Returns the permutations of the given list.
	 * 
	 * @param list the list to be permuted
	 */
	private static <T> List<List<T>> getPermutations(List<T> list) {
		List<List<T>> permutations = new ArrayList<List<T>>();
		if (list.size() <= 1) {
			permutations.add(list);
		} else {
			T head = list.remove(0);
			for (List<T> tail : getPermutations(list)) {
				for (int i = 0; i <= tail.size(); i++) {
					List<T> copy = new ArrayList<T>(tail);
					copy.add(i, head);
					if (!permutations.contains(copy)) {
						permutations.add(copy);
					}
				}
			}
		}
		return permutations;
	}
}
