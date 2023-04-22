/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Datapoint;

/**
 *
 * @author kenna
 */
public class DatapointState {
    
    // IDs for data points
    private static int student;
    private static int book;
    private static int borrow;
    private static int returns;

    
    // Instance
    private static DatapointState instance = null;
    
    
    /**
     * Singleton state from scratch
     */
    private DatapointState(){
        student = -1;
        book = -1;
        borrow = -1;
        returns = -1;
    }
    
    
    /**
     * Singleton state from previous session
     * 
     * @param bookID
     * @param studentID
     * @param borrowID
     * @param returnsID 
     */
    private DatapointState(int bookID, int studentID, int borrowID, int returnsID){
        student = bookID;
        book = studentID;
        borrow = borrowID;
        returns = returnsID;
    }
    
    /**
     * Instantiate or return previously created instance
     * 
     * @return 
     */
    public static DatapointState getInstance(){
        if( instance == null) {
            instance = new DatapointState();
        }
        return instance;
    }
    
    
    /**
     * Get an updated version the current instance
     * 
     * @param book
     * @param student
     * @param borrow
     * @param returns
     * 
     * @return instance
     */
    public static DatapointState getUpdatedInstance(int book, int student, int borrow, int returns){
        if( instance == null) {
            instance = new DatapointState(book, student, borrow, returns);
            return instance;
        }
        
        // Set fields
        instance.setBook(book);
        instance.setStudent(student);
        instance.setBorrow(borrow);
        instance.setReturns(returns);
        return instance;
    }
    
    
    /**
     * Auto-increment and get student ID
     * 
     * @return next ID
     */
    public int getStudent() {
        student++;
        return student;
    }

    
    /**
     * Update the current studentID
     * 
     * @param student 
     */
    public void setStudent(int student) {
        DatapointState.student = student;
    }

    
    /**
     * Auto-increment and get book ID
     * 
     * @return next ID
     */
    public int getBook() {
        return book;
    }

    
    /**
     * Update the current bookID
     * 
     * @param book 
     */
    public void setBook(int book) {
        DatapointState.book = book;
    }

    
    /**
     * Auto-increment and get borrow ID
     * 
     * @return next ID
     */
    public int getBorrow() {
        return borrow;
    }

    
    /**
     * Update the current borrowID
     * 
     * @param borrow 
     */
    public void setBorrow(int borrow) {
        DatapointState.borrow = borrow;
    }

    
    /**
     * Auto-increment and get return ID
     * 
     * @return next ID
     */
    public int getReturns() {
        return returns;
    }

    
    /**
     * Update the current returnID
     * 
     * @param returns 
     */
    public void setReturns(int returns) {
        DatapointState.returns = returns;
    }
}
