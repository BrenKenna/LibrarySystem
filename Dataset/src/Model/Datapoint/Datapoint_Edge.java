/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Datapoint;


// Constants to manage chain of command
import Model.Datapoint.Activity.Activity;
import Model.Datapoint.Activity.Return;
import Model.Datapoint.Activity.ReturnType;
import Model.Datapoint.Activity.Borrow;
import Model.Datapoint.Item.Genre;
import Model.Datapoint.Item.Item;
import Model.Datapoint.Item.Book;
import Model.Datapoint.Item.Student;
import Model.Dataset.Dataset_Constants;


// Generating datapoints


// Generating items from requests
import Controller.Request.Activity.ActivityRequest;
import Controller.Request.Item.ItemRequest;
import Controller.Request.Request;
import Model.Dataset.IndexCollection.Dataset_Index;
import java.util.ArrayList;


// Supporting modules
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Enum to aid translating a request for data type, to factory chain creating
 *  a specific object.
 * 
 *  Given that objects are now created from Request Types, getting closer
 *   to factory related things being in this one Enum
 * 
 * @author kenna
 */
public enum Datapoint_Edge {
    
    /**
     * Student datapoint edge
     */
    STUDENT {
        
        /**
         * Return this enum as a string
         */
        @Override
        public String toString() {
            return "Student";
        }

        /**
         * Return the datapoint type of this enum
         */
        @Override
        public Datapoint_Type whichDatapoint() {
            return Datapoint_Type.ITEM;
        }

        /**
         * Return the edge datapoint of this enum
         */
        @Override
        public Datapoint_Edge whichEdge() {
            return STUDENT;
        }

        /**
         * Validate that the queried datapoint type matches this enum
         */
        @Override
        public boolean isDatapoint(Datapoint_Type query) {
            return query == Datapoint_Type.ITEM;
        }
        
        /**
         * Validate that the queried string matches this enum
         */
        @Override
        public boolean isDatapoint(String query) {
            return "item".equals(query.toLowerCase());
        }

        /**
         * Validate the queried string matches this enum
         */
        @Override
        public boolean isEdge(String query) {
            return "student".equals(query.toLowerCase());
        }
        
        /**
         * Return the factory-chain level associated with this enum
         */
        @Override
        public int chainLevel() {
            return Dataset_Constants.ITEM_FACTORY_LEVEL;
        }
        
        /**
         * Validate the queried edge matches this enum
         */
        @Override
        public boolean isEdge(Datapoint_Edge query) {
            return query == STUDENT;
        }
        
        
        @Override
        public Item getDatapoint(Request form) {
            
            // Cast to an Item form & construct student
            ItemRequest studentForm = (ItemRequest) form;
            Student student = new Student(
                    studentForm.getForm().get("First Name"),
                    studentForm.getForm().get("Last Name")
            );
            

            // Parse & set relevant data depending on form state
            if ( studentForm.getBorrow() ) {
                
                // Set Date fields
                student.setBorrowDate(studentForm.getForm().get("Borrow Start Date"));
                student.setDueDate(studentForm.getForm().get("Return Date"));
                
                // Set borrowing stats
                int nBorrows = Integer.parseInt(studentForm.getForm().get("nBorrows"));
                student.setBorrows(nBorrows);
                //studentForm.getForm().get("Borrowing")
            }

            // Return student
            return student;
        }
        
        
        /**
         * Return level of Student Dataset 
         */
        @Override
        public int datasetLevel() {
            return Dataset_Constants.STUDENT_LEVEL;
        }
        
        @Override
        public List<Dataset_Index> getValidIndexTypes() {
            
            // List and add valid indexes
            List<Dataset_Index> output = new ArrayList<>();
            for (Dataset_Index query : Dataset_Index.values() ) {
                if ( query.partOfEdge(STUDENT) ) {
                    output.add(query);
                }
            }
            
            // Return output
            return output;
        }
    }, 
    
    
    /**
     * Book datapoint edge
     */
    BOOK {
        
        /**
         * Return this enum as a string
         */
        @Override
        public String toString() {
            return "Book";
        }

        /**
         * Return the datapoint type of this enum
         */
        @Override
        public Datapoint_Type whichDatapoint() {
            return Datapoint_Type.ITEM;
        }

        /**
         * Return the edge datapoint of this enum
         */
        @Override
        public Datapoint_Edge whichEdge() {
            return BOOK;
        }

        /**
         * Validate that the queried datapoint type matches this enum
         */
        @Override
        public boolean isDatapoint(Datapoint_Type query) {
            return query == Datapoint_Type.ITEM;
        }
        
        /**
         * Validate that the queried string matches this enum
         */
        @Override
        public boolean isDatapoint(String query) {
            return "item".equals(query.toLowerCase());
        }

        /**
         * Validate the queried string matches this enum
         */
        @Override
        public boolean isEdge(String query) {
            return "book".equals(query.toLowerCase());
        }
        
        /**
         * Return the factory-chain level associated with this enum
         */
        @Override
        public int chainLevel() {
            return Dataset_Constants.ITEM_FACTORY_LEVEL;
        }
        
        /**
         * Validate the queried edge matches this enum
         */
        @Override
        public boolean isEdge(Datapoint_Edge query) {
            return query == BOOK;
        }
        
        
        @Override
        public Item getDatapoint(Request form) {
            
            // Cast to an Item form
            ItemRequest bookForm = (ItemRequest) form; 
            
            
            // Validate input genres into a set
            Set<Genre> validGenres = new HashSet<>();
            if ( bookForm.getForm().get("Genres").replace("\"", "").contains(";") ) {
                for ( String genre : bookForm.getForm().get("Genres").replace("\"", "").split(";")) {
                    
                    // Add each valid genre
                    validGenres.add( Genre.whichGenre(genre) );
                }
            } else {
                
                // Valid single genre
                validGenres.add( Genre.whichGenre(bookForm.getForm().get("Genres").replace("\"", "")) );
            }
            
            
            // Handle queue
            if ( bookForm.getForm().containsKey("Student Queue") ) {
                
                // Handle studentID
                int activeID;
                String activeStudent = bookForm.getForm().get("Active Student");
                if (activeStudent.equals("null")) {
                    activeID = -1;
                }
                else {
                    activeID = Integer.parseInt(activeStudent);
                }
                
                
                // Return book
                // System.out.println(bookForm.getForm());
                return new Book(
                    Integer.parseInt(bookForm.getForm().get("ID")),
                    bookForm.getForm().get("Author First Name"), 
                    bookForm.getForm().get("Author Last Name"),
                    bookForm.getForm().get("Title"), 
                    bookForm.getForm().get("Genres").replace("\"", ""),
                    Integer.parseInt(bookForm.getForm().get("N Borrows")),
                    Integer.parseInt(bookForm.getForm().get("N Returns")),
                    bookForm.getForm().get("Student Queue"),  
                    activeID
                );
            }
            
            // Return book
            return new Book(
                    bookForm.getForm().get("Author First Name"), 
                    bookForm.getForm().get("Author Last Name"),
                    bookForm.getForm().get("Title"), 
                    validGenres
            );
        }
        
        
        /**
         * Return level of Book Dataset 
         */
        @Override
        public int datasetLevel() {
            return Dataset_Constants.BOOK_LEVEL;
        }
        
        @Override
        public List<Dataset_Index> getValidIndexTypes() {
            
            // List and add valid indexes
            List<Dataset_Index> output = new ArrayList<>();
            for (Dataset_Index query : Dataset_Index.values() ) {
                if ( query.partOfEdge(BOOK) ) {
                    output.add(query);
                }
            }
            
            // Return output
            return output;
        }
    },
    
    /**
     * Borrow datapoint edge
     */
    BORROW {
        
        /**
         * Return this enum as a string
         */
        @Override
        public String toString() {
            return "Borrow";
        }

        /**
         * Return the datapoint type of this enum
         */
        @Override
        public Datapoint_Type whichDatapoint() {
            return Datapoint_Type.ACTIVITY;
        }

        /**
         * Return the edge datapoint of this enum
         */
        @Override
        public Datapoint_Edge whichEdge() {
            return BORROW;
        }

        /**
         * Validate that the queried datapoint type matches this enum
         */
        @Override
        public boolean isDatapoint(Datapoint_Type query) {
            return query == Datapoint_Type.ACTIVITY;
        }
        
        /**
         * Validate that the queried string matches this enum
         */
        @Override
        public boolean isDatapoint(String query) {
            return "activity".equals(query.toLowerCase());
        }

        /**
         * Validate the queried string matches this enum
         */
        @Override
        public boolean isEdge(String query) {
            return "borrow".equals(query.toLowerCase());
        }
        
        /**
         * Return the factory-chain level associated with this enum
         */
        @Override
        public int chainLevel() {
            return Dataset_Constants.ACTIVITY_FACTORY_LEVEL;
        }
        
        /**
         * Validate the queried edge matches this enum
         */
        @Override
        public boolean isEdge(Datapoint_Edge query) {
            return query == BORROW;
        }
        
        /**
         * Generate a borrow datapoint from request form
         */
        @Override
        public Activity getDatapoint(Request form) {
            
            // Cast to activity form
            ActivityRequest actForm = (ActivityRequest) form;
            
            // Update #borrows
            actForm.getStudent().updateBorrows();
            actForm.getBook().updateBorrows();
            
            // Set borrow dates on student
            actForm.getStudent().setBorrowDate( actForm.getForm().get("Start Date") );
            actForm.getStudent().setDueDate( actForm.getForm().get("End Date") );
            
            // Update genre counts
            Set<Genre> genres = actForm.getBook().getGenre();
            actForm.getStudent().incrementGenreCount(genres);
            
            // Return activity datapoint
            Activity output = new Borrow(
                    actForm.getBook(),
                    actForm.getStudent(),
                    actForm.getForm().get("Start Date"),
                    actForm.getForm().get("End Date")
            );

            // Return output
            return output;
        }
        
        
        /**
         * Return level of Borrow Dataset 
         */
        @Override
        public int datasetLevel() {
            return Dataset_Constants.BORROW_LEVEL;
        }
        
        @Override
        public List<Dataset_Index> getValidIndexTypes() {
            
            // List and add valid indexes
            List<Dataset_Index> output = new ArrayList<>();
            for (Dataset_Index query : Dataset_Index.values() ) {
                if ( query.partOfEdge(BORROW) ) {
                    output.add(query);
                }
            }
            
            // Return output
            return output;
        }
    },
    
    /**
     * Return datapoint edge
     */
    RETURN {
        
        /**
         * Return this enum as a string
         */
        @Override
        public String toString() {
            return "Return";
        }
        
        /**
         * Return the datapoint type of this enum
         */
        @Override
        public Datapoint_Type whichDatapoint() {
            return Datapoint_Type.ACTIVITY;
        }

        /**
         * Return the edge datapoint of this enum
         */
        @Override
        public Datapoint_Edge whichEdge() {
            return RETURN;
        }

        /**
         * Validate that the queried datapoint type matches this enum
         */
        @Override
        public boolean isDatapoint(Datapoint_Type query) {
            return query == Datapoint_Type.ACTIVITY;
        }

        /**
         * Validate that the queried string matches this enum
         */
        @Override
        public boolean isDatapoint(String query) {
            return "activity".equals(query.toLowerCase());
        }

        /**
         * Validate the queried string matches this enum
         */
        @Override
        public boolean isEdge(String query) {
            return "return".equals(query.toLowerCase());
        }

        /**
         * Return the factory-chain level associated with this enum
         */
        @Override
        public int chainLevel() {
            return Dataset_Constants.ACTIVITY_FACTORY_LEVEL;
        }

        /**
         * Validate the queried edge matches this enum
         */
        @Override
        public boolean isEdge(Datapoint_Edge query) {
            return query == RETURN;
        }
        
        /**
         * Resolve the borrow datapoint form to a return
         */
        @Override
        public Activity getDatapoint(Request form) {
            
            // Cast to activity form
            ActivityRequest actForm = (ActivityRequest) form;

            
            // Generate a local borrow datapoint from request
            Activity originalBorrow = new Borrow(
                    actForm.getBook(),
                    actForm.getStudent(),
                    actForm.getForm().get("Start Date"),
                    actForm.getForm().get("End Date")
            );
            
            // Update #returns
            actForm.getStudent().updateReturns();
            actForm.getBook().updateReturns();
            
            
            // Validate the return date & set state
            ReturnType returnState;
            if( actForm.evaluateDate()) {
                returnState = ReturnType.LATE;
            }
            else {
                returnState = ReturnType.ONTIME;
            }
            
            // Update student dates
            if ( actForm.getStudent().getBorrowSet().isEmpty() ) {
                actForm.getStudent().setBorrowDate("");
                actForm.getStudent().setDueDate("");
            }
            
            // Return a validated return datapoint
            actForm.clearActiveStudent();
            return new Return(originalBorrow, actForm.getReturnDate(), returnState);
        }

        /**
         * Return level of Return Dataset 
         */
        @Override
        public int datasetLevel() {
            return Dataset_Constants.RETURN_LEVEL;
        }

        @Override
        public List<Dataset_Index> getValidIndexTypes() {
            
            // List and add valid indexes
            List<Dataset_Index> output = new ArrayList<>();
            for (Dataset_Index query : Dataset_Index.values() ) {
                if ( query.partOfEdge(RETURN) ) {
                    output.add(query);
                }
            }
            
            // Return output
            return output;
        }
    };
    
    
    /**
     * Return given enum as string
     * 
     * @return String
     */
    @Override
    public abstract String toString();
    
    
    /**
     * Abstract method to return chain level
     * 
     * @return int
     */
    public abstract int chainLevel();
    
    
    /**
     * Abstract level to return dataset level associated with datapoint edge
     * 
     * @return int
     */
    public abstract int datasetLevel();
    
    
    /**
     * Abstract method to return the root data point of an edge
     * 
     * @return Datapoint_Type 
     */
    public abstract Datapoint_Type whichDatapoint();
    
    
    /**
     * Abstract method to return the edge of self
     * 
     * @return Datapoint_Edge 
     */
    public abstract Datapoint_Edge whichEdge();
    
    
    /**
     * Method to check if an enum is a datapoint
     * 
     * @param query
     * @return boolean
     */
    public abstract boolean isDatapoint(Datapoint_Type query);
    
    
    /**
     * Abstract method to check if datapoint type match
     * @param query
     * @return 
     */
    public abstract boolean isDatapoint(String query);
    
    
    /**
     * Abstract method to check if edge type match
     * 
     * @param query
     * @return 
     */
    public abstract boolean isEdge(String query);
    
    
    /**
     * Method to handle input query
     * 
     * @param query
     * @return true/false
     */
    public abstract boolean isEdge(Datapoint_Edge query);
    
    /**
     * Static methods to validate string as valid edge
     * 
     * @param query
     * @return boolean
     */
    public static boolean hasEdge(String query) {
    
        // Initalize output and search parameters
        boolean found = false;
        Datapoint_Edge[] check = Datapoint_Edge.values();
        
        // Search until found or no more values
        int counter = 0;
        while ( !found && counter < check.length) {
            if ( check[counter].isEdge(query) ) {
                found = true;
            }
        }
        
        // Return result
        return found;
    }
    
    
    /**
     * Static methods to validate string as valid datapoint
     * 
     * @param query
     * @return boolean
     */
    public static boolean hasDatapoint(String query) {
    
        // Initalize output and search parameters
        boolean found = false;
        Datapoint_Edge[] check = Datapoint_Edge.values();
        
        // Search until found or no more values
        int counter = 0;
        while ( !found && counter < check.length) {
            if ( check[counter].isDatapoint(query) ) {
                found = true;
            }
        }
        
        // Return result
        return found;
    }
    
    
    /**
     * Get a request form for an activity datapoint
     * 
     * @param form
     * @return Activity - Borrow / Return
     */
    public abstract Datapoint getDatapoint(Request form);
    
    
    /**
     * Static method to return edges that match a datapoint type
     * 
     * @param type
     * @return Datapoint_Edge 
     */
    public static Datapoint_Edge[] getType(Datapoint_Type type){
        
        // Initalize output
        int counter = 0;
        Datapoint_Edge[] output = new Datapoint_Edge[ Dataset_Constants.N_DATAPOINTS ];

        
        // Scan edges
        for(Datapoint_Edge edge : Datapoint_Edge.values()) {
            if( edge.isDatapoint(type) ) {
                output[counter] = edge;
                counter++;
            }
        }
        
        // Return output
        return output;
    }
    
    
    /**
     * Get a list of indexes associated with datapoint
     * 
     * @return List Dataset_Index - PrimaryKey/First Name/Last Name/Title/N Borrows/N Returns
     */
    public abstract List<Dataset_Index> getValidIndexTypes();
}
