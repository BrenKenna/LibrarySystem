/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;



/**
 * Utilities class with methods to get user input from keyboard
 * 
 * @author kenna
 */
public class InputUtils {
    
    /**
    * Method to get user string
     * @param prompt
    * @return - String from users input
    */
    public String getUserString(String prompt){
        
        // Initialize variables
        String output = "";
        boolean valid = false;
        BufferedReader userKB = new BufferedReader( new InputStreamReader(System.in) );
        
        // Continually request user input
        do {
            
            // Prompt user for input
            System.out.print(prompt);
            
            
            // Try store to the output variable
            try {
                output = userKB.readLine();
                valid = true;
            }

            // Catch unexpected error
            catch (IOException e) {
                System.out.println("Error retriving input, please try again");
            }
        } while(!valid);
        
        
        // Return output
        return output;
    }
    
    
    /**
     * Method to get user string only allowing for alphabetical characters
     * @param prompt
     * @return - String from users input after validation
     */
    public String getUserStringAlpha(String prompt){
        
        // Initialize variables
        String userInput = "";
        boolean valid = false;
        BufferedReader userKB = new BufferedReader( new InputStreamReader(System.in));
        
        
        // Validation loop for alpabetical string only
        do {
        
            // Try fetch user input
            System.out.print(prompt);
            try{
                userInput = userKB.readLine().trim(); // Get user input
            }
            catch(IOException e){
                System.out.println("Error");
                valid = false;
                userInput = "";
            }
            
            // Check if string is alphabetical
            if ( userInput.matches("^[A-Za-z]+$")) {
                
                // Update valid
                valid = true;
            }
            
            // Otherwise log error to user
            else {
                System.out.println("Error, only alphabetical strings are allowed");
                valid = false;
            }
        
        } while(!valid);
        
        return userInput;
    }

    
    /**
     * Method to get user string only allowing for alphabetical characters
     * @param prompt
     * @param standardize - set string to specified case lower/upper
     * @return - String from users input after validation
     */
    public String getUserStringAlpha(String prompt, String standardize){
        
        // Initialize variables
        String userInput = "";
        standardize.toLowerCase();
        boolean valid = false;
        BufferedReader userKB = new BufferedReader( new InputStreamReader(System.in));
        
        
        // Validation loop for alpabetical string only
        do {
        
            // Try fetch user input
            System.out.print(prompt);
            try{
                userInput = userKB.readLine().trim(); // Get user input
            }
            catch(IOException e){
                System.out.println("Error");
                valid = false;
                userInput = "";
            }
            
            // Check if string is alphabetical
            if ( userInput.matches("^[A-Za-z]+$")) {
                
                // Update valid
                valid = true;
                
                // Set lower case
                if("lower".equals(standardize)) {
                    userInput = userInput.toLowerCase();
                }
                
                // Otherwise assume upper
                else {
                    userInput = userInput.toUpperCase();
                }
            }
            
            // Otherwise log error to user
            else {
                System.out.println("Error, only alphabetical strings are allowed");
                valid = false;
            }
        
        } while(!valid);
        
        return userInput;
    }

    /**
     * Get an integer from user
     * @param prompt - Prompt to user to supply an integer
     * @return - integer
     */
    public int getUserNumber(String prompt){
        
        // Initialize variables
        String userInput = "";
        int output = -1;
        boolean valid = false;
        BufferedReader userKB = new BufferedReader( new InputStreamReader(System.in) );
        
        // Validation loop to get integer from user
        do {
        
            // Only allow strings that are parsable from the user
            System.out.print(prompt);
            try {
                
                // Get user input and validate as integer
                userInput = userKB.readLine().trim();
                if ( userInput.matches("^[-]?[0-9]+$") ) {
                    
                    // Allow integer
                    output = Integer.parseInt(userInput);
                    valid = true;
                }
                
                // Otherwise di-allow integer
                else{
                    System.out.println("Error, your input must be a whole number");
                    userInput = "";
                    output = -1;
                    valid = false;
                }
                
            } catch (IOException | NumberFormatException e) {
                System.out.println("Error, only whole numbers are allowed");
                userInput = "";
                valid = false;
            }
            
        } while(!valid);
        
        // Only return output when userInput is an int parsable string
        return output;
    }
    
    
    /**
     * Get an integer from user within specified range
     * 
     * @param prompt - Prompt to user to supply an integer
     * @param num1 - Lower bound integer
     * @param num2 - Upper bound integer
     * @return - integer
     */
    public int getUserNumber(String prompt, int num1, int num2){
        
        // Initialize variables
        String userInput = "";
        int output = -1;
        boolean valid = false;
        BufferedReader userKB = new BufferedReader( new InputStreamReader(System.in) );
        
        // Validation loop to get integer from user
        do {
        
            // Only allow strings that are parsable from the user
            System.out.print(prompt);
            try {
                
                // Get user input and temporarily update valid
                userInput = userKB.readLine().trim();

                // Comparsion to range is not too big for try and catch loop
                if ( userInput.matches("^[-]?[0-9]+$") ) {
                    
                    // Only allow integers within range
                    output = Integer.parseInt(userInput);
                    if ( output >= num1 && output <= num2){
                        valid = true;
                    }
                    
                    // Otherwise inform user
                    else{
                        System.out.println("Error, only numbers inclusively between " + num1 + " and " + num2 + " are allowed");
                        output = -1;
                        userInput = "";
                        valid = false;
                    }
                    
                }

                // Otherwise dis-allow users input
                else{
                    System.out.println("Error, only whole numbers are allowed");
                    valid = false;
                }
                
            } catch (IOException | NumberFormatException e) {
                System.out.println("Error, only whole numbers are allowed");
                userInput = "";
                valid = false;
            }
            
        } while(!valid);
        
        // Only return output when userInput is an int parsable string
        return output;
    }
    
    
    /**
     * Method to support getting an array of integers from user
     * 
     * @param prompt
     * @param delim
     * @return int array
     */
    public int[] getIntArray(String prompt, String delim) {
        
        // Initalize variables
        String userInput;
        String[] stringArr;
        int[] output;
        boolean valid = false;
        
        // Request input until valid
        do {
            
            // Fetch parsable data
            boolean error = false;
            userInput = getUserString(prompt);
            stringArr = userInput.split(delim);
            output = new int[stringArr.length];
            
            // Block loop if output array is size zero
            if ( output.length == 0 ) {
                error = true;
            }
            
            // Populate output array
            int counter = 0;
            while(counter < output.length & !error) {
                if ( stringArr[counter].replace(" ", "").matches("^[0-9]+$") & !stringArr[counter].equals("") ) {
                    output[counter] = Integer.parseInt(stringArr[counter].trim());
                    counter++;
                }
                else {
                    error = true;
                }
            }
            
            // Handle errors
            if (!error) {
                valid = true;
            }
            else {
                System.out.println("Error, only integer arrays separated by a '" + delim + "' & non empty values are allowed");
            }
        }
        
        while(!valid);

        // Return results
        return output;
    }
    
    
    /**
     * Method to support getting an array of integers from user between range
     * 
     * @param prompt
     * @param delim
     * @param min
     * @param max
     * @return int array
     */
    public int[] getIntArray(String prompt, String delim, int min, int max) {
        
        // Initalize variables
        String userInput;
        String[] stringArr;
        int[] output;
        boolean valid = false;
        
        // Request input until valid
        do {
            
            // Fetch parsable data
            boolean error = false;
            userInput = getUserString(prompt);
            stringArr = userInput.split(delim);
            // System.out.println("User input: " + userInput + "\nParsed input: " + Arrays.toString(stringArr));
            output = new int[stringArr.length];
            
            // Block loop if output array is size zero
            if ( output.length == 0 ) {
                error = true;
            }
            
            // Populate output array
            int counter = 0;
            while(counter < output.length & !error) {
                if ( stringArr[counter].replace(" ", "").matches("^[0-9]+$")) {
                    int userVal = Integer.parseInt(stringArr[counter].trim());
                    if (userVal >= min & userVal <= max) {
                        output[counter] = userVal;
                        counter++;
                    }
                }
                else {
                    // System.out.println(stringArr[counter]);
                    error = true;
                }
            }
            
            // Handle errors
            if (!error) {
                valid = true;
            }
            else {
                System.out.println(
                        "Error, only numbers separated by a '" 
                        + delim + "' and values between " 
                        + min + "-" + max + " are allowed");
            }
        }
        
        while(!valid);

        // Return results
        return output;
    }
    
    
    /**
     * Get the user date in a YYYY-MM-DD structure, where year is validated
     *  between 1900-2021, months 1-12, days 1-30. Month day combinations are
     *  not validated.
     * 
     * @param prompt to user for input
     * @return String as date
     */
    public String getUserDate(String prompt){
    
        // Format output date
        int yearInt, dayInt, monthInt;
        String dateFormat = "\\d{4}-\\d{2}-\\d{2}";
        String output = "";
        String userInput = "";
        boolean valid = false;
        BufferedReader userKB = new BufferedReader( new InputStreamReader(System.in) );
        
        // Prompt user until input is valid
        do {
            
            // Try parse user keyboard input
            try {
                
                // Prompt user
                System.out.print(prompt);
                userInput = userKB.readLine().trim();
                
                // Validate that input matches date format
                if ( userInput.matches(dateFormat) ) {
                
                    // Additional simple checks
                    yearInt = Integer.parseInt(userInput.split("-")[0]);
                    monthInt = Integer.parseInt(userInput.split("-")[1]);
                    dayInt = Integer.parseInt(userInput.split("-")[2]);
                    
                    // Manage values
                    if ( (yearInt >= 1900 && yearInt <= 2021) && (monthInt >= 1 && monthInt <= 12 ) && (dayInt >= 1 && dayInt <= 31) ) {
                        
                        // Break loop and set output
                        output = userInput;
                        valid = true;
                    }
                    else {
                        userInput = "";
                        valid = false;
                        System.out.println("\nPlease enter a valid date to the below specifications:");
                        System.out.println("1. A year must be between 1900 and 2021");
                        System.out.println("2. A month must be between 1 and 12");
                        System.out.println("3. Please note! Days are loosely checked between 1 and 31.");
                    }
                }
                
                // Otherwise log issue to user
                else {
                    userInput = "";
                    valid = false;
                    System.out.println("Error, input date must be of format YYYY-MM-DD, ex 1968-01-04 or 1999-10-15");
                }
            }
            
            // Catch IOException
            catch (IOException ex) {
                userInput = "";
                output = "";
                valid = false;
            }
            
        } while(!valid);
        
        // Return string
        return output;
    }
    
    
    /**
     * Delegate getting string and parse out
     * character if string length is 1
     * @param prompt
     * @return char
     */
    public char getUserChar(String prompt){
        
        // Initialize variables
        String userInput = "";
        char output = 'n';
        boolean valid = false;
        
        // Delegate getting input to string
        do {
        
            // Get char otherwise log mistake
            userInput = getUserString(prompt);
            if ( userInput.length() == 1) {
                valid = true;
                output = userInput.charAt(0);
            }
            
            // Otherwise
            else {
                System.out.println("Error please enter a character");
                valid = false;
            }
            
        } while( !valid);
        
        // Return user character
        return output;
    }
    
    
    /**
     * Delegate getting string and parse out
     * character if string length is 1
     * @param prompt
     * @return char
     */
    public boolean getUserResponse(String prompt){
        
        // Initialize variables
        char userInput = 'n';
        boolean userResponse = false;
        boolean valid = false;
        
        // Delegate getting input to string
        do {
        
            // Check user preference
            userInput = getUserChar(prompt);
            switch (userInput) {
                
                // Handle yes
                case 'y', 'Y' :
                    valid = true;
                    userResponse = true;
                    break;
                
                
                // Handle no
                case 'n', 'N' :
                    valid = true;
                    userResponse = false;
                    break;
                
                
                // Otherwise log error
                default :
                    System.out.println("Error input must be in the form of either \"n\" / \"N\", or \"y\" or \"Y\"");
                    valid = false;
                
            }
            
        } while( !valid);
        
        // Return user character
        return userResponse;
    }
    
    
    /**
     * Method to hold until the user presses enter
     * 
     */
    public void pageHolder() {
    
        // 
        String prompt = "Press anything enter continue >>> ";
        BufferedReader userKB = new BufferedReader( new InputStreamReader(System.in) );
        System.out.print(prompt);
        try {
            String userInput = userKB.readLine().trim();
        } catch (IOException ex) {
        }
    }
}