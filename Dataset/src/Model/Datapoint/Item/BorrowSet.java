/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Datapoint.Item;

import Model.Dataset.Dataset_Constants;
import java.io.Serializable;

/**
 *
 * @author kenna
 * @param <DataType>
 */
public class BorrowSet<DataType> implements Serializable {
    
    // Array of book IDs treated as set
    private Object[] borrowing;
    private int next, size;
    
    
    /**
     * Empty constructor allows set to be built
     */
    public BorrowSet() {
        this.borrowing = new Object[Dataset_Constants.MAX_BORROW];
        this.next = 0;
        this.size = 0;
    }
    
    
    /**
     * Rebuild a set
     * 
     * @param borrowing 
     */
    public BorrowSet(int[] borrowing) {
        this.borrowing = new Object[Dataset_Constants.MAX_BORROW];
        int counter = 0;
        for( Object i : borrowing) {
            this.borrowing[counter] = i;
            this.next++;
            this.size++;
        }
    }
    
    /**
     * Build set from reading a string
     * 
     * @param borrowString
     * @param delim 
     */
    public BorrowSet(String borrowString, String delim) {
        this.borrowing = new Object[Dataset_Constants.MAX_BORROW];
        int counter = 0;
        for(String i : borrowString.split(delim)) {
            borrowing[counter] = Integer.parseInt(i);
            this.next++;
            this.size++;
        }
    }

    /**
     * Set books student is borrowing
     * 
     * @param borrowing 
     */
    public void setBorrowing(Object[] borrowing) {
        this.borrowing = borrowing;
    }
    
    
    /**
     * Get array of book IDs student is borrowing
     * 
     * @return String[Book ID] 
     */
    public Object[] getBorrowing() {
        return this.borrowing;
    }
    
    /**
     * Check if object exists in object set
     * 
     * @param item
     * @return 
     */
    public boolean hasItem(Object item) {
        for(Object i : borrowing) {
            if ( item.equals(i) ) {
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * Attempt to add item to set, otherwise return false
     * 
     * @param item
     * @return boolean
     */
    public boolean addItem(Object item) {
        
        // Skip if array is full or item already in set
        if ( !canAdd(item) ) {
            return false;
        }
        
        // Otherwise add
        borrowing[ this.next ] = item;
        this.next++;
        this.size++;
        return true;
    }
    
    
    /**
     * Drop item from set
     * 
     * @param item
     * @return 
     */
    public boolean dropItem(Object item) {
        
        // Initalize counter and scan array
        int counter = 0;
        for(Object i : borrowing) {
            if ( item.equals(i) ) {
                
                // Drop item
                borrowing[counter] = null;
                this.next--;
                this.size--;
                
                
                // Return state
                resolvePositions();
                return true;
            }
        }
        
        // Return state
        return false;
    }
    
    
    /**
     * Method to check if item can be added to set
     * 
     * @param item
     * @return boolean
     */
    public boolean canAdd(Object item) {
        
        // Skip if array size is breached
        if (this.next >= borrowing.length | hasItem(item)) {
            return false;
        }
        
        // Otherwise can add
        else {
            return true;
        }
    }
    
    /**
     * Reset complete list
     */
    public void clearItems() {
        this.borrowing = new Object[Dataset_Constants.MAX_BORROW];
    }

    
    /**
     * Check whether space in queue
     * 
     * @return boolean
     */
    public boolean hasSpace() {
        return this.next >= borrowing.length;
    }
    
    
    /**
     * Method to resolve null positions
     * 
     */
    private void resolvePositions() {
        
        // Initalize counter and new array
        int counter = 0;
        Object[] newBorrowing = new Object[Dataset_Constants.MAX_BORROW];
        
        // Copy non-null values into new array
        for ( Object i : borrowing ) {
            if ( i != null ) {
                newBorrowing[counter] = i;
                this.next++;
                this.size++;
            }
        }
        
        // Update array
        this.borrowing = newBorrowing;
    }
    
    
    /**
     * Return whether the set is empty
     * 
     * @return boolean
     */
    public boolean isEmpty() {
        return this.size < this.borrowing.length;
    }
    
    /**
     * Return borrowing as a string
     * @return 
     */
    @Override
    public String toString(){
        
        if (this.borrowing != null) {
            String output = "\"";
            for (Object i : this.borrowing ) {
                output += i + ";";
            }
            output += " \"";
            output = output.replace("; ", "").replace(" ", "");
            return output;
        }
        else {
            return "";
        }
        
    }
}
