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

package gov.nasa.jpf.vm;

import gov.nasa.jpf.annotation.MJI;
import gov.nasa.jpf.vm.MJIEnv;
import gov.nasa.jpf.vm.NativePeer;
import probabilistic.vm.ProbabilisticChoiceGenerator;

/**
 * This class is the native peer of the class probabilistic.Choice.
 * 
 * @see probabilistic.Choice
 * @author Xin Zhang
 * @author Franck van Breugel
 */
public class JPF_probabilistic_Choice extends NativePeer {
	public JPF_probabilistic_Choice() {}

	/**
	 * Returns a choice corresponding to this invocation of the make method.
	 * The first return is ignored by JPF.
	 * 
	 * @param env JPF environment.
	 * @param dummy arbitrary integer (plays no role in the method, but this
	 * parameter is needed for JPF to work properly).
	 * @param pRef reference to an array <code>p</code> of probabilities.
	 * @pre. <code>p[0] + ... + p[p.length - 1] = 1.0</code>.
	 * @return a choice corresponding to this invocation of the make method.
	 */
	@MJI
	public static int make___3D__I(MJIEnv env, int dummy, int pRef) {
		ThreadInfo ti = env.getThreadInfo();
		SystemState ss = env.getSystemState();
		ChoiceGenerator<?> cg;
		if (!ti.isFirstStepInsn()) {
			/*
			 * Only the first time this invocation of the make method is encountered a
			 * new choice generator is created. If during backtracking this invocation
			 * of the make method is encountered again, the choice generator is
			 * exploited.
			 */
			double[] p = env.getDoubleArrayObject(pRef);
			cg = new ProbabilisticChoiceGenerator("make", p);
			ss.setNextChoiceGenerator(cg);
			/*
			 * So far we have just created a new choice generator. Next, we repeat the
			 * invocation so that the choice generator is exploited.
			 */
			env.repeatInvocation();
			return -1; // JPF ignores this return
		} else {
			cg = ss.getChoiceGenerator();
			return ((ProbabilisticChoiceGenerator) cg).getNextChoice().intValue();
		}
	}
}
