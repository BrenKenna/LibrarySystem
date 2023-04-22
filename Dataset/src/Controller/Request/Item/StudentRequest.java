/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Request.Item;

import Model.Datapoint.Datapoint_Edge;
import java.util.HashMap;

/**
 * Class to aid factories in generating Student Items
 * 
 * @author kenna
 */
public class StudentRequest extends ItemRequest{

        
    /**
     * Default constructor to allow building a form
     * 
     */
    public StudentRequest(){
        this.form = new HashMap<>();
    }
    
    
    /**
     * Constructor with form data
     * 
     * @param id - Student ID
     * @param fName - Student First Name
     * @param lName - Student Last Name
     */
    public StudentRequest(String fName, String lName){
        this.form = new HashMap<>();
        form.put("First Name", fName);
        form.put("Last Name", lName);
        this.hasBorrow = false;
    }
    
    public StudentRequest(int id, String fName, String lName){
        this.form = new HashMap<>();
        form.put("ID", String.valueOf(id));
        form.put("First Name", fName);
        form.put("Last Name", lName);
        this.hasBorrow = false;
    }
    
    /**
     * Constructor with form data
     * 
     * @param id - Student ID
     * @param fName - Student First Name
     * @param lName - Student Last Name
     * @param nBorrows - Total number of books borrowed by student
     * @param borrowing - Active bookIDs borrowed by Student
     * @param borrowDate - Borrow start date
     * @param returnDate - Due date for return
     */
    public StudentRequest(String fName, String lName, String nBorrows, String borrowing, String borrowDate, String returnDate){
        this.form = new HashMap<>();
        form.put("First Name", fName);
        form.put("Last Name", lName);
        form.put("N Borrows", nBorrows);
        form.put("Borrowing", borrowing);
        form.put("Borrow Start Date", borrowDate);
        form.put("Return Date", returnDate);
    }
    
    
    public StudentRequest(int id, String fName, String lName, String nBorrows, String borrowing, String borrowDate, String returnDate){
        this.form = new HashMap<>();
        form.put("ID", String.valueOf(id));
        form.put("First Name", fName);
        form.put("Last Name", lName);
        form.put("N Borrows", nBorrows);
        form.put("Borrowing", borrowing);
        form.put("Borrow Start Date", borrowDate);
        form.put("Return Date", returnDate);
    }
    
    /**
     * Set form data
     * 
     * @param id - Student ID
     * @param fName - Student First Name
     * @param lName - Student Last Name
     * @param nBorrows - Total number of books borrowed by student
     * @param borrowing - Active bookIDs borrowed by Student
     * @param borrowDate - Borrow start date
     * @param returnDate - Due date for return
     */
    public void setForm(String id, String fName, String lName, String nBorrows, String borrowing, String borrowDate, String returnDate){
        this.form = new HashMap<>();
        form.put("ID", String.valueOf(id));
        form.put("First Name", fName);
        form.put("Last Name", lName);
        form.put("N Borrows", nBorrows);
        form.put("Borrowing", borrowing);
        form.put("Borrow Start Date", borrowDate);
        form.put("Return Date", returnDate);
    }
    

    /**
     * Return the datapoint edge that the form refers o
     * 
     * @return Datapoint_Edge Student
     */
    @Override
    public Datapoint_Edge whichEdge() {
        return Datapoint_Edge.STUDENT;
    }

    
    /**
     * Check if the queried edge matches form type
     * 
     * @param edge
     * @return boolean
     */
    @Override
    public boolean isEdgeType(Datapoint_Edge edge) {
        return Datapoint_Edge.STUDENT.isEdge(edge);
    }

    
    // Not sure yet
    @Override
    public boolean validateForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
