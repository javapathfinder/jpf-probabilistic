/*
 * Copyright (C) 2020  Yash Dhamija and Xiang Chen
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
 * This app balances the load among servers.
 * 
 * @author Yash Dhamija
 * @author Xiang Chen
 */
public class RandomizedLoadBalancing {

	/*
	 * This class calls LoadBalancingServers class.
	 * 
	 * @param Number of serves, total number of requests, max time for any job
	 */

	private static boolean isBalanced = true;

	public static void main(String[] args) {
		// if (args.length != 4) {
		// System.out.println("Usage: <num servers> <num processes> <max time for a
		// process> <range to test if is balanced>");
		// System.exit(0);
		// }

		int servers = Integer.parseInt(args[0]);
		int requests = Integer.parseInt(args[1]); // number of job requests
		// int time = Integer.parseInt(args[2]); // maximum amount of time to process a
		// job request
		int range = Integer.parseInt(args[2]); // range to test if is balanced

		int[] time = new int[requests];

		for (int r = 0; r < requests; r++) {
			time[r] = Integer.parseInt(args[r + 3]);
		}

		// Map<Integer, Integer> jobs = new HashMap<Integer, Integer>(); // request ID
		// -> process time

		// for (int i = 0; i < requests; i++) {
		// int t = UniformChoice.make(time) + 1;
		// jobs.put(i, t);
		// }

		probabilistic.examples.RandomizedLoadBalancing lb = new probabilistic.examples.RandomizedLoadBalancing(servers);

		int[] load = new int[servers]; // load per server

		int totalLoad = 0; // total load

		for (int r = 0; r < requests; r++) {
			load[lb.serverID()] += time[r]; // assign jobs to servers
			totalLoad += time[r];
		}

		int expectedLoad = totalLoad / servers; // expected load = (total load) / (number of servers)

		// test if it's balanced
		for (int i = 0; i < load.length; i++) {
			isBalanced = isBalanced && Math.abs(load[i] - expectedLoad) <= range;
		}
	}
}
