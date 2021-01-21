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

import probabilistic.UniformChoice;

/**
 * A class to balance the load among servers, without storing any additional
 * information, such as job requests and their process times. Expected load per
 * server is (total load) / (number of servers).
 * 
 * @author Zainab Fatmi
 */
public class RandomizedLoadBalancing {

	private int servers;

	/**
	 * Initializes the s servers, with ID numbers 0 to s-1.
	 * 
	 * @param s the amount of servers.
	 */
	public RandomizedLoadBalancing(int s) {
		this.servers = s;
	}

	/**
	 * Determine which server the job request should be assigned to.
	 * 
	 * @return the ID number of the server to process the job request.
	 */
	public int serverID() {
		return UniformChoice.make(servers);
	}
}
