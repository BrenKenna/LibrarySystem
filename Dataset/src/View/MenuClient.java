/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

// Supporting modules
import java.io.IOException;


/**
 * Client to start menu
 * 
 * @author kenna
 */
public class MenuClient {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        
        // Initalize the library menu
        //System.out.println(Integer.parseInt("apples"));
        
        Menu menu = new LibraryMenu();
        int menuChoice = 0;
        int counter = 0;
        
        // Main menu
        while( menuChoice >= 0 ) {
            menuChoice = menu.mainMenu();
            counter++;
        }

        // Quit menu
        System.out.println("Exiting after N = " + counter + " loops");
    } 
}
