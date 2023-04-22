/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Request.Item;

import Model.Datapoint.Datapoint_Type;
import Controller.Request.Request;
import java.util.HashMap;


/**
 * Class to aid factories in Item data points (Book & Student)
 * 
 * @author kenna
 */
public abstract class ItemRequest implements Request{

    // Shared attributes size and content different (type is same)
    protected HashMap<String, String> form;
    protected boolean hasBorrow = false;
    
    
    // Default constructor to allow forms to be built
    public ItemRequest(){
        this.form = new HashMap<>();
    }
    
    
    /**
     * Return datapoint type that concrete classes refer to
     * 
     * @return Datapoint_Type Item 
     */
    @Override
    public Datapoint_Type whichType() {
        return Datapoint_Type.ITEM;
    }

    
    /**
     * Check if the queried data point type matches form type
     * 
     * @param dataType
     * @return boolean
     */
    @Override
    public boolean isType(Datapoint_Type dataType) {
        return Datapoint_Type.ITEM == dataType;
    }
    
    
    /**
     * Return keys from the form
     * 
     * @return String[]
     */
    @Override
    public String[] getKeys() {
        
        // Initalize output
        String[] output = new String[form.size()];
        int counter = 0;
        
        // Populate output
        for (String key : form.keySet()) {
            output[counter] = key;
            counter++;
        }
        
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
        String[] output = new String[form.size()];
        int counter = 0;
        
        // Populate output
        for(String value : form.values()) {
            output[counter] = value;
            counter++;
        }
        
        // Return output
        return output;
    }
    
    
    // Return form
    public HashMap<String, String> getForm() {
        return this.form;
    }
    
    
    // Get borrow state
    public boolean getBorrow(){
        return hasBorrow;
    }
    
    
    // Set borrow
    public void setBorrow(boolean state){
        hasBorrow = state;
    }
    
    
    @Override
    public boolean hasKey(String query) {
        return this.form.containsKey(query);
    }

    @Override
    public String getValue(String query) {
        if ( hasKey(query) ) {
            return this.form.get(query);
        }
        else {
            return null;
        }
    }
}
