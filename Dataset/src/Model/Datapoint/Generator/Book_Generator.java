/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Datapoint.Generator;

import Model.Datapoint.Item.Genre;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * Random book generator, largely for debugging purposes
 * 
 * @author kenna
 */
public class Book_Generator {
    
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
    
    // To generate title
    protected String[] titles = {
        "Lord of the Rings", "The Silmarillion", "HEX", "Eye of The World", "Scorpio Illusion",
        "The Void Trilogy", "The Heartland", "Bourne Series", "Echo", "IT"
    };

    
    /**
     * Empty constructor
     */
    public Book_Generator() {
    }
    
    
    /**
     * Generate random data for constructing books
     * 
     * @return String[ UUID, First Name, Last Name, Title ]
     */
    public String[] getRandomData() {
    
        // Pick random first & last name
        String output;
        Random r = new Random();
        String first_name = this.first_names[ r.nextInt(first_names.length) ];
        String last_name = this.last_names[ r.nextInt(last_names.length) ];
        String title = this.titles[ r.nextInt(titles.length) ];
        UUID uuid = UUID.randomUUID();
        output = uuid + ";" + first_name + ";" + last_name + ";" + title;
        
        // Return name
        return output.split(";");
    }
    
    
    /**
     * Generate a random Genre[ N ]
     * 
     * @param nGenres - size of required Genre[]
     * @return Genre[]
     */
    public Set<Genre> getGenres(int nGenres) {
        Set<Genre> output = new HashSet<>();
        for(int i = 0; i < nGenres; i++) {
            
            // Skip null genre if >1 were requested: Ignoring duplicates becuase its random data => Book object genre data could be a set
            if (nGenres > 1) {
                Genre genre = Genre.fetchGenre();
                boolean notValid = genre.isGenre("null");
                while(notValid) {
                    genre = Genre.fetchGenre();
                    notValid = genre.isGenre("null");
                }
                output.add(genre);
            }
            else {
                output.add(Genre.fetchGenre());
            }
        }
        return output;
    }
    
    
    /**
     * Get a random set of valid genres, # between 0-4
     * 
     * @return String "Genre;Genre;Genre"
     */
    public String getGenresString() {
    
        // Initalize
        Random rand = new Random();
        Set<Genre> genres = getGenres(rand.nextInt(4));
        String output = genres.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(", ", ";")
        ;
        
        // Return parsed set
        return output;
    }
    
}
