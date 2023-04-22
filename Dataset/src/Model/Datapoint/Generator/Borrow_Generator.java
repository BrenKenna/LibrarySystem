/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Datapoint.Generator;

import java.util.Random;
import java.util.UUID;

/**
 * Random borrow activity/event generator, largely for debugging purposes
 * 
 * @author kenna
 */
public class Borrow_Generator {
    
    // To generate start-end dates
    protected String[] start_date = {
        "12/02/2022", "02/02/2022", "20/02/2022", "01/02/2022", "15/02/2022", "03/02/2022", "21/02/2022", "25/02/2022",
        "28/02/2022", "20/02/2022", "23/02/2022", "10/02/2022", "19/02/2022", "19/02/2022", "13/02/2022", "16/02/2022",
        "06/02/2022", "07/02/2022", "09/02/2022"
    };
    protected String[] end_date = {
        "12/03/2022", "02/03/2022", "20/03/2022", "01/03/2022", "15/03/2022", "03/03/2022", "21/03/2022", "25/03/2022",
        "28/03/2022", "20/03/2022", "23/03/2022", "10/03/2022", "19/03/2022", "19/03/2022", "13/03/2022", "16/03/2022",
        "06/03/2022", "07/03/2022", "09/03/2022"
    };
    
    
    /**
     * Empty constructor
     */
    public Borrow_Generator(){}
    
    
    /**
     * Generate a random data for borrowing
     * 
     * @return String[] - Borrow
     */
    public String[] genRand(){
        
        // Initalize output 
        String[] output = new String[3];
        Random rand = new Random();
        
        // Generate random date data
        output[0] = UUID.randomUUID().toString();
        output[1] = start_date[ rand.nextInt(start_date.length) ];
        output[2] = end_date[ rand.nextInt(end_date.length) ];
        
        // Return results
        return output;
    }
}
