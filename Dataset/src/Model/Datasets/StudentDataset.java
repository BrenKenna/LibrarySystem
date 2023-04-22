/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Datasets;

import Model.Datapoint.Datapoint;
import Model.Datapoint.Datapoint_Edge;
import Model.Datapoint.Datapoint_Type;
import Model.Datapoint.Item.Student;
import Model.Dataset.Dataset;
import Model.Dataset.Dataset_Constants;
import Model.Dataset.Iterator;
import Controller.Request.Item.ItemRequest;
import Controller.Request.Item.StudentRequest;
import Controller.Request.Request;
import Model.Dataset.IndexCollection.IndexCollection;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;



/**
 * Borrow specific dataset
 * 
 * @author kenna
 */
public class StudentDataset extends Dataset{
    
    // Default costructor allowing bookdataset to be built
    public StudentDataset(){
        super();
        this.level = Dataset_Constants.STUDENT_LEVEL;
    };
    
    
    /**
     * Constructor with a list of datapoints
     * 
     * @param datapoints - List Datapoint
     */
    public StudentDataset(List<Datapoint> datapoints) {
        super(datapoints);
        this.level = Dataset_Constants.STUDENT_LEVEL;
        
        // Configure valid indexes
        this.indexes = new IndexCollection(Datapoint_Edge.STUDENT, this.dataset);
    }
    
    
    /**
     * Construct dataset with index
     * 
     * @param datapoints
     * @param edge 
     */
    public StudentDataset(List<Datapoint> datapoints, Datapoint_Edge edge) {
        super(datapoints, edge);
    }
    

    @Override
    public Datapoint_Edge whichEdge() {
        return Datapoint_Edge.STUDENT;
    }

    @Override
    public boolean isEdge(Datapoint query) {
        return query.whichEdge() == Datapoint_Edge.STUDENT;
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
            
            // Skip any lines without an ID
            if ( dataLine.contains("ID") ) {
                
                // Array of Key-Value: Book ID, First Name, Last Name, Title, Genre, N borrows, N returns, Student Queue, Active Student
                dataLine = dataLine.replace("{", "").replace("}", "");
                String[] data = dataLine.split(",");
                ItemRequest studentForm = new StudentRequest();
            
                for(String i : data) {
                    String[] j = i.replace(" \"", "\"").split(":");
                    studentForm.getForm().put((String) j[0].replace("\"", ""), j[1].replace(" ", "").replace("\"", ""));
                }
                
                // Fetch datapoint and handle borrows & return
                Student datapoint = (Student) Datapoint_Edge.STUDENT.getDatapoint(studentForm);
                if ( !studentForm.getForm().get("N Borrows").contains("null") ) {
                    datapoint.setBorrows( Integer.parseInt(studentForm.getForm().get("N Borrows")) );
                }
                if ( !studentForm.getForm().get("N Returns").contains("null") ) {
                    datapoint.setReturns( Integer.parseInt(studentForm.getForm().get("N Returns")) );
                }
                output.add(datapoint);
            }
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
        return Datapoint_Type.ITEM;
    }

    @Override
    public boolean isType(Datapoint query) {
        return Datapoint_Type.ITEM == query.whichDatapoint();
    }

    @Override
    public String returnJsonFile() {
        return Dataset_Constants.STUDENT_JSON;
    }

    @Override
    public String returnSerializedFile() {
        return Dataset_Constants.STUDENT_SER;
    }
    
    @Override
    protected void setDataset(List<Datapoint> datapoints) {
        while( datapoints.size() >= 1) {
            this.dataset.add( datapoints.remove(0) );
        }
        this.indexes = new IndexCollection(Datapoint_Edge.STUDENT, this.dataset);
    }
    
    
    /**
     * Return the book associated with ID
     * 
     * @param bookID
     * @return Datapoint - Book/null
     */
    @Override
    protected Datapoint getBook(int bookID) {
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
        return getDatapoint(studentID);
    }

    @Override
    protected Datapoint getBook(int bookID, int studentID) {
        return null;
    }
    
    
    @Override
    protected Request convertToRequest(Datapoint datapoint) {
        
        // Cast to student & return request
        Student student = (Student) datapoint;
        return new StudentRequest(
                student.getAutoID(),
                student.getfName(),
                student.getlName()
        );
    }
    
    /**
     * Serve student dataset prototype
     * @return Dataset-Student
     */
    @Override
    protected Dataset copyDataset() {
        List<Datapoint> clone = new ArrayList<>();
        for ( Datapoint data : this.dataset ) {
            clone.add(data);
        }
        return new StudentDataset(clone);
    }

    
    // This will be where new data append and index is rebuilt
    @Override
    protected void addIndex() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    protected void addIndexes() {
        this.indexes = new IndexCollection(Datapoint_Edge.STUDENT, this.dataset);
    }
}
