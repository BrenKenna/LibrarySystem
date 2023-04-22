/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Datapoint.Item;

import java.util.Random;

/**
 * Enum for automated interactions with "valid" Genres. The long-term 
 * view is that books with no genres should be added by the librarian, additionally 
 *  end-users can search by genres
 * 
 * @author kenna
 */
public enum Genre {
    
    ACTION {
        @Override
        public String toString() {
            return "Action";
        }

        @Override
        public boolean isGenre(Genre query) {
            return query == ACTION;
        }

        @Override
        public boolean isGenre(String query) {
            return "action".equals(query.toLowerCase());
        }
        
        @Override
        public int getOrder() {
            return 17;
        }
    },
    
    DRAMA {
        @Override
        public String toString() {
            return "Drama";
        }

        @Override
        public boolean isGenre(Genre query) {
            return query == DRAMA;
        }

        @Override
        public boolean isGenre(String query) {
            return "drama".equals(query.toLowerCase());
        }
        
        @Override
        public int getOrder() {
            return 1;
        }
    },
    
    FANTASY {
        @Override
        public String toString() {
            return "Fantasy";
        }

        @Override
        public boolean isGenre(Genre query) {
            return query == FANTASY;
        }

        @Override
        public boolean isGenre(String query) {
            return "fantasy".equals(query.toLowerCase());
        }
        
        @Override
        public int getOrder() {
            return 2;
        }
    },
    
    ADVENTURE {
        @Override
        public String toString() {
            return "Adventure";
        }

        @Override
        public boolean isGenre(Genre query) {
            return query == ADVENTURE;
        }

        @Override
        public boolean isGenre(String query) {
            return "adventure".equals(query.toLowerCase());
        }
        
        @Override
        public int getOrder() {
            return 3;
        }
    },
    
    ROMANCE {
        @Override
        public String toString() {
            return "Romance";
        }

        @Override
        public boolean isGenre(Genre query) {
            return query == ROMANCE;
        }

        @Override
        public boolean isGenre(String query) {
            return "romance".equals(query.toLowerCase());
        }
        
        @Override
        public int getOrder() {
            return 4;
        }
    },
    
    THRILLER {
        @Override
        public String toString() {
            return "Thriller";
        }

        @Override
        public boolean isGenre(Genre query) {
            return query == THRILLER;
        }

        @Override
        public boolean isGenre(String query) {
            return "thriller".equals(query.toLowerCase());
        }
        
        @Override
        public int getOrder() {
            return 5;
        }
    },
    
    MYSTERY {
        @Override
        public String toString() {
            return "Mystery";
        }

        @Override
        public boolean isGenre(Genre query) {
            return query == MYSTERY;
        }

        @Override
        public boolean isGenre(String query) {
            return "mystery".equals(query.toLowerCase());
        }
        
        @Override
        public int getOrder() {
            return 6;
        }
    },
    
    WAR {
        @Override
        public String toString() {
            return "War";
        }

        @Override
        public boolean isGenre(Genre query) {
            return query == WAR;
        }

        @Override
        public boolean isGenre(String query) {
            return "war".equals(query.toLowerCase());
        }
        
        @Override
        public int getOrder() {
            return 7;
        }
    },
    
    MUSICAL {
        @Override
        public String toString() {
            return "Musical";
        }

        @Override
        public boolean isGenre(Genre query) {
            return query == MUSICAL;
        }

        @Override
        public boolean isGenre(String query) {
            return "musical".equals(query.toLowerCase());
        }
        
        @Override
        public int getOrder() {
            return 8;
        }
    },
    
    NULL {
        @Override
        public String toString() {
            return "Null";
        }

        @Override
        public boolean isGenre(Genre query) {
            return query == NULL;
        }

        @Override
        public boolean isGenre(String query) {
            return "null".equals(query.toLowerCase());
        }
        
        @Override
        public int getOrder() {
            return 0;
        }
    },
    
    WESTERN {
        @Override
        public String toString() {
            return "Western";
        }

        @Override
        public boolean isGenre(Genre query) {
            return query == WESTERN;
        }

        @Override
        public boolean isGenre(String query) {
            return "western".equals(query.toLowerCase());
        }
        
        @Override
        public int getOrder() {
            return 9;
        }
    },
    
    DOCUMENTARY {
        @Override
        public String toString() {
            return "Documentary";
        }

        @Override
        public boolean isGenre(Genre query) {
            return query == DOCUMENTARY;
        }

        @Override
        public boolean isGenre(String query) {
            return "documentary".equals(query.toLowerCase());
        }
        
        @Override
        public int getOrder() {
            return 10;
        }
    },
    
    COMEDY {
        @Override
        public String toString() {
            return "Comedy";
        }

        @Override
        public boolean isGenre(Genre query) {
            return query == COMEDY;
        }

        @Override
        public boolean isGenre(String query) {
            return "comedy".equals(query.toLowerCase());
        }
        
        @Override
        public int getOrder() {
            return 11;
        }
    },
    
    HORROR {
        @Override
        public String toString() {
            return "Horror";
        }

        @Override
        public boolean isGenre(Genre query) {
            return query == HORROR;
        }

        @Override
        public boolean isGenre(String query) {
            return "horror".equals(query.toLowerCase());
        }
        
        @Override
        public int getOrder() {
            return 12;
        }
    },
    
    SCI_FI {
        @Override
        public String toString() {
            return "Sci-Fi";
        }

        @Override
        public boolean isGenre(Genre query) {
            return query == SCI_FI;
        }

        @Override
        public boolean isGenre(String query) {
            return "sci fi".equals(query.toLowerCase().replace("-", " ").replace("_", " "));
        }
        
        @Override
        public int getOrder() {
            return 13;
        }
    },
    
    FILM_NOIR {
        @Override
        public String toString() {
            return "Film Noir";
        }

        @Override
        public boolean isGenre(Genre query) {
            return query == FILM_NOIR;
        }

        @Override
        public boolean isGenre(String query) {
            return "film noir".equals(query.toLowerCase().replace("-", " ").replace("_", " "));
        }
        
        @Override
        public int getOrder() {
            return 14;
        }
    },
    
    CHILDREN {
        @Override
        public String toString() {
            return "Children";
        }

        @Override
        public boolean isGenre(Genre query) {
            return query == CHILDREN;
        }

        @Override
        public boolean isGenre(String query) {
            return "children".equals(query.toLowerCase());
        }
        
        @Override
        public int getOrder() {
            return 15;
        }
    },
    
    ANIMATION {
        @Override
        public String toString() {
            return "Animation";
        }

        @Override
        public boolean isGenre(Genre query) {
            return query == ANIMATION;
        }

        @Override
        public boolean isGenre(String query) {
            return "animation".equals(query.toLowerCase());
        }

        @Override
        public int getOrder() {
            return 16;
        }
    };
    
    
    /**
     * Static method to convert a query to a valid Genre Type
     * 
     * @param query
     * @return 
     */
    public static Genre whichGenre(String query) {
        
        // Initalize
        Genre output = Genre.NULL;
        boolean found = false;
        int counter = 0;
        Genre[] check = Genre.values();
        
        // Search while not found and generes to search
        while ( !found && counter < check.length ) {
            
            // Stop loop and update output assumptions
            if ( check[counter].isGenre(query) ) {
                found = true;
                output = check[counter];
            }
            
            // Otherwise increment counter
            else {
                counter++;
            }
        }
        
        // Return search
        return output;
        
    }
    
    /**
     * Print genre as string
     * 
     * @return String 
     */
    @Override
    public abstract String toString();
    
    
    /**
     * Check if the queried genre is the same as the query
     * 
     * @param query - Genre
     * @return boolean
     */
    public abstract boolean isGenre(Genre query);
    
    
    /**
     * Check if the queried genre is the same as the query string
     * 
     * @param query - String
     * @return 
     */
    public abstract boolean isGenre(String query);
    
    
    /**
     * Method to query a valid system wide genre
     * 
     * @param query
     * @return boolean
     */
    public static boolean isValid(String query) {
        
        // Initialize output and search params
        boolean valid = false;
        int counter = 0;
        Genre[] check = Genre.values();
        
        // Search while not found and generes to search
        while ( !valid && counter < check.length ) {
            
            // Stop loop by updating valid
            if ( check[counter].isGenre(query) ) {
                valid = true;
            }
            
            // Otherwise increment counter
            else {
                counter++;
            }
        }
        
        // Return search
        return valid;
    }
    
    
    /**
     * Method to fetch a random genre, to help generate random data
     * 
     * @return Genre 
     */
    public static Genre fetchGenre(){
        
        // Fetch a random genre
        Random rand = new Random();
        Genre[] genres = Genre.values();
        return genres[ rand.nextInt( genres.length - 1 ) ];
    }
    
    
    /**
     * Abstract method to get rank of genre in enum for sorting (Null = 0, Action = 17)
     * 
     * @return int - Genre rank
     */
    public abstract int getOrder();
    
    
    /**
     * Compare two genres, distance of query from the sample
     * 
     * @param sample
     * @param query
     * @return int - "sample - query"
     */
    public static int compareTo(Genre sample, Genre query) {
        return sample.getOrder() - query.getOrder();
    }
    
    
    /**
     * Return an array of non-null genres
     * 
     * @return 
     */
    public static Genre[] genreSelector() {
        
        // Setup output array
        Genre[] output = new Genre[ Genre.values().length-1 ];
        
        // Skip null genre
        int counter = 0;
        for(int i = 0; i < Genre.values().length; i++) {
            if ( !Genre.values()[i].isGenre(Genre.NULL) ) {
                output[counter] = Genre.values()[i];
                counter++;
            }
        }
        
        // Return array
        return output;
    }
}
