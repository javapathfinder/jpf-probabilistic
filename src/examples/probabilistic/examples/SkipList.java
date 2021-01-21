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

import java.util.ArrayList;
import java.util.List;

import probabilistic.Coin;

/**
 * this is a SIMULATED version of real SkipList
 * the purpose of this class is to give a test of randomized
 * algorithm.
 * how it works:
 *  whenever a new item is added, a new node will be created,
 *  the new node will be put to the this.header of the linked list,
 *  then a coin will be flipped, if it is true, a new link to
 *  to another node which is double length awar will be added.
 *  this process will continue until either reach the end of
 *  the linked list or coin returns false
 *  
 * @author Xin Zhang
 */
public class SkipList<T> {
	
    private ListNode<T> header;
    private int size;
    
    /**
     * Initializes this skip list to be empty.
     */
    public SkipList(){
         this.header = null;
         this.size = 0;
    }

    /**
     * Represents a node of a skip list.
     * 
     * @param <T> type of the this.data stored in the node
     */
    private static class ListNode<T> {
        private T data;
        private List<ListNode<T>> links;

        /**
         * Initializes this node with the given data and no links.
         * 
         * @param data the data of this node
         */
        private ListNode(T data){
            this.data = data;
            this.links = new ArrayList<ListNode<T>>(1);
        }

        /**
         * Adds the given link to this node.
         * 
         * @param link the link to be added
         */
        private void addLink(ListNode<T> link){
            this.links.add(link);
        }

        /**
         * Returns the links of this node.
         * 
         * @return the links of this node
         */
        private List<ListNode<T>> getLinks(){
            return this.links;
        }
    }

    /**
     * Returns the node of this skip list with the given index.
     * 
     * @param index the index of the node
     * @return the node of this skip list with the given index
     */
    private ListNode<T> getNodeByIndex(int index) {
        ListNode<T> current = this.header;
        int cursor = 0;
        while (current != null && cursor < index) {
            List<ListNode<T>> links = current.getLinks();
            for (int i = links.size() - 1; i >= 0; i--) {
                int power = new Double(Math.pow(2, i)).intValue();
                if (power < cursor){
                    current = links.get(i);
                    cursor = power + cursor;
                    break;
                }

            }
        }
        return current;
    }

    /**
     * Adds the given data to this skip list.
     * 
     * @param data the data to be added
     * @pre. data != null
     */
    public void addFirst(T data) {
        ListNode<T> node = new ListNode<T>(data);
        this.size++;
        node.addLink(this.header);
        this.header = node;
        int index = 1;
        while (Coin.flip() == 1 && index < this.size / 2){
            index = index * 2;
            node.addLink(this.getNodeByIndex(index));
        }
    }


    /*
     * Test method
     */
    public static void main(String args[]){
        int cap = Integer.valueOf(args[0]);
        SkipList<Integer> skip = new SkipList<Integer>();
        for (int i = cap; i > 0; i --)
            skip.addFirst(i);
    }
}
