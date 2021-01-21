/*
 * Copyright (C) 2020  Franck van Breugel and Yash Dhamija
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

//import java.util.Arrays;
//import org.junit.Assert;

import probabilistic.UniformChoice;

/*
 * Finds the Kth element in an array.  Implements algorithms described in
 * C.A.R. Hoare.  Algorithm 63: Partition and Algorithm 65: Find.  Communications of the ACM, 4(7): 321-322, July 1961.
 * This class uses Find class, and is invoked by jpf.
 * 
 * @author Franck van Breugel
 * @author Yash Dhamija
 */
public class QuickSelect {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: <num of trials> <elemenst of array>");
			System.exit(0);
		}
		int trials = Integer.parseInt(args[0]);
		double[] arr = new double[args.length-1];
		
		for(int a = 1; a < args.length; a++)
			arr[a-1] = Double.parseDouble(args[a]);
		
		int M = 0; 
		int N = arr.length - 1;
		
		for (int i = 0; i < trials; i++) {
			probabilistic.examples.QuickSelect.init(arr, M, N);
			int index = UniformChoice.make(arr.length); //pick a random index for fing operation
			probabilistic.examples.QuickSelect.find(index);
			
//			double actual = (int) arr[index];
//			Arrays.sort(arr);
//			double expected = (int) arr[index];
//			if (expected == actual)
//				System.out.println("true  ");
		}
		
	}
}
