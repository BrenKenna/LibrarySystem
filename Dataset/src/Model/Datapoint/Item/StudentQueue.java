/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Datapoint.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Adapter to treat an array list as a queue. Specifically used in this 
 * system for list of integers from student IDs.
 * 
 * @author kenna
 * @param <DataType>
 */
public class StudentQueue<DataType> implements Serializable {
    
    
    // Queue list
    private final List<Object> queue;
    
    
    /**
     * Empty constructor sets array list
     */
    public StudentQueue(){
        this.queue = new ArrayList<>();
    }
    
    
    /**
     * Add a new element to back of queue
     * 
     * @param item 
     */
    public void offer(Object item) {
        queue.add(item);
    }
    
    
    /**
     * Retrieve & remove first element in queue
     * 
     * @return Object-First element
     */
    public Object poll() {
        if ( queue.size() > 0 ) {
            return queue.remove(0);
        }
        else {
            return null;
        }
    }
    
    
    /**
     * View first element in queue, without removal
     * 
     * @return Object-First element
     */
    public Object peakFirst() {
        if ( queue.size() > 0 ) {
            return queue.get(0);
        }
        else {
            return null;
        }
    }
    
    
    /**
     * View last element in queue, without removal
     * 
     * @return Object-Last element
     */
    public Object peakLast() {
        if ( queue.size() > 0 ) {
            return queue.get( queue.size()-1 );
        }
        else {
            return null;
        }
    }
    
    
    /**
     * Check whether queue has queried object
     * 
     * @param item
     * @return boolean
     */
    public boolean hasItem(Object item) {
        return queue.indexOf(item) > -1;
    }
    
    
    /**
     * Remove item from queue if present
     * 
     * @param item 
     */
    public void dropItem(Object item) {
        int index = queue.indexOf(item);
        if ( index > -1 ) {
            queue.remove(index);
        }
    }
    
    
    /**
     * Return whether queue is empty
     * 
     * @return boolean 
     */
    public boolean isEmpty() {
        return queue.size() < 1;
    }
    
    
    /**
     * Return size of queue
     * 
     * @return int
     */
    public int size() {
        return queue.size();
    }
    
    
    /**
     * Get queue
     * 
     * @return queue 
     */
    public List<Object> getQueue() {
        return this.queue;
    }
    
    
    /**
     * Check whether queried input is next in queue
     * 
     * @param query
     * @return boolean
     */
    public boolean isNext(Object query) {
        Object nextItem = peakFirst();
        if ( nextItem != null) {
            return nextItem.equals(query);
        }
        else {
            return false;
        }
    }
}
