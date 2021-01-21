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
 * Solves the n-queens problem.
 * 
 * @author Maeve Wildes
 */
public class Queens {
	private Queens() {}

	/**
	 * Checks if a queen can be placed on the given board for 
	 * all rows whose index is greater than or equal to the 
	 * given row index.  For each row, randomly a safe column
	 * is chosen.
	 * 
	 * @param row the index of a row
	 * @param board the board (whether there is a queen)
	 * @return true if a queen can be placed on the given board for 
	 * all rows whose index is greater than or equal to the 
	 * given row index, false otherwise
	 */	
	public static boolean place(int row, boolean[][] board) {
		int size = board.length;
		if (row == size) {
			return true;
		} else {
			List<Integer> safeSpots = new ArrayList<Integer>();
			for (int column = 0; column < size; column++) {
				if (isSafe(row, column, board)) {
					safeSpots.add(column);
				}
			}

			if (safeSpots.size() == 0) {
				return false;
			} else {		
				int chosenSpot = UniformChoice.make(safeSpots.size());
				int j = safeSpots.get(chosenSpot);
				board[row][j] = true;
				return place(row + 1, board);
			}
		}
	}
	
	/**
	 * Check if it is safe to place a queen in the given row and column 
	 * of the given board, by checking all rows whose index is smaller
	 * than or equal to the given row index.
	 * 
	 * @param row the row index
	 * @param column the column index
	 * @param board the board (whether there is a queen)
	 * @return true if it is safe to place a queen in the given row and column 
	 * of the given board, false otherwise
	 */
	private static boolean isSafe(int row, int column, boolean[][] board) {
		int size = board.length;
		if (row == 0) {
			return true;
		} else {
			// check if there is already a queen in that column
			for (int r = 0; r < row; r++) {
				if (board[r][column]) {
					return false;
				}
			}
			
			// check if there is already a queen in the left diagonal
			int j = 1;
			while(j <= row && j <= column) {
				if (board[row - j][column - j]) {
					return false;
				}
				j++;
			}
			
			// check if there is already a queen in the right diagonal
			j = 1;
			while (j <= row && j < size - column) {
				if (board[row - j][column + j]) {
					return false;
				}
				j++;
			}
			
			return true;
		}
	}
}
