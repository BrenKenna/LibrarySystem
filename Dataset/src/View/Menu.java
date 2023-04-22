/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.util.List;

/**
 * Generic menu interface
 * 
 * @author kenna
 */
public interface Menu {
    
    
    /**
     * Display title banner
     * 
     * @return 
     */
    public String displayTitle();
    
    
    /**
     * Display all results from a list of strings
     * 
     * @param results 
     */
    public void displayResults(List<String> results);
    
    
    /**
     * Display requested number of items from list
     * 
     * @param results
     * @param nRows 
     */
    public void displayResults(List<String> results, int nRows);
    
    
    /**
     * Display main menu
     * 
     * @return 
     */
    public int mainMenu();
    
    
    /**
     * Display main menu or quit gracefully
     * 
     * @return 
     */
    public int placeMenu();
    
    
    /**
     * Login
     * 
     * @param username
     * @param password
     * @return 
     */
    public boolean login(String username, String password);
}
