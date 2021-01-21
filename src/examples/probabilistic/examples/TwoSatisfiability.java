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

import probabilistic.Coin;

/**
 * Attempts to find a satisfying assignment for a boolean expression.
 * 
 * @author Maeve Wildes
 */
public class TwoSatisfiability {
	
	/**
	 * Attempts to find a satisfying assignment for the given boolean expression.
	 * 
	 * @param input a boolean expression denoted as the following: {{1, -2}, {3, 1}, {-1, -2}} represents
	 * (x1 || !x2) && (x3 || x1) && (!x1 || !x2)
	 * @param numberOfVariables number of variables
	 * @return an array of booleans with values such that the expression evaluates to true (if possible)
	 */
	public static boolean[] find(int[][] input, int numberOfVariables)
	{
		boolean[] variables = new boolean[numberOfVariables + 1];	

		// random assign initial values
		for (int i = 1; i <= numberOfVariables; i++) {
			if (Coin.flip() == 0) {
				variables[i] = true;
			}
			else {
				variables[i] = false;
			}
		}

		boolean[] expression = new boolean[input.length];
		buildExpression(expression, input, variables);

		boolean complete = true;
		for (int i = 0; i < expression.length; i++) {
			complete = complete && expression[i];
		}

		int count = 0;
		while (!complete && count < 2 * numberOfVariables * numberOfVariables) {
			for (int i = 0; i < expression.length; i++) {
				// if any of the clauses are false, pick one of the two literals at random and flip its value
				if (!expression[i]) {	
					if (Coin.flip() == 0) {
						variables[Math.abs(input[i][0])]= !variables[Math.abs(input[i][0])];		
					} else {
						variables[Math.abs(input[i][1])]= !variables[Math.abs(input[i][1])];	
					}				
				}	
			}

			buildExpression(expression, input, variables);

			complete = true;
			for (int i = 0; i < expression.length; i++) {
				complete = complete && expression[i];
			}
			count ++;
		}

		if (complete) {
			//for (int i = 1; i < variables.length; i++) {
			//	System.out.print(variables[i]+ " ");
			//}
			//System.out.println();
			return variables;
		} else {
			//System.out.println("no solution.");
			return variables;
		}
	}

	/**
	 * 
	 * @param expression
	 * @param input
	 * @param variables
	 */
	private static void buildExpression(boolean[] expression, int[][] input, boolean[] variables) {
		for (int i = 0; i < expression.length; i++) {
			if (input[i][0] < 0) {
				if (input[i][1] >= 0) {
					expression[i] = !variables[Math.abs(input[i][0])] || variables[input[i][1]];
				} else {
					expression[i] = !variables[Math.abs(input[i][0])] || !variables[Math.abs(input[i][0])];
				}
			} else if (input[i][1] < 0) {
				expression[i] = variables[input[i][0]] || !variables[Math.abs(input[i][1])];
			} else {
				expression[i] = variables[input[i][0]] || variables[input[i][1]];
			}
		}
	}
}
