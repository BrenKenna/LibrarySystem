/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;


// LibraryDB & communication interface
import Controller.LibrarySystem;
import Controller.Request.Request;
import Controller.Request.Item.BookRequest;
import Controller.Request.Item.StudentRequest;


// Manage user input & dataset types
import IOUtils.InputUtils;
import Model.Datapoint.Datapoint_Edge;
import Model.Datapoint.Item.Genre;
import Model.Dataset.IndexCollection.Dataset_Index;


// Supporting
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


/**
 * Adds library DBMS related functionality to menu interface
 * 
 * @author kenna
 */
public class LibraryMenu implements Menu {

    // Attributes
    private final LibrarySystem libraryDB;
    private final InputUtils inputHandler = new InputUtils(); 
    
    
    /**
     * 
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public LibraryMenu() throws IOException, ClassNotFoundException {
        
        // Persistence with serialization
        this.libraryDB = LibrarySystem.getInstance();
        
        // For importing book data
        // this.libraryDB = LibrarySystem.getInstanceWithMockData();
    }

    
    /**
     * Print desired number of rows, all or that results are empty
     * 
     * @param results
     * @param nRows 
     */
    @Override
    public void displayResults(List<String> results, int nRows) {
        
        // Print to desired number of rows
        Boundaries.HEADER.resultBoundaries();
        if( nRows < results.size() ) {
            Boundaries.HEADER.queryBoundaries();
            for(int i = 0; i < nRows; i++) {
                System.out.println(results.get(i));
            }
            Boundaries.FOOTER.queryBoundaries();
        }
        
        // Otherwise print all if not empty
        else if ( results.size() >= 1 ) {
            Boundaries.HEADER.queryBoundaries();
            for(String row : results) {
                System.out.println(row);
            }
            Boundaries.FOOTER.queryBoundaries();
        }
        
        // Otherwise print empty
        else {
            Boundaries.HEADER.queryBoundaries();
            System.out.println("No results found");
            Boundaries.FOOTER.queryBoundaries();
        }
        Boundaries.FOOTER.resultBoundaries();
    }
    
    /**
     * Method to help printing supplied results
     * 
     * @param results 
     */
    @Override
    public void displayResults(List<String> results) {
    
        // Determine whether to filter results
        if( results.size() > 30 ) {
            
            // Allow user to limit values displayed
            String prompt = "\n'" + results.size() + "' records returned, do you wish filter the number of rows displayed? (y/Y or n/N) >>> ";
            if ( inputHandler.getUserResponse(prompt) ) {
                
                // Get value
                prompt = "Please enter a value between 1 and " + results.size() + " >>> ";
                int nRows = inputHandler.getUserNumber(prompt, 1, results.size());
                displayResults(results, nRows);
            }
            
            // Otherwise print all
            else {
                displayResults(results, results.size());
            }
        }
        
        // Otherwise print all
        else {
            displayResults(results, results.size());
        }
    
        
        // Hold page until user is ready to proceed
        inputHandler.pageHolder();
    }
    
    
    /**
     * Display main menu
     * 
     * @return int
     */
    @Override
    public int mainMenu() {
        
        // List options & get choice
        int request = 0;
        Boundaries.TITLE.resultBoundaries();
        System.out.println("Please select an option:\n");
        System.out.println("1). Register a new student");
        System.out.println("2). Register a new book\n");
        System.out.println("3). Issue a new borrow for a book by a student");
        System.out.println("4). Submit a return of a book by a student\n");
        System.out.println("5). View all records for a dataset");
        System.out.println("6). View the sorted records for a dataset\n");
        System.out.println("7). Query a dataset");
        System.out.println("8). View Books/Students by Borrows/Returns\n");
        System.out.println("9). Update indexes for new data");
        System.out.println("10). Save database\n");
        System.out.println("11). Quit");
        request = inputHandler.getUserNumber("\n>>> ", 1, 11);
        
        // Handle movement
        switch(request) {
        
            // Go to the Student creation menu
            case 1:
                createStudent();
                break;
            
            // Go to the Book creation menu
            case 2:
                createBook();
                break;
                
            // Go to the Borrow creation menu
            case 3:
                createBorrow();
                break;
                
            // Go to the Return creation menu
            case 4:
                createReturn();
                break;
            
            // Go to the view all records enu
            case 5:
                request = selectAllDataset();
                break;

            
            // Inner join item activity
            case 6:
                request = selectAllIndex(); 
                break;
            
            // Go to the view all records enu
            case 7:
                request = queryDataset();
                break;
            
            // Go to the dataset query menu
            case 8:
                request = innerJoinItemActivity();
                break;
                
            // Update indexes
            case 9:
                Boundaries.printBlock("\nUpdating indexes for:\na). Student.\nb). Book\nc). Borrow\nd). Return");
                libraryDB.updateIndexes();
                request = 0;
                break;
                
            // Save database
            case 10:
                request = 0;
                Boundaries.printBlock();
                try {
                    libraryDB.serializeDB();
                } catch (IOException ex) {
                    System.out.println("Unexcepted error on saving database, please check the associated resources");
                }
                Boundaries.printBlock();
                inputHandler.pageHolder();
                break;

                
            // Otherwise set the quit flag for the client
            default:
                request = -1;
                try {
                    libraryDB.serializeDB();
                } catch (IOException ex) {
                    System.out.println("Unexcepted error on saving database, please check the associated resources");
                }
                break;

        }
        
        
        // Return client flag
        return request;
    }
    
    
    /**
     * Return to main menu or quit application
     * 
     * @return int
     */
    @Override
    public int placeMenu() {
        
        // List options & get choice
        int request;
        System.out.println("\nDo you wish quit or go to main menu?" );
        System.out.println("1). Main menu");
        System.out.println("2). Quit");
        request = inputHandler.getUserNumber("\n>>> ", 1, 2);
        
        // Handle movement
        switch(request) {
        
            // Go to the Student creation menu
            case 1:
                request = mainMenu();
                break;
            
            // Go to the Book creation menu
            default:
                System.out.println("\nSaving database before quitting");
                request = -1;
                try {
                    libraryDB.serializeDB();
                } catch (IOException ex) {
                    System.out.println("Unexcepted error on saving database, please check the associated resources");
                }
                break;
        }
        
        // Return menu flag
        return request;
    }
    
    
    /**
     * Request library system to create student via the request interface
     */
    private void createStudent() {
    
        // Create request & inputs
        String fName, lName;
        fName = inputHandler.getUserStringAlpha("\nPlease enter the First Name of the student >>> ");
        lName = inputHandler.getUserStringAlpha("\nPlease enter the Last Name of the student >>> ");

        
        // Request library system to create student and add to DB
        Request studentForm = new StudentRequest(fName, lName);
        libraryDB.insert(Datapoint_Edge.STUDENT, studentForm);
        Boundaries.embedBlock("Registered new student under '" + fName + " " + lName + "'");
        libraryDB.updateIndexes();
        inputHandler.pageHolder();
    }
    
    
    /**
     * Submit request to create a book to the library system
     */
    private void createBook() {
        
        // Create request & inputs
        String fName, lName, title, genre;
        fName = inputHandler.getUserStringAlpha("\nPlease enter the Authors First Name >>> ");
        lName = inputHandler.getUserStringAlpha("\nPlease enter the Authors Last Name >>> ");
        title = inputHandler.getUserString("\nPlease enter the Title of the book >>> ");
        if ( inputHandler.getUserResponse("\nDo you wish to register a genre for this book? y/Y or n/N >>> ") ) {
            genre = getUserGenre();
        }
        else {
            genre = "";
        }
        
        // Request library system to create student and add to DB
        Request bookForm = new BookRequest(fName, lName, title, genre);
        libraryDB.insert(Datapoint_Edge.BOOK, bookForm);
        Boundaries.printBlock();
        System.out.println("Registered new book under:");
        System.out.println("Title: '" + title + "'\nAuthor First name:'" + fName + "'\nAuthor Last name: '" + lName + "'\nGenre: " + Arrays.toString(genre.split(";")));
        Boundaries.printBlock();
        libraryDB.updateIndexes();
        inputHandler.pageHolder();
    }
    
    
    /**
     * Get genre string from user
     * 
     * @return String - Genre-1;Genre-2;Genre-N
     */
    private String getUserGenre() {
    
        // Initalize output
        int[] chosen;
        String[] genres;
        
        // Display choices
        int counter = 1;
        System.out.println("\nPlease enter genre(s), more than one separated by comma:");
        for(Genre genre: Genre.genreSelector() ) {
            System.out.println(counter + "). " + genre);
            counter++;
        }
        
        // Determine the number of genres
        String prompt = "\nPlease select value(s) between 1 and " + Genre.genreSelector().length + " >>> ";
        chosen = inputHandler.getIntArray(prompt, ",", 1, Genre.genreSelector().length);
        
        // Get genres
        genres = new String[chosen.length];
        for( int i = 0; i < chosen.length; i++ ) {
            int valueAt = chosen[i];
            genres[i] = Genre.genreSelector()[valueAt-1].toString();
        }
        
        // Return output
        return String.join(";", genres);
    }
    
    
    /**
     * Get book by an index
     * 
     * @return Request - Item - Book 
     */
    private Request getBook() {
        
        // Initalize output
        int bookIndex;
        
        // Choose book
        int nBooks = libraryDB.getDatasetSize(Datapoint_Edge.BOOK, true);
        bookIndex = inputHandler.getUserNumber("\nPlease select book by an index between 0 and " + nBooks + " >> ", 0, nBooks);
        return libraryDB.queryID_Reqest(Datapoint_Edge.BOOK, bookIndex);
    }
    
    
    /**
     * Get student by an index
     * 
     * @return Request - Item - Student
     */
    private Request getStudent() {
        
        // Initalize output
        int studentIndex;
        
        // Choose student
        int nStudents = libraryDB.getDatasetSize(Datapoint_Edge.STUDENT, true);
        studentIndex = inputHandler.getUserNumber("\nPlease select student by an index between 0 and " + nStudents + " >> ", 0, nStudents);
        return libraryDB.queryID_Reqest(Datapoint_Edge.STUDENT, studentIndex);
    }
    
    
    /**
     * Create a borrow datapoint or add student to borrowing queue on book,
     *  via the Request interface
     */
    private void createBorrow() {
        
        // Get student & book
        Request studentForm, bookForm;
        studentForm = getStudent();
        bookForm = getBook();
        
        // Maka a borrow request for book by student
        int bookID = Integer.parseInt( bookForm.getValue("ID") );
        int studentID = Integer.parseInt( studentForm.getValue("ID") );
        String libraryMsg = libraryDB.issueBorrow(bookID, studentID);
        Boundaries.embedBlock(libraryMsg);
        libraryDB.updateIndexes();
        inputHandler.pageHolder();
    }
    
    
    /**
     * Issue a return on a book by a student
     */
    private void createReturn() {
        
        // Get student & book
        Request studentForm, bookForm;
        studentForm = getStudent();
        bookForm = getBook();
        
        // Maka a return request for book by student
        int bookID = Integer.parseInt( bookForm.getValue("ID") );
        int studentID = Integer.parseInt( studentForm.getValue("ID") );
        String libraryMsg = libraryDB.issueReturn(bookID, studentID);
        Boundaries.embedBlock(libraryMsg);
        libraryDB.updateIndexes();
        inputHandler.pageHolder();
    }
    
    
    
    /**
     * LibraryMenu to choose datapoint edge (ie dataset)
     * 
     * @return Datapoint_Edge - Student,Book,Borrow,Return or null
     */
    private Datapoint_Edge selectEdge() {
    
        // Display choices
        int request;
        int choices = 1;
        System.out.println("\nPlease select a dataset:\n");
        for(Datapoint_Edge type :  Datapoint_Edge.values()){
            System.out.println((choices) + "). " + type.toString());
            choices++;
        }
        System.out.println(choices + "). Main menu/Quit");
        
        
        // Get user request
        request = inputHandler.getUserNumber("\n>>> ", 1, (choices+1));
        
        
        // Return edge
        if( request <= Datapoint_Edge.values().length ) {
            return Datapoint_Edge.values()[ request -1 ];
        }
        
        // Otherwise return null
        else {
            return null;
        }
    }
    
        
    /**
     * Select all records from a dataset
     * 
     * @return int
     */
    private int selectAllDataset() {
        
        // Initalize request
        int request = 0;
        Datapoint_Edge choice = selectEdge();
        
        // Display results
        if( choice != null ) {
            List<String> results = libraryDB.selectAllString(choice);
            displayResults(results);
        }
        
        // Otherwise go to quit/main
        else {
            request = placeMenu();
        }
        
        // Return menu flag
        return request;
    }
    
    
    /**
     * Get a valid index to query for a datapoint
     * 
     * @param edge
     * @return Dataset_Index
     */
    private Dataset_Index selectIndex(Datapoint_Edge edge) {
        
        // Display choices
        int request;
        int choices = 1;
        List<Dataset_Index> indexes = edge.getValidIndexTypes();
        System.out.println("\nPlease select an index to query:\n");
        for(Dataset_Index index :  indexes ){
            System.out.println((choices) + "). " + index.toString());
            choices++;
        }
        System.out.println( choices + "). Main menu/Quit");
        
        
        // Get user request
        request = inputHandler.getUserNumber("\n>>> ", 1, (choices+1));
        
        
        // Return edge
        if( request <= indexes.size() ) {
            return indexes.get(request-1);
        }
        
        // Otherwise return null
        else {
            return null;
        }
    }
    
    
    
    /**
     * Query selected index on dataset
     * 
     * @return - menu handler
     */
    public int queryDataset() {

        // Choose dataset
        List<String> results;
        int request = 0;
        Datapoint_Edge edge = selectEdge();
        if (edge == null) {
            return placeMenu();
        }
        
        // Choose index
        Dataset_Index index = selectIndex(edge);
        if (index == null) {
            return placeMenu();
        }
        
        // Handle user input based on index type
        if ( index.isInt() ) {
            int userInt = inputHandler.getUserNumber("\nPlease enter a number to search for " +  index.toString() + " >>> ");
            results = libraryDB.queryByIndex(edge, index, userInt);
        }
        
        // Otherwise get string input
        else {
            String userString = inputHandler.getUserString("\nPlease select enter a string to match " +  index.toString() + " >>> ");
            results = libraryDB.queryByIndex(edge, index, userString);
        }
        
        // Print results and return menu flag
        displayResults(results);
        return request;
    }
    
    
    /**
     * LibraryMenu to select all data from table, sorted ascending/descending by an index
     * 
     * @return menu handler 
     */
    public int selectAllIndex() {
        
        // Initalize handler and get table
        Datapoint_Edge edge = selectEdge();
        if (edge == null) {
            return placeMenu();
        }
        
        // Get column
        Dataset_Index indexType = selectIndex(edge);
        if (indexType == null) {
            return placeMenu();
        }
        
        // Get sorting direction
        boolean userBoolean = inputHandler.getUserResponse("\nSort by ascending order (y/Y or n/N) >>> ");
        
        // Display results
        List<String> results = libraryDB.selectAllIndexString(edge, indexType, userBoolean);
        displayResults(results);
        return 0;
    }
    
    
    /**
     * LibraryMenu to inspect library activity
     * 
     * @return 
     */
    public int innerJoinItemActivity() {
    
        // LibraryMenu options
        int request = 0;
        System.out.println("\nPlease select an option:\n");
        System.out.println("1). View active books");
        System.out.println("2). View students actively borrowing books");
        System.out.println("3). View borrowed books");
        System.out.println("4). View students who have borrowed books");
        System.out.println("5). Main menu/Quit");
        request = inputHandler.getUserNumber("\n>>> ", 1, 5);
        
        // Handle movement
        switch(request) {
        
            // Go to the Student creation menu
            case 1:
                displayResults( libraryDB.innerJoin(Datapoint_Edge.BORROW, Dataset_Index.BOOK_ID) );
                break;
            
            // Go to the Book creation menu
            case 2:
                displayResults(libraryDB.innerJoin(Datapoint_Edge.BORROW, Dataset_Index.STUDENT_ID));
                break;
                
            // Go to the Book creation menu
            case 3:
                displayResults(libraryDB.innerJoin(Datapoint_Edge.RETURN, Dataset_Index.BOOK_ID));
                break;
                
                
            // Go to the Book creation menu
            case 4:
                displayResults( libraryDB.innerJoin(Datapoint_Edge.RETURN, Dataset_Index.STUDENT_ID) );
                break;
                
                
            // Go to the Book creation menu
            default:
                request = placeMenu();
                break;
        }

        // Return menu handler
        return request;
    }


    /**
     * Display title banner
     * 
     * @return String
     */
    @Override
    public String displayTitle() {
        return Boundaries.TITLE.toString();
    }


    /**
     * Login not implemented
     * 
     * @param username
     * @param password
     * @return null
     */
    @Override
    public boolean login(String username, String password) {
        return true;
    }
}
