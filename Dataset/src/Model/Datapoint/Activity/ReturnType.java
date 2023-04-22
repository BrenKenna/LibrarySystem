/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Datapoint.Activity;

import java.util.Random;

/**
 * Enum for issuing a return state based on whether or not the expected end date
 *  occurs before the return date
 * 
 * @author kenna
 */
public enum ReturnType {
    
    /**
     * Late return type
     */
    LATE {
        
        /**
         * Return this enum as a string
         */
        @Override
        public String toString() {
            return "Late";
        }

        /**
         * Validate if queried string mathes this enum
         */
        @Override
        public boolean isType(String query) {
            return "late".equals(query.toLowerCase());
        }

        /**
         * Validate if the queried return type maatches this enums
         */
        @Override
        public boolean isType(ReturnType returnState) {
            return returnState == LATE;
        }
    },
    
    /**
     * Ontime return type
     */
    ONTIME {
        
        /**
         * Return this enum as a string
         */
        @Override
        public String toString() {
            return "On Time";
        }

        /**
         * Validate if queried string mathes this enum
         */
        @Override
        public boolean isType(String query) {
            return "ontime".equals( query.toLowerCase().replace(" ", "").replace("-", "").replace("_", "") );
        }

        /**
         * Validate if the queried return type maatches this enums
         */
        @Override
        public boolean isType(ReturnType returnState) {
            return returnState == ONTIME;
        }
    };
    
    
    /**
     * Method to return enum as string
     * 
     * @return String 
     */
    @Override
    public abstract String toString();
    
    
    /**
     * Method to check if query string matches enum
     * 
     * @param query
     * @return oolean
     */
    public abstract boolean isType(String query);
    
    
    /**
     * Methods to check if return state matches enum
     * 
     * @param returnState
     * @return boolean
     */
    public abstract boolean isType(ReturnType returnState);
    
    
    /**
     * Method to return random return state
     * 
     * @return ReturnType
     */
    public static ReturnType getState(){
        Random rand = new Random();
        ReturnType[] state = ReturnType.values();
        return state[ rand.nextInt(state.length) ];
    }
}
