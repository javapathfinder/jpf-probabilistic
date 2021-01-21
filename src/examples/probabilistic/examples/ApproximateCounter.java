/*
 * Copyright (C) 2020  Zainab Fatmi
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
 * This class implements an approximate counter as described in
 * Robert Morris. Counting large numbers of events in small registers. 
 * Communications of the ACM, 21(10): 840–842, October 1978.
 * 
 * Such an approximate counter can count a large number of
 * events using a small amount of memory by only storing an 
 * exponent. The maximum value that the counter can represent is 
 * a * ((base)^(Integer.MAX_VALUE) - 1), for constants a and base,
 * with an expected error of σ^2 = n * (n - 1) / 2 * a.
 * 
 * @author Zainab Fatmi
 */
public class ApproximateCounter {

	private int exponent;
	private double base;
	private double a;

	/**
	 * Initializes this approximate counter with a base of (1 + 1 / a).
	 * 
	 * @param a value to determine the base of the counter
	 * @pre. a > 0
	 */
	public ApproximateCounter(double a) {
		this.a = a;
		this.exponent = 0;
		this.base = 1 + (1 / a);
	}
	
	 /**
	 * Initializes this approximate counter with a base of (1 + 1 / a) 
	 * and the given exponent.
	 * 
	 * @param a  value to determine the base of the counter
	 * @pre. a > 0
	 * @param exponent the value of the starting exponent
	 * @pre. exponent >= 0
	 */
	public ApproximateCounter(double a, int exponent) {
		this.a = a;
		this.exponent = exponent;
		this.base = 1 + (1 / a);
	}

	/**
	 * Increments this approximate counter by one.
	 */
	public void increment() {
		// probabilistically determine whether to increment the exponent
		double probability = Math.pow(1 / base, exponent);
		double[] choice = {probability, 1 - probability};
		if (Choice.make(choice) == 0) {
			this.exponent++;
		}
	}

	/**
	 * Resets the counter to zero.
	 */
	public void reset() {
		this.exponent = 0;
	}
	
	/**
	 * Returns the value of this approximate counter.
	 * 
	 * @return the value of this approximate counter
	 */
	public int getValue() {
		return (int) (this.a * (Math.pow(this.base, this.exponent) - 1));
	}
	
	/**
	 * Returns the exponent of this approximate counter.
	 * 
	 * @return the exponent of this approximate counter
	 */
	public int getExponent() {
		return this.exponent;
	}
}

