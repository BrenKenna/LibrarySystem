/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Request.Activity;

import Model.Datapoint.Activity.ReturnType;
import Model.Datapoint.Datapoint;
import Model.Datapoint.Datapoint_Edge;



/**
 * Class to generate return requests from a borrow object/data 
 * 
 * @author kenna
 */
public class ReturnRequest extends ActivityRequest{
    
    /**
     * Default constuctor
     */
    public ReturnRequest(){
        super();
    }
    
    
    /**
     * Construct a return request from data of borrow datapoint
     * 
     * @param id
     * @param book
     * @param student
     * @param startDate
     * @param endDate
     * @param returnDate
     * @param returnState 
     */
    public ReturnRequest(int id, Datapoint book, Datapoint student, String startDate, String endDate, String returnDate, ReturnType returnState){
        
        // Data from the borrow activity: Be nice to condense this by ID
        super(id, book, student, startDate, endDate, returnDate, returnState);
    }
    
    
    /**
     * Construct a return request from a borrow datapoint
     * 
     * @param inputBorrow
     * @param returnDate
     * @param returnState 
     */
    public ReturnRequest(Datapoint inputBorrow, String returnDate, ReturnType returnState){
    
        // Populate form from borrow
        super(inputBorrow, returnDate, returnState);
    }

    
    /**
     * Construct return request from borrow request
     * @param inputBorrow 
     */
    public ReturnRequest(Datapoint inputBorrow) {
        super(inputBorrow);
    }
    
    /**
     * Return the associated datapoint edge of form
     * 
     * @return Datapoint_Edge Return 
     */
    @Override
    public Datapoint_Edge whichEdge() {
        return Datapoint_Edge.RETURN;
    }

    
    /**
     * Check if queried edge type matches to forms
     * 
     * @param edge
     * @return boolean 
     */
    @Override
    public boolean isEdgeType(Datapoint_Edge edge) {
        return Datapoint_Edge.RETURN.isEdge(edge);
    }

    
    /**
     * Return keys from the form
     * 
     * @return String[]
     */
    @Override
    public String[] getKeys() {
        
        // Initalize output
        String[] output = new String[getForm().size() + 4];
        int counter = 0;
        
        // Populate output
        for (String key : getForm().keySet()) {
            output[counter] = key;
            counter++;
        }
        
        // Add book, student and return specific data
        output[counter] = "Book ID";
        output[counter+1] = "Student ID";
        output[counter+2] = "Return Date";
        output[counter+3] = "Return State";
        
        // Return output
        return output;
    }

    
    /**
     * Return the values of the form
     * 
     * @return String[]
     */
    @Override
    public String[] getValues() {
       
        // Initalize output
        String[] output = new String[getForm().size() + 4];
        int counter = 0;
        
        // Populate output
        for(String value : getForm().values()) {
            output[counter] = value;
            counter++;
        }
        
        // Add book, student and return specific data
        output[counter] = String.valueOf(getBook().getAutoID());
        output[counter+1] = String.valueOf(getStudent().getAutoID());
        output[counter+2] = getForm().get("Return Date");
        output[counter+3] = getReturnState().toString();
        
        // Return output
        return output;
    }

    @Override
    public boolean validateForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
