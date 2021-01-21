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

import net.objecthunter.exp4j.ExpressionBuilder;

import probabilistic.UniformChoice;

/**
 * Integrates a function from the unit interval to the unit interval.
 * The class relies on exp4j.  The latter is available licensed under 
 * the Apache License, Version 2.0.
 * 
 * @author Maeve Wildes
 */
public class ApproximateIntegral {
	private ApproximateIntegral() {}
	
	/**
	 * Given a function f from the unit interval to the unit interval, returns its integral.
	 *  
	 * @param expression a string representation of the function using the exp4j format
	 * @param repeats the number of repeats
	 * @param granularity the granularity of the samples in the unit interval
	 */
	public static double integrate (String expression, int repeats, int granularity) {
		int count = 0;
		for (int r = 0; r < repeats; r++) {
			double x = UniformChoice.make(granularity + 1) / (double) granularity;
			double y = UniformChoice.make(granularity + 1) / (double) granularity;
			
			double result = (new ExpressionBuilder(expression).variables("x").build().setVariable("x", x)).evaluate();
			
			if (y <= result) {
				count++;
			}
		}
		return count / (double) repeats;
	}
}
