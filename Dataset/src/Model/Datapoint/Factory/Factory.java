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
 * Abstract Factory chain for generating datapoints for required Datapoint Edge.
 *  Goal is to also provide a Request object (ie form) for constructing non-random
 *  objects
 * 
 * @author kenna
 */
public abstract class Factory{
    
    // Attributes for concrete factory level
    public static final int ITEM_FACTORY_LEVEL = Dataset_Constants.ITEM_FACTORY_LEVEL;
    public static final int ACTIVITY_FACTORY_LEVEL = Dataset_Constants.ACTIVITY_FACTORY_LEVEL;
    
    // Current and next element in chain
    protected int level;
    protected Factory nextFactory;
    
    /**
     * Set the next factory
     * 
     * @param nextFactory 
     */
    protected void setNextFactory(Factory nextFactory) {
        this.nextFactory = nextFactory;
    }
    
    
    
    /**
     * Method to generate a random datapoint by delegating the
     *  edge request to the appropriate factory.Goal is to also
     *  provide a Request 
     * 
     * @param edge
     * @param form
     * @return Datapoint
     */
    public Datapoint validateRequest(Datapoint_Edge edge, Request form){
    
        // Delegate request if needs be
        if ( this.level == edge.chainLevel() ) {
            return getEdge(edge, form);
        }
        
        // Otherwise delegate
        else {
            return nextFactory.getEdge(edge, form);
        }
    }
    
    /**
     * Abstract method to generate datapoint from edge & form
     * 
     * @param edge
     * @param form
     * @return Datapoint
     */
    protected abstract Datapoint getEdge(Datapoint_Edge edge, Request form);
}
