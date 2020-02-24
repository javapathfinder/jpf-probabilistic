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

package probabilistic;

/**
 * The class <code>Choice</code> contains the static method <code>make</code>.
 * This method, given an array <code>p</code> of probabilities, returns an
 * integer <code>i</code>, where <code>0 &le; i &lt; p.length</code>, with
 * probability <code>p[i]</code>. This method can be exploited to implement
 * randomize algorithms. For example, the following snippet mimicks a coin flip.
 * 
 * <pre>
 * double[] p = { 0.5, 0.5 };
 * switch (Choice.make(p)) {
 * case 0:
 * 	System.out.println(&quot;heads&quot;);
 * 	break;
 * case 1:
 * 	System.out.println(&quot;tails&quot;);
 * 	break;
 * }
 * </pre>
 * 
 * @author Xin Zhang
 * @author Franck van Breugel
 */
public class Choice {
	private Choice() {}

	/**
	 * Given an array <code>p</code> of probabilities, returns <code>i</code> with
	 * probability <code>p[i]</code>, where <code>0 &le; i &lt; p.length</code>.
	 * 
	 * @param p probabilities.
	 * @pre. <code>p[0] + ... + p[p.length - 1] = 1.0</code>.
	 * @return <code>i</code> with probability <code>p[i]</code>.
	 */
	public static int make(double[] p) {
		double sum = p[0];
		double choice = Math.random();
		int alternative = 0;
		while (choice >= sum) {
			alternative++;
			sum += p[alternative];
		}
		return alternative;
	}
}
