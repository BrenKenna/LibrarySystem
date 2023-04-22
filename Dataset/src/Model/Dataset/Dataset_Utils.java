/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Dataset;


// Datapoints + Factories + Generators
import Model.Datapoint.Generator.*;
import Model.Datapoint.Factory.*;
import Model.Datapoint.Activity.ReturnType;
import Model.Datapoint.Item.Book;
import Model.Datapoint.Datapoint_Edge;
import Model.Datapoint.Datapoint;
import Controller.Request.Activity.*;
import Controller.Request.Item.*;
import Model.Datasets.BookDataset;
import Model.Datasets.*;


// Requests
import Controller.Request.Request;


// Supporting
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Utility methods for dataset
 * 
 * @author kenna
 */
public class Dataset_Utils {
    
    // Instantiate Factory chain
    private final Factory dataPointFactory = Factory_Chain.getFactoryChain();
    
    // Random data generators
    private final Student_Generator studentGenerator = new Student_Generator();
    private final Book_Generator bookGenerator = new Book_Generator();
    private final Borrow_Generator borrowGenerator = new Borrow_Generator();
    private final Return_Generator generator = new Return_Generator();
    

    /**
     * 
     * @param dataLine
     * @return 
     */
    private Request parseLine(String dataLine) {
    
        // Swap the genre delimiter
        Request bookForm = null;
        String[] bookData;
        dataLine = dataLine.replace("|", ";");

        // Parse book with comma in title
        if (dataLine.contains("\"")) {

            // Get book title, then split array
            String bookTitle = dataLine.replace("\"", "\t").split("\t")[1];
            bookData = dataLine.split(",");

            // Construct & submit bookRequest to Datapoint factory, then add to output
            bookForm = new BookRequest(bookData[1], bookData[2], bookTitle, bookData[bookData.length - 1]);
        }

        // Otherwise do normal split
        else if (!dataLine.contains("\"") && dataLine.contains(",")) {

            // Split and generate a book request
            bookData = dataLine.split(",");
            bookForm = new BookRequest(bookData[1], bookData[2], bookData[3], bookData[4]);
        }
        
        // Return request
        return bookForm;
    }
    
    
    /**
     * 
     * @param inputFile
     * @param hasHeader
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public Dataset streamBooks(String inputFile, boolean hasHeader) throws FileNotFoundException, IOException {
    
        // Initalize output, loop variables
        Dataset output = new BookDataset();
        String dataLine;
        
        // Try initalize reader from input
        try {
            
            // Initalize reader
            BufferedReader reader = new BufferedReader( new FileReader(inputFile));
            
            // Skip header
            if ( hasHeader ) {
                dataLine = reader.readLine();
            }
            
            // Read all of the lines
            while( (dataLine = reader.readLine()) != null ) {
                Request bookForm = parseLine(dataLine);
                output.addDatapoint( dataPointFactory.validateRequest(Datapoint_Edge.BOOK, bookForm) );
            }
        }
        
        // Otherwise throw excetion
        catch(Exception ex) {
            throw ex;
        }
        
        
        // Return indexed book dataset
        output.delegate_addIndexes(Datapoint_Edge.BOOK);
        return output;
    }
    
    
    /**
     * Import books to an array list of datapoints
     * 
     * @param inputFile
     * @param hasHeader
     * @return List-Datapoint
     * 
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public List<Datapoint> importBooks(String inputFile, boolean hasHeader) throws FileNotFoundException, IOException {
    
        // Initalize output, loop variables
        List<Datapoint> output = new ArrayList<>();
        String dataLine;
        
        // Try initalize reader from input
        try {
            
            // Initalize reader
            BufferedReader reader = new BufferedReader( new FileReader(inputFile));
            
            // Skip header
            if ( hasHeader ) {
                dataLine = reader.readLine();
            }
            
            // Read all of the lines
            while( (dataLine = reader.readLine()) != null ) {
                Request bookForm = parseLine(dataLine);
                output.add( dataPointFactory.validateRequest(Datapoint_Edge.BOOK, bookForm) );
            }
        }
        
        // Otherwise throw excetion
        catch(Exception ex) {
            throw ex;
        }
        
        // Return list of books
        return output;
    }
    
    
    /**
     * Return a dataset of #Randomly generated students
     * 
     * @param nStudents
     * @return Dataset-Item-Student
     */
    public List<Datapoint>  generateStudents(int nStudents) {
        
        // Initalize student dataset
        List<Datapoint> studentList = new ArrayList<>();
        for(int i = 0; i < nStudents; i++) {
        
            // Generate request for a random student
            String[] studentData;
            Request studentForm;
            studentData = studentGenerator.getRandomData();
            studentForm = new StudentRequest(studentData[1], studentData[2]);
            studentList.add(dataPointFactory.validateRequest(Datapoint_Edge.STUDENT, studentForm));
        }
    
        // Return dataset
        return studentList;
    }
    
    
    /**
     * Return a dataset of #Randomly Generated books
     * 
     * @param nBooks
     * @return Dataset-Item-Book
     */
    public List<Datapoint> generateBooks(int nBooks) {
    
        // Generate random books and add to output dataset
        List<Datapoint> bookList = new ArrayList<>();
        for(int i = 0; i < nBooks; i++) {
        
            // Generate random book request
            String[]bookData = bookGenerator.getRandomData();
            Request bookForm = new BookRequest(bookData[1], bookData[2], bookData[3], bookGenerator.getGenresString());
            bookList.add( dataPointFactory.validateRequest(Datapoint_Edge.BOOK, bookForm) );
        }
    
        // Return book dataset
        return bookList;
    }
    
    
    /**
     * Return a dataset of #Randomly generated borrows from the input datasets
     * 
     * @param studentDataset
     * @param bookDataset
     * @param nBorrows
     * @return Dataset-Activity-Borrow
     */
    public List<Datapoint> generateBorrows(List<Datapoint> studentDataset, List<Datapoint> bookDataset, int nBorrows){
    
        // Initalize output and last book holder
        Datapoint lastBook = null;
        List<Datapoint> borrowList = new ArrayList<>();
        
        // Handle number of borrows
        int nStudents = studentDataset.size();
        int nBooks = bookDataset.size();
        if ( nStudents > nBooks ) {
            
            // Base update off the book dataset
            if ( nBorrows > nBooks) {
                nBorrows = (nBooks / 2);
            }
        }
        
        // Otherwise base update off the students
        else {
            if ( nBorrows > nStudents) {
                nBorrows = (nStudents / 2);
            }
        }
        
        // Generate borrow data: Even students borrow, odd students go to queue
        for(int i = 0; i < nBorrows; i++) {
        
            // Fetch student and book
            String[] borrowData;
            Datapoint book = bookDataset.get(i);
            Datapoint student = studentDataset.get(i);
            ActivityRequest borrowForm;
            
            // Use the new book
            if (  (i % 2) == 0 ) {
                System.out.println("\nGetting a new book, with new student");
                borrowData = borrowGenerator.genRand(); // ID, Start Date, End Date
                borrowForm = new BorrowRequest(0, book, student, borrowData[1], borrowData[2]);
            }
            
            // Otherwise use previous
            else {
                System.out.println("\nUsing old book, with new student");
                borrowData = borrowGenerator.genRand();
                borrowForm = new BorrowRequest(0, lastBook, student, borrowData[1], borrowData[2]);
            }
            
            // Create a borrow datapoint
            boolean bookState = borrowForm.evaluateBorrow();
            if ( bookState ) {
                System.out.println("Borrow datapoint created for: " + student.getAutoID() + ", post = " + bookState);
                borrowList.add(dataPointFactory.validateRequest(Datapoint_Edge.BORROW, borrowForm));
            }
            
            // Otherwise note that book has being borrowed
            else {
                System.out.println("You have being added to the queue");
            }
            
            // Update last book
            lastBook = book;
        }
    
        // Return borrow dataset
        return borrowList;
    }
    
    
    /**
     * Return a dataset of #Random returns from a borrow dataset
     * 
     * @param borrowList
     * @param nReturns
     * @return Dataset-Activity-Return
     */
    public List<Datapoint> generateReturns(List<Datapoint> borrowList, int nReturns){
    
        // Sample half of dataset if required amount exceeds dataset size
        if ( nReturns > borrowList.size()) {
            nReturns = (borrowList.size() / 2) ;
        }
        
        // Generate random data: Return date
        List<Datapoint> toRemove = new ArrayList<>();
        List<Datapoint> returnList = new ArrayList<>();
        for(int i = 0; i < nReturns; i++) {
        
            // Fetch student and book
            Datapoint borrow = borrowList.get(i);
            
            // Generate random borrow datapoint
            String[] returnData = generator.genRand();
            ActivityRequest returnForm = new ReturnRequest(borrow, returnData[3], ReturnType.getState());
            returnList.add(dataPointFactory.validateRequest(Datapoint_Edge.RETURN, returnForm));
            toRemove.add(borrow);
        }
    
        // Drop borrows
        for(Datapoint datapoint : toRemove) {
            borrowList.remove(datapoint);
        }
        
        // Return the dataset
        return returnList;
    }
    
    
    /**
     * Issue next book to the next student
     * 
     * @param book
     * @param studentDataset
     * @return Datapoint-Activity-Borrow
     */
    public Datapoint issueNext(Book book, Dataset studentDataset){
    
        // Fetch student data point
        Datapoint nextStudent;
        Datapoint output = null;
        
        // Try update null output
        int studentId = (Integer) book.getStudenQueue().poll();
        if (studentId != -1) {
            
            // Fetch student & generate random borrow request
            String[] borrowData;
            ActivityRequest borrowForm;
            nextStudent = studentDataset.getDatapoint(studentId);
            borrowData = borrowGenerator.genRand(); // ID, Start Date, End Date
            
            // Generate request and evalutate
            borrowForm = new BorrowRequest(0, book, nextStudent, borrowData[1], borrowData[2]);
            boolean bookState = borrowForm.evaluateBorrow();
            if ( bookState  ) {
                System.out.println("Borrow datapoint created for: " + nextStudent.getAutoID() + ", post = " + bookState);
                output = dataPointFactory.validateRequest(Datapoint_Edge.BORROW, borrowForm);
            }
            
            // Otherwise note that book has being borrowed
            else {
                System.out.println("You have being added to the queue");
            }
        }
        
        
        // Return output
        return output;
    }
    
    
    /**
     * Issue a random return for the input borrow, and remove from dataset
     *  if a valid return was generated
     * 
     * @param borrow
     * @return Datapoint-Activity-Return
     */
    public Datapoint issueReturn(Datapoint borrow){
        
        // Initalize output & random return data
        Datapoint output = null;
        String[] returnData; // Swap out for now related Date
        ActivityRequest returnForm;
        
        
        // Generate random borrow datapoint
        returnData = generator.genRand();
        returnForm = new ReturnRequest(borrow, returnData[3], ReturnType.getState());
        output = dataPointFactory.validateRequest(Datapoint_Edge.RETURN, returnForm);
    
        
        /* Only works if method is not executed within a loop,
        
        // Remove datapoint from borrow
        if ( output != null ) {
            borrowDataset.dropDatapoint(borrow);
        }
        
        */
        
        
        // Return output
        return output;
    }
    
    
    /**
     * Clear a list of borrows from borrow dataset
     * 
     * @param toRemove - List of borrow datapoints to remove
     * @param borrowDataset - Dataset to remove them from
     */
    public void clearBorrow(List<Datapoint> toRemove, Dataset borrowDataset) {
    
        // Clear list of borrows
        for(Datapoint datapoint : toRemove) {
        
            // Validate edge
            if (  datapoint.isEdge(Datapoint_Edge.BORROW) ) {
                borrowDataset.dropDatapoint(datapoint);
            }
        }
    }
    
    
    /**
     * Serialize object to file on local filesystem
     * 
     * @param dataset
     * @param outFile
     * @throws IOException 
     */
    public void writeSerializedList(Dataset dataset, String outFile) throws IOException{
    
        // Create output if not exists
        File output = checkFile(outFile);

        // Stream datapoints into output
        ObjectOutputStream outputStream = null;
        try {
            
            // Try writeout objects
            outputStream = new ObjectOutputStream(new FileOutputStream(output));
            for (Datapoint data : dataset.getDataset()) {
                Datapoint student =  data;
                outputStream.writeObject(student);
            }
        }
        
        
        // Otherwise throw exception
        catch(IOException ex) {
            throw ex;
        }
        
        // Close stream
        outputStream.close();
    }
    
    
    /**
     * Read objects
     * 
     * @param inputFile
     * @return List<Datapoint>
     * @throws IOException 
     */
    public List<Datapoint> readSerializedStudents(String inputFile) throws IOException {
    
        // Initalize output & input stream
        List<Datapoint> output = new ArrayList<>();
        ObjectInputStream inputStream;
        
        // Try read input
        try {
        
            // Read input until End Of File error: "resources/dataset/item/student.ser"
            inputStream = new ObjectInputStream(new FileInputStream(inputFile));
            boolean EOF = false;
            while (!EOF) {
                try {
                    
                    // Add datapoint
                    Datapoint data = (Datapoint) inputStream.readObject();
                    output.add(data);
                    
                } catch (ClassNotFoundException e) {
                    System.out.println(e);
                } catch (EOFException end) {
                    EOF = true;
                }
            }
        }
        
        // Otherwise throw exception
        catch(IOException ex) {
            throw ex;
        }
        
        // Return output
        inputStream.close();
        return output;
    }
    
    
    /**
     * Create file if not exists, or return null
     * 
     * @param input
     * @return File Object/null
     */
    private File checkFile(String input) {
    
        // Initalize output & file hadler
        boolean fileCreated = false; // Throw an exception instead
        File outFile = new File(input);
        
        
        // Check if file exists
        if (!outFile.exists()) {
            try {
                
                // Create
                outFile.createNewFile();
                fileCreated = true;
                
            } catch (IOException ex) {
                
                // Log exists
                fileCreated = false;
            }
        }
        
        
        // Return whether file was created
        return outFile;
    }
    
    
    /**
    // Get mock datastream
    public InputStream getMockDataStream(){
        System.out.println("Class loader name = " + classLoader.getName() + ", Parent name = " + classLoader.getParent().getName());
        System.out.println(ClassLoader.getSystemResourceAsStream("MOCK-DATA.csv"));
        System.out.println(classLoader.getParent().getResourceAsStream("/resources/MOCK-DATA.csv"));
        System.out.println( classLoader.getResource("MOCK_DATA.csv") );
        InputStream mockDataStream = classLoader.getResourceAsStream("/resources/MOCK_DATA.csv");
        return mockDataStream;
    }
    **/
    
}
