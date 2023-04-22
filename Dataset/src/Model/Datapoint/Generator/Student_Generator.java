/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Datapoint.Generator;

import java.util.Random;
import java.util.UUID;

/**
 * Random student generator, largely for debugging purposes
 * 
 * @author kenna
 */
public class Student_Generator {
    
    // To generate first & last name
    protected String[] first_names = {
        "Alex", "Michael", "Tom", "Sarah", "Lisa", "Michelle", "Alice", "Martin", "Martina",
        "Bill", "Mary", "Tim", "Bob", "Jane", "John", "Elizabeth", "Danielle", "Amy", "Peter",
        "Penelope"
    };
    
    protected String[] last_names = {
        "Jones", "Doe", "Berkhiem", "Montgomery", "Simpson", "Sizlack", "Burns", "Ziff", "Baggins",
        "Skywalker", "Bond", "Bathory", "Doe", "East", "Collins", "Bakker", "Hamilton", "Harris"
    };

    /**
     * Empty constructor
     */
    public Student_Generator() {
    }
    
    
    /**
     * Generate random data for constructing student
     * 
     * @return String[ UUID, First Name, Last Name ]
     */
    public String[] getRandomData() {
    
        // Pick random first & last name
        String output;
        Random r = new Random();
        String first_name = this.first_names[ r.nextInt(first_names.length) ];
        String last_name = this.last_names[ r.nextInt(last_names.length) ];
        UUID uuid = UUID.randomUUID();
        output = uuid + " " + first_name + " " + last_name;
        
        // Return name
        return output.split(" ");
    }
}
