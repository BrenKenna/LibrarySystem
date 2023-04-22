/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Dataset.IndexCollection;

// Datapoints and types
import Model.Datapoint.Datapoint;
import Model.Datapoint.Datapoint_Edge;

// Index interface
import Model.Dataset.Index.Index;
import Model.Dataset.Index.IndexEntry;
import Model.Dataset.Index.IndexType;
import Model.Dataset.Index.IntIndex;
import Model.Dataset.Index.StringIndex;

// Supporting
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Wrap a collection of indexes for a dataset into a single object
 * 
 * @author kenna
 */
public class IndexCollection {
    
    // Index
    private Map<Dataset_Index, Index> indexes;
    
    
    /**
     * Default constructor
     */
    public IndexCollection(){}
    
    
    /**
     * Construct a dataset index map for a list of datapoints
     * 
     * @param type
     * @param dataset 
     */
    public IndexCollection(Datapoint_Edge type, List<Datapoint> dataset) {
        
        // Initalize and populate map with valid indexes
        this.indexes = new HashMap<>();
        Map<Dataset_Index, List<IndexEntry> > indexMap =  Dataset_Index.configureIndexEntries(type, dataset);
        
        // Configure objects index
        for ( Dataset_Index indexType : indexMap.keySet() ) {
            // System.out.println("Index for " + indexType + ", on " + type);
            IndexType current = indexType.indexType();
            List<IndexEntry> typeList = indexMap.get(indexType);
            
            // Create string index
            Index index;
            if ( current.isType( IndexType.STRING_IND ) ) {
                index = new StringIndex(typeList); // Should be done by enum
                // System.out.println("String index " + indexType + " = "  + index.getSize() );
            }
            
            // Create integer index
            else {
                index = new IntIndex(typeList);
                // System.out.println("Int index " + indexType + " = "  + index.getSize() );
            }
            
            // Add index to output map
            this.indexes.put(indexType, index);
        }
    }

    
    /**
     * Check if index is valid for datapoint edge
     * 
     * @param edge
     * @param dataInd
     * @return boolean
     */
    public boolean hasIndex(Datapoint_Edge edge, Dataset_Index dataInd) {
        return listIndexes(edge).indexOf(dataInd) != -1;
    }
    
    /**
     * List the indexed fields for a datapoint edge, all have a primary key
     * 
     * @param edge
     * @return List-Dataset_Index
     */
    public List<Dataset_Index> listIndexes(Datapoint_Edge edge) {
        
        // Initalize and scan enum for indexed fields on a datapoint edge
        List<Dataset_Index> validIndexes = new ArrayList<>();
        for( Dataset_Index dataInd : Dataset_Index.values() ) {
            if ( dataInd.partOfEdge(edge) ) {
                validIndexes.add(dataInd);
            }
        }
        
        // Return output
        return validIndexes;
    }
    
    /**
     * Return primary keys matching string for index
     * 
     * @param dataIndex
     * @param query
     * @return List-Integers - Primary Keys
     */
    public List<Integer> queryIndex(Dataset_Index dataIndex, String query) {
        
        // Initalize output and get PKs
        // System.out.println("\nPre-sort: " + dataIndex + " ==> " + indexes.get(dataIndex) );
        List<Integer> primaryKeys = indexes.get(dataIndex).search(query);
        // System.out.println("Post-sort: " + dataIndex + " ==> " + indexes.get(dataIndex) );
        
        
        // Handle no values
        if ( primaryKeys.isEmpty() ) {
            return null;
        }
        
        // Otherwise add data
        else {
            return primaryKeys;
        }
    }
    
    
    /**
     * Return primary keys matching int for index
     * 
     * @param dataIndex
     * @param query
     * @return List-Integers - Primary Keys
     */
    public List<Integer> queryIndex(Dataset_Index dataIndex, int query) {
        
        // Initalize output and get PKs
        // System.out.println("\nPre-sort: " + dataIndex + " ==> " + indexes.get(dataIndex) );
        List<Integer> primaryKeys = indexes.get(dataIndex).search(query);
        // System.out.println("Post-sort: " + dataIndex + " ==> " + indexes.get(dataIndex) );
        
        // Handle no values
        if ( primaryKeys.isEmpty() ) {
            return null;
        }
        
        // Otherwise add data
        else {
            return primaryKeys;
        }
    }
    

    /**
     * Get the index map
     * @return index map
     */
    public Map<Dataset_Index, Index> getIndexes() {
        return indexes;
    }

    /**
     * Set the index map
     * @param indexes 
     */
    public void setIndexes(Map<Dataset_Index, Index> indexes) {
        this.indexes = indexes;
    }

    
    /**
     * Return the values of an dataset index type
     * 
     * @param query
     * @return String
     */
    public String valuesOf(Dataset_Index query) {
        return indexes.get(query).valueString();
    }
    
    
    /**
     * Return the values & primary keys of a dataset type
     * 
     * @param query
     * @return String - "Value=X, PrimaryKey=Y;...;..." 
     */
    public String printIndex(Dataset_Index query) {
        return indexes.get(query).toString();
    }
    
    
    /**
     * Get number of elements in index
     * 
     * @param query
     * @return int
     */
    public int summarizeIndex(Dataset_Index query) {
        return indexes.get(query).getSize();
    }
    
    
    /**
     * Sort queried index ascending/descending
     * 
     * @param query
     * @param ascending 
     */
    public void sortIndex(Dataset_Index query, boolean ascending) {
        
        // Sort ascending
        if (ascending) {
            indexes.get(query).sort(true);
        }
        
        // Otherwise sort descendings
        else {
            indexes.get(query).sort(false);
        }
    }
    
    
    /**
     * Get the values for an index
     * 
     * @param query
     * 
     * @return List-Object: String, Int
     */
    public List<Object> getIndex(Dataset_Index query) {
        return indexes.get(query).getValues();
    }
}
