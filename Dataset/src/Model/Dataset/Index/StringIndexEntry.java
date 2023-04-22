/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Dataset.Index;


/**
 * String specific index entry, blocks/nulls int related
 *  comparisons
 * 
 * @author kenna
 */
public class StringIndexEntry implements IndexEntry{

    // Attributes
    private String value;
    private int primaryKey;
    
    
    /**
     * 
     */
    public StringIndexEntry(){}
    
    
    /**
     * 
     * @param value
     * @param primaryKey 
     */
    public StringIndexEntry(String value, int primaryKey){
        this.value = value.trim().toLowerCase().replaceAll("[^A-Za-z0-9]","");
        this.primaryKey = primaryKey;
    }

    
    /**
     * 
     * @return 
     */
    public String getValue(){
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
        StringIndexEntry input = (StringIndexEntry) query;
        return value.equals( input.getValue() );
    }
    
    
    /**
     * Slice string before comparing
     * 
     * @param query
     * @param slice
     * @return 
     */
    public boolean equals(IndexEntry query, int slice) {
        
        // Manage input entry
        StringIndexEntry input = (StringIndexEntry) query;
        String queryString = input.getValue().substring(0, slice);
        
        // Substring current entry before comparing
        String testString = value.substring(0, queryString.length());
        return testString.equals( queryString );
    }
    
    
    /**
     * 
     * @param query
     * @return 
     */
    @Override
    public boolean equals(int query) {
        return false;
    }
    
    
    /**
     * Standardize how queries are compared
     * 
     * @param query
     * @return alphanumeric string
     */
    private String parseQuery(String query) {
        return query.trim().toLowerCase().replaceAll("[^A-Za-z0-9]","");
    }
    
    
    /**
     * 
     * @param query
     * @return 
     */
    @Override
    public boolean equals(String query) {
        return value.equals( parseQuery(query) );
    }

    
    /**
     * 
     * @param query
     * @param slice
     * @return 
     */
    @Override
    public boolean equals(String query, int slice) {
        String testString = value.substring(0, slice);
        return testString.equals(query);
    }
    
    /**
     * 
     * @param query
     * @return 
     */
    @Override
    public int compareTo(IndexEntry query) {
        
        // Handle query string
        StringIndexEntry input = (StringIndexEntry) query;
        String queryString = input.getValue();
        
        // Substring this entry value
        return value.compareTo(queryString);
    }

    
    /**
     * 
     * @param query
     * @param slice
     * @return 
     */
    @Override
    public int compareTo(IndexEntry query, int slice) {
        
        // Handle query string
        StringIndexEntry input = (StringIndexEntry) query;
        String queryString = input.getValue().substring(0, slice);
        
        // Substring this entry value
        String testString = value.substring(0, slice);
        return testString.compareTo(queryString);
    }
    
    /**
     * 
     * @param query
     * @return 
     */
    @Override
    public int compareTo(String query) {
        return value.compareTo(query);
    }
    
    
    /**
     * 
     * @param query
     * @param slice
     * @return 
     */
    @Override
    public int compareTo(String query, int slice) {
        String testString = value.substring(0, slice);
        return testString.compareTo(query);
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public IndexType whichType() {
        return IndexType.STRING_IND;
    }

    
    /**
     * 
     * @param query
     * @return 
     */
    @Override
    public boolean isType(IndexEntry query) {
        return IndexType.STRING_IND == query.whichType();
    }
    
    
    /**
     * Check if this value is bigger than query
     * 
     * @param query
     * @return true/false
     */
    @Override
    public boolean isBigger(String query) {
        int distance = compareTo( parseQuery(query) );
        return distance > 0;
    }

    
    /**
     * Check if this value is smaller than query
     * 
     * @param query
     * @return true/false
     */
    @Override
    public boolean isSmaller(String query) {
        int distance = compareTo(parseQuery(query));
        return distance < 0;
    }
    

    /**
     * Check if this value is bigger than query
     * 
     * @param query
     * @return true/false
     */
    @Override
    public boolean isBigger(IndexEntry query) {
        int distance = compareTo(query);
        return distance > 0;
    }

    
    /**
     * Check if this value is smaller than query
     * 
     * @param query
     * @return true/false
     */
    @Override
    public boolean isSmaller(IndexEntry query) {
        int distance = compareTo(query);
        return distance < 0;
    }


    @Override
    public boolean isBigger(int query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public boolean isSmaller(int query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public boolean isBigger(IndexEntry query, int slice) {
        int distance = compareTo(query, slice);
        return distance > 0;
    }

    @Override
    public boolean isBigger(String query, int slice) {
        int distance = compareTo(parseQuery(query), slice);
        return distance > 0;
    }

    @Override
    public boolean isSmaller(IndexEntry query, int slice) {
        int distance = compareTo(query, slice);
        return distance < 0;
    }

    @Override
    public boolean isSmaller(String query, int slice) {
        int distance = compareTo(parseQuery(query), slice);
        return distance < 0;
    }

    @Override
    public int compareTo(int query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getEntryValue() {
        return this.value;
    }
}
