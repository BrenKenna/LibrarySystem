/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Datasets;

import Model.Datapoint.Datapoint;
import Model.Datapoint.Datapoint_Edge;
import Model.Datapoint.Datapoint_Type;
import Model.Datapoint.Item.Book;
import Model.Dataset.Dataset;
import Model.Dataset.Dataset_Constants;
import Model.Dataset.Iterator;
import Controller.Request.Item.BookRequest;
import Controller.Request.Item.ItemRequest;
import Controller.Request.Request;
import Model.Dataset.IndexCollection.IndexCollection;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



/**
 * Dataset specific to books
 * 
 * @author kenna
 */
public class BookDataset extends Dataset{
    
    
    // Default costructor allowing bookdataset to be built
    public BookDataset(){
        super();
        this.level = Dataset_Constants.BOOK_LEVEL;
    };
    
    // Constructor with arguments
    public BookDataset(List<Datapoint> datapoints) {
        
        // Configure dataset, primary key & chain level
        super(datapoints);
        this.level = Dataset_Constants.BOOK_LEVEL;
        
        // Configure valid indexes
        this.indexes = new IndexCollection(Datapoint_Edge.BOOK, this.dataset);
    }
    
    
    /**
     * Construct dataset with index
     * 
     * @param datapoints
     * @param edge 
     */
    public BookDataset(List<Datapoint> datapoints, Datapoint_Edge edge) {
        super(datapoints, edge);
    }
    

    @Override
    public Datapoint_Edge whichEdge() {
        return Datapoint_Edge.BOOK;
    }

    @Override
    public boolean isEdge(Datapoint query) {
        return query.whichEdge() == Datapoint_Edge.BOOK;
    }
    
    
    @Override
    public List<Datapoint> readDataset() throws IOException {
    
        // Handle which target to read
        ArrayList<Datapoint> output = new ArrayList<>();
        String targetFile = returnJsonFile();
        String dataLine;
        // System.out.println(targetFile);
        
        // Read in data
        BufferedReader reader = new BufferedReader( new FileReader(targetFile));
        dataLine = reader.readLine();
            
        // Read all of the lines
        while( (dataLine = reader.readLine()) != null ) {
            
            // Skip any lines without an ID
            if ( dataLine.contains("ID") ) {
                
                // Array of Key-Value: Book ID, First Name, Last Name, Title, Genre, N borrows, N returns, Student Queue, Active Student
                // System.out.println(dataLine);
                dataLine = dataLine.replace("{", "").replace("}", "");
                String[] data = dataLine.split(",");
                ItemRequest bookForm = new BookRequest();
            
                for(String i : data) {
                    String[] j = i.replace(": \"", ":\"").split(":");
                    bookForm.getForm().put((String) j[0].replace("\"", "").trim(), j[1].replace("\"", "").trim());
                }
                
                // Come back for the nBorrows, nReturns, Student Queue, Active Student
                // System.out.println(bookForm.getForm() + "\nGenre = " + bookForm.getForm().get("Genre"));
                Book datapoint = (Book) Datapoint_Edge.BOOK.getDatapoint(bookForm);
                if ( !bookForm.getForm().get("N Borrows").contains("null") ) {
                    datapoint.setBorrows( Integer.parseInt(bookForm.getForm().get("N Borrows")) );
                }
                if ( !bookForm.getForm().get("N Returns").contains("null") ) {
                    datapoint.setReturns( Integer.parseInt(bookForm.getForm().get("N Returns")) );
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
        return Dataset_Constants.BOOK_JSON;
    }

    @Override
    public String returnSerializedFile() {
        return Dataset_Constants.BOOK_SER;
    }

    @Override
    protected void setDataset(List<Datapoint> datapoints) {
        while( datapoints.size() >= 1) {
            this.dataset.add( datapoints.remove(0) );
        }
        this.indexes = new IndexCollection(Datapoint_Edge.BOOK, this.dataset);
    }
    
    
    /**
     * Return the book associated with ID
     * 
     * @param bookID
     * @return Datapoint - Book/null
     */
    @Override
    protected Datapoint getBook(int bookID) {
        
        return getDatapoint(bookID);
    }

    
    /**
     * Get datapoint associated with student
     * 
     * @param studentID
     * @return Datapoint - Student/null
     */
    @Override
    protected Datapoint getStudent(int studentID) {
        return null;
    }

    @Override
    protected Datapoint getBook(int bookID, int studentID) {
        return null;
    }

    @Override
    protected Request convertToRequest(Datapoint datapoint) {
        
        // Cast to book & return request
        Book book = (Book) datapoint;
        return new BookRequest(
                book.getAutoID(),
                book.getfName(),
                book.getlName(),
                book.getTitle(),
                book.getGenreString()
        );
    }

    /**
     * Serve book dataset prototype
     * @return Dataset-Book
     */
    @Override
    protected Dataset copyDataset() {
        List<Datapoint> clone = new ArrayList<>();
        for ( Datapoint data : this.dataset ) {
            clone.add(data);
        }
        return new BookDataset(clone);
    }


    @Override
    protected void addIndex() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void addIndexes() {
        this.indexes = new IndexCollection(Datapoint_Edge.BOOK, this.dataset);
    }
}
