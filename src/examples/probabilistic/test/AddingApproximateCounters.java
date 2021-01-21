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
 * This class calls the AddingApproximateCounters class.
 * 
 *  @author Yash Dhamija
 */
public class AddingApproximateCounters {

	/**
	 * Adds two approximate counters.
	 * 
	 * @param args[0] the number to initialize both ApproximateCounter with
	 * @param args[1] the number of times to increment first counter
	 * @param args[2] the number of times to increment second counter
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Usage: java AddingApproximateCounters <value to initialize counters> <no. of increments for first counter> <no. of increments for second counter>");
			System.exit(0);
		}
		
		probabilistic.examples.ApproximateCounter first = new probabilistic.examples.ApproximateCounter(Integer.parseInt(args[0]));
		for (int i = 0; i < Integer.parseInt(args[1]); i++)
			first.increment();
		
		probabilistic.examples.ApproximateCounter second = new probabilistic.examples.ApproximateCounter(Integer.parseInt(args[0]));
		for (int i = 0; i < Integer.parseInt(args[2]); i++)
			second.increment();
				
		probabilistic.examples.AddingApproximateCounters.add(first, second);
	}
}
