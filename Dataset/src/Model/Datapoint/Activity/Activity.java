/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Datapoint.Activity;

import Model.Datapoint.Item.Book;
import Model.Datapoint.Item.Student;
import Model.Datapoint.Datapoint_Edge;
import Model.Datapoint.Datapoint;
import Model.Datapoint.Datapoint_Type;
import Model.Datapoint.Item.StudentQueue;
import java.io.Serializable;


/**
 * Abstract Activity class from Datapoint Interface. Idea is that these objects
 *  share the Items declarative function, but are different in these datapoints connect
 *  Items together in a way that useful for the end-user. Allowing books & students to be
 *  themselves and these activities/events to also be themselves
 * 
 * @author kenna
 */
public abstract class Activity implements Datapoint, Serializable {
    
    
    // Shared attributes
    protected String startDate, endDate;
    protected Book book;
    protected Student student;
    protected StudentQueue studentQueue = new StudentQueue<Integer>();
    
    
    /**
     * Default null constructor
     */
    public Activity(){}
    
    
    /**
     * Constructor for shared attributes
     * 
     * @param id
     * @param book
     * @param student
     * @param startDate
     * @param endDate 
     */
    public Activity(Book book, Student student, String startDate, String endDate){
        this.book = book;
        this.student = student;
        this.studentQueue = book.getStudenQueue();
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    
    /**
     * Constructor from a borrow activity
     * 
     * @param borrow 
     * @param isString 
     */
    public Activity(Activity borrow, boolean isString){
        
        // Ideally throw an exception
        if ( borrow.isEdge(Datapoint_Edge.BORROW)) {
            
            // Set attributes
            this.book = borrow.getBook();
            this.student = borrow.getStudent();
            this.startDate = borrow.getStartDate();
            this.endDate = borrow.getEndDate();
            
            
            // Handle student queue from object/reading JSON dataset
            if( isString) {
                
                // Offer each student Id to queue
                for(Object studentId : borrow.getStudentQueue().getQueue() ) {
                    this.studentQueue.offer( (Integer) studentId);
                }
            }
            
            // Otherwise just set directly
            else {
                this.studentQueue = borrow.getStudentQueue();
            }
        }
    
    }
    
    /**
     * Return the associated datapoint type
     * 
     * @return Datapoint_Type-Activity
     */
    @Override
    public Datapoint_Type whichDatapoint(){
        return Datapoint_Type.ACTIVITY;
    }
    
    
    /**
     * Validate if the queried data point type
     *  matches this class
     * 
     * @param query
     * @return boolean
     */
    @Override
    public boolean isDatapoint(Datapoint_Type query){
        return query == Datapoint_Type.ACTIVITY;
    }
    
    
    /**
     * Return whether queried datapoint_type matches Activity
     * 
     * @param query
     * @return boolean
     */
    @Override
    public boolean isDatapoint(Datapoint query) {
        return query.whichDatapoint() == Datapoint_Type.ACTIVITY;
    }
    
    /**
     * Return the Stduent associated with the borrow datapoint
     * 
     * @return Student
     */
    public Student getStudent() {
        return this.student;
    }
    

    /**
     * Return the current student queue on book
     * @return 
     */
    public StudentQueue getStudentQueue(){
        return this.studentQueue;
    }
    
    /**
     * Get the date when borrow was created
     * 
     * @return String 
     */
    public String getStartDate() {
        return this.startDate;
    }
    
    
    /**
     * Get the required end date of the borrow
     * 
     * @return 
     */
    public String getEndDate() {
        return this.endDate;
    }
    
    /**
     * Return the book associated with the borrow datapoint
     * 
     * @return Book
     */
    public Book getBook(){
        return this.book;
    }
    
    /**
     * Edit the end date of this borrow
     * 
     * @param newDate 
     */
    public void setEndDate(String newDate) {
        this.endDate = newDate;
    }
    
    
    /**
     * Get student queue as string
     * 
     * @return 
     */
    protected String getQueueString(){
        String output = "\"";
        for (Object i : studentQueue.getQueue() ) {
            output += i.toString() + ";";
        }
        output += " \"";
        output = output.replace("; ", "");
        return output;
    }
}
