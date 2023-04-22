/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

/**
 * Commonly used boundaries for display events from the application
 *  in blocks of text / boundaries, which functions towards aesthetic neatness
 * 
 * @author kenna
 */
public enum Boundaries {

    /**
     * Main menu of the app
    */
    TITLE {
        
        @Override
        public void queryBoundaries() {
        }

        @Override
        public void resultBoundaries() {
            System.out.println(
                "\n\n********************************************************\n"
                + "****************** Library System **********************\n"
                + "********************************************************\n\n"
            );
        }
    },
    
    /**
     * Top of a boundary
    */
    HEADER {
        
        @Override
        public void queryBoundaries() {
            System.out.println(
                "\n\n***********************************************\n"
                + "******* Fetching Results for your Query *******\n"
                + "***********************************************\n"
            );
        }

        @Override
        public void resultBoundaries() {
            System.out.println(
                "\n\n"
                + "***********************************************\n"
                + "*************** Results Start *****************\n"
                + "***********************************************\n"
            );
        }
    },
    
    /**
     * Bottom of a boundary
    */
    FOOTER {
        @Override
        public void queryBoundaries() {
            System.out.println(
                "\n***********************************************\n"
                + "************** Query Complete *****************\n"
                + "***********************************************\n\n"
            );
        }

        @Override
        public void resultBoundaries() {
            System.out.println(
                "\n***********************************************\n"
                + "**************** Results End ******************\n"
                + "***********************************************\n\n"
            );
        }
    };
    
    
    /**
     * Abstract method to "place" queries in a border
    */
    public abstract void queryBoundaries();
    
    
    /**
     * Abstract method to "place" any printable results in an obvious block
    */
    public abstract void resultBoundaries();
    
    
    /**
     * Print a block separator
     */
    public static void printBlock() {
        System.out.println(
            "\n\n************************************************\n"
            + "************************************************\n\n"
        );
    }
    
    
    /**
     * Print a message within a block separator
     * 
     * @param msg 
     */
    public static void printBlock(String msg) {
        System.out.println(
            "\n\n************************************************\n"
            + "\n " + msg + "\n"
            + "\n************************************************\n\n"
        );
    }
    
    
    /**
     * Embed a message in a block separator
     * 
     * @param msg 
     */
    public static void embedBlock(String msg) {
        System.out.println(
            "\n************************************************\n"
            + "*"
            + "\n* " + msg
            + "\n*"
            + "\n************************************************\n\n"
        );
    }
}
