/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Request.Activity;

import Model.Datapoint.Datapoint;
import Model.Datapoint.Datapoint_Edge;


/**
 * Class to generate a borrow request from a student & book
 * 
 * @author kenna
 */
public class BorrowRequest extends ActivityRequest{

    
    /**
     * Default constructor to allow building a form
     */
    public BorrowRequest(){
        super();
    }
    
    /**
     * Construct a borrow request from a book & student
     * @param book
     * @param student 
     */
    public BorrowRequest(Datapoint book, Datapoint student) {
        super(book, student);
    }
    
    /**
     * Construct a borrow request form
     * 
     * @param id
     * @param book
     * @param student
     * @param startDate
     * @param endDate 
     */
    public BorrowRequest(int id, Datapoint book, Datapoint student, String startDate, String endDate){
        super(id, book, student, startDate, endDate);
    }
    

    /**
     * Return the Datapoint_Edge associated with this request
     * 
     * @return Datapoint_Edge-Borrow
     */
    @Override
    public Datapoint_Edge whichEdge() {
        return Datapoint_Edge.BORROW;
    }

    
    /**
     * Validate that the queried data point edge matches this object
     * @param edge
     * @return booleam
     */
    @Override
    public boolean isEdgeType(Datapoint_Edge edge) {
        return Datapoint_Edge.BORROW.isEdge(edge);
    }

    
    @Override
    public boolean validateForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
