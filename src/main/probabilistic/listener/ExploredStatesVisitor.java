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

package probabilistic.listener;

import gov.nasa.jpf.ListenerAdapter;
import gov.nasa.jpf.search.Search;
import gov.nasa.jpf.vm.ChoiceGenerator;

/**
 * A visitor of StateSpaceDot, which marks explored states by adding a second
 * periphery around them.
 * 
 * @author Zainab Fatmi
 */
public class ExploredStatesVisitor extends ListenerAdapter implements Visitor {

	private int id;

	@Override
	public void searchStarted(Search search) {
		this.id = -1;
		this.visitDottyListeners(search);
	}

	@Override
	public void stateAdvanced(Search search) {
		if (search.isEndState()) {
			this.id = search.getStateId();
			this.visitDottyListeners(search);
		}
		ChoiceGenerator<?> cg = search.getVM().getChoiceGenerator();
		if (cg != null && !cg.hasMoreChoices()) {
			this.id = cg.getStateId();
			this.visitDottyListeners(search);
		}
	}

	@Override
	public void visitStateSpaceDot(StringBuilder result) {
		result.append(this.id + " [peripheries=2]\n");
	}

	@Override
	public void visitStateSpaceText(StringBuilder result) {
	}

	/**
	 * Invokes the accept(Visitor) method in all listeners of type StateSpaceDot.
	 * 
	 * @param search JPF's search object.
	 */
	private void visitDottyListeners(Search search) {
		StateSpaceDot dot = search.getNextListenerOfType(StateSpaceDot.class, null);
		while (dot != null) {
			dot.accept(this);
			dot = search.getNextListenerOfType(StateSpaceDot.class, dot);
		}
	}
}
