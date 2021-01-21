/*
 * Copyright (C) 2020  Yash Dhamija
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

package probabilistic.test;

/**
 * This class processes the argument to be sent to ByzantineAgreement
 * 
 * @author Yash Dhamija
 */
public class ByzantineAgreement {

	/**
	 * @param args initial values of processors in {0,1}, and first element is total number of processors and
	 * the second element is the number of faulty processors
	 */
	public static void main (String[] args) {
		if (args.length < 3 || args.length != Integer.parseInt(args[0]) + 2) {
			System.out.println("Usage: ByzantineAgreement <total no. of processors> <no. of faulty processors> [0,1]{# processors}");
		}
		
		int n = Integer.parseInt(args[0]);
		int t = Integer.parseInt(args[1]);		
		int[] arr = new int[n];
		for(int i = 2; i < args.length; i++)
			arr[i-2] = Integer.parseInt(args[i]);
		
			probabilistic.examples.ByzantineAgreement protocol = new probabilistic.examples.ByzantineAgreement(n, t, arr);
		protocol.execute();	
	}
}
