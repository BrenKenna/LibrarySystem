/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Datapoint.Factory;

/**
 * Return a configured factory chain
 * 
 * @author kenna
 */
public class Factory_Chain {
    
    /**
     * Return the abstract Datapoint_Type factory chain
     * 
     * @return Factory for Datapoint_Type
     */
    public static Factory getFactoryChain(){
        
        // Instantiate factories
        Factory itemFactory = new ItemFactory();
        Factory activityFactory = new ActivityFactory();
        
        // Set next factory
        itemFactory.setNextFactory(activityFactory);
        activityFactory.setNextFactory(itemFactory);
        
        // Return configured factory
        return itemFactory;
    }
}
