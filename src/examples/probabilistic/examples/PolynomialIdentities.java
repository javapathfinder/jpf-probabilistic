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

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import probabilistic.UniformChoice;

/**
 * Applying Frievald's technique to the verification of identities involving
 * polynomials.
 * 
 * @author Zainab Fatmi
 */
public class PolynomialIdentities {

	private int s; // the upper bound of the subset
	private int l; // the lower bound of the subset

	/**
	 * Initializes a subset of the domain. The the probability of error is at most
	 * 2n/s, where n is the degree of the polynomials.
	 * 
	 * @param s the upper bound on the subset of the domain of the polynomials (s
	 *          should be less than the n-th root of 2^32)
	 */
	public PolynomialIdentities(int s) {
		this(s, 0);
	}

	/**
	 * Initializes a subset of the domain. The the probability of error is at most
	 * 2n/(s-l), where n is the degree of the polynomials.
	 * 
	 * @param s the upper bound on the subset of the domain of the polynomials (s
	 *          should be less than the n-th root of 2^32)
	 * @param l the lower bound on the subset of the domain of the polynomials
	 */
	public PolynomialIdentities(int s, int l) {
		this.s = s;
		this.l = l;
	}

	/**
	 * Verifies whether the given polynomial, P3, is the product of the given
	 * polynomials, P1 and P2.
	 * 
	 * @param P1  a polynomial expression
	 * @param P2  a polynomial expression
	 * @param P3  the product of P1 and P2
	 * @param var an array containing all of the variable names
	 * @return true if P1 x P2 = P3, false otherwise
	 */
	public boolean verifyProduct(String P1, String P2, String P3, String[] var) {
		return verify("(" + P1 + ") * (" + P2 + ") - (" + P3 + ")", var);
	}

	/**
	 * Verifies whether the given polynomials, P1 and P2, are equal.
	 * 
	 * @param P1  a polynomial expression
	 * @param P2  a polynomial expression
	 * @param var an array containing all of the variable names
	 * @return true if P1 = P2, false otherwise
	 */
	public boolean verifyEquality(String P1, String P2, String[] var) {
		return verify("(" + P1 + ") - (" + P2 + ")", var);
	}

	/**
	 * Verifies whether the given polynomial, Q, is identically zero, or Q ≡ 0.
	 * 
	 * @param Q   the polynomial expression to evaluate
	 * @param var an array containing all of the variable names
	 * @return true if Q ≡ 0, false otherwise
	 */
	public boolean verify(String Q, String[] var) {
		// pick randomly from a subset of the domain
		Expression q = new ExpressionBuilder(Q).variables(var).build();
		for (int i = 0; i < var.length; i++) {
			q.setVariable(var[i], UniformChoice.make(s-l) + l);
		}
		return q.evaluate() == 0;
	}
}
