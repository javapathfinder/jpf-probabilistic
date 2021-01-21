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

import java.lang.Math;
import java.util.Arrays;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;

import probabilistic.UniformChoice;

/**
 * Finds the roots a and b of a polynomial, f(x) = x^2 + ax + b
 * over the field Zp, where p is a given prime number.
 * The class relies on org.apache.commons.math3.  The latter is available licensed under 
 * the Apache License, Version 2.0.
 * 
 * @author Maeve Wildes
 */
public class RandomizedPolynomialFactorization {
	private RandomizedPolynomialFactorization() {}
	
	/**
	 * 
	 * @param p, a prime identifying the field we are in
	 * @param A, the first coefficient of the polynomial f(X) = X^2 + AX + B
	 * @param B, the second coefficient of the polynomial f(X)
	 * f(X) must be monic and reducible. the algorithm will find the roots a and b of the polynomial over the 
	 * field Zp
	 */
	public static int[] get(int p, int A, int B) {
		
		double a;
		double b;

		while (true) {
			int r = UniformChoice.make(p);
			// find coefficients A1 and B1 of polynomial g(X) such that g(X) = f(X-r)
			double A1 = Math.floorMod(A - 2*r, p);
			double B1 = Math.floorMod(B-A*r+r*r, p);
			if (Math.floorMod((int)B1, p) == 0) {
				a = -r;
				b = -r - A1;
//				System.out.println(Math.floorMod((int)a, p) + " " + Math.floorMod((int)b, p));
				int [] roots = {Math.floorMod((int)a, p), Math.floorMod((int)b, p)};
				return roots;
			}
			// find h(X) = gcd(g(X), X^((p-1)/2) -1). A2 and B2 are the coefficients of h(X)
			double [] g = {B1, A1, 1};
			int power = (p-1)/2;
			double [] poly = new double[power+1];
			poly[0] = -1;
			poly[power] = 1;
			PolynomialFunction gX = new PolynomialFunction(g);
			PolynomialFunction p2 = new PolynomialFunction(poly);
			PolynomialFunction hX =gcd(p2, gX);
			double [] h = hX.getCoefficients();
			// reduce the gcd function to a monic polynomial
			for (int i=0; i<h.length; i++) {
				h[i] = h[i] / h[h.length-1];
			}
			hX = new PolynomialFunction(h);
			double [] ones = new double[h.length];
			ones[0]=1;
			PolynomialFunction one = new PolynomialFunction(ones);
			// repeat from while loop if h(X) = g(X) or h(X) = 1
			if (!(hX.equals(gX)) && !(hX.equals(one))) {
				// if h(X) != g(X) and h(X) != 1, then find c such that h(X) = X-c 
				double [] x1 = {0, 1};
				PolynomialFunction x =new PolynomialFunction(x1);
				PolynomialFunction c = x.add(hX.negate());
				A = (int)c.value(0);
				B = -(int)A1 -A;
				a = Math.floorMod(A-r, p);
				b = Math.floorMod(B-r, p);
//				System.out.println((int)a + " " + (int)b);
				int [] roots = {(int)a, (int)b};
				return roots;
			}
		}
		
	}
	/**
	 * 
	 * @param a, the larger polynomial
	 * @param b, the smaller polynomial
	 * @return the gcd of the two polynomials
	 */
	private static PolynomialFunction gcd(PolynomialFunction a, PolynomialFunction b) {
		double [] zeros = new double[b.degree()+1];
		
		PolynomialFunction zero = new PolynomialFunction(zeros);
		if (b.equals(zero)) {
			return a;
		}
		else {
			return gcd(b, remainder(a, b));
		}
		
	}
	/**
	 * 
	 * @param a, the dividend
	 * @param b, the divisor
	 * @return r, the remainder from dividing a by b
	 */
	private static PolynomialFunction remainder(PolynomialFunction a, PolynomialFunction b) {
		double [] a1 = a.getCoefficients();
		double [] b1 = b.getCoefficients();
		for (int i=0 ;i<a1.length/2; i++) {
			double temp = a1[i];
			a1[i] = a1[a1.length-i-1];
			a1[a1.length-i-1] = temp;
		}
		for (int i=0 ;i<b1.length/2; i++) {
			double temp = b1[i];
			b1[i] = b1[b1.length-i-1];
			b1[b1.length-i-1] = temp;
			
		}
		double [] r = a1.clone();
		// c is the leading coefficient of b
		double c = b1[0];
		for  (int i=0; i< a1.length -(b1.length -1); i++) {
			r[i] /= c;
			double coef = r[i];
			if (coef != 0) {
				for (int j=0; j<b1.length; j++) {
					r[i+j] += -b1[j]*coef;
				}
			}
			
		}
		
		int remainderIndex= r.length - (b1.length -1);
		double [] remainder = Arrays.copyOfRange(r, remainderIndex, r.length);
		for (int i=0 ;i<remainder.length/2; i++) {
			double temp = remainder[i];
			remainder[i] = remainder[remainder.length-i-1];
			remainder[remainder.length-i-1] = temp;
			
		}
		if (remainder.length == 0) {
			double [] zero = {0};
			PolynomialFunction ans= new PolynomialFunction(zero);
			return ans;
		}
		PolynomialFunction ans = new PolynomialFunction(remainder);
	
		return ans;
	}
}
