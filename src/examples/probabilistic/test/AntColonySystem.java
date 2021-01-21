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
 * An invocation of the Ant Colony System (cooperative learning) method for solving the
 * the Traveling Salesman Problem.
 * 
 * @author Yash Dhamija
 */
public class AntColonySystem {

	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Usage: AntColonySystem <int: number of cycles/tours> <int: number of cities> <double: coordinates of cities>");
			System.exit(0);
		}
		
		int n = Integer.parseInt(args[1]);
		
		if (args.length != 2*n+2) {
			System.out.println("Invalid amount of inputs");
			System.exit(0);
		}	

		double[] x = new double[n];
		double[] y = new double[n];
		
		int xi = 0; int yi = 0;
		for (int i = 2; i < args.length; i++) {
			x[xi++] = Double.parseDouble(args[i++]);
			y[yi++] = Double.parseDouble(args[i]);
		}

		double[][] sigma = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < i; j++) {
				double X = Math.pow(x[i] - x[j], 2);
				double Y = Math.pow(y[i] - y[j], 2);
				sigma[i][j] = Math.sqrt(X + Y);
				sigma[j][i] = sigma[i][j];
			}
		}
		
		probabilistic.examples.AntColonySystem colony = new probabilistic.examples.AntColonySystem(sigma, Integer.parseInt(args[0]));
		int[] path = colony.getOptimalPath();

//		for (int i = 0; i < path.length; i++) {
//		 	System.out.println(path[i]);
//		}
	}
}
