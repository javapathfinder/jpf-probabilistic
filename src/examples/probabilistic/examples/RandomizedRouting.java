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

import java.util.ArrayList;
import java.util.List;

import probabilistic.UniformChoice;

/**
 * This is an implementation of the randomized permutation routing algorithm as described in 4.2 of Algorithms
 * by Motwani and Raghavan. Given an n-dimensional boolean hypercube, each node is the destination for exactly 
 * one of the 2^n "packets" being sent.
 * 
 * @author Maeve Wildes
 */
public class RandomizedRouting {
	
	/**
	 * Represents a packet.
	 */
	private static class Packet {
		private int curPosition;
		private int nextPosition;
		private int destination;
		private int intermediate;
		private boolean stepCompleted;
		private int edgeChosen;
		
		public Packet(int source, int destination, int intermediate) {
			this.curPosition = source;
			this.destination = destination;
			this.intermediate = intermediate;
			this.stepCompleted = false;
		}
		
		public void setStepCompleted(boolean b) {
			this.stepCompleted = b;
		}
		
		public boolean getStepCompleted() {
			return this.stepCompleted;
		}
		
		public void setEdgeChosen(int edge) {
			this.edgeChosen = edge;
		}
		
		public int getEdgeChosen() {
			return this.edgeChosen;
		}
		
		public void setCurPosition(int cur) {
			this.curPosition= cur;
		}
		
		public void setNextPosition(int next) {
			this.nextPosition = next;
		}
		
		public int getIntermediate() {
			return this.intermediate;
		}
		
		public int getCurPosition() {
			return this.curPosition;
		}
		
		public int getNextPosition() {
			return this.nextPosition;
		}
		
		public int getDest() {
			return this.destination;
		}
	}
	
	/**
	 * 
	 * @param n the dimension
	 * @param perm an array of length 2^n showing the permutation
	 * 
	 * eg. n=4, perm =  {0b0011, 0b0000, 0b1000, 0b1100, 0b1111, 0b0010, 0b0001, 0b1110, 0b1010, 0b0101, 0b0111, 0b0100, 0b0110, 0b1001, 0b1011, 0b1101};
	 */
	public static List<Packet> main (int n, int [] perm) {
		int N = (int) Math.pow(2, n); // number of nodes, 2^n
		List<Packet> packets = new ArrayList<Packet>(N);
		for (int i = 0; i < N;  i++) {
			packets.add(i,(new Packet(i, perm[i], UniformChoice.make(N))));
			// create a package with given source and destination, and with a randomly chosen intermediate destination
		}
		
		List<List<List<Packet>>> queues = new ArrayList<List<List<Packet>>>(N); // arraylist of arraylists, each edge has one queue, there are n*N edges
		for (int i = 0; i < N; i++) {
			queues.add(i, (new ArrayList<List<Packet>>(n)));
			for (int j = 0; j < n; j++) {
				queues.get(i).add(j, new ArrayList<Packet>());
			}
		}

		while (!isFinished(packets)) {
			for (int i=0; i<N; i++) {
				packets.get(i).setStepCompleted(false);
			}
			for (int i=0; i<N; i++) {
				takeStep(i, packets, n, queues);
			}
			for (int i=0; i<N; i++) {
				setQueues(packets, queues, i);
			}
		}

		// clear the queues
		for (int i=0; i<N; i++) {
			for (int j=0; j<n; j++) {
				queues.get(i).get(j).clear();
			}
		}

		while (!isFinishedP2(packets)) {
			for (int i=0; i<N; i++) {
				packets.get(i).setStepCompleted(false);
			}
			for (int i=0; i<N; i++) {
				takeStepP2(i, packets, n, queues);
			}
			for (int i=0; i<N; i++) {
				setQueues(packets, queues, i);
			}
		}

		return packets;
	}
	
	/**
	 * 
	 * @param i
	 * @param packets
	 * @param n
	 * @param queues
	 */
	private static void takeStep(int i, List<Packet> packets, int n, List<List<List<Packet>>> queues ) {
		for (int j = 0; j < n; j++) {
			if (((packets.get(i).getCurPosition() ^ packets.get(i).getIntermediate()) >> (n - 1 - j) )!= 0) {
				// then we have found the edge to send the package along, ie. the bit to switch
				packets.get(i).setNextPosition(packets.get(i).getCurPosition() ^(1 << (n - 1 - j)));
				packets.get(i).setEdgeChosen(j);
				if (!(queues.get(packets.get(i).getCurPosition()).get(j).contains(packets.get(i)))) {
					queues.get(packets.get(i).getCurPosition()).get(j).add(packets.get(i));
				}
				return;
			}
		}
		packets.get(i).setNextPosition(packets.get(i).getCurPosition()); // if it doesn't want to change positions; it has reached its destination
		packets.get(i).setStepCompleted(true);
		return;	
	}
	
	/**
	 * 
	 * @param i
	 * @param packets
	 * @param n
	 * @param queues
	 */
	private static void takeStepP2(int i, List<Packet> packets, int n, List<List<List<Packet>>> queues) {
		for (int j = 0; j < n; j++) {
			if (((packets.get(i).getCurPosition() ^ packets.get(i).getDest()) >> (n - 1 - j) )!= 0) {
				// then we have found the edge to send the package along, ie. the bit to switch
				packets.get(i).setNextPosition(packets.get(i).getCurPosition() ^ (1 << (n - 1 - j)));
				packets.get(i).setEdgeChosen(j);
				if (!(queues.get(packets.get(i).getCurPosition()).get(j).contains(packets.get(i)))) {
					queues.get(packets.get(i).getCurPosition()).get(j).add(packets.get(i));
				}
				return;
			}
		}
		packets.get(i).setNextPosition(packets.get(i).getCurPosition()); // if it doesn't want to change positions
		packets.get(i).setEdgeChosen(n + 1); //signifies that it has reached its destination
		packets.get(i).setStepCompleted(true);
		return;
	}
	
	/**
	 * 
	 * @param packets
	 * @param queues
	 * @param i
	 */
	private static void setQueues(List<Packet> packets, List<List<List<Packet>>> queues, int i) {
		if (!(packets.get(i).getStepCompleted())) {
			if (queues.get(packets.get(i).getCurPosition()).get(packets.get(i).getEdgeChosen()).size() == 1) {
				// if there's nothing else in the queue, move ahead
				queues.get(packets.get(i).getCurPosition()).get(packets.get(i).getEdgeChosen()).remove(0);
				packets.get(i).setCurPosition(packets.get(i).getNextPosition());
				packets.get(i).setStepCompleted(true);
			}
			else if (queues.get(packets.get(i).getCurPosition()).get(packets.get(i).getEdgeChosen()).size() > 1) {
				// move one forward, delete it from the queue, leave the other one(s) in the queue
				Packet p = queues.get(packets.get(i).getCurPosition()).get(packets.get(i).getEdgeChosen()).get(0); // choose this one to go through, the other remains in the queue
				for (int j = 0; j < queues.get(packets.get(i).getCurPosition()).get(packets.get(i).getEdgeChosen()).size(); j++) {
					queues.get(packets.get(i).getCurPosition()).get(packets.get(i).getEdgeChosen()).get(j).setStepCompleted(true);
				}
				queues.get(packets.get(i).getCurPosition()).get(packets.get(i).getEdgeChosen()).remove(p); // remove from queue
				p.setCurPosition(p.getNextPosition()); // move this one forward(FIFO)		
			}
		}
	}
	
	/**
	 * 
	 * @param packets
	 * @return
	 */
	private static boolean isFinished(List<Packet> packets) {
		for (int i = 0; i < packets.size(); i++) {
			if (packets.get(i).getCurPosition() != packets.get(i).getIntermediate()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param packets
	 * @return
	 */
	private static boolean isFinishedP2(List<Packet> packets) {
		for (int i = 0; i < packets.size(); i++) {
			if (packets.get(i).getCurPosition() != packets.get(i).getDest()) {
				return false;
			}
		}
		return true;
	}
}
