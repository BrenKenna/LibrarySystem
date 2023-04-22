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
import java.util.Random;


// Towards persistence on primary keys
// import Model.Datapoint.DatapointState;
// import java.util.concurrent.atomic.AtomicInteger;


/**
 * Concrete Return type of Activity under the Datapoint Interface.
 * 
 * Considerations:
 *  => Maybe just leave it as Student-ID
 * 
 * @author kenna
 */
public class Return extends Activity {
    
    // Attributes
    private String returnDate;
    private ReturnType returnState;

    // Auto-incremented ID
    // private static final DatapointState currentID = DatapointState.getInstance();
    // private static final AtomicInteger currentID = new AtomicInteger(0);
    private static int currentID = -1;
    private int autoID;
    
    // Debugging: Set random state of return
    private final Random rand = new Random();
    private final int randNum = rand.nextInt(10);
   
    
    /**
     * Default null constructor
     */
    public Return(){
        this.autoID = setAutoID();
    }
    
    
    /**
     * Constructor to create return from an borrow activity
     * 
     * @param borrow
     * @param returnDate
     * @param returnState 
     */
    public Return(Activity borrow, String returnDate, ReturnType returnState) {
        super(borrow, true);
        this.returnDate = returnDate;
        this.returnState = returnState;
        this.autoID = setAutoID();
    }
    
    
    /**
     * Construct return with all data specified
     * 
     * @param id
     * @param book
     * @param student
     * @param startDate
     * @param endDate
     * @param returnDate
     * @param returnState 
     */
    public Return(Book book, Student student, String startDate, String endDate, String returnDate, ReturnType returnState){
        super(book, student, startDate, endDate);
        this.returnDate = returnDate;
        this.returnState = returnState;
        this.autoID = setAutoID();
    }
    
    
    /**
     * Constructor with random return state
     * 
     * @param id
     * @param book
     * @param student
     * @param startDate
     * @param endDate
     * @param returnDate 
     */
    public Return(Book book, Student student, String startDate, String endDate, String returnDate){
        super(book, student, startDate, endDate);
        this.returnDate = returnDate;
        this.autoID = setAutoID();
        
        // Randomly set state for now
        if ( randNum >= 5 ) {
            this.returnState = ReturnType.LATE;
        } 
        else {
            this.returnState = ReturnType.ONTIME;
        }
    }
    

    /**
     * Constructor from a borrow event and randomized state
     * 
     * @param borrow
     * @param returnDate 
     */
    public Return(Activity borrow, String returnDate) {
        super(borrow, false);
        this.returnDate = returnDate;
        this.autoID = setAutoID();
            
        // Randomly set state for now
        if ( randNum >= 5 ) {
            this.returnState = ReturnType.LATE;
        } 
        else {
            this.returnState = ReturnType.ONTIME;
        }
    }
    
    
    /**
     * Method to set auto-incremented ID during construction
     * @return playerID 
     */
    private static int setAutoID(){
        Return.currentID++;
        return Return.currentID;
        
        // From atomic integer
        // return currentID.incrementAndGet();
        
        // From custom DatapointState
        // return currentID.getReturns();
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
        Return.currentID = newVal;
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
     * Get the return state
     * 
     * @return ReturnType-OnTime/Late
     */
    public ReturnType getReturnState(){
        return this.returnState;
    }
    
    /**
     * Get the return date
     * @return String-dd/MM/yyyy
     */
    public String getReturnDate(){
        return this.returnDate;
    }
    
    
    /**
     * Set return state
     * @param returnState 
     */
    public void setReturnState(ReturnType returnState) {
        this.returnState = returnState;
    }

    
    /**
     * Print instance as string
     * @return String
     */
    @Override
    public String toString() {
        return "Return{" + "autoID=" + autoID
                + ", startDate=" + getStartDate() + ", endDate=" + getEndDate() 
                + " returnDate=" + returnDate + ", book=" + getBook().getAutoID()
                + ", bookActiveStudent=" + getBook().getStudentID() + ", bookQueue=" + getQueueString()
                + ", student=" + getStudent().getAutoID() + ", returnState=" + returnState + '}';
    }    

    
    /**
     * Print instance as JSON string
     * @return String
     */
    @Override
    public String toJsonString() {
        return "{"
                + " \"ID\": " + autoID
                + ", \"Start Date\": " + "\"" + getStartDate() + "\""
                + ", \"End Date\": " + "\"" + getEndDate() + "\""
                + ", \"Return Date\": " + "\"" + returnDate + "\""
                + ", \"Return State\": " + "\"" + returnState + "\""
                + ", \"Book ID\": " + "\"" + getBook().getAutoID() + "\""
                + ", \"Student ID\": " + "\"" + getStudent().getAutoID() + "\""
                + ", \"Book Queue\": " + getQueueString()
                +" }";
    }
    
    /**
     * Return the Datapoint_Edge of this concrete class
     * 
     * @return Datapoint_Edge-Return
     */
    @Override
    public Datapoint_Edge whichEdge() {
        return Datapoint_Edge.RETURN;
    }

    
    /**
     * Validate whether the queried Datapoint_Edge matches
     * this concrete class
     * 
     * @param query
     * @return boolean
     */
    @Override
    public boolean isEdge(Datapoint_Edge query) {
        return query == Datapoint_Edge.RETURN;
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
        return query.whichEdge() == Datapoint_Edge.RETURN;
    }
}
