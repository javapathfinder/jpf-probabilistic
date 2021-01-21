/*
 * Copyright (C) 2011  Xin Zhang, Zainab Fatmi and Franck van Breugel
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import gov.nasa.jpf.vm.ChoiceGeneratorBase;

/**
 * A choice generator for the interval <code>[0, probability.length - 1]</code> which
 * keeps also track of the probabilities of the choices. Choice <code>i</code>
 * has probability <code>probability[i]</code>.
 * 
 * @author Xin Zhang
 * @author Zainab Fatmi
 * @author Franck van Breugel
 */
public class ProbabilisticChoiceGenerator extends ChoiceGeneratorBase<Integer> implements Probabilistic {

	private double[] probability;
	private int next;
	private int processedNumberOfChoices;
	private int totalNumberOfChoices;

	/**
	 * Initializes this choice generator with the given id and probabilities.
	 * 
	 * @param id the id of this choice generator
	 * @param probability the probabilities of the choices
	 * @throws IllegalArgumentException if <code>probability[0] + ... + probability[probability.length - 1] != 1.0</code>
	 */
	public ProbabilisticChoiceGenerator(String id, double[] probability) throws IllegalArgumentException {
		super(id);
		this.probability = probability;
		this.checkSum();
		this.next = - 1;
		this.processedNumberOfChoices = 0;
		this.totalNumberOfChoices = 0;
		for (double p : this.probability) {
			if (p > 0) {
				this.totalNumberOfChoices++;
			}
		}
	}

	/**
	 * Initializes this choice generator with the given id and total number of choices.
	 * Each choice has the same probability.
	 * 
	 * @param id the id of this choice generator
	 * @param totalNumberOfChoices total number of choices
	 */
	public ProbabilisticChoiceGenerator(String id, int totalNumberOfChoices) {
	    super(id);
	    this.probability = null; // uniform choice
	    this.next = - 1;
	    this.processedNumberOfChoices = 0;
	    this.totalNumberOfChoices = totalNumberOfChoices;
	    
	}

	/**
	 * Advances this choice generator to the next choice.
	 */
	@Override
	public void advance() {
	    if (this.probability == null) { // uniform choice
		this.next++;
	    } else {
		do {
		    this.next++;
		} while (this.probability[this.next] == 0);
	    }
	    this.processedNumberOfChoices++;
	}

	/**
	 * Returns the type of the choices of this choice generator: integers.
	 * 
	 * @return the type of the choices of this choice generator
	 */
	@Override
	public Class<Integer> getChoiceType() {
		return Integer.class;
	}

	/**
	 * Returns the next choice of this choice generator.
	 * 
	 * @return the next choice of this choice generator
	 */
	@Override
	public Integer getNextChoice() {
		return this.next;
	}

	/**
	 * Returns the number of choices that have been processed by this choice generator.
	 * 
	 * @return the number of choices that have been processed by this choice generator
	 */
	@Override
	public int getProcessedNumberOfChoices() {
		return this.processedNumberOfChoices;
	}

	/**
	 * Returns the total number of choices of this choice generator.
	 * 
	 * @return the total number of choices of this choice generator
	 */
	@Override
	public int getTotalNumberOfChoices() {
		return this.totalNumberOfChoices;
	}

	/**
	 * Checks whether this choice generator has more choices.
	 * 
	 * @return true if this choice generator has more choices, false otherwise
	 */
	@Override
	public boolean hasMoreChoices() {
		if (super.isDone) {
			return false;
		} else {
			return this.processedNumberOfChoices < this.totalNumberOfChoices;
		}
	}

	/**
	 * Resets this choice generator to the initial state.
	 */
	@Override
	public void reset() {
		super.isDone = false;
		this.next = - 1;
		this.processedNumberOfChoices = 0;
	}

	/**
	 * Returns the probability of the current choice.
	 * 
	 * @return the probability of the current choice
	 */
	public double getProbability() {
	    if (this.probability == null) { // uniform choice
		return 1.0 / this.totalNumberOfChoices;
	    } else {
		return this.probability[this.next];
	    }
	}

	/**
	 * Verifies that the sum of the probabilities is equal to one.
	 * 
	 * @throws IllegalArgumentException if <code>probability[0] + ... + probability[probability.length - 1] != 1.0</code>
	 */
	private void checkSum() throws IllegalArgumentException {
		final double EPSILON = 1e-10;
		
		// sort first
		double[] p = Arrays.copyOf(this.probability, this.probability.length);
		Arrays.sort(p);
		// then sum
		double sum = 0;
		for (double probability : p) {
			sum += probability;
		}
		if (Math.abs(sum - 1.0) > EPSILON) {
			throw new IllegalArgumentException("The sum of probabilities should equal to 1.0 but is " + sum);
		}
	}
}
