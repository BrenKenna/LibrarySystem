/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Datapoint.Item;

import Model.Datapoint.Datapoint;
import Model.Datapoint.Datapoint_Type;
import java.io.Serializable;

/**
 * Abstract Item class from Datapoint Interface. Idea is that these objects
 *  are largely declarative in function, but are connected by activities/events
 *  that themselves are data points that the end-user are interested in
 * 
 * @author kenna
 */
public abstract class Item implements Datapoint, Serializable {
    
    
    /**
     * Return the object Datapoint_Type
     * 
     * @return Datapoint_Type-Item
     */
    @Override
    public Datapoint_Type whichDatapoint(){
        return Datapoint_Type.ITEM;
    }
    
    /**
     * Validate queried Datapoint_Type matches the object
     * 
     * @param query
     * @return boolean
     */
    @Override
    public boolean isDatapoint(Datapoint_Type query){
        return query == Datapoint_Type.ITEM;
    }
    
    
    /**
     * Return whether queried datapoints type matches Item
     * 
     * @param query
     * @return boolean
     */
    @Override
    public boolean isDatapoint(Datapoint query) {
        return query.whichDatapoint() == Datapoint_Type.ITEM;
    }
}
