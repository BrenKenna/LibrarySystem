/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Datasets;


// Datapoint
import Controller.Request.Activity.ReturnRequest;
import Controller.Request.Request;
import Model.Datapoint.Activity.Return;
import Model.Datapoint.Datapoint;
import Model.Datapoint.Datapoint_Edge;
import Model.Datapoint.Datapoint_Type;


// Dataset
import Model.Dataset.Dataset;
import Model.Dataset.Dataset_Constants;
import Model.Dataset.IndexCollection.IndexCollection;
import Model.Dataset.Iterator;


// Supporting modules
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Return specific dataset
 * 
 * @author kenna
 */
public class ReturnDataset extends Dataset{
    
    
    /**
     * Default null constructor
     */
    public ReturnDataset(){
        super();
        this.level = Dataset_Constants.RETURN_LEVEL;
    };
    
    
    /**
     * Constructor with a list of datapoints
     * 
     * @param datapoints - List Datapoint
     */
    public ReturnDataset(List<Datapoint> datapoints) {
        super(datapoints);
        this.level = Dataset_Constants.RETURN_LEVEL;
        
        // Configure valid indexes
        this.indexes = new IndexCollection(Datapoint_Edge.RETURN, this.dataset);
    }
    
    
    /**
     * Construct dataset with index
     * 
     * @param datapoints
     * @param edge 
     */
    public ReturnDataset(List<Datapoint> datapoints, Datapoint_Edge edge) {
        super(datapoints, edge);
    }


    @Override
    public Datapoint_Edge whichEdge() {
        return Datapoint_Edge.RETURN;
    }

    @Override
    public boolean isEdge(Datapoint query) {
        return query.whichEdge() == Datapoint_Edge.RETURN;
    }

    
        
    @Override
    public List<Datapoint> readDataset() throws IOException {
    
        // Handle which target to read
        ArrayList<Datapoint> output = new ArrayList<>();
        String targetFile = returnJsonFile();
        String dataLine;
        
        // Read in data
        BufferedReader reader = new BufferedReader( new FileReader(targetFile));
        dataLine = reader.readLine();
            
        // Read all of the lines
        while( (dataLine = reader.readLine()) != null ) {
                
            // Array of Key-Value: Return ID, Start Date, End Date, Return Date, Return State, Book ID, Student ID, Book Queue
            dataLine = dataLine.replace("{", "").replace("}", "");
            String[] data = dataLine.split(",");
        }
        
        // Return output
        return output;
    }

    @Override
    public Iterator getIterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Datapoint_Type whichType() {
        return Datapoint_Type.ACTIVITY;
    }

    @Override
    public boolean isType(Datapoint query) {
        return Datapoint_Type.ACTIVITY == query.whichDatapoint();
    }

    @Override
    public String returnJsonFile() {
        return Dataset_Constants.RETURN_JSON;
    }

    @Override
    public String returnSerializedFile() {
        return Dataset_Constants.RETURN_SER;
    }
    
    @Override
    protected void setDataset(List<Datapoint> datapoints) {
        while( datapoints.size() >= 1) {
            this.dataset.add( datapoints.remove(0) );
        }
        this.indexes = new IndexCollection(Datapoint_Edge.RETURN, this.dataset);
    }
    
    
    /**
     * Return the book associated with ID
     * 
     * @param bookID
     * @return Datapoint - Book/null
     */
    @Override
    protected Datapoint getBook(int bookID) {
        
        // Scan dataset until found
        for(Datapoint data : dataset ) {
            
            // Cast to borrow to get active book ID
            Return borrow = (Return) data;
            int activeID = borrow.getBook().getAutoID();
            
            // Return datapoint on match
            if ( activeID == bookID ) {
                return data;
            }
        }
        
        // Return null
        return null;
    }

    
    /**
     * Get datapoint associated with student
     * 
     * @param studentID
     * @return Datapoint - Student/null
     */
    @Override
    protected Datapoint getStudent(int studentID) {
        
        // Scan dataset until found
        for(Datapoint data : dataset ) {
            
            // Cast to borrow to get active student ID
            Return borrow = (Return) data;
            int activeID = borrow.getStudent().getAutoID();
            
            // Return datapoint on match
            if ( activeID == studentID ) {
                return data;
            }
        }
        
        // Return null
        return null;
    }
    
    
    @Override
    protected Datapoint getBook(int bookID, int studentID) {
        
        // Scan dataset until found
        for(Datapoint data : dataset ) {
            
            // Cast to borrow to get active student ID
            Return borrow = (Return) data;
            int active_bookID = borrow.getBook().getAutoID();
            int active_studID = borrow.getStudent().getAutoID();
            
            // Return datapoint on match
            if ( active_bookID == bookID && active_studID == studentID ) {
                return data;
            }
        }
        
        // Return null
        return null;
    }
    
    @Override
    protected Request convertToRequest(Datapoint datapoint) {
        
        // Cast to return & return request
        Return returnDP = (Return) datapoint;
        return new ReturnRequest(
                returnDP.getAutoID(),
                returnDP.getBook(),
                returnDP.getStudent(),
                returnDP.getStartDate(),
                returnDP.getEndDate(),
                returnDP.getReturnDate(),
                returnDP.getReturnState()
        );
    }
    
    /**
     * Serve return dataset prototype
     * @return Dataset-Return
     */
    @Override
    protected Dataset copyDataset() {
        List<Datapoint> clone = new ArrayList<>();
        for ( Datapoint data : this.dataset ) {
            clone.add(data);
        }
        return new ReturnDataset(clone);
    }

    @Override
    protected void addIndex() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    protected void addIndexes() {
        this.indexes = new IndexCollection(Datapoint_Edge.RETURN, this.dataset);
    }
}
