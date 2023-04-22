/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Datapoint.Item;

import Model.Datapoint.Datapoint_Edge;
import Model.Datapoint.Datapoint;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Concrete Item of the Datapoint Interface.
 * 
 * Todo:
 *  i). Borrowing array -> Book-IDs?
 *  ii). Drop borrowDate & due date / do this some other way?
 * 
 * @author kenna
 */
public class Student extends Item {
    
    // Attributes
    private String fName, lName;
    private int nBorrows;
    private final BorrowSet borrowSet;
    
    
    // Auto-incremented ID
    private static int currentID = -1;
    private int autoID;
    
    
    // How relevant is this really for a student?
    private String borrowDate;
    private String dueDate;
    private int nReturns;
    private Map<Genre, Integer> genreCount;
    
    
    /**
     * Default null constructor
     */
    public Student(){
        
        // Set auto-increment ID
        this.autoID = setAutoID();
        this.borrowSet = new BorrowSet();
        this.genreCount = new HashMap<>();
        for(Genre genre : Genre.values()) {
            this.genreCount.put(genre, 0);
        }
    }
    
    
    /**
     * Constructor with ID & name
     * @param fName
     * @param lName 
     */
    public Student(String fName, String lName) {
        
        // Set auto-increment ID
        this.autoID = setAutoID();
        this.genreCount = new HashMap<>();
        this.fName = fName;
        this.lName = lName;
        this.borrowSet = new BorrowSet();
        
        // Manage genre counts
        this.genreCount = new HashMap<>();
        for(Genre genre : Genre.values()) {
            this.genreCount.put(genre, 0);
        }
    }
    
    /**
     * Method to set auto-incremented ID during construction
     * @return playerID 
     */
    private static int setAutoID(){
        currentID++;
        return currentID;
    }
    
    
    /**
     * Get auto-incremented ID
     * 
     * @return int - ID 
     */
    @Override
    public int getAutoID(){
        return this.autoID;
    }
    
    
    /**
     * Update the auto-incremented ID
     * @param newVal 
     */
    @Override
    public void updateCurrentID(int newVal){
        Student.currentID = newVal;
        //Borrow.currentID = new AtomicInteger(newVal);
        //Borrow.currentID.setBorrow(newVal);
    }
    
    /**
     * Update the auto-incremented ID
     * @param newVal 
     */
    @Override
    public void updateAutoID(int newVal){
        this.autoID = newVal;
    }
    
    /**
     * Return count of rented genres
     * 
     * @return 
     */
    public Map<Genre, Integer> getGenreCount(){
        return this.genreCount;
    }
    
    
    /**
     * Convert genre count map to JSON string
     * 
     * @return String
     */
    public String getGenreCountString(){
        
        // Initalize output
        String output = "";
        for(Genre genre : Genre.values() ) {
            output += genre + "=" + genreCount.get(genre) + ";";
        }
        
        // Return output
        output += " }";
        output = output.replace("; }", " }");
        return output;
    }
    
    /**
     * Set the genre count
     * 
     * @param genreCount 
     */
    public void setGenreCount(Map<Genre, Integer> genreCount){
        this.genreCount = genreCount;
    }
    
    
    /**
     * Set genre count from string, ie import
     * @param genreCount 
     */
    public void setGenreCount(String genreCount) {
        for(String genreData : genreCount.replace("\"", "").split(";")) {
            
            // Get data
            Genre genre = Genre.whichGenre(genreData.split("=")[0]);
            int count = Integer.parseInt(genreData.split("=")[1]);
            
            // Add to map
            this.genreCount.put(genre, count);
        }
    }
    
    
    /**
     * Increment genres by one
     * 
     * @param genres - Set
     */
    public void incrementGenreCount(Set<Genre> genres) {
        for(Genre genre : genres) {
            int update = this.genreCount.get(genre)+1;
            this.genreCount.put(genre, update);
        }
    }

    
    /**
     * Return student's first name
     * 
     * @return String
     */
    public String getfName() {
        return this.fName;
    }
    
    /**
     * Return student's last name
     * 
     * @return String
     */
    public String getlName() {
        return this.lName;
    }
    
    /**
     * Return student's number of borrows
     * 
     * @return int
     */
    public int getnBorrows() {
        return this.nBorrows;
    }
    
    /**
     * Set the student's first name
     * @param nBorrows
     */
    public void setBorrows(int nBorrows){
        this.nBorrows = nBorrows;
    }
    
    /**
     * Return student's number of returns
     * 
     * @return int
     */
    public int getReturns() {
        return this.nReturns;
    }
    
    /**
     * Set the student's first name
     * @param nReturns
     */
    public void setReturns(int nReturns){
        this.nReturns = nReturns;
    }

    
    /**
     * Get the start date of students active borrow
     * 
     * @return String-dd/MM/yyyy 
     */
    public String getBorrowDate() {
        return this.borrowDate;
    }
    
    /**
     * Get the end date of students active borrow
     * 
     * @return String-dd/MM/yyyy 
     */
    public String getDueDate() {
        return this.dueDate;
    }
   
    /**
     * Set first name
     * @param fName 
     */
    public void setfName(String fName) {
        this.fName = fName;
    }
    
    /**
     * Set last name
     * @param lName 
     */
    public void setlName(String lName) {
        this.lName = lName;
    }

    
    /**
     * Set the date student borrowed books
     * 
     * @param borrowDate 
     */
    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }
    
    /**
     * Set the expected return date of books
     * @param dueDate 
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    
    public BorrowSet getBorrowSet() {
        return this.borrowSet;
    }
    
    /**
     * Increment number of borrows
     */
    public void updateBorrows(){
        this.nBorrows++;
    }
    
    /**
     * Increment number of returns
     */
    public void updateReturns(){
        this.nReturns++;
    }


    /**
     * Return instance as a JSON string
     * 
     * @return String
     */
    @Override
    public String toJsonString() {
        return "{"
                + " \"ID\": " + autoID 
                + ", \"First Name\": " + "\"" + fName + "\""
                + ", \"Last Name\": " + "\"" + lName + "\""
                + ", \"N Borrows\": " + nBorrows
                + ", \"N Returns\": " + nReturns
                + ", \"Borrowing\": " +  borrowSet.toString()
                + ", \"Borrow Date\": " + "\"" + borrowDate + "\""
                + ", \"Due Date\": " + "\"" + dueDate + "\""
                + ", \"Genre Count\": " + "\"" + getGenreCountString() + "\""
                + " }";
    }

    /**
     * Return instance as a string
     * 
     * @return String 
     */
    @Override
    public String toString() {
        return "Student{"
                + "autoID=" + autoID + ", fName=" + fName
                + ", lName=" + lName + ", nBorrows=" + nBorrows
                + ", nReturns=" + nReturns + ", borrowing=" + borrowSet.toString()
                + ", borrowDate=" + borrowDate + ", dueDate=" + dueDate
                + "}";
    }

    
    /**
     * Return the Datapoint_Edge of this concrete class
     * 
     * @return Datapoint_Edge-Student
     */
    @Override
    public Datapoint_Edge whichEdge() {
        return Datapoint_Edge.STUDENT;
    }

    
    /**
     * Validate whether the queried Datapoint_Edge matches
     *  this concrete class
     * 
     * @param query
     * @return boolean
     */
    @Override
    public boolean isEdge(Datapoint_Edge query) {
        return query == Datapoint_Edge.STUDENT;
    }

    
    /**
     * Validate whether the queried datapoints edge type
     *  matches this concrete class
     * 
     * @param query
     * @return boolean
     */
    @Override
    public boolean isEdge(Datapoint query) {
        return query.whichEdge() == Datapoint_Edge.STUDENT;
    }
}
