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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import probabilistic.UniformChoice;

/**
 * Finds the majority element in a list and its multiplicity. 
 * 
 * @author Zainab Fatmi
 *
 * @param <T> the type of elements in the list.
 */
public class MajorityElement<T> {

	private double alpha = 1.0 / 3.0;
	private double epsilon;
	private double beta = 0.45; // 0.4226 < b < 0.4758
	private int multiplicity = -1;

	/**
	 * Finds, if it exists, the element that occurs more than n/2 times in the given
	 * list.
	 * 
	 * @param M a list of elements
	 * @return the majority element if it exists, otherwise null
	 */
	public T majority(List<T> M) {
		int n = M.size();
		epsilon = Math.pow(n, -0.1);
		if (n == 0) {
			multiplicity = -1;
			return null;
		} else if (n == 1) {
			multiplicity = 1;
			return M.get(0);
		} else {
			// create a sample of size n^a and find the frequencies of all elements
			int s = (int) Math.ceil(Math.pow(n, alpha));
			List<T> sample = new ArrayList<T>();
			HashMap<T, Integer> frequencies = new HashMap<T, Integer>();
			for (int i = 0; i < s; i++) {
				T element = M.get(UniformChoice.make(n));
				sample.add(element);
				if (frequencies.containsKey(element)) {
					frequencies.put(element, frequencies.get(element) + 1);
				} else {
					frequencies.put(element, 1);
				}
			}

			// find the two largest frequencies and the sum of squares
			int q1 = 0;
			int q2 = 0;
			T v1 = null;
			int ss = (int) (2 * epsilon);
			for (T e : frequencies.keySet()) {
				int f = frequencies.get(e);
				if (f > q1) {
					q2 = q1;
					q1 = f;
					v1 = e;
				} else if (f > q2) {
					q2 = f;
				}
				ss += f * f;
			}

			// approximate the color frequencies using the small random sample and choose
			// the right strategy
			int b1 = (int) Math.floor(0.5 - 4 * epsilon);
			int b2 = (int) Math.ceil(0.5 + 4 * epsilon);
			if (q2 > 0 && (q1 >= b1 && q1 <= b2) && (q2 >= b1 && q2 <= b2)) {
				return balanced(M);
			} else if (q1 > beta && q1 * q1 >= ss - q1 * q1) {
				return heavy(M, v1);
			} else {
				return light(M);
			}
		}
	}

	/**
	 * Returns the multiplicity of the last found majority element.
	 * 
	 * @return the multiplicity of the majority element if it exists, otherwise -1
	 */
	public int getMultiplicity() {
		return multiplicity;
	}

	/**
	 * Finds, if it exists, the element that occurs more than n/2 times in the given
	 * list, when there are two elements with frequencies close to 0.5.
	 * 
	 * @param M a list of elements, with two elements with frequencies close to 0.5
	 * @return the majority element if it exists, otherwise null
	 */
	private T balanced(List<T> M) {
		Collections.shuffle(M);
		ArrayList<T> X = new ArrayList<T>();
		ArrayList<T> Y = new ArrayList<T>();
		int half = (int) Math.floor(M.size() / 2.0);
		for (int i = 1; i <= half; i++) {
			if (M.get(2 * i - 2).equals(M.get(2 * i - 1))) {
				X.add(M.get(2 * i - 1));
			} else {
				Y.add(M.get(2 * i - 2));
				Y.add(M.get(2 * i - 1));
			}
		}
		T v = majority(X);
		if (multiplicity <= 0) {
			return null;
		} else {
			int cnt = 2 * getMultiplicity();
			int y = (int) Math.floor(Y.size() / 2.0);
			for (int i = 1; i <= y; i++) {
				if (v.equals(Y.get(2 * i - 2))) {
					cnt++;
				} else if (v.equals(Y.get(2 * i - 1))) {
					cnt++;
				}
			}
			if (cnt <= half) {
				multiplicity = -1;
				return null;
			} else {
				multiplicity = cnt;
				return v;
			}
		}
	}

	/**
	 * Finds, if it exists, the element that occurs more than n/2 times in the given
	 * list, when there is one element with a large frequency.
	 * 
	 * @param M a list of elements, with one element with a large frequency
	 * @param v the element, in the list, with large frequency
	 * @return the majority element if it exists, otherwise null
	 */
	private T heavy(List<T> M, T v) {
		int n = M.size();
		int cnt = 0;
		ArrayList<T> X = new ArrayList<T>();
		for (int i = 0; i < n; i++) {
			if (M.get(i).equals(v)) {
				cnt++;
			} else {
				X.add(M.get(i));
			}
		}
		int half = (int) Math.floor(n / 2.0);
		if (cnt > half) {
			multiplicity = cnt;
			return v;
		} else {
			int k = half - cnt;
			Collections.shuffle(X);
			int x = (int) Math.floor(X.size() / 2.0);
			for (int i = 1; i <= x; i++) {
				if (!X.get(2 * i - 2).equals(X.get(2 * i - 1)) && k - 1 == 0) {
					multiplicity = -1;
					return null;
				}
			}
			return boyerMoore(M);
		}
	}

	/**
	 * The Boyer–Moore majority vote algorithm for finding the majority of a
	 * sequence of elements using linear time and constant space.
	 * 
	 * @param M a sequence of elements
	 * @return the majority element if it exists, otherwise null
	 */
	private T boyerMoore(List<T> M) {
		T m = M.get(0);
		int i = 0;
		for (int x = 0; x < M.size(); x++) {
			if (i == 0) {
				m = M.get(x);
			} else if (m.equals(M.get(x))) {
				i++;
			} else {
				i--;
			}
		}
		int f = 0;
		for (int x = 0; x < M.size(); x++) {
			if (m.equals(M.get(x))) {
				f++;
			}
		}
		if (f > Math.floor(M.size() / 2.0)) {
			multiplicity = f;
			return m;
		} else {
			multiplicity = -1;
			return null;
		}
	}

	/**
	 * Finds, if it exists, the element that occurs more than n/2 times in the given
	 * list, when all frequencies (of elements) are small.
	 * 
	 * @param M a list of elements with small frequencies
	 * @return the majority element if it exists, otherwise null
	 */
	private T light(List<T> M) {
		Collections.shuffle(M);
		ArrayList<T> X = new ArrayList<T>();
		ArrayList<T> Y = new ArrayList<T>();
		int half = (int) Math.floor(M.size() / 2.0);
		for (int i = 1; i <= half; i++) {
			if (M.get(2 * i - 2).equals(M.get(2 * i - 1))) {
				X.add(M.get(2 * i - 1));
			} else {
				Y.add(M.get(2 * i - 2));
				Y.add(M.get(2 * i - 1));
			}
		}
		T v = majority(X);
		if (multiplicity <= 0) {
			return null;
		} else {
			int cnt = 2 * getMultiplicity() - X.size();
			int y = (int) Math.floor(Y.size() / 2.0);
			for (int i = 1; i <= y; i++) {
				if (!v.equals(Y.get(2 * i - 2))) {
					if (!v.equals(Y.get(2 * i - 1))) {
						cnt--;
					}
				}
				if (cnt <= 0) {
					multiplicity = -1;
					return null;
				}
			}
			multiplicity = half + cnt;
			return v;
		}
	}
}

