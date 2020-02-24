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

import probabilistic.vm.Probabilistic;

/**
 * This search strategy randomly selects the next state to be explored.  
 * The probability that a state is chosen in proportional to the probability 
 * of the path along the state was reached during the search. 
 *
 * @author Xin Zhang
 * @author Franck van Breugel
 */
public class RandomSearch extends Search {

	/**
	 * Initializes this search.
	 *
	 * @param config JPF's configuration.
	 * @param vm JPF's VM.
	 */
	public RandomSearch(Config config, VM vm) {
		super(config, vm);
	}

	/**
	 * The search of the random search strategy.
	 */
	public void search() {
		notifySearchStarted();
		final int MAX_DEPTH = getDepthLimit();
		Distribution<RestorableVMState> distribution = new List<RestorableVMState>();  //new RedBlackTree<RestorableVMState>(); //
		distribution.add(vm.getRestorableState(), 1.0);
		setStateDepth(vm.getStateId() + 1, 0);
		notifyStateStored();
		do {
			Weighted<RestorableVMState> selected = distribution.remove();
			RestorableVMState source = selected.getElement();
			vm.restoreState(source);
			notifyStateRestored();
			int depth = getStateDepth(vm.getStateId() + 1);
			if (depth >= MAX_DEPTH) {
				notifySearchConstraintHit("depth limit reached: " + MAX_DEPTH);
				continue;
			}
			while (!done) {
				if (!forward()) {
					break;
				} else {
					notifyStateAdvanced();
					if (currentError != null) {
						notifyPropertyViolated();
						if (hasPropertyTermination()) {
							break;
						}
					}
					if (isNewState() && !isEndState() && !isIgnoredState()) {
						double probability = selected.getWeight();
						ChoiceGenerator<?> cg = vm.getChoiceGenerator();
						if (cg instanceof Probabilistic) {
							probability *= ((Probabilistic) cg).getProbability();
						}
						RestorableVMState target = vm.getRestorableState();
						distribution.add(target, probability);
						setStateDepth(vm.getStateId() + 1, depth + 1);
						notifyStateStored();
						if (!checkStateSpaceLimit()) {
							notifySearchConstraintHit("memory limit reached: " + minFreeMemory);
							done = true;
							break;
						}
					}
					backtrack();
					notifyStateBacktracked();
				}
			}
		} while (!distribution.isEmpty() && !done);
		notifySearchFinished();
	}
}
