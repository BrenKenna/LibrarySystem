/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Dataset.Index;

import java.util.ArrayList;
import java.util.List;


/**
 * Container for index entries that sortable & searchable
 *  separately to the associated dataset.
 * 
 * @author kenna
 */
public abstract class Index {
    
    // Attributes
    private final List<IndexEntry> index;
    private int lastEntry;
    private final IndexType type;

    
    /**
     * 
     * @param index 
     */
    public Index(List<IndexEntry> index) {
        this.index = index;
        this.type = index.get(0).whichType();
        this.lastEntry = index.size()-1;
    }
    
    
    /**
     * Get the primary keys in current order
     * 
     * @return List-Integers
     */
    public List<Integer> getKeys() {
        List<Integer> keys = new ArrayList<>();
        for ( IndexEntry entry : index ) {
            keys.add( entry.getPrimaryKey() );
        }
        return keys;
    }
    
    
    /**
     * Get all values from the index
     * 
     * @return List Object: String/Int
     */
    public List<Object> getValues() {
    
        List<Object> values = new ArrayList<>();
        for(IndexEntry entry : index) {
            values.add(entry.getEntryValue());
        }
        return values;
    }
    
    /**
     * 
     * @return 
     */
    public IndexType getType(){
        return this.type;
    }
    
    
    /**
     * Scan array swapping elements in ascending order (Bubble sort)
     * 
     * @return true/false
     */
    private boolean scanArrayAscending(){
        
        // Initalize output: Only needs to be changed once
        boolean swapped = false;
        
        // Increment position towards the end
        for( int pos = 0; pos < (this.index.size() - 1); pos++ ) {
            
            // Determine if elements should be swapped
            IndexEntry current, next, holder;
            current = index.get(pos);
            next = index.get(pos+1);
            
            // Swap elements if needs be
            if( current.isBigger(next) ) {
                holder = index.get(pos);
                index.set(pos, next);
                index.set( (pos+1), holder);
                swapped = true;
            }
        }
        
        // Return output
        return swapped;
    }
    
    
    /**
     * Scan array swapping elements in descending order (Bubble sort)
     * 
     * @return true/false 
     */
    private boolean scanArrayDescending(){
        
        // Initalize output: Only needs to be changed once
        boolean swapped = false;
        
        // Increment position towards the end
        for( int pos = 0; pos < (this.index.size() - 1); pos++ ) {
            
            // Determine if elements should be swapped
            IndexEntry current, next, holder;
            current = index.get(pos);
            next = index.get(pos+1);
            
            // Swap elements if needs be
            if( current.isSmaller(next) ) {
                holder = index.get(pos);
                index.set(pos, next);
                index.set( (pos+1), holder);
                swapped = true; 
            }
        }
        
        // Return output
        return swapped;
    }
    
    
    /**
     * Bubble sort index in ascending/descending order
     * 
     * @param ascending - boolean for ascending/desceding order
     * @return int - iterations took to sort
     */
    public int sort(boolean ascending) {

        // Check first
        int counter = 0;
        
        // Sort ascending
        if(ascending) {
            if ( isAscending() ) {
                return counter;
            }
            while( scanArrayAscending() ) {
                counter++;
            }
        }

        // Sort descending
        else {
            if ( isDescending() ) {
                return counter;
            }
            while( scanArrayDescending() ) {
                counter++;
            }
        }
        
        // Return iterations
        return counter;
    }

/////////////////////////////////////////////////////
/////////////////////////////////////////////////////
    
    /**
     * 
     * Merge Sorting
     * 
     */
    
    
    /**
     * Recursive merge sort implementation
     */
    public void mergeSort(){
        partition(0, this.index.size()-1);
    }
    
    
    /**
     * Recursively call partitioning and sorting
     * @param start
     * @param end 
     */
    private void partition(int start, int end){
    
        // Partition until a single index
        if(start < end && (end - start) > 0 ) {
        
            // Half positions
            int mid = (end + start) / 2;
            //System.out.println("Coords: start = " + start + ", mid = " + mid + ", end = " + end);
            partition(start, mid); // Left
            partition(mid+1, end);  // Right
            
            // Merge sort partition
            sortPart(start, mid, end);
        }
    }
    
    
    /**
     * Sort partitions and merge back into array
     * 
     * @param start
     * @param mid
     * @param end 
     */
    private void sortPart(int start, int mid, int end) {
    
        // Holder array to feedback into index
        List<IndexEntry> holder = new ArrayList<>();
        int leftPos = start;
        int rightPos = mid+1;
        
        // Move up from left up to mid,
        // & from mid to end
        while ( leftPos <= mid && rightPos <= end ) {
        
            // Fetch elements for comparision
            IndexEntry leftElm = this.index.get(leftPos);
            IndexEntry rightElm = this.index.get(rightPos);
            
            // Add left elmenet
            if ( leftElm.isSmaller(rightElm) ) {
                holder.add(leftElm);
                leftPos++;
            }
            
            // Add right element
            else {
                holder.add(rightElm);
                rightPos++;
            }
        }
        
        // Complete left extraction
        while ( leftPos <= mid) {
            IndexEntry elm = this.index.get(leftPos);
            holder.add(elm);
            leftPos++;
        }
        
        // Complete right extraction
        while(rightPos <= end) {
            IndexEntry elm = this.index.get(rightPos);
            holder.add(elm);
            rightPos++;
        }
        
        
        // Feed array back into the objects index
        int pos = start;
        while( holder.size() > 0 ) {
            IndexEntry elm = holder.remove(0);
            this.index.set(pos, elm);
            pos++;
        }
        // nIters++;
    }
    
    
/////////////////////////////////////////////////////
/////////////////////////////////////////////////////
    
    
    /**
     * 
     * @return 
     */
    private boolean handleSize() {
        return lastEntry > 0;
    }
    
    
    /**
     * Check sorting of the index slice
     * 
     * @param start
     * @param end
     * @return boolean
     */
    private boolean checkAscending(int start, int end) {
        
        // Initalize scope
        boolean isSorted = true;
        int iter = start;
        int stop = end;
        
        // Handle sizes
        if ( end > index.size() - 1 ) {
            return false;
        }
        
        // Check sorting until broken or stop point reached
        while (isSorted && stop >= 0 & iter+1 < end) {
            
            // Fetch current and next
            IndexEntry current = index.get(iter);
            IndexEntry next = index.get(iter + 1);
            
            // Proceed to next iteration
            if (current.isSmaller(next) || current.equals(next)) {
                stop--;
                iter++;
            }
            
            // Otherwise break
            else {
                isSorted = false;
            }
        }
        
        // Return sorted state
        return isSorted;
    }

    
    /**
     * Check whether array slice is descendingly sorted
     * 
     * @param start
     * @param end
     * @return boolean
     */
    private boolean checkDescending(int start, int end) {
        
        // Initalize scope
        boolean isSorted = true;
        int iter = start;
        int stop = end;
        
        // Handle sizes
        if ( end > index.size() - 1 ) {
            return false;
        }
        
        // Check sorting until broken or stop point reached
        while (isSorted && stop >= 0 ) {
            
            // Fetch current and next
            IndexEntry current = index.get(iter);
            IndexEntry next = index.get(iter + 1);
            
            // Proceed to next iteration
            if (current.isBigger(next) || current.equals(next)) {
                stop--;
                iter++;
            }
            
            // Otherwise break
            else {
                isSorted = false;
            }
        }
        
        // Return sorted state
        return isSorted;
    }
    
    /**
     * Check whether index is descendingly sorted
     * 
     * @return true/false
     */
    public boolean isAscending() {
    
        // Only bother if more than 1
        boolean isSorted = true;
        if ( handleSize() ) {
            
            // Check first ten
            if ( index.size() > 11 ) {
                isSorted = checkAscending(0, 10);
            }
            
            // Otherwise whole array
            else {
                isSorted = checkAscending(0, index.size()-1);
            }
        }

        // Return
        return isSorted;
    } 
    
    
    /**
     * Check whether index is descendingly sorted
     * 
     * @return true/false
     */
    public boolean isDescending() {

        // Only bother if more than 1
        boolean isSorted = true;
        if ( handleSize() ) {
            
            // Check first ten
            if ( index.size() > 11 ) {
                isSorted = checkDescending(0, 10);
            }
            
            // Otherwise whole array
            else {
                isSorted = checkDescending(0, index.size()-1);
            }
        }

        // Return
        return isSorted;
    }
    
    
    /**
     * Check whether array slice is sorted
     * 
     * @param start
     * @param end
     * @param ascending
     * @return 
     */
    public boolean isSorted(int start, int end, boolean ascending) {
        
        // Check if inputs are valid
        if (start == end) {
            return true;
        }
        else if ( (end < start) || ( start > index.size()-1 ) || ( end > index.size()-1 )  ) {
            return false; // should really be an exception
        }
        
        // Check slice until end
        boolean isSorted = true;
        while ( isSorted && start < end ) {
            
            // Get current & next entry
            IndexEntry current = index.get(end);
            IndexEntry next = index.get(start+1);
            
            // Check ascending order
            if (ascending) {
                isSorted = current.isSmaller(next) || current.equals(next);
            }
            
            // Otherwise check descending
            else {
                isSorted = current.isBigger(next) || current.equals(next);
            }
            start++;
        }
        
        // Return sorting state
        return isSorted;
    }

    
    /**
     * Query a string from index
     * 
     * @param query
     * @param start
     * @param end
     * @return String
     */
    public int binarySearch(String query, int start, int end) {
        
        // Restrict search to operate within array
        if (start <= end) {
            
            // Calculate the midpoint between indexes
            int mid = (start + end) / 2;
            IndexEntry current = getIndex().get(mid);

            // Return found index
            if ( current.equals(query) ) {
                return mid;
            } 
            
            // Move left
            else if ( current.isBigger(query) ) {
                return binarySearch(query, start, (mid-1) );
            }
            
            // Move right
            else {
                return binarySearch(query, (mid+1) , end);
            }
        }
        
        // Otherwise return element not found
        else {
            return -1;
        }
    }
    
    
    /**
     * Query an integer from index
     * 
     * @param query
     * @param start
     * @param end
     * @return int
     */
    public int binarySearch(int query, int start, int end) {

        // Restrict to operating within array
        if (start <= end) {
            
            // Calculate the midpoint between indexes
            int mid = (start + end) / 2;
            IndexEntry current = getIndex().get(mid);

            // Return found index
            if ( current.equals(query) ) {
                return mid;
            } 
            
            // Move left
            else if ( current.isBigger(query) ) {
                return binarySearch(query, start, (mid-1) );
            }
            
            // Move right
            else {
                return binarySearch(query, (mid+1) , end);
            }
        }
        
        // Otherwise return element not found
        else {
            return -1;
        }
    }
    
    
    /**
     * Return a list of primary keys matching search
     * 
     * @param query - int
     * @return List - Primary Keys
     */
    public List<Integer> search(String query) {
        
        // Handle sorting
        if ( !isAscending() || !isDescending() ) {
            //System.out.println("\nSorting array");
            mergeSort();
        }
        
        // Initalize output, find index
        List<Integer> output = new ArrayList<>();
        query = query.replaceAll("[^A-Za-z0-9]","");
        int queryIndex = binarySearch(query, 0, index.size()-1);

        // Extend if found
        if ( queryIndex >= 0 ) {

            // Slice if not the same
            // System.out.println("Expanding from: " + queryIndex);
            int start = extendLeft(queryIndex);
            int end = extendRight(queryIndex);
            // System.out.println("Left set to " + start + ", Right set to " + end);
            
            // Get primary keys
            output = getPrimaryKeys(start, end);
        }
        
        // Return results
        return output;
    }
    
    
    /**
     * Return a list of primary keys matching search
     * 
     * @param query - int
     * @return List - Primary Keys
     */
    public List<Integer> search(int query) {
        
        // Handle sorting
        if ( !isAscending() || !isDescending() ) {
            //System.out.println("\nSorting array");
            sort(true);
        }
        else {
            //System.out.println("\nSkipping the sort");
        }
        
        // Initalize output, find index
        List<Integer> output = new ArrayList<>();
        int queryIndex = binarySearch(query, 0, index.size()-1);

        // Extend if found
        if ( queryIndex >= 0 ) {

            // Slice if not the same
            // System.out.println("Expanding from: " + queryIndex);
            int start = extendLeft(queryIndex);
            int end = extendRight(queryIndex);
            
            // Get primary keys
            output = getPrimaryKeys(start, end);
        }
        
        // Return results
        return output;
    }

    
    /**
     * Extend binary search point to farthest right still equally queried value
     * 
     * @param point
     * @return int - right index
     */
    private int extendRight(int point) {
        
        // Initalize search params
        //System.out.println("\nExtending left from: " + point);
        IndexEntry data = index.get(point);
        boolean found = false;
        int counter = 0;
        int output = -1;
        int pos = point+1;
        
        // Search until no longer equal
        while(!found && pos < index.size() ) {
            
            // Compare value at input to next
            IndexEntry current = index.get(pos);
            //System.out.println("Checking " + pos + ", Data = " + current.toString());
            if ( !data.equals(current) ) {
                found = true;
                output = pos-1;
            }
            
            // Otherwise extend
            else {
                counter++;
                pos++;
            }
        }

        // Handle returning input 
        if ( counter == 0 ) {
            return point;
        }
        
        // Handle end of array reach
        if ( output == -1) {
            return index.size()-1;
        }
        
        // Otherwise results
        return output;
    }
    
    
    /**
     * Extend binary search point to farthest left still equally queried value
     * 
     * @param point
     * @return int - left index
     */
    private int extendLeft(int point) {
        
        // Initalize search params
        //System.out.println("\nExtending left from: " + point);
        IndexEntry data = index.get(point);
        boolean found = false;
        int counter = 0;
        int output = -1;
        int pos = point-1;
        
        // Search until no longer equal
        while(!found && pos >= 0 ) {
            
            // Compare value at input to next
            IndexEntry current = index.get(pos);
            // System.out.println("Checking " + pos + ", Data = " + current.toString());
            if ( !data.equals(current) ) {
                found = true;
                output = pos+1;
            }
            
            // Otherwise extend
            else {
                pos--;
                counter++;
            }
        }

        // Handle returning input 
        if ( counter == 0 ) {
            return point;
        }
        
        // Handle end of array reach
        if ( output == -1) {
            return 0;
        }
        
        // Otherwise results
        return output;
    }
    
    
    /**
     * Get list of primary keys between indexes
     * 
     * @param start
     * @param end
     * @return List-Integer - Primary Keys
     */
    private List<Integer> getPrimaryKeys(int start, int end) {
    
        // Add primary keys within range
        List<Integer> primaryKeys = new ArrayList<>();
        if (start != end) {
            for( int i = start; i <= end; i++) {
                IndexEntry current = index.get(i);
                primaryKeys.add( current.getPrimaryKey());
            }
        }
        
        // Otherwise add PK
        else {
            IndexEntry current = index.get(start);
            primaryKeys.add(current.getPrimaryKey());
        }
        
        // Return list of primary keys
        return primaryKeys;
    }
    
    
    /**
     * 
     * @param entry 
     */
    public void insert(IndexEntry entry) {
        this.lastEntry = index.size()-1;
        this.index.add(entry);
    }
    
    
    protected List<Integer> searchNew(int query) {return null;} // Linear search
    protected List<Integer> searchNew(String query) {return null;} // Linear search
    
    
    /**
     * Return size of index
     * 
     * @return int 
     */
    public int getSize() {
        return index.size();
    }


    /**
     * Return the index of last import
     * 
     * @return int
     */
    private int getLastEntry() {
        return this.lastEntry;
    }
    
    
    /**
     * Return index
     * 
     * @return List-IndexEntry
     */
    private List<IndexEntry> getIndex() {
        return this.index;
    }
    
    
    /**
     * 
     * @return 
     */
    public String valueString() {
        // Print array
        String msg = "";
        for( IndexEntry i : index ) {
            msg += i.valueString() + ", ";
        }
        msg+= "}";
        msg = msg.replace(", }", "");
        return msg;
    }


    /**
     * 
     * @return 
     */
    @Override
    public String toString(){
        // Print array
        String msg = "";
        for( IndexEntry i : index ) {
            msg += i.toString() + "; ";
        }
        msg+= "}";
        msg = msg.replace("; }", "");
        return msg;
    }
    
    
    /**
     * Binary search nearest int given comparator flag
     * 
     * @param query
     * @param start
     * @param end
     * @param favourSmall - false for min, true for max
     * @return 
     */
    private int binaryNearest(int query, int start, int end, boolean favourSmall) {
        
        // Restrict search to operate within array
        int mid = (start + end) / 2;
        IndexEntry current = getIndex().get(mid);

        // Return found index
        if (current.equals(query)) {
            return mid;
        }

        // Handle how to move left
        else if (current.isBigger(query)) {

            // Check if next can be evaluated
            if (mid - 1 < 0) {
                return mid;
            }

            // Check if next is better
            IndexEntry next = getIndex().get(mid - 1);
            if (next.isSmaller(query)) {

                // Handle choice
                if (closest(query, current, next, favourSmall)) {
                    return mid;
                }
                else {
                    return mid - 1;
                }
            }

            // Otherwise move left
            else {
                return binaryNearest(query, start, (mid - 1), favourSmall);
            }
        }
        
        // Handle how to move right
        else {

            // Check if next can be evaluated
            if (mid + 1 > end) {
                return mid;
            }

            // Check if next mid is better
            IndexEntry next = getIndex().get(mid + 1);
            if (next.isBigger(query)) {

                // Handle choice
                if (closest(query, current, next, favourSmall)) {
                    return mid;
                }
                else {
                    return mid + 1;
                }
            }

            // Otherwise move right
            else {
                return binaryNearest(query, (mid + 1), end, favourSmall);
            }
        }
    }
    
    
    /**
     * Handle how to choose which index entry to get
     * 
     * @param query
     * @param current
     * @param next
     * @param favourSmall
     * @return 
     */
    private boolean closest(int query, IndexEntry current, IndexEntry next, boolean favourSmall) {
        
        // Calculate difference
        int dist1 = current.compareTo(query);
        int dist2 = next.compareTo(query);
        
        // Handle difference
        if (favourSmall) {
            return dist1 <= dist2;
        }
        else {
            return dist1 >= dist2;
        }
        
    }
    
    /**
     * Retrieve primary keys that are inclusively between min & max input
     * 
     * @param minVal
     * @param maxVal
     * @return List-Primary Keys
     */
    public List<Integer> searchRange(int minVal, int maxVal) {
        
        // Only allow min -> max
        if( minVal > maxVal) {
            return null;
        }
        
        // Initalize output & handle sorting
        if ( !isAscending() || !isDescending() ) {
            sort(true);
        }
        
        // Binary search min & extend left
        boolean favourSmall = false;
        int nearest = binaryNearest(minVal, 0, index.size()-1, favourSmall);
        int leftPos = extendLeft(nearest);
        
        // Search left & extend right
        favourSmall = true;
        nearest = binaryNearest(maxVal, 0, index.size()-1, favourSmall);
        int rightPos = extendRight(nearest);
        
        // Fetch array slice
        return getPrimaryKeys(leftPos, rightPos);
    }
    
    
    /**
     * Search index entries for values starting with query
     * 
     * @param query
     * @return List-Primary Keys
     */
    public List<Integer> startsWith(String query) {
    
        // Initalize output and search string
        int pos = binaryStartsWith(query, 0, index.size()-1);
        if (pos == -1) {
            return null;
        }
        
        // Extend position left & right
        int leftPos = extendLeft(pos, query.length());
        int rightPos = extendRight(pos, query.length());
        
        // Get primary keys
        return getPrimaryKeys(leftPos, rightPos);
    }
    
    /**
     * Allow searching strings starting with query
     * 
     * @param query
     * @param start
     * @param end
     * @return int - index
     */
    private int binaryStartsWith(String query, int start, int end) {
        
        // Restrict search to operate within array
        if (start <= end) {

            // Calculate mid
            int mid = (start + end) / 2;
            IndexEntry current = getIndex().get(mid);
            
            // Return found index
            if ( current.equals(query, query.length()) ) {
                return mid;
            } 
            
            // Move left
            else if ( current.isBigger(query, query.length()) ) {
                return binaryStartsWith(query, start, (mid-1) );
            }
            
            // Move right
            else {
                return binaryStartsWith(query, (mid+1) , end);
            }
        }
        
        // Otherwise return element not found
        else {
            return -1;
        }
    }
    
    
    /**
     * Extend left position for starts with search
     * 
     * @param point
     * @param slice
     * @return 
     */
    private int extendLeft(int point, int slice) {
        
        // Initalize search params
        //System.out.println("\nExtending left from: " + point);
        IndexEntry data = index.get(point);
        boolean found = false;
        int counter = 0;
        int output = -1;
        int pos = point-1;
        
        // Search until no longer equal
        while(!found && pos >= 0 ) {
            
            // Compare value at input to next
            IndexEntry current = index.get(pos);
            //System.out.println("Checking " + pos + ", Data = " + current.toString());
            if ( !data.equals(current, slice) ) {
                found = true;
                output = pos+1;
            }
            
            // Otherwise extend
            else {
                pos--;
                counter++;
            }
        }

        // Handle returning input 
        if ( counter == 0 ) {
            return point;
        }
        
        // Handle begining of list
        if ( output == -1 ) {
            return 0;
        }
        
        // Otherwise results
        return output;
    }
    
    
    /**
     * Extend right position for starts with search
     * 
     * @param point
     * @param slice
     * @return 
     */
    private int extendRight(int point, int slice) {
        
        // Initalize search params
        //System.out.println("\nExtending right from: " + point);
        IndexEntry data = index.get(point);
        boolean found = false;
        int output = -1;
        int pos = point+1;

        // Search until no longer equal
        while(!found && pos < index.size() ) {
            
            // Compare value at input to next
            IndexEntry current = index.get(pos);
            if ( !data.equals(current, slice) ) {
                
                // Terminate loop
                found = true;
                output = pos-1;
            }
            
            // Otherwise extend
            else {
                pos++;
            }
        }
        
        
        // Handle returning input 
        if ( !found ) {
            return point;
        }
        
        // Handle begining of list
        if ( output == -1 ) {
            return index.size()-1;
        }
        
        // Otherwise results
        return output;
    }
}
