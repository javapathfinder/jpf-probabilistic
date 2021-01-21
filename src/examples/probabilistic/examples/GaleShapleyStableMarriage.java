/*
 * Copyright (C) 2010  Xin Zhang
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

/**
 * Verifies that the stable marriage problem can be solved
 * by using proposal algorithm.
 * 
 * @author Xin Zhang
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import probabilistic.Choice;
import probabilistic.UniformChoice;

public class GaleShapleyStableMarriage<T>{
	private List<Person<T>> men;
	private List<Person<T>> women;

	private static int couples;

	private static class Person<T>{
		private T id;
		private List<Person<T>> preferenceList;
		private Person<T> partner;

		/**
		 * 
		 * @param id
		 */
		public Person(T id){
			this.id = id;
			this.preferenceList = new ArrayList<Person<T>>();
		}

		/**
		 * 
		 * @param prefers
		 */
		public void addPreferences(List<Person<T>> prefers){
			for (Person<T> prefer: prefers) {
				this.addPreference(prefer);
			}
		}

		/**
		 * 
		 * @param prefer
		 */
		public void addPreference(Person<T> prefer){
			if (this.preferenceList.size() < couples) {
				this.preferenceList.add(prefer);
			}
		}

		/**
		 * 
		 * @return
		 */
		public boolean isMarried(){
			return this.partner != null;
		}

		/**
		 * 
		 */
		public void divorce(){
			if (this.partner != null) {
				this.partner.partner = null;
				this.partner = null;
			}
		}

		/**
		 * 
		 * @param proposer
		 * @return
		 */
		public boolean acceptProposal(Person<T> proposer){
			if (this.partner == null){
				this.partner = proposer;
				return true;
			} else {
				int oldIndex = this.preferenceList.indexOf(this.partner);
				int newIndex = this.preferenceList.indexOf(proposer);
				if (oldIndex > newIndex) {
					this.divorce();
					this.partner = proposer;
					return true;
				} else {
					return false;
				}
			}
		}

		/**
		 * 
		 */
		public String toString(){
			return id.toString();
		}
	}

	/**
	 * 
	 * @param couples
	 */
	public GaleShapleyStableMarriage(int couples){
		GaleShapleyStableMarriage.couples = couples;
		this.men = new ArrayList<Person<T>>();
		this.women = new ArrayList<Person<T>>();
	}

	/**
	 * 
	 * @param newPerson
	 */
	public void addMan(Person<T> newPerson){
		if (this.men.size() < couples)
			this.men.add(newPerson);
	}

	/**
	 * 
	 * @param newPerson
	 */
	public void addWoman(Person<T> newPerson){
		if (this.women.size() < couples)
			this.women.add(newPerson);
	}

	/**
	 * Terminates when all men and women are married.
	 */
	public void match(){
		boolean allMarried = false;
		while (!allMarried) {
			allMarried = true;
			for (Person<T> man : this.men){
				if (man.isMarried()){
					continue;
				}
				// if not married, then there is possibility to let a couple divorce
				allMarried = false;
				for(int index = 0; index < couples && !man.isMarried(); index++) {
					Person<T> woman = man.preferenceList.get(index);
					if (woman.acceptProposal(man)) {
						man.partner = woman;
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	private Person<T> getWoman(int index){
		if (index >= this.women.size()) {
			return null;
		} else {
			return this.women.get(index);
		}
	}

	/**
	 * 
	 * @return
	 */
	public Iterable<Person<T>> getMen(){
		return this.men;
	}

	/**
	 * 
	 * @return
	 */
	public Iterable<Person<T>> getWomen(){
		return this.women;
	}

	/**
	 * 
	 * 
	 * @param couples
	 */
	public static void verify(int couples){
		GaleShapleyStableMarriage<String> marriage = new GaleShapleyStableMarriage<String>(couples);
		for (int c = 0; c < couples; c++) {
			Person<String> man = new Person<String>("M" + c);
			marriage.addMan(man);
			Person<String> woman = new Person<String>("W" + c);
			marriage.addWoman(woman);
		}

		// create a random prefer list for each man
		for (Person<String> man : marriage.getMen()) {
			List<Person<String>> preferList = new ArrayList<Person<String>>();
			while (preferList.size() < couples){
				Person<String> woman = marriage.getWoman(UniformChoice.make(couples));
				if (woman != null && !preferList.contains(woman)) {
					preferList.add(woman);
				}
			}
			man.addPreferences(preferList);
		}

		for (Person<String> woman : marriage.getWomen()) {
			for (Person<String> man : marriage.getMen()) {
				woman.addPreference(man);
			}
		}
		
		marriage.match();
	}
}
