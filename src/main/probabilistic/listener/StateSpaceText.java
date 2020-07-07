/*
 * Copyright (C) 2011  Xin Zhang, Franck van Breugel, and Syyeda Zainab Fatmi
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

import gov.nasa.jpf.Config;
import gov.nasa.jpf.search.Search;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * This listener outputs the state space to a file. The name of the file is
 * &lt;name of app&gt;.tra. The state space is represented in the following
 * format. The first line contains two nonnegative integers separated by a
 * single space: the number of states (s) and the number of transitions (t). The
 * next t lines represent the transitions. Each transition is captured by the
 * source state, the target state, and the probability, all separated by a
 * single space. The source and target states are represented by an integer and
 * the probability is represented by a double.
 *
 * @author Xin Zhang
 * @author Franck van Breugel
 * @author Syyeda Zainab Fatmi
 */
public class StateSpaceText extends StateSpace {
	private int transitions; // number of transitions
	private StringBuilder result; // the result to be written to the transition file

	/**
	 * Initializes the listener.
	 *
	 * @param config JPF's configuration.
	 */
	public StateSpaceText(Config config) {
		super(config);
		this.transitions = 0;
		this.result = new StringBuilder();
	}

	@Override
	public void addTransition(int source, int target, double probability) {
		this.result.append(String.format("%d %d %f%n", source, target, probability));
		this.transitions++;
	}

	@Override
	public void writeStateSpace(Search search, String name) {
		try {
			PrintWriter writer = new PrintWriter(name + ".tra");
			writer.println(String.format("%d %d", this.getStates(), this.transitions));
			writer.print(this.result);
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("Listener could not write to file " + name + ".tra");
			search.terminate();
		}
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visitStateSpaceText(this.result);
	}
}
