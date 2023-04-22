/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Request;

import Model.Datapoint.Datapoint_Edge;
import Model.Datapoint.Datapoint_Type;

/**
 * Enum for request types
 * 
 * Input types are waaaaay to different, hence why a request object
 *  was made in the first, to generate a factory. 
 * 
 * This style will go more towards a Request Collection,
 *  where the end-user could be returned a promise and later
 *  have it resolved.... ie a web-server + custom POST/GET queue.
 *  Which is out of scope
 * 
 * @author kenna
 */
public enum Request_Type {
    STUDENT {
        @Override
        public Datapoint_Type whichType() {
            return Datapoint_Type.ITEM;
        }

        @Override
        public Datapoint_Edge whichEdgeType() {
            return Datapoint_Edge.STUDENT;
        }
    },
    
    BOOK {
        @Override
        public Datapoint_Type whichType() {
            return Datapoint_Type.ITEM;
        }

        @Override
        public Datapoint_Edge whichEdgeType() {
            return Datapoint_Edge.BOOK;
        }
        
    },
    
    BORROW {
        @Override
        public Datapoint_Type whichType() {
            return Datapoint_Type.ACTIVITY;
        }

        @Override
        public Datapoint_Edge whichEdgeType() {
            return Datapoint_Edge.BORROW;
        }
        
    },
    
    RETURN {
        @Override
        public Datapoint_Type whichType() {
            return Datapoint_Type.ACTIVITY;
        }

        @Override
        public Datapoint_Edge whichEdgeType() {
            return Datapoint_Edge.RETURN;
        }
    };
    
    
    public abstract Datapoint_Type whichType();
    public abstract Datapoint_Edge whichEdgeType();
}
