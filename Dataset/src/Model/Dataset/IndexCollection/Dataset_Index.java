/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Dataset.IndexCollection;

// Datapoints, types and referencing specific fields
import Model.Datapoint.Datapoint;
import Model.Datapoint.Datapoint_Edge;
import Model.Datapoint.Datapoint_Type;
import Model.Datapoint.Activity.Activity;
import Model.Datapoint.Item.Book;
import Model.Datapoint.Item.Student;

// Index interface
import Model.Dataset.Index.*;

// Supporting
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Enum for all possible indexes, appropriate datapoints 
 * that can use them and foreign key labels for inner join
 * 
 * @author kenna
 */
public enum Dataset_Index {
    
    PRIMARY_KEY {
        @Override
        public IndexType indexType() {
            return IndexType.INTEGER_IND;
        }

        @Override
        public Dataset_Index whichIndex() {
            return PRIMARY_KEY;
        }

        @Override
        public boolean partOfEdge(Datapoint_Edge type) {
            return true;
        }

        /**
         * Generate a primary key integer index on list of datapoints
         */
        @Override
        public Index getKey(List<Datapoint> datapoints) {
            
            // Add each element to dataset
            List<IndexEntry> indexEntries = new ArrayList<>();
            int counter = 0;
            for(Datapoint data : datapoints) {

                // Add primary key value and pointer to index
                IndexEntry priKey = IndexType.INTEGER_IND.makeIndexEntry(data.getAutoID(), counter);
                indexEntries.add(priKey);
                counter++;
            }
            
            // Return primary key index
            return new IntIndex(indexEntries);
        }

        @Override
        public IndexEntry getValidEntry(Datapoint datapoint, int index) {
            return IndexType.INTEGER_IND.makeIndexEntry(datapoint.getAutoID(), index);
        }
        
        @Override
        public boolean isInt() {
            return true;
        }

        @Override
        public boolean isString() {
            return false;
        }
        
        @Override
        public boolean isForeignKey() {
            return false;
        }
        
        @Override
        public Datapoint_Edge getForeignKeyPartner() {
            return null;
        }
    },
    
    FIRST_NAME {
        
        @Override
        public IndexType indexType() {
            return IndexType.STRING_IND;
        }

        @Override
        public Dataset_Index whichIndex() {
            return FIRST_NAME;
        }

        @Override
        public boolean partOfEdge(Datapoint_Edge type) {
            return type.isDatapoint(Datapoint_Type.ITEM);
        }

        @Override
        public Index getKey(List<Datapoint> datapoints) {

            // Initalize output and create index
            List<IndexEntry> stringEntries = new ArrayList<>();
            int counter = 0;
            
            // Add data
            for (Datapoint data : datapoints) {

                // Handle books vs student
                String entry;
                if ( data.isEdge(Datapoint_Edge.STUDENT) ) {
                    entry = ((Student) data).getfName();
                }
                else {
                    entry = ((Book) data).getfName();
                }

                // Generate pk inde and add to entries
                IndexEntry fnameInd = IndexType.STRING_IND.makeIndexEntry(entry, counter);
                stringEntries.add(fnameInd);
                counter++;
            }
            
            
            // Return output
            return new StringIndex(stringEntries);
        }
        
        
        @Override
        public IndexEntry getValidEntry(Datapoint datapoint, int index) {
            String entry;
            if ( datapoint.isEdge(Datapoint_Edge.STUDENT) ) {
                entry = ( (Student) datapoint).getfName();
            }
            else {
                entry = ( (Book) datapoint).getfName();
            }
            return IndexType.STRING_IND.makeIndexEntry(entry, index);
        }
        
        @Override
        public boolean isInt() {
            return false;
        }

        @Override
        public boolean isString() {
            return true;
        }
        
        @Override
        public boolean isForeignKey() {
            return false;
        }
        
        @Override
        public Datapoint_Edge getForeignKeyPartner() {
            return null;
        }
    },
    
    LAST_NAME {
        
        @Override
        public IndexType indexType() {
            return IndexType.STRING_IND;
        }

        @Override
        public Dataset_Index whichIndex() {
            return LAST_NAME;
        }

        @Override
        public boolean partOfEdge(Datapoint_Edge type) {
            return type.isDatapoint(Datapoint_Type.ITEM);
        }

        @Override
        public Index getKey(List<Datapoint> datapoints) {
            
            // Initalize output and create index
            List<IndexEntry> stringEntries = new ArrayList<>();
            int counter = 0;
            
            // Add data
            for (Datapoint data : datapoints) {

                // Handle books vs student
                String entry;
                if ( data.isEdge(Datapoint_Edge.STUDENT) ) {
                    entry = ((Student) data).getlName();
                }
                else {
                    entry = ((Book) data).getlName();
                }

                // Generate pk inde and add to entries
                IndexEntry fnameInd = IndexType.STRING_IND.makeIndexEntry(entry, counter);
                stringEntries.add(fnameInd);
                counter++;
            }
            
            
            // Return output
            return new StringIndex(stringEntries);
        }
        
        
        @Override
        public IndexEntry getValidEntry(Datapoint datapoint, int index) {
            String entry;
            if ( datapoint.isEdge(Datapoint_Edge.STUDENT) ) {
                entry = ( (Student) datapoint).getlName();
            }
            else {
                entry = ( (Book) datapoint).getlName();
            }
            return IndexType.STRING_IND.makeIndexEntry(entry, index);
        }

        @Override
        public boolean isInt() {
            return false;
        }

        @Override
        public boolean isString() {
            return true;
        }
        
        @Override
        public boolean isForeignKey() {
            return false;
        }
        
        @Override
        public Datapoint_Edge getForeignKeyPartner() {
            return null;
        }
    },
    
    TITLE {
        @Override
        public IndexType indexType() {
            return IndexType.STRING_IND;
        }

        @Override
        public Dataset_Index whichIndex() {
            return TITLE;
        }

        @Override
        public boolean partOfEdge(Datapoint_Edge type) {
            return type == Datapoint_Edge.BOOK;
        }

        @Override
        public Index getKey(List<Datapoint> datapoints) {
            
            // Initalize output and create index
            List<IndexEntry> stringEntries = new ArrayList<>();
            int counter = 0;
            
            // Add data
            for (Datapoint data : datapoints) {

                // Handle books vs student
                String entry = ((Book) data).getTitle();

                // Generate pk inde and add to entries
                IndexEntry fnameInd = IndexType.STRING_IND.makeIndexEntry(entry, counter);
                stringEntries.add(fnameInd);
                counter++;
            }
            
            
            // Return output
            return new StringIndex(stringEntries);
        }
        
        
        @Override
        public IndexEntry getValidEntry(Datapoint datapoint, int index) {
            String entry = ( (Book) datapoint).getTitle();
            return IndexType.STRING_IND.makeIndexEntry(entry, index);
        }
        
        @Override
        public boolean isInt() {
            return false;
        }

        @Override
        public boolean isString() {
            return true;
        }
        
        @Override
        public boolean isForeignKey() {
            return false;
        }
        
        @Override
        public Datapoint_Edge getForeignKeyPartner() {
            return null;
        }
    },
    
    N_BORROW {
        
        @Override
        public IndexType indexType() {
            return IndexType.INTEGER_IND;
        }

        @Override
        public Dataset_Index whichIndex() {
            return N_BORROW;
        }

        @Override
        public boolean partOfEdge(Datapoint_Edge type) {
            return type.isDatapoint(Datapoint_Type.ITEM);
        }

        @Override
        public Index getKey(List<Datapoint> datapoints) {
            
            // Add each element to dataset
            List<IndexEntry> indexEntries = new ArrayList<>();
            int counter = 0;
            for(Datapoint data : datapoints) {

                // Handle getting borrow from book vs student
                int nBorrows;
                if ( data.isEdge(Datapoint_Edge.STUDENT) ) {
                    nBorrows = ( (Student) data).getnBorrows();
                }
                else {
                    nBorrows = ( (Book) data).getBorrows();
                }
                
                // Create object and add to the index
                IndexEntry entry = IndexType.INTEGER_IND.makeIndexEntry(nBorrows, counter);
                indexEntries.add(entry);
                counter++;
            }
            
            // Return primary key index
            return new IntIndex(indexEntries);
        }

        @Override
        public IndexEntry getValidEntry(Datapoint datapoint, int index) {
            int entry;
            if ( datapoint.isEdge(Datapoint_Edge.STUDENT) ) {
                entry = ( (Student) datapoint).getnBorrows();
            }
            else {
                entry = ( (Book) datapoint).getBorrows();
            }
            return IndexType.INTEGER_IND.makeIndexEntry(entry, index);
        }
        
        @Override
        public boolean isInt() {
            return true;
        }

        @Override
        public boolean isString() {
            return false;
        }
        
        @Override
        public boolean isForeignKey() {
            return false;
        }
        
        @Override
        public Datapoint_Edge getForeignKeyPartner() {
            return null;
        }
    },
    
    N_RETURNS {
        @Override
        public IndexType indexType() {
            return IndexType.INTEGER_IND;
        }

        @Override
        public Dataset_Index whichIndex() {
            return N_RETURNS;
        }

        @Override
        public boolean partOfEdge(Datapoint_Edge type) {
            return type.isDatapoint(Datapoint_Type.ITEM);
        }

        @Override
        public Index getKey(List<Datapoint> datapoints) {
            
            // Add each element to dataset
            List<IndexEntry> indexEntries = new ArrayList<>();
            int counter = 0;
            for(Datapoint data : datapoints) {

                // Handle getting borrow from book vs student
                int nReturns;
                if ( data.isEdge(Datapoint_Edge.STUDENT) ) {
                    nReturns = ( (Student) data).getReturns();
                }
                else {
                    nReturns = ( (Book) data).getReturns();
                }
                
                // Create object and add to the index
                IndexEntry entry = IndexType.INTEGER_IND.makeIndexEntry(nReturns, counter);
                indexEntries.add(entry);
                counter++;
            }
            
            // Return primary key index
            return new IntIndex(indexEntries);
        }
        
        @Override
        public IndexEntry getValidEntry(Datapoint datapoint, int index) {
            int entry;
            if ( datapoint.isEdge(Datapoint_Edge.STUDENT) ) {
                entry = ( (Student) datapoint).getReturns();
            }
            else {
                entry = ( (Book) datapoint).getReturns();
            }
            return IndexType.INTEGER_IND.makeIndexEntry(entry, index);
        }

        @Override
        public boolean isInt() {
            return true;
        }

        @Override
        public boolean isString() {
            return false;
        }
        
        @Override
        public boolean isForeignKey() {
            return false;
        }
        
        @Override
        public Datapoint_Edge getForeignKeyPartner() {
            return null;
        }
    },
    
    BOOK_ID {
        @Override
        public IndexType indexType() {
            return IndexType.INTEGER_IND;
        }

        @Override
        public Dataset_Index whichIndex() {
            return BOOK_ID;
        }

        @Override
        public boolean partOfEdge(Datapoint_Edge type) {
            return type.isDatapoint(Datapoint_Type.ACTIVITY);
        }

        @Override
        public Index getKey(List<Datapoint> datapoints) {
            
            // Add each element to dataset
            List<IndexEntry> indexEntries = new ArrayList<>();
            int counter = 0;
            for(Datapoint data : datapoints) {

                // Handle getting borrow from book vs student
                int bookID = ( (Activity) data ).getBook().getAutoID();
                
                // Create object and add to the index
                IndexEntry entry = IndexType.INTEGER_IND.makeIndexEntry(bookID, counter);
                indexEntries.add(entry);
                counter++;
            }
            
            // Return primary key index
            return new IntIndex(indexEntries);
        }

        @Override
        public IndexEntry getValidEntry(Datapoint datapoint, int index) {
            if ( datapoint.isDatapoint(Datapoint_Type.ACTIVITY) ) {
                Activity input = (Activity) datapoint;
                return IndexType.INTEGER_IND.makeIndexEntry(input.getBook().getAutoID(), index);
            }
            else {
                return null;
            }
        }

        @Override
        public boolean isInt() {
            return true;
        }

        @Override
        public boolean isString() {
            return false;
        }
        
        @Override
        public boolean isForeignKey() {
            return true;
        }
        
        @Override
        public Datapoint_Edge getForeignKeyPartner() {
            return Datapoint_Edge.BOOK;
        }
    },
    
    STUDENT_ID {
        @Override
        public IndexType indexType() {
            return IndexType.INTEGER_IND;
        }

        @Override
        public Dataset_Index whichIndex() {
            return STUDENT_ID;
        }

        @Override
        public boolean partOfEdge(Datapoint_Edge type) {
            return type.isDatapoint(Datapoint_Type.ACTIVITY);
        }

        @Override
        public Index getKey(List<Datapoint> datapoints) {
            
            // Add each element to dataset
            List<IndexEntry> indexEntries = new ArrayList<>();
            int counter = 0;
            for(Datapoint data : datapoints) {

                // Handle getting borrow from book vs student
                int studentID = ( (Activity) data ).getStudent().getAutoID();
                
                // Create object and add to the index
                IndexEntry entry = IndexType.INTEGER_IND.makeIndexEntry(studentID, counter);
                indexEntries.add(entry);
                counter++;
            }
            
            // Return primary key index
            return new IntIndex(indexEntries);
        }

        @Override
        public IndexEntry getValidEntry(Datapoint datapoint, int index) {
            if ( datapoint.isDatapoint(Datapoint_Type.ACTIVITY) ) {
                Activity input = (Activity) datapoint;
                return IndexType.INTEGER_IND.makeIndexEntry(input.getStudent().getAutoID(), index);
            }
            else {
                return null;
            }
        }

        @Override
        public boolean isInt() {
            return true;
        }

        @Override
        public boolean isString() {
            return false;
        }

        @Override
        public boolean isForeignKey() {
            return true;
        }

        @Override
        public Datapoint_Edge getForeignKeyPartner() {
            return Datapoint_Edge.STUDENT;
        }
    };
    
    
    /**
     * Get index type applied to the dataset
     * 
     * @return IndexType - Int/String
     */
    public abstract IndexType indexType();
    
    
    /**
     * Get index field
     * 
     * @return Dataset_Index - Primary Key/First Name/Last Name/N Borrows/N Returns 
     */
    public abstract Dataset_Index whichIndex();
    
    /**
     * Return whether queried datapoint can be associated with specific index
     * 
     * @param type
     * @return true/false
     */
    public abstract boolean partOfEdge(Datapoint_Edge type);
    
    
    /**
     * Return index for list of datapoint
     * 
     * @param datapoints
     * @return Index - Integer/String
     */
    public abstract Index getKey(List<Datapoint> datapoints);
    
    /**
     * Return an index entry for a datapoint
     * 
     * @param datapoint
     * @param index
     * @return IndexEntry - Integer/String
     */
    public abstract IndexEntry getValidEntry(Datapoint datapoint, int index);
    
    
    /**
     * Get a map of valid index entries for a datasets
     * 
     * @param type
     * @param dataset
     * @return Map- Valid Indexes, List IndexEntries-Integer/String
     */
    public static Map<Dataset_Index, List<IndexEntry> > configureIndexEntries(Datapoint_Edge type, List<Datapoint> dataset){
    
        // Configure output
        Map<Dataset_Index, List<IndexEntry> > indexMap = new HashMap<>();
        for ( Dataset_Index index : type.getValidIndexTypes() ) {
            indexMap.put(index, new ArrayList<>());
        }
        
        // Add each datapoint to their appropriate index
        int counter = 0;
        for ( Datapoint data : dataset ) {
        
            // Add Index entry for each valid index
            for ( Dataset_Index indexType : indexMap.keySet() ) {
                // System.out.println("\nCreating index = " + indexType + ", for " + type);
                List<IndexEntry> typeList = indexMap.get(indexType);
                IndexEntry indexEntry = indexType.getValidEntry(data, counter);
                typeList.add(indexEntry);
            }
            
            // Increment index of dataset
            counter++;
        }
        
        // Return output
        return indexMap;
    }
    
    
    /**
     * Configure the complete index map for a dataset
     * 
     * @param type
     * @param dataset
     * @return Map-DatasetIndex, Index
     */
    public static Map<Dataset_Index, Index> configureIndexes(Datapoint_Edge type, List<Datapoint> dataset) {
        
        // Initalize and populate map with valid indexes
        Map<Dataset_Index, Index> output = new HashMap<>();
        Map<Dataset_Index, List<IndexEntry> > indexMap = configureIndexEntries(type, dataset);
        
        
        // Configure output index map
        for ( Dataset_Index indexType : indexMap.keySet() ) {
            IndexType current = indexType.indexType();
            List<IndexEntry> typeList = indexMap.get(indexType);
            
            // Create string index
            Index index;
            if ( current.isType( IndexType.STRING_IND ) ) {
                index = new StringIndex(typeList);
            }
            
            // Create integer index
            else {
                index = new StringIndex(typeList);
            }
            
            // Add index to output map
            output.put(indexType, index);
        }
        
        // Return output map
        return output;
    }
    
    
    /**
     * Abstract method to check whether associated index takes integers
     * 
     * @return boolean 
     */
    public abstract boolean isInt();
    
    
    /**
     * Abstract method to check whether associated index takes strings
     * 
     * @return boolean 
     */
    public abstract boolean isString();
    
    
    /**
     * Abstract method to return whether enum is a foreign key
     * 
     * @return boolean
     */
    public abstract boolean isForeignKey();
    
    
    /**
     * Return the datapoint edge associated with foriegn key
     * 
     * @return Datapoint_Edge
     */
    public abstract Datapoint_Edge getForeignKeyPartner();
}
