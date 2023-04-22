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



// Towards persistence on primary keys
// import Model.Datapoint.DatapointState;
// import java.util.concurrent.atomic.AtomicInteger;

/**
 * Concrete Borrow type of Activity under the DataPoint Interface
 * 
 * @author kenna
 */
public class Borrow extends Activity {

    
    // Auto-incremented ID
    // private static final DatapointState currentID = DatapointState.getInstance();
    // private static final AtomicInteger currentID = new AtomicInteger(0);
    private static int currentID = -1;
    private int autoID;
    

    /**
     * Default null constructor
     * 
     */
    public Borrow(){
        super();
        this.autoID = setAutoID();
        System.out.println("\nAutoID now at: " + Borrow.currentID);
    }
    
    
    /**
     * Construct a Borrow Datapoint_Type-Activity
     * 
     * @param id
     * @param book
     * @param student
     * @param startDate
     * @param endDate 
     */
    public Borrow(Book book, Student student, String startDate, String endDate){
        super(book, student, startDate, endDate);
        this.autoID = setAutoID();
    }
    
    
    /**
     * Method to set auto-incremented ID during construction
     * @return playerID 
     */
    private static int setAutoID() {
        Borrow.currentID++;
        return Borrow.currentID;
        
        // From atomic integer
        // return currentID.incrementAndGet();
        
        // From custom DatapointState class
        // return currentID.getBorrow();
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
        Borrow.currentID = newVal;
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
     * Return the borrow as a string
     * 
     * @return Sring
     */
    @Override
    public String toString() {
        return "Borrow{" + "autoID=" + autoID
                + ", startDate=" + getStartDate()
                + ", endDate=" + getEndDate() + ", book=" + getBook().getAutoID()
                + ", bookActiveStudent=" + getBook().getStudentID() + ", bookQueue=" + getQueueString()
                + ", student=" + getStudent().getAutoID() + '}';
    }

    
    
    /**
     * Return object in JSON format
     * 
     * @return 
     */
    @Override
    public String toJsonString() {
        return "{"
                + " \"ID\": " + autoID
                + ", \"Start Date\": " + "\"" + getStartDate() + "\""
                + ", \"End Date\": " + "\"" + getEndDate() + "\""
                + ", \"Book ID\": " + "\"" + getBook().getAutoID() + "\""
                + ", \"Student ID\": " + "\"" + getStudent().getAutoID() + "\""
                + ", \"Book Queue\": " + getQueueString()
                +" }";
    }
    
    /**
     * Return the concrete class datapoint_edge
     * 
     * @return Datapoint_Edge-Borrow
     */
    @Override
    public Datapoint_Edge whichEdge() {
        return Datapoint_Edge.BORROW;
    }

    
    /**
     * Return whether the queried datapoint_Edge matches Borrow
     * 
     * @return Datapoint_Edge-Borrow
     */
    @Override
    public boolean isEdge(Datapoint_Edge query) {
        return query == Datapoint_Edge.BORROW;
    }

    
    /**
     * Validate the queried datapoints edge is Borrow
     * 
     * @return Datapoint_Edge-Borrow
     */
    @Override
    public boolean isEdge(Datapoint query) {
        return query.whichEdge() == Datapoint_Edge.BORROW;
    }
}
