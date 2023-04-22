/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Dataset.Index;


/**
 * Class containing value and a pointer to a primary key in a dataset. 
 * Class exposes methods for comparing index entries, or the value of the 
 * concrete index entry type to a string/int. Collectively allowing a list 
 * of these IndexEntries to be searchable/sortable, regardless of the 
 * wider data structure
 * 
 * @author kenna
 */
public interface IndexEntry {
    
    
    /**
     * Get primary key
     * 
     * @return int 
     */
    public int getPrimaryKey();
    
    
    /**
     * Get the index entries value
     * 
     * @return Object - String/Int
     */
    public Object getEntryValue();
    
    /**
     * Compare values from index entries
     * 
     * @param query
     * @return true/false
     */
    public boolean equals(IndexEntry query);
    
    /**
     * Compare values from slice 
     * 
     * @param query
     * @param slice
     * @return true/false
     */
    public boolean equals(IndexEntry query, int slice);
    
    
    /**
     * Compare two ints
     * 
     * @param query
     * @return boolean
     */
    public boolean equals(int query);
    
    
    /**
     * Compare query to current index entry
     * 
     * @param query
     * @return true/false
     */
    public boolean equals(String query);
    
    
    /**
     * Compare slice of two strings
     * 
     * @param query
     * @param slice
     * @return boolean
     */
    public boolean equals(String query, int slice);
    
    /**
     * Compare current to query 
     * 
     * @param query
     * @return int
     */
    public int compareTo(IndexEntry query);
    
    
    /**
     * Calculate difference between queried int and value
     * 
     * @param query
     * @return int
     */
    public int compareTo(int query);
    
    
    /**
     * Compare current to slice of query
     * 
     * @param query
     * @param slice
     * @return int
     */
    public int compareTo(IndexEntry query, int slice);
    
    
    /**
     * Compare query string to current index entry
     * 
     * @param query
     * @return in
     */
    public int compareTo(String query);
    
    
    /**
     * Compare query string to slice of index entry
     * 
     * @param query
     * @param slice
     * @return int
     */
    public int compareTo(String query, int slice);
    
    
    /**
     * Return whether current value is greater than query
     * 
     * @param query
     * @return true/false
     */
    public boolean isBigger(IndexEntry query);
    
    /**
     * 
     * @param query
     * @param slice
     * @return 
     */
    public boolean isBigger(IndexEntry query, int slice);

    
    /**
     * Return whether current value is greater than query
     * 
     * @param query
     * @return true/false
     */
    public boolean isBigger(int query);
    
    
    /**
     * Return whether current value is greater than query
     * 
     * @param query
     * @return true/false
     */
    public boolean isBigger(String query);
    
    
    /**
     * 
     * @param query
     * @param slice
     * @return 
     */
    public boolean isBigger(String query, int slice);
    
    
    /**
     * Return whether current value is smaller than query
     * 
     * @param query
     * @return true/false
     */
    public boolean isSmaller(IndexEntry query);
    
    
    /**
     * 
     * @param query
     * @param slice
     * @return 
     */
    public boolean isSmaller(IndexEntry query, int slice);
    
    
    /**
     * Return whether current value is smaller than query
     * 
     * @param query
     * @return true/false
     */
    public boolean isSmaller(String query);
    
    
    /**
     * 
     * @param query
     * @param slice
     * @return 
     */
    public boolean isSmaller(String query, int slice);
    
    
    /**
     * Return whether current value is smaller than query
     * 
     * @param query
     * @return true/false
     */
    public boolean isSmaller(int query);
    

    /**
     * Return index type
     * 
     * @return IndexType - String/Integer 
     */
    public IndexType whichType();
    
    
    /**
     * Check whether IndexTypes match
     * 
     * @param query
     * @return true/false
     */
    public boolean isType(IndexEntry query);
    
    
    /**
     * Represent value and primary key as a string
     * 
     * @return String
     */
    @Override
    public String toString();
    
    
    /**
     * Represent value as a string
     * 
     * @return String
     */
    public String valueString();
}
