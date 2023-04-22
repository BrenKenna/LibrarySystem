/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Request.Item;

import Model.Datapoint.Datapoint_Edge;
import java.util.HashMap;

/**
 * Class to aid factories in generating Book Items
 * 
 * @author kenna
 */
public class BookRequest extends ItemRequest{
    
    /**
     * Default constructor to allow building a form
     * 
     */
    public BookRequest(){
        this.form = new HashMap<>();
    }
    
    
    /**
     * Default constructor to allow building a form
     * 
     * @param inpForm
     */
    public BookRequest(HashMap<String, String> inpForm){
        this.form = new HashMap<>();
        for(String key : inpForm.keySet()) {
            this.form.put(key, inpForm.get(key));
        }
    }
    
    
    public BookRequest(String fName, String lName, String title, String genres){
        this.form = new HashMap<>();
        form.put("Author First Name", fName);
        form.put("Author Last Name", lName);
        form.put("Title", title);
        form.put("Genres", genres);
    }
    
    /**
     * Constructor with form data
     * 
     * @param id - Book ID
     * @param fName - Authour First Name
     * @param lName - Author Last Name
     * @param title - Book Title
     * @param genres - String of Genres
     */
    public BookRequest(int id, String fName, String lName, String title, String genres){
        this.form = new HashMap<>();
        form.put("ID", String.valueOf(id));
        form.put("Author First Name", fName);
        form.put("Author Last Name", lName);
        form.put("Title", title);
        form.put("Genres", genres);
    }
    
    
    /**
     * Set form data
     * 
     * @param id - Book ID
     * @param fName - Author First Name
     * @param lName - Author Last Name
     * @param title - Book Title
     * @param genres  - Book Genres
     */
    public void setForm(int id, String fName, String lName, String title, String genres){
        this.form = new HashMap<>();
        form.put("ID", String.valueOf(id));
        form.put("Author First Name", fName);
        form.put("Author Last Name", lName);
        form.put("Title", title);
        form.put("Genres", genres);
    }
    

    @Override
    public Datapoint_Edge whichEdge() {
        return Datapoint_Edge.BOOK;
    }

    @Override
    public boolean isEdgeType(Datapoint_Edge edge) {
        return Datapoint_Edge.BOOK.isEdge(edge);
    }

    
    @Override
    public boolean validateForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public String getBookID(){
        return form.get("Book ID");
    }
    public String getfName(){
        return form.get("Author First Name");
    }
    public String getlName(){
        return form.get("Book ID");
    }
    public String getTitle(){
        return form.get("Title");
    }
    public String getGenres(){
        return form.get("Genres");
    }
    
    
}
