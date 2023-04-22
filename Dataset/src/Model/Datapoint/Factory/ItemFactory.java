/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Datapoint.Factory;

import Model.Datapoint.Datapoint;
import Model.Datapoint.Datapoint_Edge;
import Model.Dataset.Dataset_Constants;
import Controller.Request.Request;

/**
 * Factory for returning valid Item type Datapoints
 * 
 * @author kenna
 */
public class ItemFactory extends Factory{

    /**
     * Constructor setting the factory level from the chain constants
     * 
     */
    public ItemFactory(){
        this.level = Dataset_Constants.ITEM_FACTORY_LEVEL;
    }
    
    /**
     * Construct an Item Datapoint_Type from request form
     * 
     * @param edge
     * @param form
     * @return Datapoint-Book/Student
     */
    @Override
    protected Datapoint getEdge(Datapoint_Edge edge, Request form) {
        
        
        // Return a book datapoint
        if ( edge.isEdge(Datapoint_Edge.BOOK) ) {
            return Datapoint_Edge.BOOK.getDatapoint(form);
        }
        
        // Otherwise assume student
        else {
            return Datapoint_Edge.STUDENT.getDatapoint(form);
        }
    }
}
