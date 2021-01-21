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
import java.util.HashMap;

import probabilistic.Choice;
import probabilistic.UniformChoice;

/**
 * An implementation of the Ant Colony System (cooperative learning) approach to
 * the Traveling Salesman Problem.
 * 
 * @author Zainab Fatmi
 */
public class AntColonySystem {

	/** Number of cycles */
	private int c;
	/** Number of cities/nodes */
	private int n;
	/** Number of ants */
	private int m;
	/** Ants in the colony system */
	private Ant[] ants;
	/** The intensity of the pheromone trail (memory or past experience) */
	private double[][] tau;
	/** The distances or costs between cities (greedy heuristic) */
	private double[][] sigma;
	/** The relative importance of pheromone versus distance */
	private double beta;
	/** The relative importance of exploitation versus exploration */
	private double q0;
	/** Pheromone decay parameter; (1-a) is the evaporation after an iteration */
	private double alpha;
	/** Pheromone diminishing parameter for visited edges */
	private double rho = 0.1;
	/** Initial pheromone level */
	private double tau0;

	private class Ant {

		/** Current partial solution */
		public ArrayList<Integer> visitedCities;
		/** Current partial solution */
		public ArrayList<Integer> remainingCities;
		/** Current length of the tour */
		public double tourLength;
		/** Initial city */
		private int k;

		/**
		 * Initializes the ant at the starting city, k.
		 * 
		 * @param k the starting city
		 */
		public Ant(int k) {
			this.k = k;
			reset();
		}

		/**
		 * Resets the path of the ant and the ant returns to its starting city.
		 */
		public void reset() {
			visitedCities = new ArrayList<Integer>();
			visitedCities.add(k);
			remainingCities = new ArrayList<Integer>();
			for (int i = 0; i < n; i++) {
				if (i != k) {
					remainingCities.add(i);
				}
			}
			tourLength = 0;
		}
	}

	/**
	 * Initializes the distances between the cities and the maximum number of
	 * tours/cycles to complete.
	 * 
	 * @param s a square matrix containing the distances between the cities.
	 * @param c the number of tours/cycles
	 */
	public AntColonySystem(double[][] s, int c) {
		this.sigma = s; // 1/sigma is the visibility
		this.n = sigma.length;
		this.c = c;
		double Ln = nearNeighbour();
		this.tau0 = 1 / (n * Ln);
		this.tau = new double[n][n];

		// ACS Parameter Settings:
		this.q0 = 0.9; // 0 <= q0 <= 1
		this.rho = 0.1; // 0 < rho < 1
		this.alpha = 0.1; // 0 < alpha < 1
		this.beta = 2; // beta > 0
		this.m = 10; // m > 0
		ants = new Ant[m];
	}

	/**
	 * Returns a near-optimal path for the Traveling Salesman Problem, and prints
	 * the total distance of the path.
	 * 
	 * @return a near-optimal path
	 */
	public int[] getOptimalPath() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < i; j++) {
				// initialize pheromone level
				tau[i][j] = tau0;
				tau[j][i] = tau0;
			}
		}
		for (int a = 0; a < m; a++) {
			// position each ant on a starting node
			ants[a] = new Ant(UniformChoice.make(n));
		}

		double d = Double.MAX_VALUE;
		int[] path = new int[n + 1];
		for (int i = 0; i < c; i++) { // iteration
			for (int a = 0; a < m; a++) { // reset tours
				ants[a].reset();
			}
			for (int j = 0; j < n; j++) { // each ant builds a feasible solution
				for (int k = 0; k < m; k++) {
					transition(ants[k]); // step
				}
				for (int k = 0; k < m; k++) {
					localUpdate(ants[k]); // step
				}
			}
			Ant a = getMinPath();
			if (a.tourLength < d) {
				d = a.tourLength;
				for (int p = 0; p < n + 1; p++) {
					path[p] = a.visitedCities.get(p);
				}
			}
			globalUpdate(path, d);
		}
//		System.out.println("Distance = " + d);
		return path;
	}

	/**
	 * Moves the ant to the next city in its tour, using the ACS state transition
	 * rule.
	 * 
	 * @param a the ant
	 */
	private void transition(Ant a) {
		int r = a.visitedCities.get(a.visitedCities.size() - 1); // current city
		int s; // next city

		if (a.remainingCities.isEmpty()) {
			s = a.visitedCities.get(0);
		} else {
			double[] p = probabilities(a);
//			double q = random.nextDouble();
			double[] choices = { q0, 1 - q0 };
			double q = Choice.make(choices);
			if (q == 0) { // exploitation
				int index = getMax(p);
				s = a.remainingCities.remove(index);
			} else { // biased exploration
//				q = Choice.make(new double[] {p[index], 1-p[index]});
//				for (int i = 1; i < p.length; i++) {
//					p[i] += p[i - 1];
//				}
//				int index = 0;
//				q = Choice.make(new double[] {p[index], 1-p[index]});
//				while (q >= p[index]) { // && i < p.length - 1
//					index++;
//				}
				int index = Choice.make(p);
				s = a.remainingCities.remove(index); // next city
			}
		}
		// System.out.println("r = " + r + "s = " + s);
		a.visitedCities.add(s);
		a.tourLength += sigma[r][s];
	}

	/**
	 * Returns the probabilities with which the ant will choose to move to each
	 * remaining/allowed city.
	 * 
	 * @param a the ant
	 * @return an array of probabilities corresponding to the list of remaining
	 *         cities
	 */
	private double[] probabilities(Ant a) {
		int l = a.remainingCities.size(); // amount of allowed cities
		double sum = 0;
		double[] result = new double[l];

		int r = a.visitedCities.get(a.visitedCities.size() - 1); // current city
		for (int i = 0; i < l; i++) { // all possible cities
			int s = a.remainingCities.get(i);
			result[i] = tau[r][s] * Math.pow(1 / sigma[r][s], beta); // probability of going to city s
			sum += result[i];
		}
		for (int i = 0; i < l; i++) {
			result[i] /= sum;
		}
		return result;
	}

	/**
	 * Updates the local pheromones on the last edge that the ant traversed,
	 * according to the ACS local updating rule.
	 * 
	 * @param a the ant
	 */
	private void localUpdate(Ant a) {
		int size = a.visitedCities.size();
		int r = a.visitedCities.get(size - 2);
		int s = a.visitedCities.get(size - 1);
		tau[r][s] = ((1 - rho) * tau[r][s]) + (rho * tau0);
		tau[s][r] = tau[r][s];
	}

	/**
	 * Updates the pheromones on all edges of the current optimal path, according to
	 * the ACS global updating rule.
	 * 
	 * @param path   the current optimal path
	 * @param length the total distance of the current optimal path
	 */
	private void globalUpdate(int[] path, double length) {
		HashMap<Integer, Integer> edges = new HashMap<Integer, Integer>();
		for (int i = 0; i < n; i++) {
			edges.put(path[i], path[i + 1]);
		}

		double L = 1 / length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < i; j++) {
				// for each edge update the pheromone trail
				int delta = 0;
				if (edges.get(i) == j || edges.get(j) == i) {
					delta += L;
				}
				tau[i][j] = ((1 - alpha) * tau[i][j]) + (alpha * delta);
				tau[j][i] = tau[i][j];
			}
		}
	}

	/**
	 * Get the index of the element with the maximum value in an array.
	 * 
	 * @param array an array of double values
	 * @return the index of the maximum element in the array
	 */
	private int getMax(double[] array) {
		int index = 0;
		double max = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
				index = i;
			}
		}
		return index;
	}

	/**
	 * Get the ant with the minimum path length in the current iteration.
	 * 
	 * @return the ant with the minimum path length (in the current iteration)
	 */
	private Ant getMinPath() {
		double[] lengths = new double[m];
		for (int k = 0; k < m; k++) {
			lengths[k] = ants[k].tourLength;
		}
		int index = 0;
		double min = lengths[0];
		for (int i = 1; i < lengths.length; i++) {
			if (lengths[i] < min) {
				min = lengths[i];
				index = i;
			}
		}
		return ants[index];
	}

	/**
	 * Uses the "nearest neighbour heuristic" to compute the predicted tour length.
	 * 
	 * @return the tour length produced by the nearest neighbour heuristic.
	 */
	private double nearNeighbour() {
		// start at a random node
		int k = UniformChoice.make(n);
		ArrayList<Integer> visitedCities = new ArrayList<Integer>();
		visitedCities.add(k);
		ArrayList<Integer> remainingCities = new ArrayList<Integer>();
		remainingCities = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			if (i != k) {
				remainingCities.add(i);
			}
		}
		double tourLength = 0;
		int currentCity = k;
		int nextCity = k;

		// keep adding edges until path is complete
		for (int i = 0; i < n - 1; i++) {
			// find closest node
			double min = Double.MAX_VALUE;
			for (int j : remainingCities) {
				if (sigma[currentCity][j] < min) {
					nextCity = j;
					min = sigma[currentCity][j];
				}
			}
			visitedCities.add(nextCity);
			remainingCities.remove(remainingCities.indexOf(nextCity));
			tourLength += sigma[currentCity][nextCity];
			currentCity = nextCity;
		}
		tourLength += sigma[currentCity][k];

		return tourLength;
	}
}
