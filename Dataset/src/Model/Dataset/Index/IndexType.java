/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Dataset.Index;


/**
 * Enum to facilitate construction of valid index entry types integer/string.
 * 
 * @author kenna
 */
public enum IndexType {
    
    INTEGER_IND {
        
        @Override
        public IndexEntry makeIndexEntry(Object value, int primaryKey) {
            return new IntIndexEntry( (int) value, primaryKey);
        }

        @Override
        public IndexType whichType() {
            return INTEGER_IND;
        }

        @Override
        public boolean isType(IndexEntry query) {
            return query == INTEGER_IND;
        }

        @Override
        public String toString() {
            return "Integer Index";
        }

        @Override
        public boolean isType(IndexType query) {
            return query == INTEGER_IND;
        }
    },
    
    STRING_IND {
        @Override
        public IndexEntry makeIndexEntry(Object value, int primaryKey) {
            return new StringIndexEntry( (String) value, primaryKey);
        }

        @Override
        public IndexType whichType() {
            return STRING_IND;
        }

        @Override
        public boolean isType(IndexEntry query) {
            return query == STRING_IND;
        }

        @Override
        public String toString() {
            return "String Index";
        }

        @Override
        public boolean isType(IndexType query) {
            return query == STRING_IND;
        }

    };
    
    
    /**
     * Abstract method to construct a valid index entry from a value
     *  and its corresponding position in an array
     * 
     * @param value
     * @param primaryKey
     * @return IndexEntry: String/Int
     */
    public abstract IndexEntry makeIndexEntry(Object value, int primaryKey);
    
    /**
     * Return type of index entry
     * 
     * @return String_Ind/Int_Ind
     */
    public abstract IndexType whichType();
    
    
    /**
     * Check if index entry type matches specific type
     * 
     * @param query
     * @return boolean
     */
    public abstract boolean isType(IndexEntry query);
    
    
    /**
     * Compare two index types
     * 
     * @param query
     * @return boolean
     */
    public abstract boolean isType(IndexType query);
    
    
    /**
     * Represent specific enum as string
     * @return 
     */
    @Override
    public abstract String toString();
}
