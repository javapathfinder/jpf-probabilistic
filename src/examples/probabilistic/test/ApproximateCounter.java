/*
 * Copyright (C) 2020  Yash Dhamija
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
 * This app increments an approximate counter.
 * 
 *  @author Yash Dhamija
 */
public class ApproximateCounter {
	private ApproximateCounter() {}
	
	/**
	 * Ghost field that captures whether the counter has an incorrect value.
	 */
	@SuppressWarnings("unused")
	private static boolean incorrect = false;

	/**
	 * @param args[0] the number to initialize the approximate counter
	 * @param args[1] the number of times the increment method is called
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java ApproximateCounter <integer to initialize counter> <number of increments>");
			System.exit(0);
		}
		
		probabilistic.examples.ApproximateCounter counter = new probabilistic.examples.ApproximateCounter(Integer.parseInt(args[0]));
		int number = Integer.parseInt(args[1]);
		for (int i = 0; i < number; i++) {
			counter.increment();
		}
		
		incorrect = counter.getValue() != number;
	}
}