/*
 * Copyright (C) 2020  Maeve Wildes
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

import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import probabilistic.Choice;

import org.apache.commons.math3.optim.linear.NonNegativeConstraint;

import java.util.ArrayList;

/**
 * This app uses linear programming followed by randomized rounding to find a minimum set cover for a 
 * set expressed as a 1-0 matrix m: we wish to find a column vector c such that the dot product of each row
 * of m with c is positive, while minimizing c
 * 
 * @author Maeve Wildes
 */
public class SetCover {
	/**
	 * 
	 * @param int [][] m, a 1-0 matrix (note that the matrix cannot have a row of all zeros)
	 * @param int n, number of trials 
	 * 
	 */
	/*
	 * Run the setCover method n times, checking if a successful set cover has been found
	 */
	public static boolean find(int[][] m, int n) {
		boolean b = false;
		for (int i = 0; i < n; i++) {
			//System.out.println("Trial " + (i+1));
			if (setCover(m)) {
				b=true;
			}
		}
		return b;
	}
	public static boolean setCover (int [][] m) {
		int n = m.length;
		int k = m[0].length;
		ArrayList<LinearConstraint> constraints = new ArrayList<LinearConstraint>(n);
		for (int i=0; i<n; i++) {
			double [] constraintCoeffs = new double[k];
			for (int j=0; j<k; j++) {
				constraintCoeffs[j] = m[i][j];
			}
			constraints.add(new LinearConstraint(constraintCoeffs, Relationship.GEQ, 1));
		}
		NonNegativeConstraint con = new NonNegativeConstraint(true);
		LinearConstraintSet constraintSet = new LinearConstraintSet(constraints);
		double [] coeffs = new double[k];
		for (int i=0; i<k; i++) {
			coeffs[i] = 1;
		}
		LinearObjectiveFunction function = new LinearObjectiveFunction(coeffs, 0);
		SimplexSolver opt = new SimplexSolver();
		//the goal is to minimize the norm of column vector c
		PointValuePair pair = opt.optimize(function, constraintSet, GoalType.MINIMIZE, con);
		double[] c = pair.getPoint();
		//System.out.println("Initial column vector:");
		//for (int i=0; i<k; i++) {
			//System.out.print(c[i] + " ");
		//}
		//System.out.println();
		/*use randomized rounding to set each element in vector c to 0 or 1, with probability
		 * equal to its current value
		 */
		for (int i = 0; i < k; i++) {
			double[] choices = { 1 - c[i], c[i] };
			c[i] = Choice.make(choices);
		}
		//System.out.println("Column vector after rounding:");
		//for (int i=0; i<k; i++) {
		//	System.out.print(c[i] + " ");
		//}
		//System.out.println();
		//calculate whether a successful set cover was found
		for (int i=0; i<n; i++) {
			double count=0;
			for (int j=0; j<k; j++) {
				count= count+ (m[i][j]*c[j]);
			}
			if (count==0) {
				return false;
			}
		}
		return true;
	}
}
