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

import probabilistic.Choice;

/**
 * This class contains an implementation of a die by means of a coin. The 
 * implementation is based on the paper
 * <P>
 * Donald E. Knuth and Andrew C. Yao. The Complexity of Nonuniform Random Number
 * Generation. In, J.F. Traub, editor, Proceedings of a Symposium on New
 * Directions and Recent Results in Algorithms and Complexity, pages 375-428,
 * Pittsburgh, PA, USA, April 1976. Academic Press.
 * </P>
 * 
 * @author Xin Zhang
 * @author Franck van Breugel
 */
public class KnuthYaoDie {
	/**
	 * Initializes this die.
	 */
	public KnuthYaoDie() {}

	/**
	 * Returns the result of the roll of this die.
	 * 
	 * @return the result of the roll of this die.
	 */
	public int roll() {
		int state = 0;
		double[] choices = { 0.5, 0.5 };
		while (true) {
			switch (state) {
			case 0:
				switch (Choice.make(choices)) {
				case 0:
					state = 1;
					break;
				case 1:
					state = 2;
					break;
				}
				break;
			case 1:
				switch (Choice.make(choices)) {
				case 0:
					state = 3;
					break;
				case 1:
					state = 4;
					break;
				}
				break;
			case 2:
				switch (Choice.make(choices)) {
				case 0:
					state = 5;
					break;
				case 1:
					state = 6;
					break;
				}
				break;
			case 3:
				switch (Choice.make(choices)) {
				case 0:
					state = 1;
					break;
				case 1:
					return 1;
				}
				break;
			case 4:
				switch (Choice.make(choices)) {
				case 0:
					return 2;
				case 1:
					return 3;
				}
				break;
			case 5:
				switch (Choice.make(choices)) {
				case 0:
					return 4;
				case 1:
					return 5;
				}
				break;
			case 6:
				switch (Choice.make(choices)) {
				case 0:
					return 6;
				case 1:
					state = 2;
					break;
				}
				break;
			}
		}
	}
}
