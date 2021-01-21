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

import java.util.Iterator;
import java.util.Set;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.search.Search;
import gov.nasa.jpf.vm.VM;
import label.StateLabel;

/**
 * A visitor of StateLabelDot, which colours states according to the specified
 * labelling functions.
 * 
 * @author Zainab Fatmi
 */
public class StateLabelVisitor extends StateLabel implements Visitor {
	private int id;
	private Set<Integer> labels;

	public StateLabelVisitor(Config configuration) {
		super(configuration);
	}

	@Override
	public void labelState(int id, Set<Integer> labels) {
		this.id = id;
		this.labels = labels;

		Search search = VM.getVM().getSearch();
		StateSpaceDot dot = search.getNextListenerOfType(StateSpaceDot.class, null);
		while (dot != null) {
			dot.accept(this);
			dot = search.getNextListenerOfType(StateSpaceDot.class, dot);
		}
	}

	@Override
	public void writeStateLabels(Search search, String name) {
		this.generateLegendFile();
	}

	@Override
	public void visitStateSpaceDot(StringBuilder result) {
		if (!this.labels.isEmpty()) {
			if (result.indexOf("node [colorscheme=\"set312\" style=wedged]") < 0) {
				result.insert(0, "node [colorscheme=\"set312\" style=wedged]\n");
			}
			Iterator<Integer> iter = this.labels.iterator();
			if (this.labels.size() == 1) {
				result.append(this.id + " [style=filled fillcolor=" + getColour(iter.next()) + "]\n");
			} else {
				result.append(this.id + " [fillcolor=\"");
				while (iter.hasNext()) {
					result.append(getColour(iter.next()));
					if (iter.hasNext()) {
						result.append(":");
					}
				}
				result.append("\"]\n");
			}
		}
	}

	@Override
	public void visitStateSpaceText(StringBuilder result) {
	}
}
