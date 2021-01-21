/*
 * Copyright (C) 2010  Xin Zhang
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

import probabilistic.UniformChoice;

/**
 * Simulates the scissor, paper and rock game.
 * 
 * @author Xin Zhang
 */
public class RockPaperScissors {
	private RockPaperScissors() {}

	/**
	 * Returns the result of the play.
	 * 
	 * @param first choice of first player
	 * @param second choice of second player
	 * @return 1 if first player wins, -1 if second player wins, and 0 if it is a draw
	 */
	private static int getResult(int first, int second){
		if (first == second) {
			return 0;
		} else if (first == 0 && second == 1) {
			return 1;
		} else if (first == 1 && second == 2) {
			return 1;
		} else if (first == 2 && second == 0) {
			return 1;
		} else {
			return -1;
		}
	}

	/**
	 * Plays a game.
	 * 
	 * @return true if the player wins, false if the opponent wins.
	 */
	public static boolean play(){
		int result;
		do {
			int player = UniformChoice.make(3);
			int opponent = UniformChoice.make(3);
			result = getResult(player, opponent);
		} while (result != 0);
		return result == 1;
	}
}
