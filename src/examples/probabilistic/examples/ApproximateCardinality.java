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

package probabilistic.examples;

import java.util.Iterator;
import java.util.Set;

import probabilistic.Choice;
import probabilistic.UniformChoice;

/**
 * Estimates the cardinality of a union of sets.
 * 
 * @param <E> the type of the elements in the sets.
 * 
 * @author Zainab Fatmi
 */
public class ApproximateCardinality<E> {

	/**
	 * Produces an estimate of the cardinality of the union of t sets, by taking the
	 * average of N samples of the estimator, X, where E[X] = |⋃Si|; i.e. Y = (X1 +
	 * X2 + ... + XN) / N.
	 * 
	 * @param sets an array (of size t) of sets.
	 * @param N    the number of samples. Note: if we require Pr[(Y - |⋃Si|) / |⋃Si|
	 *             > ε] < δ, a sufficient sample size is N = t ln(2/δ) 4.5/ε^2.
	 * @return Y, the estimate of |⋃Si|, the cardinality of the union of the sets.
	 */
	public long getCardinality(Set<E>[] sets, int N) {
		long sum = 0;
		for (int j = 0; j < N; j++) {
			sum += getEstimator(sets);
		}
		return Math.round(sum / N);
	}

	/**
	 * Produces an estimator, X, of the cardinality of the union of the given sets,
	 * using a two-stage sampling process; i.e. E[X] = |⋃Si|.
	 * 
	 * @param sets an array of sets.
	 * @return X, the estimator of the cardinality of the union of the sets.
	 */
	public long getEstimator(Set<E>[] sets) {
		/*
		 * Draw a set at random from the distribution in which the probability of
		 * drawing Si is proportional to its cardinality; i.e., Pr[Si] = |Si| / Σ|Sj|
		 * (from j = 1 to t).
		 */
		int t = sets.length;
		double sum = 0;
		for (int j = 0; j < t; j++) {
			sum += sets[j].size();
		}
		double[] probabilities = new double[t];
		probabilities[0] = sets[0].size() / sum;
		for (int j = 1; j < t; j++) {
			probabilities[j] = (sets[j].size() / sum) + probabilities[j - 1];
		}
		
		int i = 0;
		while (i < t - 1 && Choice.make(new double[] {probabilities[i], 1-probabilities[i]}) == 1) {
			i++;
		}

		// Having drawn Si, choose a random element x from Si.
		double r = UniformChoice.make(sets[i].size());
		Iterator<E> itr = sets[i].iterator();
		E x = itr.next();
		while (r > 0 && itr.hasNext()) {
			x = itr.next();
			r--;
		}

		// By testing the membership of x in each Sj, determine cov(x) = |{i|x ∈ Si}|.
		int cov = 0;
		for (int j = 0; j < t; j++) {
			if (sets[j].contains(x)) {
				cov++;
			}
		}

		// X = Σ |Si| / cov(x), (from j = 1 to t).
		return Math.round(sum / cov);
	}
}
