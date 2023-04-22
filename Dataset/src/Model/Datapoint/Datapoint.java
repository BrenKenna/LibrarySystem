/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Datapoint;


/**
 * All data points all declarative but in different ways. They share
 *  the need to describe themselves and declare themselves in a
 *  standardized way. The Items (Books & Students) are connected by
 *  Activity (Borrow, Return), both of which are core datapoints that
 *  the Librarian wants to know and different in "objectified function"
 * 
 * Can extend for which edge?
 * 
 * @author kenna
 */
public interface Datapoint {
    
    public int getAutoID();
    public void updateAutoID(int newVal);
    public void updateCurrentID(int newVal);
    
    /**
     * Return the Datapoint_Type
     * @return Datapoint_Type-Item/Activity
     */
    public Datapoint_Type whichDatapoint();
    
    
    /**
     * Validate the Datapoint_Type of query datapoint_type
     * @param query
     * @return boolean
     */
    public boolean isDatapoint(Datapoint_Type query);
    
    
    /**
     * Validate the queried datapoint matches Data_Type point
     * @param query
     * @return 
     */
    public boolean isDatapoint(Datapoint query);
    
    
    /**
     * Return the Datapoint_Edge of object
     * @return 
     */
    public Datapoint_Edge whichEdge();
    
    
    /**
     * Validate the queried datapoint_Edge matches the
     *  concrete class
     * @param query
     * @return boolean
     */
    public boolean isEdge(Datapoint_Edge query);
    
    
    /**
     * Validate that the queried datapoints type
     *  matches concrete object
     * 
     * @param query
     * @return boolean
     */
    public boolean isEdge(Datapoint query);
    
    
    /**
     * Return Datapoint_Type as JSON string
     * 
     * @return String
     */
    public String toJsonString();
}
