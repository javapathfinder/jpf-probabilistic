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

import probabilistic.UniformChoice;
 
/**
 * This is an implementation of Polya's Urn Scheme, an example of a martingale. 
 * n selections are performed, at each step the chosen ball is replaced by c balls 
 * of the same colour
 * 
 * @author Maeve Wildes
 */
public class PolyasUrn {
	
	/**
	 * 
	 * @param black initial black balls
	 * @param white initial white balls
	 * @param c number of balls added
	 * @param n number of selections
	 */
	public static double main(int black, int white, int c, int n) {
		// 1 represents black ball, 0 represents white ball
		List<Integer> urn = new ArrayList<Integer>();
		// initialize urn with black balls and white balls
		for (int i = 0; i < black; i++) {
			urn.add(1);
		}
		for (int i = 0; i < white; i++) {
			urn.add(0);
		}
		
		for (int i = 0; i < n; i++) {
			//select a ball, add c balls of that colour
			select(urn, c);
		}
		
		return fraction(urn);
	}
	
	private static void select(List<Integer> urn, int c) {
		int ball = urn.get(UniformChoice.make(urn.size()));
		if (ball == 1) {
			for (int i = 0; i < c; i++) {
				urn.add(1);
			}
		}
		else {
			for (int i = 0; i < c; i++) {
				urn.add(0);
			}
		}
	}
	
	private static double fraction(List<Integer> urn) {
		int numBlack = 0;
		for (int i = 0; i < urn.size(); i++) {
			if (urn.get(i) == 1) {
				numBlack++;
			}
		}
		return numBlack / (double) urn.size();
	}
}
