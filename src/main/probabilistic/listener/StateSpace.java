/*
 * Copyright (C) 2020  Xin Zhang, Franck van Breugel, and Syyeda Zainab Fatmi
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

package probabilistic.listener;

import java.util.Arrays;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.ListenerAdapter;
import gov.nasa.jpf.search.Search;
import gov.nasa.jpf.search.SearchListener;
import gov.nasa.jpf.vm.ChoiceGenerator;
import probabilistic.vm.Probabilistic;
import probabilistic.vm.ProbabilisticChoiceGenerator;

/**
 * This abstract listener handles the writing of the state space to a file,
 * delegating the formatting to its subclasses.
 * 
 * @author Xin Zhang
 * @author Franck van Breugel
 * @author Syyeda Zainab Fatmi
 */
public abstract class StateSpace extends ListenerAdapter implements SearchListener {
	private int states; // number of states
	private int source; // id of the source of the next transition
	public final int precision; // precision of the probabilities

	/**
	 * Initializes the listener.
	 *
	 * @param configuration JPF's configuration.
	 */
	public StateSpace(Config configuration) {
		this.source = -1;
		this.states = 0;
		this.precision = configuration.getInt("probabilistic.statespace.precision", 2);
	}

	/**
	 * Executed whenever the search advances to the next state.
	 * 
	 * @param search object that provides information about the search.
	 */
	@Override
	public void stateAdvanced(Search search) {
		int target = search.getStateId();
		this.states = Math.max(this.states, target);
		ChoiceGenerator<?> cg = search.getVM().getChoiceGenerator();
		if (cg instanceof Probabilistic) {
			double probability = ((Probabilistic) cg).getProbability();

			// make sure the sum of the probabilities = 1.0 after applying the precision
			if (cg instanceof ProbabilisticChoiceGenerator && !cg.hasMoreChoices()) {
				double[] probabilities = ((ProbabilisticChoiceGenerator) cg).getProcesedProbabilities();
				probability = 1.0 - getSum(probabilities, probabilities.length - 1); // 1.0 - previous choices
			}

			this.addTransition(this.source, target, probability);
		} else {
			this.addTransition(this.source, target, 1.0);
		}
		if (search.isNewState() && search.isEndState()) {
			this.addTransition(target, target, 1.0);
		}
		this.source = target;
	}

	/**
	 * Executed whenever the search backtracks.
	 * 
	 * @param search object that provides information about the search.
	 */
	@Override
	public void stateBacktracked(Search search) {
		this.source = search.getStateId();
	}

	/**
	 * Executed whenever the search moves to a previously visited state.
	 * 
	 * @param search object that provides information about the search.
	 */
	@Override
	public void stateRestored(Search search) {
		this.source = search.getStateId();
	}

	/**
	 * Dump the state space if a constraint is hit.
	 * 
	 * @param search JPF's search.
	 */
	@Override
	public void searchConstraintHit(Search search) {
		this.writeStateSpace(search, search.getVM().getSUTName() + "_" + search.getSearchConstraint());
	}

	/**
	 * When JPF finishes, write the state space to a file.
	 * 
	 * @param search JPF's search.
	 */
	@Override
	public void searchFinished(Search search) {
		this.writeStateSpace(search, search.getVM().getSUTName());
	}

	/**
	 * Returns the amount of states in the system.
	 * 
	 * @return the number of states.
	 */
	public int getStates() {
		return this.states + 2;
	}

	/**
	 * Adds a transition from the source to the target with the associated
	 * probability.
	 * 
	 * @param source      the source state ID.
	 * @param target      the target state ID.
	 * @param probability the probability of going from the source to the target.
	 */
	public abstract void addTransition(int source, int target, double probability);

	/**
	 * Writes the current state space to a file.
	 * 
	 * @param search JPF's search.
	 * @param name   the name of the system under test, appended with the search
	 *               constraint if one was hit.
	 */
	public abstract void writeStateSpace(Search search, String name);

	/**
	 * Sums the array of probabilities, with the specified precision, until the
	 * specified index (exclusive).
	 * 
	 * @param probabilities the array of probabilities.
	 * @param endIndex      the index at which to stop (exclusive).
	 * @return the sum of the probabilities.
	 */
	protected double getSum(double[] probabilities, int endIndex) {
		Arrays.sort(probabilities);
		double sum = 0;
		for (int i = 0; i < endIndex; i++) {
			sum += Double.parseDouble(String.format("%." + this.precision + "f%n", probabilities[i]));
		}
		return sum;
	}

	/**
	 * Accepts a visitor.
	 * 
	 * @param visitor the Visitor to accept.
	 */
	public abstract void accept(Visitor visitor);
}
