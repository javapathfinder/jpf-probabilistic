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

package probabilistic.listener;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import gov.nasa.jpf.report.Publisher;
import gov.nasa.jpf.report.PublisherExtension;
import gov.nasa.jpf.search.Search;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * This listener outputs the state space in DOT format to a file. The name of
 * the file is &lt;name of app&gt;.dot. The file contains a graphical
 * representation of the state space that can be viewed with the dotty command.
 * 
 * The precision of the probability can be specified in the application
 * properties file by setting the property probabilistic.listener.StateSpaceDot.precision.
 * Its default value is two.
 * 
 * @author Xin Zhang
 * @author Franck van Breugel
 * @author Syyeda Zainab Fatmi
 */
public class StateSpaceDot extends StateSpace implements PublisherExtension {
	private StringBuilder result;
	private int precision;

	/**
	 * Initializes the listener.
	 *
	 * @param config JPF's configuration.
	 * @param jpf    JPF.
	 */
	public StateSpaceDot(Config config, JPF jpf) {
		super(config);
		this.result = new StringBuilder();
		this.precision = config.getInt("probabilistic.listener.StateSpaceDot.precision", 2);
		jpf.addPublisherExtension(Publisher.class, this);
	}

	@Override
	public void addTransition(int source, int target, double probability) {
		this.result.append(
				String.format("%d -> %d [ label=\"%." + this.precision + "f\" ];%n", source, target, probability));
	}

	@Override
	public void writeStateSpace(Search search, String name) {
		try {
			PrintWriter writer = new PrintWriter(name + ".dot");
			writer.println("digraph statespace {");
			writer.print(this.result);
			writer.println("}");
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("Listener could not write to file " + name + ".dot");
			search.terminate();
		}
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visitStateSpaceDot(this.result);
	}
}
