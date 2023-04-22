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
 * Factory for returning valid activity type Datapoints. Coordinates with the
 *  ActivityType Enum
 * 
 * Need to reorganize Datapoint type to contain Activity & Item Type,
 *  so that this can be resolved here
 * @author kenna
 */
public class ActivityFactory extends Factory{
    
    
    /**
     * Constructor using setting the level from the Chain Constant
     */
    public ActivityFactory(){
        this.level = Dataset_Constants.ACTIVITY_FACTORY_LEVEL;
    }
    

    /**
     * Return a valid Activity-Datapoint Borrow/Return from a request form
     * 
     * @param edge
     * @param form
     * @return Datapoint-Borrow/Return
     */
    @Override
    protected Datapoint getEdge(Datapoint_Edge edge, Request form) {
        
        // Return Datapoint for borrow
        if ( edge.isEdge(Datapoint_Edge.BORROW) ) {
            return Datapoint_Edge.BORROW.getDatapoint(form);
        }
        
        // Otherwise return datapoint for return
        else {
            return Datapoint_Edge.RETURN.getDatapoint(form);
        }
    }
    
}
