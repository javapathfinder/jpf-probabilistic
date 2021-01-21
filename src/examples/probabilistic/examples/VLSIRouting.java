/*
 * Copyright (C) 2020  Asma Hassan and Franck van Breugel
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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import probabilistic.Choice;

/**
 * This class solves a simplified version of the problem of global wiring in 
 * gate-arrays.  A gate-array is a two-dimensional array of gates abutting 
 * each other, arranged at regularly spaced points in the plane.  A logic 
 * circuit is implemented on such an array by connecting together some of 
 * the gates using wires.  A net is a pair of gates to be connected by a wire.  
 * Wires run over the array in Manhattan form, that is, they run parallel to 
 * the axes of orientation of the gate-array.  We assume that the global route 
 * for each net contains at most one 90 degree turn.  Thus, in joining the two 
 * end-points of a net, the wire will either first traverse the horizontal 
 * dimension and then the vertical dimension, or the other way around.  The 
 * boundary between adjacent gates in an array has a fixed physical dimension
 * and can therefore accommodate only a prescribed maximum number of wires.
 * We wish to find an assignment of global routes to all the nets in the 
 * wiring problem, such that a minimal number of nets pass through any boundary.
 * For each net, we wish to determine whether the wire will either first traverse 
 * the horizontal dimension and then the vertical dimension, or the other way 
 * around so that at most w wires pass through each boundary, where w is minimized.
 * 
 * @author Asma Hassan
 * @author Franck van Breugel
 */
public class VLSIRouting {
	private VLSIRouting() {}

	/**
	 * A net.
	 */
	private static class Net {
		private int startColumn;
		private int startRow;
		private int endColumn;
		private int endRow;

		/**
		 * Initializes this net.
		 * 
		 * @param startColumn column number of start point of this net.
		 * @param startRow row number of start point of this net.
		 * @param endColumn column number of end point of this net.
		 * @param endRow row number of end point of this net.
		 */
		private Net(int startColumn, int startRow, int endColumn, int endRow) {
			super();
			this.startColumn = startColumn;
			this.startRow = startRow;
			this.endColumn = endColumn;
			this.endRow = endRow;
		}

		/**
		 * Returns a string representation of this net.
		 * 
		 * @return a string representation of this net.
		 */
		public String toString() {
			return String.format("net from (%d, %d) to (%d, %d)", startColumn, startRow, endColumn, endRow);
		}
	}

	/**
	 * Returns the directions in which each net should start.
	 * 
	 * @param columns the number of columns of the gate array
	 * @param rows the number of rows of the gate array
	 * @param args[2..] x- and y-coordinates of the endpoints of the nets
	 */
	public static boolean[] getDirections(int columns, int rows, int[][] endPoints) {
		int number = endPoints.length;
		Net[] net = new Net[number];
		for (int n = 0; n < number; n++) {
			int startColumn = endPoints[n][0];
			int startRow = endPoints[n][1];
			int endColumn = endPoints[n][2];
			int endRow = endPoints[n][3];
			net[n] = new Net(startColumn, startRow, endColumn, endRow);
		}

		// compute boundaries that are crossed when the route goes horizontally first
		Map<String, Set<Integer>> horizontalFirst = new HashMap<String, Set<Integer>>();
		Set<String> boundaries = new HashSet<String>();
		for (int n = 0; n < net.length; n++) {
			int min = Math.min(net[n].startColumn, net[n].endColumn);
			int max = Math.max(net[n].startColumn, net[n].endColumn);
			for (int i = min; i < max; i++) {
				String boundary = String.format("West boundary of cell (%d, %d)", i, net[n].startRow);
				boundaries.add(boundary);
				if (!horizontalFirst.containsKey(boundary)) {
					horizontalFirst.put(boundary, new HashSet<Integer>());
				}
				horizontalFirst.get(boundary).add(n);
			}
			min = Math.min(net[n].startRow, net[n].endRow);
			max = Math.max(net[n].startRow, net[n].endRow);
			for (int i = min; i < max; i++) {
				String boundary = String.format("North boundary of cell (%d, %d)", net[n].endColumn, i);
				boundaries.add(boundary);
				if (!horizontalFirst.containsKey(boundary)) {
					horizontalFirst.put(boundary, new HashSet<Integer>());
				}
				horizontalFirst.get(boundary).add(n);
			}
		}

		// compute boundaries that are crossed when the route goes vertically first
		Map<String, Set<Integer>> verticalFirst = new HashMap<String, Set<Integer>>();
		for (int n = 0; n < number; n++) {
			int min = Math.min(net[n].startRow, net[n].endRow);
			int max = Math.max(net[n].startRow, net[n].endRow);
			for (int i = min; i < max; i++) {
				String boundary = String.format("North boundary of cell (%d, %d)", net[n].startColumn, i);
				boundaries.add(boundary);
				if (!verticalFirst.containsKey(boundary)) {
					verticalFirst.put(boundary, new HashSet<Integer>());
				}
				verticalFirst.get(boundary).add(n);
			}
			min = Math.min(net[n].startColumn, net[n].endColumn);
			max = Math.max(net[n].startColumn, net[n].endColumn);
			for (int i = min; i < max; i++) {
				String boundary = String.format("West boundary of cell (%d, %d)", i, net[n].endRow);
				boundaries.add(boundary);
				if (!verticalFirst.containsKey(boundary)) {
					verticalFirst.put(boundary, new HashSet<Integer>());
				}
				verticalFirst.get(boundary).add(n);
			}
		}

		// constraints
		Collection<LinearConstraint> constraints = new ArrayList<LinearConstraint>();

		// xn0 >= 0.0 and xn1 >= 0.0
		for (int n = 0; n < 2 * number; n++) {
			double[] coefficient = new double[2 * number + 1];
			coefficient[n] = 1.0;
			constraints.add(new LinearConstraint(coefficient, Relationship.GEQ, 0.0));
		}

		// xn0 <= 1.0 and xn1 <= 1.0
		for (int n = 0; n < 2 * number; n++) {
			double[] coefficient = new double[2 * number + 1];
			coefficient[n] = 1.0;
			constraints.add(new LinearConstraint(coefficient, Relationship.LEQ, 1.0));
		}

		// xn0 + xn1 = 1.0
		for (int n = 0; n < number; n++) {
			double[] coefficient = new double[2 * number + 1];
			coefficient[n] = 1.0;
			coefficient[number + n] = 1.0;
			constraints.add(new LinearConstraint(coefficient, Relationship.EQ, 1.0));
		}

		// sum { xn0 | n in horizontalFirst[b] } + sum { xn1 | n \in verticalFirst[b] } - w <= 0
		for (String boundary : boundaries) {
			double[] coefficient = new double[2 * number + 1];
			if (horizontalFirst.containsKey(boundary)) {
				for (Integer index : horizontalFirst.get(boundary)) {
					coefficient[index] = 1.0;
				}
			}
			if (verticalFirst.containsKey(boundary)) {
				for (Integer index : verticalFirst.get(boundary)) {
					coefficient[number + index] = 1.0;
				}
			}
			coefficient[2 * number] = -1.0;
			constraints.add(new LinearConstraint(coefficient, Relationship.LEQ, 0.0));
		}

		// objective function
		double[] coefficient = new double[2 * number + 1];
		coefficient[2 * number] = 1.0;
		LinearObjectiveFunction objective = new LinearObjectiveFunction(coefficient, 0.0);

		// solve
		SimplexSolver solver = new SimplexSolver();
		PointValuePair solution = solver.optimize(objective, new LinearConstraintSet(constraints), GoalType.MINIMIZE);
		double[] point = solution.getPoint();

		// randomized rounding
		boolean[] horizontal = new boolean[number];
		for (int n = 0; n < number; n++) {
			double[] choices = { point[n], 1 - point[n] };
			horizontal[n] = Choice.make(choices) == 0;
		}
		
		return horizontal;
	}
}
