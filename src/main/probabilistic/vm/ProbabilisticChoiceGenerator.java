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

package probabilistic.vm;

import java.util.Arrays;
import gov.nasa.jpf.vm.choice.IntIntervalGenerator;

/**
 * A choice generator for the interval <code>[0, p.length - 1]</code> which
 * keeps also track of the probabilities of the choices. Choice <code>i</code>
 * has probability <code>p[i]</code>.
 * 
 * @author Xin Zhang
 * @author Franck van Breugel
 * @author Zainab Fatmi
 */
public class ProbabilisticChoiceGenerator extends IntIntervalGenerator implements Probabilistic {
	private double[] p;
	private static final double EPSILON = 1e-10;

	/**
	 * Initializes a ProbabilisticChoiceGenerator with the given id and
	 * probabilities.
	 * 
	 * @param id id.
	 * @param p  probabilities.
	 * @pre. <code>p[0] + ... + p[p.length - 1] = 1.0</code>.
	 */
	public ProbabilisticChoiceGenerator(String id, double[] p) {
		super(id, 0, p.length - 1);
		/*
		 * The array p is copied so that modifications of the array by the application
		 * do not interfere with JPF.
		 */
		this.p = Arrays.copyOf(p, p.length);
		this.checkSum();
	}

	/**
	 * Returns the probability of the current choice.
	 * 
	 * @return the probability of the current choice.
	 */
	public double getProbability() {
		return this.p[this.next];
	}

	/**
	 * Returns the probabilities of the processed choices.
	 * 
	 * @return an array of the probabilities of the processed choices.
	 */
	public double[] getProcesedProbabilities() {
		return Arrays.copyOf(this.p, this.getProcessedNumberOfChoices());
	}

	/**
	 * Verifies that the sum of the probabilities is equal to one.
	 * 
	 * @throws IllegalArgumentException if <code>p[0] + ... + p[p.length - 1] != 1.0</code>.
	 */
	private void checkSum() {
		// sort first
		double[] probabilities = Arrays.copyOf(this.p, this.p.length);
		Arrays.sort(probabilities);
		// then sum
		double sum = 0;
		for (double probability : probabilities) {
			sum += probability;
		}
		if (Math.abs(sum - 1.0) > EPSILON) {
			throw new IllegalArgumentException("The sum of probabilities should equal to 1.0.");
		}
	}
}
