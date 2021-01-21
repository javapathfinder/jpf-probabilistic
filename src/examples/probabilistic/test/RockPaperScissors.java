/*
 * Copyright (C) 2020  Franck van Breugel
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

/**
 * This app calls RockPaperScissors.play.
 * 
 * @author Franck van Breugel
 */
public class RockPaperScissors {
	private RockPaperScissors() {}

	/**
	 * Plays the scissor game.
	 */
	public static void main(String[] args) {
		probabilistic.examples.RockPaperScissors.play();
	}
}
