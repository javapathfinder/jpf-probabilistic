/*
 * Copyright (C) 2013  Xin Zhang and Franck van Breugel
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

package probabilistic.search;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.vm.ChoiceGenerator;
import gov.nasa.jpf.vm.VM;
import gov.nasa.jpf.vm.RestorableVMState;
import gov.nasa.jpf.search.Search;

import java.util.PriorityQueue;

import probabilistic.vm.Probabilistic;

/**
 * This search strategy selects the next state based on the probability
 * of the path along the state was reached during the search.
 *
 * @author Xin Zhang
 * @author Franck van Breugel
 */
public class PFSearch extends Search {

	/**
	 * Initializes this search.
	 *
	 * @param config JPF's configuration.
	 * @param vm JPF's VM.
	 */
	public PFSearch(Config config, VM vm) {
		super(config, vm);
	}

	/**
	 * A pair consisting of a restorable state and a probability.
	 */
	private static class PVMState implements Comparable<PVMState> {
		private RestorableVMState state;
		private double probability;

		/**
		 * Initializes this pair with the given state and probability.
		 *
		 * @param state the state of this pair.
		 * @param probability the probability of this pair.
		 */
		private PVMState(RestorableVMState state, double probability) {
			this.state = state;
			this.probability = probability;
		}

		/**
		 * Compares this pair to the other pair.
		 *
		 * @param other another pair.
		 * @return -1 if the probability of this pair is greater than that
		 * of the other pair, 1 if the probability of this pair is smaller
		 *  than that of the other pair, 0 otherwise.
		 */
		public int compareTo(PVMState other) {
			if (this.probability < other.probability) {
				return 1;
			} else if (this.probability > other.probability) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	/**
	 * This search strategy does not support backtracking.
	 * 
	 * @return false.
	 */
	public boolean supportBacktrack() {
		return false;
	}

	/**
	 * The search of the probability-first search strategy.
	 */
	public void search() {
		notifySearchStarted();
		final int MAX_DEPTH = getDepthLimit();
		PriorityQueue<PVMState> queue = new PriorityQueue<PVMState>();
		setStateDepth(vm.getStateId() + 1, 0);
		queue.add(new PVMState(vm.getRestorableState(), 1.0));
		notifyStateStored();
		while (!queue.isEmpty() && !done) {
			PVMState head = queue.poll();
			vm.restoreState(head.state);
			notifyStateRestored();
			int depth = getStateDepth(vm.getStateId() + 1);
			if (depth >= MAX_DEPTH) {
				notifySearchConstraintHit("depth limit reached: " + MAX_DEPTH);
			} else {
				while (forward()) {
					notifyStateAdvanced();
					if (currentError != null) {
						notifyPropertyViolated();
						if (hasPropertyTermination()) {
							break;
						}
					}
					if (isNewState() && !isEndState() && !isIgnoredState()) {
						double probability = head.probability;
						ChoiceGenerator<?> cg = vm.getChoiceGenerator();
						if (cg instanceof Probabilistic) {
							probability *= ((Probabilistic) cg).getProbability();
						}
						setStateDepth(vm.getStateId() + 1, depth + 1);
						queue.add(new PVMState(vm.getRestorableState(), probability));
						notifyStateStored();
					}
					backtrack();
					notifyStateBacktracked();
				}
				notifyStateProcessed();
			}
			if (!checkStateSpaceLimit()) {
				notifySearchConstraintHit("memory limit reached: " + minFreeMemory);
				done = true;
			}
		}
		notifySearchFinished();
	}
}
