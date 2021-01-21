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

import java.math.BigInteger;

import probabilistic.UniformChoice;

/**
 * Given a prime p and a quadratic residue a in Z*(p), finds the square roots of a modulo p.
 * 
 * @author Maeve Wildes
 */
public class QuadraticResidue {
	
	/**
	 * Let Z*(p) = {1,...,p-1} for p prime.
	 * A quadratic residue a in Z*(p) is an element such that there exists some x in Z*(p) such that a = x^2 mod p.
	 * Given a prime p and a quadratic residue a in Z*(p), find the square roots x1 and x2 of a modulo p.
	 * The square root x2 = -x1, i.e., x2 = p-x1.
	 * 
	 * The idea is to find an odd power of a, say a^(2l+1) = 1 (mod p) so that a^(2l+2) = a (mod p) meaning
	 * the square roots are +-a^(l+1).
	 * 
	 * @param a the integer of which the square root is wanted
	 * @param p a prime defining the field we are in
	 * @return an array of size two holding the two square roots of a, null if a is not a quadratic residue
	 */
	public static int[] getRoots(int a, int p) {
		int[] roots = new int[2];
		BigInteger a1 = BigInteger.valueOf(a);
		BigInteger p1 = BigInteger.valueOf(p);
		BigInteger one = BigInteger.valueOf(1);
		// verify that a is a quadratic residue
		if (!quadResidue(a, p)) {
			return null;
		}
		int b = 1;
		// randomly select a quadratic non-residue, b
		while (quadResidue(b, p)) {
			b = UniformChoice.make(p - 2) + 1;
		}
		BigInteger b1 = BigInteger.valueOf(b);
		//case 1
		if (p % 4 == 3) {
			int k = (p - 3) / 4;
			BigInteger x = a1.pow(k + 1).mod(p1);
			roots[0]= x.intValue();
			roots[1]= p - roots[0];
		}
		//case 2
		else if (p % 8 == 5) {
			int k = (p - 5) / 8;
			if (a1.pow(2 * k + 1).mod(p1).equals(one)) {
				BigInteger x = a1.pow(k + 1).mod(p1);
				roots[0] = x.intValue();
				roots[1] = p-roots[0];
			}
			else {
				// here, a^(2k+1) = -1 (mod p), so we use the quadratic non-residue b
				BigInteger A  = a1.pow(k + 1);
				BigInteger B = b1.pow(2 * k + 1);
				BigInteger AB = A.multiply(B);
				roots[0] = AB.mod(p1).intValue();
				roots[1] = p - roots[0];
			}
		}
		//case 3 (the only other possibility: p%8 == 1)
		else {
			int k = (p - 1) / 8;
			int[] rs = findRs(k);
			// k = R*2^r
			int r = rs[0];
			int R = rs[1];
			if (a1.pow(R).mod(p1).equals(one)) {
				//this is the easy case where a^R = 1 (mod p)
				BigInteger x = a1.pow((R + 1) / 2).mod(p1);
				roots[0] = x.intValue();
				roots[1] = p - roots[0];
			}
			else {
				int power = ((int) Math.pow(2, r + 2)) * R;
				BigInteger A = a1.pow(power);
				/* note that a^(2^(r+2)*R) = A = 1 (mod p), but the exponent of a is not odd.
				so we find 0<j<r+2 such that Aj = a^(2^j)*R != 1 (mod p) but Aj^2 = 1 (mod p) by repeatedly taking 
				the square root of A
				*/
				int j= r + 2;		
				while (A.mod(p1).equals(one)) {
					j--;
					A = a1.pow(((int) Math.pow(2, j)) * R);
				}
				int y1 = ((int) Math.pow(2, j)) * R;
				int y2 = ((int) Math.pow(2, r + 2)) * R;
				// A = a^y1 (mod p)
				A = a1.pow(y1).mod(p1);
				// B = b^y2 (mod p)
				BigInteger B = b1.pow(y2).mod(p1);
				while (true) {
					//multiply A with B until a result is found
					while(A.multiply(B).mod(p1).equals(one) && y1 != R) {
						y1 /= 2;
						y2 /= 2;
						A = a1.pow(y1).mod(p1);
						B = b1.pow(y2).mod(p1);
					}
					if (y1 == R && A.multiply(B).mod(p1).equals(one)) {
						// the exponent of a is equal to R, so a result has been found
						y2 /= 2;
						B = b1.pow(y2).mod(p1);
						y1 = (y1 + 1) / 2;
						A = a1.pow(y1).mod(p1);
						int x = A.multiply(B).intValue() % p;
						roots[0] = x;
						roots[1] = p - roots[0];
						return roots;
					}
					else {
						// this is the case where A*B != 1, so we try again
						y2 = y2 + ((int)Math.pow(2, r + 2)) * R;
						B = b1.pow(y2);
					}
					
				}
			}
		}
		return roots;
	}
	
	/**
	 * Calculates Legendre symbol of a mod p, to determine whether a is a quadratic residue.
	 * 
	 * @param a integer to determine whether or not it is a quadratic residue
	 * @param p a prime
	 * @return true if a is a quadratic residue, false otherwise
	 */
	public static boolean quadResidue(int a, int p) {
		BigInteger base = BigInteger.valueOf(a);
		BigInteger A = base.pow((p - 1) / 2);
		BigInteger mod = BigInteger.valueOf(p);
		return (A.mod(mod).equals(BigInteger.valueOf(1)));
	}
	
	/**
	 * 
	 * @param k
	 * @return int [] rs where rs[0] = r, rs[1] = R  such that k=R*2^r for odd R
	 */
	public static int[] findRs(int k){
		int r = 0;
		while(k % 2 == 0) {
			r++; 
			k= k / 2;
			
		}
		int[] rs = new int[2];
		rs[0] = r;
		rs[1] = k;
		return rs;
	}
}
