/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Request;

import Model.Datapoint.Datapoint_Edge;
import Model.Datapoint.Datapoint_Type;


/**
 * Complete methods as they are, adding appropriate attributes to
 *  abstract classes. Rational is to work towards to creating a string
 *  based interface that the end-user can fillout in a standardized way
 * 
 * Consider methods here carefully, will need a factory that constuction gets
 *  passed too
 * 
 * @author kenna
 */
public interface Request {
    
    public Datapoint_Type whichType();
    public boolean isType(Datapoint_Type dataType);
    
    public Datapoint_Edge whichEdge();
    public boolean isEdgeType(Datapoint_Edge edge);
    
    public String[] getKeys();
    public String[] getValues();
    public boolean validateForm();
    
    
    public boolean hasKey(String query);
    public String getValue(String query);
    
    @Override
    public String toString();
}
