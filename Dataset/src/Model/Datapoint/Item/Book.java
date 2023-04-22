/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Datapoint.Item;

import Model.Datapoint.Datapoint_Edge;
import Model.Datapoint.Datapoint;
import java.util.HashSet;
import java.util.Set;

/**
 * A concrete Item Datapoint representing books in the library.
 * 
 * To do:
 *  i). Genre as a set instead of an array.
 *  ii). Student Queue:
 *      a). Use standard FIFO to debug.
 *      b). Replace this with custom implementation of FIFO queue.
 * 
 * Common consideration:
 *  - Does a book need to know about student, or some kind of 
 *  validated token representing a valid student?
 *      => The IDs have been the only thing I've being looking at from any toString logs
 * 
 * @author kenna
 */
public class Book extends Item {
    
    // Attributes
    private String fName, lName, title;
    private Set<Genre> genre;
    private int studentID = -1;
    private StudentQueue studentQueue = new StudentQueue<Integer>();
    private int nBorrows, nReturns;
        
    
    // Auto-incremented ID
    private static int currentID = -1;
    private int autoID;
    
    
    /**
     * Default null constructor
     */
    public Book(){
        this.autoID = setAutoID();
    }
    
    
    /**
     * Construct book without genre
     * 
     * @param fName
     * @param lName
     * @param title 
     */
    public Book(String fName, String lName, String title) {
        this.autoID = setAutoID();
        this.fName = fName;
        this.lName = lName;
        this.title = title;
    }
    
    
    /**
     * Constructor with genre
     * 
     * @param fName
     * @param lName
     * @param title
     * @param genres 
     */
    public Book(String fName, String lName, String title, Set<Genre> genres) {
        this.autoID = setAutoID();
        this.fName = fName;
        this.lName = lName;
        this.title = title;
        this.genre = genres;
    }
    
    
    /**
     * Constructor from reading json dump
     * 
     * @param id
     * @param fName
     * @param lName
     * @param title
     * @param inpGenre
     * @param nBorrows
     * @param nReturns
     * @param inpQueue
     * @param activeStudent 
     */
    public Book(int id, String fName, String lName, String title, String inpGenre, int nBorrows, int nReturns, String inpQueue, int activeStudent) {
        
        // Set easiset properties
        this.autoID = id;
        this.fName = fName;
        this.lName = lName;
        this.title = title;
        this.nBorrows = nBorrows;
        this.nReturns = nReturns;
        this.studentID = activeStudent;
        
        // Add each student queue
        if (inpQueue.contains("[0-9]+")) {
            for(String studID : inpQueue.split(";")) {
                this.studentQueue.offer( Integer.parseInt(studID));
            }
        }
        
        // Add genre
        this.genre = new HashSet<>();
        for ( String i : inpGenre.split(";")) {
            genre.add( Genre.whichGenre(i) );
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
        Book.currentID = newVal;
        //Book.currentID = new AtomicInteger(newVal);
        //Book.currentID.setBorrow(newVal);
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
     * Return author's first name
     * 
     * @return String
     */
    public String getfName() {
        return this.fName;
    }
    
    /**
     * Return author's last name
     * 
     * @return String 
     */
    public String getlName() {
        return this.lName;
    }
    
    /**
     * Return title of book
     * 
     * @return String
     */
    public String getTitle() {
        return this.title;
    }
    
    /**
     * Return number of borrow events
     * 
     * @return int 
     */
    public int getBorrows(){
        return this.nBorrows;
    }
    
    
    /**
     * Set the number of borrows
     * 
     * @param nBorrows 
     */
    public void setBorrows(int nBorrows) {
        this.nBorrows = nBorrows;
    }
    
    /**
     * Get the number of returns
     * 
     * @return int 
     */
    public int getReturns(){
        return this.nReturns;
    }
    
    /**
     * Set the number of returns
     * 
     * @param nReturns 
     */
    public void setReturns(int nReturns) {
        this.nReturns = nReturns;
    }
    
    /**
     * Get the genre set of the book
     * 
     * @return Set<Genre>
     */
    public Set<Genre> getGenre() {
        return this.genre;
    }

    /**
     * Set the genre Set
     * @param genre 
     */
    public void setGenre(Set<Genre> genre) {
        this.genre = genre;
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
     * Get the student queue
     * 
     * @return Queue - Integer 
     */
    public StudentQueue getStudenQueue() {
        return this.studentQueue;
    }
    
    /**
     * Set the student queue
     * @param studentQueue 
     */
    public void setStudenQueue(StudentQueue studentQueue) {
        this.studentQueue = studentQueue;
    }
    
    
    /**
     * Add student to queue
     * @param studentID 
     */
    public void addStudentID(int studentID){
        this.studentQueue.offer(studentID);
    }
    
    /**
     * Drop student
     * 
     * @param studentID 
     */
    public void dropStudentID(int studentID) {
        this.studentQueue.dropItem(studentID);
    }
    
    
    /**
     * Serve student
     * @return String
     */
    public int serveStudent(){
        return (Integer) this.studentQueue.poll();
    }

    /**
     * Is queue empty
     * @return true/false
     */
    public boolean queueEmpty(){
        return this.studentQueue.isEmpty();
    }
    
    
    /**
     * Return the size of the queue
     * 
     * @return int
     */
    public int queueSize(){
        return this.studentQueue.size();
    }

    /**
     * Return the studenId currently borrowing book
     * @return String
     */
    public int getStudentID(){
        return this.studentID;
    }
    
    /**
     * Set new borrower
     * @param studentID 
     */
    public void setStudenID(int studentID){
        this.studentID = studentID;
    }
    
    /**
     * Clear student ID
     */
    public void clearID(){
        this.studentID = -1;
    }
    
    /**
     * Return the Genre Set as a string
     * @return 
     */
    public String getGenreString() {
        String output = "\"";
        for (Genre i : this.genre ) {
            output += i + ";";
        }
        output += " \"";
        output = output.replace("; ", "").replace(" ", "");
        return output;
    }
    
    
    /**
     * Return student queue as string
     * @return 
     */
    private String getQueueString(){
        String output = "\"";
        for (Object i : studentQueue.getQueue() ) {
            output += i.toString() + ";";
        }
        output += " \"";
        output = output.replace("; ", "").replace(" ", "");;
        return output;
    }
    
    
    /**
     * Return instance as JSON string
     * @return 
     */
    @Override
    public String toJsonString() {
        return "{"
                + " \"ID\": " + autoID
                + ", \"Author First Name\": " + "\"" + fName + "\""
                + ", \"Author Last Name\": " + "\"" + lName + "\""
                + ", \"Title\": " + "\"" + title + "\""
                + ", \"Genres\": " + getGenreString() 
                + ", \"N Borrows\": " + nBorrows
                + ", \"N Returns\": " + nReturns
                + ", \"Student Queue\": " + getQueueString()
                + ", \"Active Student\": " + "\"" + studentID + "\""
                + " }";
    }

    
    /**
     * Return instance as string
     * @return 
     */
    @Override
    public String toString() {
        return "Book{" + "autoID=" + autoID
                + ", fName=" + fName + ", lName=" + lName
                + ", title=" + title + ", genre=" + genre
                + ", nBorrows=" + nBorrows + ", nReturns=" + nReturns
                + ", studentQueue=" + studentQueue + ", studentID=" + studentID
                + "}";
    }

    
    /**
     * Return the Datapoint_Edge of this concrete class
     * 
     * @return Datapoint_Edge-Book
     */
    @Override
    public Datapoint_Edge whichEdge() {
        return Datapoint_Edge.BOOK;
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
        return query == Datapoint_Edge.BOOK;
    }

    
    /**
     * Validate whether the queried datapoints edge type
     *  matches this conrete class
     * 
     * @param query
     * @return boolean
     */
    @Override
    public boolean isEdge(Datapoint query) {
        return query.whichEdge() == Datapoint_Edge.BOOK;
    }
}
