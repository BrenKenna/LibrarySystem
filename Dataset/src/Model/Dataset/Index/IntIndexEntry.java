/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Dataset.Index;


/**
 * Integer specific index entry, blocks/nulls string related
 *  comparisons
 * 
 * @author kenna
 */
public class IntIndexEntry implements IndexEntry {
    
    
    // Attributes
    private int value;
    private int primaryKey;
    
    
    /**
     * 
     */
    public IntIndexEntry(){}
    
    
    /**
     * 
     * @param value
     * @param primaryKey 
     */
    public IntIndexEntry(int value, int primaryKey){
        this.value = value;
        this.primaryKey = primaryKey;
    }

    
    /**
     * 
     * @return 
     */
    public int getValue(){
        return this.value;
    }
    
    
    /**
     * 
     * @return 
     */
    @Override
    public int getPrimaryKey() {
        return this.primaryKey;
    }

    
    /**
     * 
     * @param query
     * @return 
     */
    @Override
    public boolean equals(IndexEntry query) {
        IntIndexEntry input = (IntIndexEntry) query;
        return input.getValue() == value;
    }
    
    
    /**
     * 
     * @param query
     * @return 
     */
    @Override
    public boolean equals(int query) {
        return value == query;
    }
    
    
    /**
     * 
     * @param query
     * @return 
     */
    @Override
    public boolean equals(String query) {
        return false;
    }

    
    /**
     * Determine whether this index entry is bigger than query
     * 
     * @param query
     * @return true/false
     */
    @Override
    public boolean isBigger(IndexEntry query){
        IntIndexEntry input = (IntIndexEntry) query;
        return value > input.getValue();
    }
    
    
    /**
     * Determine whether this index entry is smaller than query
     * 
     * @param query
     * @return true/false
     */
    @Override
    public boolean isSmaller(IndexEntry query){
        IntIndexEntry input = (IntIndexEntry) query;
        return value < input.getValue();
    }
    
    /**
     * 
     * @param query
     * @return 
     */
    @Override
    public int compareTo(IndexEntry query) {
        IntIndexEntry input = (IntIndexEntry) query;
        return value - input.getValue();
    }

    /**
     * 
     * @param query
     * @return 
     */
    @Override
    public int compareTo(String query) {
        return -1;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public IndexType whichType() {
        return IndexType.INTEGER_IND;
    }

    
    /**
     * 
     * @param query
     * @return 
     */
    @Override
    public boolean isType(IndexEntry query) {
        return IndexType.INTEGER_IND == query.whichType();
    }

    @Override
    public boolean isBigger(int query) {
        return value > query;
    }
    
    @Override
    public boolean isSmaller(int query) {
        return value < query;
    }

    @Override
    public boolean isBigger(String query) {
        return false; // Could compare lexicographically?
    }

    @Override
    public boolean isSmaller(String query) {
        return false; // Could compare lexicographically?
    }
    
    @Override
    public String toString() {
        return "Value=" + value + ", PrimaryKey=" + primaryKey;
    }
    
    @Override
    public String valueString() {
        return "Value=" + value;
    }
    
    @Override
    public int compareTo(int query) {
        return value - query;
    }

    @Override
    public boolean equals(IndexEntry query, int slice) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(String query, int slice) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compareTo(IndexEntry query, int slice) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compareTo(String query, int slice) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isBigger(IndexEntry query, int slice) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isBigger(String query, int slice) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isSmaller(IndexEntry query, int slice) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isSmaller(String query, int slice) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getEntryValue() {
        return this.value;
    }
}
