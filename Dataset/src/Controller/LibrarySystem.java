 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


// Dataset chain & interface
import Model.Dataset.Dataset_Chain;
import Model.Dataset.Dataset;


// Datapoint Factory chain & interface
import Model.Datapoint.Factory.Factory_Chain;
import Model.Datapoint.Factory.Factory;
import Model.Datapoint.Datapoint;
import Model.Datapoint.Datapoint_Edge;


// Constructing requests for the Datapoint Factory
import Controller.Request.Request;
import Controller.Request.Activity.*; // Issuing borrows & returns
import Model.Dataset.Dataset_Utils;
import Model.Dataset.IndexCollection.Dataset_Index;


// Supporting
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Singleton controller for library database supporting CRUD operations. 
 * Library system was built on custom database implementation, along with 
 * custom index implementation, scope around querying string limited outside 
 * of exact matches, ranges for integers and starts with for string.
 * 
 * @author kenna
 */
public class LibrarySystem {
    
    // Attributes
    private static LibrarySystem instance = null;
    private static final Dataset database = Dataset_Chain.getNullDatasetChain();
    private static final Factory dataFactory = Factory_Chain.getFactoryChain();
    private static final Dataset_Utils dataUtils = new Dataset_Utils();
    
    
    /**
     * Private constructor for the library system by importing relevant data
     * 
     * @throws IOException 
     * @throws java.lang.ClassNotFoundException 
     */
    private LibrarySystem() throws IOException, ClassNotFoundException{
    
        // Try configure database
        List<Datapoint> data;
        try {
            
            // Import Books
            data = database.delegate_Deserialize(Datapoint_Edge.BOOK);
            database.delegate_setDataset(Datapoint_Edge.BOOK, data);
            
            // Import students
            data = database.delegate_Deserialize(Datapoint_Edge.STUDENT);
            database.delegate_setDataset(Datapoint_Edge.STUDENT, data);
            
            // Import borrows
            data = database.delegate_Deserialize(Datapoint_Edge.BORROW);
            if (data.size() > 0) {
                database.delegate_setDataset(Datapoint_Edge.BORROW, data);
            }
            
            // Import returns
            data = database.delegate_Deserialize(Datapoint_Edge.RETURN);
            if (data.size() > 0) {
                database.delegate_setDataset(Datapoint_Edge.RETURN, data);
            }
        }
        
        catch(Exception ex) {
            throw ex;
        }
    }
    
    
    /**
     * Private constructor for the library system by importing relevant data
     * 
     * @throws IOException 
     * @throws java.lang.ClassNotFoundException 
     */
    private LibrarySystem(boolean randomData) throws IOException, ClassNotFoundException{
    
        // Try configure database
        List<Datapoint> data, bookList, studentList, borrowList, returnList;
        try {
            
            // Generate random data
            if (randomData) {
                
                // Generate random data
                bookList = dataUtils.generateBooks(100);
                studentList = dataUtils.generateStudents(100);
                borrowList = dataUtils.generateBorrows(studentList, bookList, 50);
                returnList = dataUtils.generateReturns(borrowList, 50);
                
                
                // Generate datasets
                database.delegate_setDataset(Datapoint_Edge.BOOK, bookList);
                database.delegate_setDataset(Datapoint_Edge.STUDENT, studentList);
                database.delegate_setDataset(Datapoint_Edge.BORROW, borrowList);
                database.delegate_setDataset(Datapoint_Edge.RETURN, returnList);
            }
            
            // Otherwise proceed
            else {
                
                // Import books
                data = database.delegate_Deserialize(Datapoint_Edge.BOOK);
                database.delegate_setDataset(Datapoint_Edge.BOOK, data);

                // Import students
                data = database.delegate_Deserialize(Datapoint_Edge.STUDENT);
                database.delegate_setDataset(Datapoint_Edge.STUDENT, data);

                // Import borrows
                data = database.delegate_Deserialize(Datapoint_Edge.BORROW);
                if (data.size() > 0) {
                    database.delegate_setDataset(Datapoint_Edge.BORROW, data);
                }

                // Import returns
                data = database.delegate_Deserialize(Datapoint_Edge.RETURN);
                if (data.size() > 0) {
                    database.delegate_setDataset(Datapoint_Edge.RETURN, data);
                }
            }
            
        }
        
        catch(Exception ex) {
            throw ex;
        }
    }
    
    /**
     * Instantiate or return previously constructed library system
     * 
     * @return LibrarySystem
     * @throws IOException 
     * @throws java.lang.ClassNotFoundException 
     */
    public static LibrarySystem getInstance() throws IOException, ClassNotFoundException {
        
        // Handle delivering instance/instantiating library
        if(instance == null) {
            instance = new LibrarySystem();
        }
        
        // Return instance
        return instance;
    }
    
    
    /**
     * Instantiate or return previously constructed library system from random data. Debugging tool
     * 
     * @param randomData
     * @return LibrarySystem
     * @throws IOException 
     * @throws java.lang.ClassNotFoundException 
     */
    public static LibrarySystem getInstance(boolean randomData) throws IOException, ClassNotFoundException {
        
        // Handle delivering instance/instantiating library
        if(instance == null) {
            instance = new LibrarySystem(randomData);
        }
        
        // Return instance
        return instance;
    }
    
    
    /**
     * Once off method to import the mock "book" data
     * 
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     * @return LibrarySystem
     */
    public static LibrarySystem getInstanceWithMockData() throws IOException, ClassNotFoundException {
        
        // Instantiate if not already
        if(instance == null) {
            instance = new LibrarySystem(true);
        }
        
        // Import mock data
        List<Datapoint> books = dataUtils.importBooks("resources/mock-data/MOCK_DATA.csv", true);
        System.out.println(books.get(0));
        for ( Datapoint data : books ) {
            database.delegate_Add(data);
        }
        database.delegate_addIndexes(Datapoint_Edge.BOOK);
        
        // Return instance
        return instance;
    }
    
    
    /**
     * Private method to sanity check deserialized data
     * 
     * @param dataList 
     */
    private void checkImport(List<Datapoint> dataList) {
        int counter = 0;
        while( counter < 5 && counter < dataList.size() ) {
            System.out.println(dataList.get(counter));
            counter++;
        }
    }
    
    /**
     * Export current dataset to file
     * 
     * @param type
     * @throws IOException 
     */
    public void saveDataset(Datapoint_Edge type) throws IOException {
        database.delegate_writeDataset(type);
    }
    
    
    /**
     * Serialize dataset
     * 
     * @param type
     * @throws IOException 
     */
    public void seralizeDataset(Datapoint_Edge type) throws IOException {
        database.delegate_SerializedFile(type);
    }
    
    
    /**
     * Export all datasets to file
     * 
     * @throws IOException 
     */
    public void saveDB() throws IOException {
        for(Datapoint_Edge table : Datapoint_Edge.values()) {
            if (!table.isDatapoint("activity")) {
                System.out.println("Saving table: " + table);
                saveDataset(table);
            }
        }
    }
    
    
    /**
     * Serialize database
     * @throws IOException 
     */
    public void serializeDB() throws IOException {
        for(Datapoint_Edge table : Datapoint_Edge.values()) {
            System.out.println("Saving table: " + table);
            seralizeDataset(table);
        }
    }
    
    
    /**
     * Select all datapoints from a dataset
     * 
     * @param type
     * @return List-Datapoint
     */
    public List<Datapoint> selectAll(Datapoint_Edge type) {
        return database.delegate_getDataset(type);
    }
    
    
    /**
     * Select all datapoints from a dataset
     * 
     * @param type
     * @return List-Datapoint
     */
    public List<String> selectAllString(Datapoint_Edge type) {
        List<String> output = new ArrayList<>();
        for(Datapoint data : database.delegate_getDataset(type) ) {
            output.add(data.toJsonString());
        }
        return output;
    }
    
    
    /**
     * Select all from dataset order in dataset/supplied if not already sorted
     * 
     * @param edge
     * @param indexType
     * @param ascending
     * @return List-Datapoint
     */
    public List<Datapoint> selectAllIndex(Datapoint_Edge edge, Dataset_Index indexType, boolean ascending) {
        return database.delegate_getDatasetByIndex(edge, indexType, ascending);
    }
    
    
    /**
     * Return dataset as string by index order 
     * 
     * @param edge
     * @param indexType
     * @param ascending
     * @return List-String
     */
    public List<String> selectAllIndexString(Datapoint_Edge edge, Dataset_Index indexType, boolean ascending) {
        List<String> output = new ArrayList<>();
        for(Datapoint data : database.delegate_getDatasetByIndex(edge, indexType, ascending) ) {
            output.add(data.toJsonString());
        }
        return output;
    }
    
    /**
     * Insert a datapoint to database from request
     * 
     * @param type
     * @param form 
     */
    public void insert(Datapoint_Edge type, Request form) {
        database.delegate_Add(dataFactory.validateRequest(type, form));
    }
    
    
    /**
     * Debugging: Insert datapoint to database from valid object
     * 
     * @param datapoint 
     */
    public void insert(Datapoint datapoint) {
        database.delegate_Add(datapoint);
    }
    
    
    /**
     * Query dataset by ID
     * 
     * @param type
     * @param id
     * @return Datapoint - valid/null
     */
    public Datapoint queryID(Datapoint_Edge type, int id) {
        
        // Return result
        Datapoint output = database.delegate_getByIndex(type, id);
        return output;
    }
    
    
    /**
     * Return a request object for getting datapoint by index
     * 
     * @param type
     * @param id
     * @return Request
     */
    public Request queryID_Reqest(Datapoint_Edge type, int id) {
        
        // Return result
        return database.delegate_RequestConversion(database.delegate_getByIndex(type, id)) ;
    }
    
    /**
     * Query an index for an int
     * 
     * @param type
     * @param indexType
     * @param query
     * @return List-String - Item/Activity 
     */
    public List<String> queryByIndex(Datapoint_Edge type, Dataset_Index indexType, int query) {
        
        // Initalize output and run query
        List<String> output = new ArrayList<>();
        List<Datapoint> results = database.delegateIndexQuery(type, indexType, query);
        
        // Populate output if not null
        if ( results != null ) {
            output = new ArrayList<>();
            for(Datapoint row : results) {
                output.add(row.toJsonString());
            }
        }
        
        // Return results
        return output;
    }
    
    
    /**
     * Query an index for an string
     * 
     * @param type
     * @param indexType
     * @param query
     * @return List-String - Item/Activity 
     */
    public List<String> queryByIndex(Datapoint_Edge type, Dataset_Index indexType, String query) {
        
        // Initalize output and run query
        List<String> output = new ArrayList<>();
        List<Datapoint> results = database.delegateIndexQuery(type, indexType, query);
        
        // Populate output if not null
        if ( results != null ) {
            output = new ArrayList<>();
            for(Datapoint row : results) {
                output.add(row.toJsonString());
            }
        }
        
        // Return results
        return output;
    }
    
    
    /**
     * Issue a borrow for a book from a student, or add student to queue
     * 
     * @param bookID
     * @param studentID
     * @return String - Message to client
     */
    public String issueBorrow(int bookID, int studentID) {
        
        // Get book and student
        String outputMsg;
        Datapoint book = queryID(Datapoint_Edge.BOOK, bookID);
        Datapoint student = queryID(Datapoint_Edge.STUDENT, studentID);
        
        // Check null record
        if (book == null) {
            return "Book not found";
        }
        if (student == null) {
            return "Student not found";
        }
        
        // Generate borrow request & evaluate on student level
        ActivityRequest borrowForm = new BorrowRequest(book, student);
        if ( !borrowForm.evaluateStudent() ) {
            return "Invalid borrow, please check student is not already borrowing book or has reched borrow limit";
        }
        
        // Evaluate borrow on book level
        if ( borrowForm.evaluateBorrow() ) {

            // Set output message
            outputMsg = "Borrow datapoint created for: student = " + student.getAutoID() + ", book = " + book.getAutoID();
            insert(Datapoint_Edge.BORROW, borrowForm);
        }
            
        // Otherwise note that book has being borrowed
        else {
            outputMsg = "You have being added to the queue";
        }
        
        // Return message
        return outputMsg;
    }
    
    
    /**
     * Issue a return on a book
     * 
     * @param bookID
     * @return String - Message to client about ontime/late return
     */
    public String issueReturn(int bookID) {
        
        // Get book and borrow
        String outputMsg = "Book returned ontime";
        Datapoint borrow = database.delegate_getBook(Datapoint_Edge.BORROW, bookID);

        // Generate return request
        ActivityRequest returnForm = new ReturnRequest(borrow);
        if ( returnForm.isLate() ) {
            outputMsg = "Book returned late";
        }
        
        // Generate datapoint and output message to client
        insert(Datapoint_Edge.RETURN, returnForm);
        database.delegate_Drop(borrow);
        return outputMsg;
    }
    

    /**
     * Issue return on borrow from a student for a book
     * 
     * @param bookID
     * @param studentID
     * @return String - Message to client
     */
    public String issueReturn(int bookID, int studentID) {
        
        // Get borrow involving book and student
        String outputMsg = "Book returned ontime";
        Datapoint borrow = database.delegate_getBook(Datapoint_Edge.BORROW, bookID, studentID);

        // Handle null record
        if (borrow == null) {
            return "Active borrow for book = " + bookID + ", and student = " + studentID + " not found";
        }
        
        // Generate return request
        ActivityRequest returnForm = new ReturnRequest(borrow);
        if ( returnForm.isLate() ) {
            outputMsg = "Book returned late";
        }
        
        // Generate datapoint and output message to client
        insert(Datapoint_Edge.RETURN, returnForm);
        database.delegate_Drop(borrow);
        return outputMsg;
    }
    
    
    /**
     * Update indexes in system
     */
    public void updateIndexes() {
        for ( Datapoint_Edge edge : Datapoint_Edge.values() ) {
            if ( database.delegate_getSize(edge) > 0 ) {
                database.delegate_addIndexes(edge);
            }
        }
    }
    
    
    /**
     * Fetch size of current dataset
     * @param edge
     * @param zeroIndexed
     * 
     * @return size of dataset
     */
    public int getDatasetSize(Datapoint_Edge edge, boolean zeroIndexed) { 
        if ( zeroIndexed ) {
            return database.delegate_getSize(edge)-1;
        }
        else {
            return database.delegate_getSize(edge);
        }
    }
    
 
    /**
     * Return list of datapoints associated with a foreign key
     * 
     * @param edge
     * @param foreignKey
     * @return List String
     */
    public List<String> innerJoin(Datapoint_Edge edge, Dataset_Index foreignKey) {
        return getResultSet( database.delegate_InnerJoin(edge, foreignKey) );
    }
    
    
    /**
     * Convert datapoint list to JSON string list
     * 
     * @param input
     * @return String List
     */
    public List<String> getResultSet(List<Datapoint> input) {
        List<String> output = new ArrayList<>();
        for(Datapoint data : input) {
            output.add(data.toJsonString());
        }
        return output;
    }
}
