/*
 * Copyright (C) 2020  Maeve Wildes
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

import probabilistic.Choice;

/**
 * Given the universe of integers [0, size) and two disjoint subsets of the university
 * of equal size, a sample from the universe chosen, where each element in the
 * universe is chosen independently with probability 1 / size.  Such a sample is good
 * if it is disjoint from the first subset and it intersects the the second subset.
 * 
 * See D.R. Karger and R. Motwani. Derandomization through approximation: An NC algorithm 
 * for minimum cuts. In Proceedings of the 26th Annual ACM Symposium on Theory of Computing, 
 * pages 497-506, Montreal, Canada, May 1994. ACM.
 * 
 * @author Maeve Wildes
 */
public class SetIsolation {
	private SetIsolation() {}

	/**
	 * Given the universe of integers [0, size) and two disjoint subsets of the university
	 * of equal size, randomly selects a sample from the universe where each element in the
	 * universe is chosen independently with probability 1 / size.  Returns whether the 
	 * sample is good, that is, it is disjoint from the first subset and it intersects the
	 * the second subset.
	 * 
	 * See D.R. Karger and R. Motwani. Derandomization through approximation: An NC algorithm 
	 * for minimum cuts. In Proceedings of the 26th Annual ACM Symposium on Theory of Computing, 
	 * pages 497-506, Montreal, Canada, May 1994. ACM.
	 * 
	 * @param size the size of the universe
	 * @param first a subset of the universe
	 * @param second a subset of the universe
	 * @pre. first and second are disjoint subsets of the universe of the same cardinality
	 * @return true if the randomly selected sample is good, false otherwise
	 */
	public static boolean check(int size, int[] first, int[] second) {
		List<Integer> sample = new ArrayList<Integer>();
		double[] choices = { 1.0 / size, (size - 1.0) / size };
		for (int i = 0; i < size; i++) {
			if (Choice.make(choices) == 0) {
				sample.add(i);
			}
		}
		
		boolean good = true;
		for (int i = 0; i < sample.size(); i++) {
			for (int j = 0; j < first.length; j++) {
				if (sample.get(i) == first[j]) {
					good = false;
				}
			}
		}
		if (good) {
			for (int i = 0; i < sample.size(); i++) {
				for (int j = 0; j < first.length; j++) {
					if (sample.get(i) == second[j]) {
						return true;
					}
				}
			}
		}
		return false;	
	}
}
