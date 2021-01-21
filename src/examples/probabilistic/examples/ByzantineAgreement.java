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
import java.util.List;

import probabilistic.Coin;
import probabilistic.UniformChoice;

/**
 * The Byzantine Agreement Protocol with n processors (t faulty processors and n
 * - t good processors). The protocol ends with a decision E {0,1} such that all
 * good processors finish with the same decision, and if all good processors
 * began with the same value, v, they finish with a decision = v. This is a
 * Las-Vegas protocol, and the expected number of rounds to reach an agreement
 * is constant, while any deterministic algorithm for agreement in this model
 * requires t + 1 rounds.
 * 
 * @author Zainab Fatmi
 */
public class ByzantineAgreement {

	private int n; // total number of processors
	private int t; // amount of faulty processors
	private int decision; // the agreed upon value, 0 or 1
	private List<Processor> processors;

	/**
	 * Initializes the processors with their values for the first vote.
	 * 
	 * @param n the total number of processors, a multiple of 8.
	 * @param t the number of faulty processors, t <= n/8.
	 * @param b an array holding the initial values (0 or 1) for the processors,
	 *          values for good processors first and faulty processors last.
	 */
	public ByzantineAgreement(int n, int t, int[] b) {
		this.n = n;
		this.t = t;
		processors = new ArrayList<Processor>();
		for (int i = 0; i < n - t; i++) {
			processors.add(new GoodProcessor(b[i]));
		}
		for (int i = n - t; i < n; i++) {
			processors.add(new FaultyProcessor(b[i]));
		}
	}

	/**
	 * Executes the agreement protocol.
	 * 
	 * @return the value of the decision upon agreement, 0 or 1.
	 */
	public int execute() {
		while (!hasEnded()) { // if no agreement yet, begin a round
			// receive votes from all other processors
			for (Processor p : processors) {
				p.recieveVotes(processors);
			}
			// global coin toss
			int flip = Coin.flip();
			boolean coin = (flip == 1) ? true : false;
			// set votes for next round
			for (Processor p : processors) {
				p.setVote(coin);
			}
		}
		return decision;
	}

	/**
	 * Agreement is achieved when every good processor has computed its decision.
	 * consistent with the others.
	 * 
	 * @return true if an agreement has been reached, false otherwise.
	 */
	private boolean hasEnded() {
		boolean done = true;
		decision = processors.get(0).getDecision();
		for (int i = 0; i < n - t; i++) {
			if ((!processors.get(i).hasAgreed()) || processors.get(i).getDecision() != decision) {
				done = false;
			}
		}
		return done;
	}

	// Private classes to store the processors' information:

	/**
	 * Abstract class to model a processor.
	 */
	private abstract class Processor {
		protected int vote; // the processor's current vote, E {0,1}
		protected int decision; // each processor ends the protocol with a decision of 0 or 1
		protected boolean agreed; // has the processor come to a decision yet

		/**
		 * Initialize the value of the first vote of this processor.
		 * 
		 * @param b the value of the vote, 0 or 1.
		 */
		public Processor(int b) {
			this.vote = b;
			this.agreed = false;
		}

		/**
		 * @return the decision made by this processor, 0 or 1.
		 */
		public int getDecision() {
			return decision;
		}

		/**
		 * @return true if this processor has reached a decision yet, false otherwise.
		 */
		public boolean hasAgreed() {
			return agreed;
		}

		/**
		 * @return the vote of the processor for the current round.
		 */
		public abstract int getVote();

		/**
		 * Computes and sets the value for the vote of the processor in the next round.
		 * 
		 * @param coin true if HEADS and false if TAILS.
		 */
		public abstract void setVote(boolean coin);

		/**
		 * Receives the votes from all other processors.
		 * 
		 * @param processors a list containing all processors.
		 */
		public abstract void recieveVotes(List<Processor> processors);
	}

	/**
	 * A class to model good processors. Good processors follow the protocol
	 * exactly.
	 */
	private class GoodProcessor extends Processor {
		private int L;
		private int H;
		private int G;
		private int ones;
		private int zeros;

		/**
		 * Initialize the value of the first vote of this processor.
		 * 
		 * @param b the value of the vote, 0 or 1.
		 */
		public GoodProcessor(int b) {
			super(b);
			this.L = ((5 * n) / 8) + 1; // L >= (n/2) + t + 1
			this.H = ((3 * n) / 4) + 1; // H >= L + t
			this.G = (7 * n) / 8;
		}

		/**
		 * Broadcasts the same vote to all processors.
		 */
		@Override
		public int getVote() {
			return vote;
		}

		@Override
		public void recieveVotes(List<Processor> processors) {
			ones = 0;
			zeros = 0;
			for (Processor p : processors) {
				if (p.getVote() == 1) {
					ones++;
				} else {
					zeros++;
				}
			}
		}

		@Override
		public void setVote(boolean coin) {
			int maj = 0, tally = 0;
			if (ones >= zeros) {
				maj = 1;
				tally = ones;
			} else {
				maj = 0;
				tally = zeros;
			}
			int threshold;
			if (coin) {
				threshold = L;
			} else {
				threshold = H;
			}
			if (tally >= threshold) {
				this.vote = maj;
			} else {
				this.vote = 0;
			}
			if (tally >= G) {
				setDecision(maj);
			}
		}

		/**
		 * Sets the value of the decision permanently.
		 * 
		 * @param d the value of the decision.
		 */
		public void setDecision(int d) {
			if (!agreed) {
				this.decision = d;
				agreed = true;
			}
		}
	}

	/**
	 * An implementation of a faulty processor. This is a simple implementation,
	 * where the processor's votes are random and the processor never reaches a
	 * decision. May be implemented differently to collude with other faulty
	 * processors in an attempt to subvert the agreement process.
	 */
	private class FaultyProcessor extends Processor {
		/**
		 * Initialize this processor.
		 * 
		 * @param b the value of the vote, 0 or 1.
		 */
		public FaultyProcessor(int b) {
			super(b);
		}

		/**
		 * May send arbitrary votes that are totally inconsistent with the protocol, and
		 * may send different votes to different processors.
		 */
		@Override
		public int getVote() {
			vote = UniformChoice.make(2);
			return vote;
		}

		@Override
		public void recieveVotes(List<Processor> processors) {
		}

		@Override
		public void setVote(boolean coin) {
		}
	}
}
