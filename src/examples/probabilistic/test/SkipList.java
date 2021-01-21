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

package probabilistic.test;

/**
 * Manipulates a skip list.
 * 
 * @author Xin Zhang
 */
public class SkipList {
	
	/**
	 * Creates a skip list of the given capacity and adds 
	 * the integers capacity, capacity - 1, ..., 1 to the
	 * skip list.
	 * 
	 * @param args[0] the capacity of the skip list
	 */
    public static void main(String args[]){
        int capacity = Integer.valueOf(args[0]);
        probabilistic.examples.SkipList<Integer> skipList = new probabilistic.examples.SkipList<Integer>();
        for (int i = capacity; i > 0; i --) {
            skipList.addFirst(i);
        }
    }
}
