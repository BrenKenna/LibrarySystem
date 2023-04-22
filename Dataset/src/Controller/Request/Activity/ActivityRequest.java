/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Request.Activity;

import Model.Datapoint.Activity.Activity_Constants;
import Model.Datapoint.Activity.Activity;
import Model.Datapoint.Activity.ReturnType;
import Model.Datapoint.Item.Book;
import Model.Datapoint.Item.Student;
import Model.Datapoint.Datapoint;
import Model.Datapoint.Datapoint_Type;
import Controller.Request.Request;
import java.util.HashMap;


// To properly manage dates :)
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author kenna
 */
public abstract class ActivityRequest implements Request{
    
    // Attributes
    private HashMap<String, String> form;
    private Book book;
    private Student student;
    private Activity borrow = null;
    private String returnDate = null;
    private ReturnType returnState = null;
    
    
    // Atrributes for date
    private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Date startDate;
    private Date endDate;
    private Date nowDate;
    private String nowString;
    
    
    
    /**
     * Default constructor to allow building a form
     */
    public ActivityRequest(){}
    
    
    /**
     * Construct an activity request from a book & student
     * 
     * @param book
     * @param student 
     */
    public ActivityRequest(Datapoint book, Datapoint student){
        
        // Create form and set ID
        this.form = new HashMap<>();
        this.form.put("Act-ID", UUID.randomUUID().toString());
        this.book = (Book) book;
        this.student = (Student) student;
        
        // Manage start & end dates
        this.setDate();
        this.endDate = getTermDate(getNowString());
        this.form.put("Start Date", getNowString());
        this.form.put("End Date", dateFormat.format(endDate));
    }
    
    
    /**
     * Construct a borrow request form
     * 
     * @param id
     * @param book
     * @param student
     * @param startDate
     * @param endDate 
     */
    public ActivityRequest(int id, Datapoint book, Datapoint student, String startDate, String endDate){
        this.form = new HashMap<>();
        this.form.put("ID", String.valueOf(id));
        this.book = (Book) book;
        this.student = (Student) student;
        this.form.put("Start Date", startDate);
        this.form.put("End Date", endDate);
    }
    
    
    /**
     * Construct a return request from data of borrow datapoint
     * 
     * @param id
     * @param book
     * @param student
     * @param startDate
     * @param endDate
     * @param returnDate
     * @param returnState 
     */
    public ActivityRequest(int id, Datapoint book, Datapoint student, String startDate, String endDate, String returnDate, ReturnType returnState){
        
        // Data from the borrow activity: Be nice to condense this by ID
        this.form = new HashMap<>();
        this.form.put("ID", String.valueOf(id));
        this.book = (Book) book;
        this.student = (Student) student;
        this.form.put("Start Date", startDate);
        this.form.put("End Date", endDate);
        
        // Set Return data
        this.returnDate = returnDate;
        this.returnState = returnState;
    }
    
    
    /**
     * Constructor with return date
     * 
     * @param inputBorrow
     * @param returnDate
     * @param returnState 
     */
    public ActivityRequest(Datapoint inputBorrow, String returnDate, ReturnType returnState){
    
        // Populate form from borrow
        this.form = new HashMap<>();
        Activity borrow = (Activity) inputBorrow;
        this.form.put("ID", String.valueOf(borrow.getAutoID()));
        this.book = borrow.getBook();
        this.student = borrow.getStudent();
        this.form.put("Start Date", borrow.getStartDate());
        this.form.put("End Date", borrow.getEndDate());
        
        // Set return specific properties
        this.returnDate = returnDate;
        this.returnState = returnState;
    }
    
    
    /**
     * Construct return request from borrow request
     * @param inputBorrow 
     */
    public ActivityRequest(Datapoint inputBorrow) {
    
        // Populate form from borrow
        this.form = new HashMap<>();
        Activity borrow = (Activity) inputBorrow;
        this.form.put("ID", String.valueOf(borrow.getAutoID()));
        this.book = borrow.getBook();
        this.student = borrow.getStudent();
        this.form.put("Start Date", borrow.getStartDate());
        this.form.put("End Date", borrow.getEndDate());
        
        // Handle return date & state
        setDate();
        this.returnDate = getNowString();
        if ( evaluateDate() ) {
            this.returnState = ReturnType.LATE;
        }
        else {
            this.returnState = ReturnType.ONTIME;
        }
    }
    
    
    /**
     * Set this borrow requests form data
     * 
     * @param id
     * @param book
     * @param student
     * @param startDate
     * @param endDate 
     */
    public void setForm(int id, Datapoint book, Datapoint student, String startDate, String endDate){
        this.form = new HashMap<>();
        this.form.put("ID", String.valueOf(borrow.getAutoID()));
        this.book = (Book) book;
        this.student = (Student) student;
        this.form.put("Start Date", startDate);
        this.form.put("End Date", endDate);
    }

    
    /**
     * Return keys from the form, extending output with the book & student ID
     * 
     * @return String[]
     */
    @Override
    public String[] getKeys() {
        
        // Initalize output
        String[] output = new String[form.size() + 2];
        int counter = 0;
        
        // Populate output
        for (String key : form.keySet()) {
            output[counter] = key;
            counter++;
        }
        
        // Add book and student ID
        output[counter] = "Book ID";
        output[counter+1] = "Student ID";
        
        // Return output
        return output;
    }

    /**
     * Return the values of the form, extending with book & student
     * 
     * @return String[]
     */
    @Override
    public String[] getValues() {
       
        // Initalize output
        String[] output = new String[form.size() + 2];
        int counter = 0;
        
        // Populate output
        for(String value : form.values()) {
            output[counter] = value;
            counter++;
        }
        
        // Add book and student ID
        output[counter] = String.valueOf(book.getAutoID());
        output[counter+1] = String.valueOf(student.getAutoID());
        
        // Return output
        return output;
    }

    /**
     * Return the datapoint type associated with this class
     * 
     * @return Datapoint_Type-Activity
     */
    @Override
    public Datapoint_Type whichType() {
        return Datapoint_Type.ACTIVITY;
    }
    
    
    /**
     * Validate if the queried datapoint type matches
     *  the this classes type
     * 
     * @param dataType
     * @return boolean
     */
    @Override
    public boolean isType(Datapoint_Type dataType) {
        return dataType == Datapoint_Type.ACTIVITY;
    }

    /**
     * Return the activity requests form
     * 
     * @return HashMap<String, String>
     */
    public HashMap<String, String> getForm() {
        return form;
    }
    
    /**
     * Return the requests associated book
     * 
     * @return Book
     */
    public Book getBook() {
        return book;
    }
    
    /**
     * Return the requests associated student
     * 
     * @return Student
     */
    public Student getStudent() {
        return student;
    }
    
    /**
     * Return the borrow the requests associated
     *  borrow datapoint
     * 
     * @return Activity-Borrow
     */
    public Activity getBorrow() {
        return borrow;
    }
    
    /**
     * Return the requests associated return date
     * 
     * @return 
     */
    public String getReturnDate() {
        return returnDate;
    }
    
    /**
     * Return the requests associated return type
     * 
     * @return Return_Type
     */
    public ReturnType getReturnState() {
        return returnState;
    }

    
    /**
     * Return the start date in dd/MM/yyyy format
     * 
     * @return String - dd/MM/yyyy
     */
    public String getStartDate() {
        return dateFormat.format(startDate);
    }
    
    
    /**
     * Set end date from date string
     * 
     * @param startDate 
     */
    public void setStartDate(String startDate){
        this.endDate = parseDateString(startDate);
    }
    
    
    /**
     * Return the start date in dd/MM/yyyy format
     * 
     * @return String - dd/MM/yyyy
     */
    public String getEndDate() {
        return dateFormat.format(endDate);
    }
    
    
    /**
     * Set end date from date string
     * 
     * @param endDate 
     */
    public void setEndDate(String endDate){
        this.endDate = parseDateString(endDate);
    }
    
    
    
    /**
     * Return input date as a string
     * 
     * @param dateString
     * @return Date - dd/MM/yyyy | null
     */
    public Date parseDateString(String dateString) {
        
        // Initalize output & Try parse input string
        Date output;
        try {
            output = dateFormat.parse(dateString);
        } catch (ParseException ex) {
            output = null;
        }
        
        // Return output
        return output;
    }
    
    
    /**
     * Set now date attribute to now
     * 
     */
    public void setDate(){
        Date now = new Date((new Date()).getTime());
        this.nowDate = now;
        this.nowString = dateFormat.format(now);
    }
    

    /**
     * Set now date attribute to now
     * 
     * @param now
     */
    public void setNowDate(Date now){
        this.nowDate = now;
        this.nowString = dateFormat.format(now);
    }
    
    
    /**
     * Get current date as string
     * 
     * @return String - dd/MM/yyyy 
     */
    public String getNowString(){
        return this.nowString;
    }
    
    
    /**
     * Get current date as Date
     * 
     * @return Date - dd/MM/yyyy
     */
    public Date getNowDate(){
        return this.nowDate;
    }
    
    /**
     * Compare the current time to the expected return date
     * 
     * @return boolean
     */
    public boolean evaluateDate(){

        // Handle the students borrow set
        int bookID = book.getAutoID();
        student.getBorrowSet().dropItem(bookID);
        
        // Compare dates
        Date deadline = parseDateString(form.get("End Date"));
        Date currentTime = parseDateString(returnDate);
        return currentTime.getTime() > deadline.getTime();
    }
    
    /**
     * Get deadline for returning book
     * 
     * @param date
     * @return Date - dd/MM/yyyy
     */
    public Date getTermDate(String date) {
    
        // Parse input dae & initalize output
        Date inputDate = parseDateString(date);
        Date outputDate;
        
        
        // Handle conversion of input date
        if (inputDate != null) {
            
            // Set calandar
            Calendar calendar = Calendar.getInstance();
            calendar.setTime( inputDate ); 
        
            // Add the allowed time
            calendar.add(Calendar.DAY_OF_YEAR, Activity_Constants.ALLOWED_BORROW_DAYS);
            outputDate = calendar.getTime();
        }

        // Otherwise set ouput to null
        else {
            outputDate = null;
        }

        // Return date
        return outputDate;
    }
    
    
    /**
     * Evaluate if student can borrow book, or be added to queue
     * 
     * @return true(borrowed) / false(added to queue)
     */
    public boolean evaluateBorrow(){
            
        // Handle no active student & queue is empty
        int studentID = student.getAutoID();
        int bookID = book.getAutoID();
        if ( book.getStudentID() == -1 & book.getStudenQueue().isEmpty() ) {
            book.setStudenID(studentID);
            student.getBorrowSet().addItem(bookID);
            return true;
        }
        
        // Check if they are next
        else if ( book.getStudenQueue().isNext(studentID) ) {
            book.getStudenQueue().poll();
            book.setStudenID(studentID);
            student.getBorrowSet().addItem(bookID);
            return true;
        }
        
        // Otherwise note they have being added but not borrower
        else {
            book.addStudentID(studentID);
            return false;
        }
        
    }
    
    
    /**
     * Clear active studentID borrowing book
     */
    public void clearActiveStudent(){
    
        // Drop student from book queue
        book.clearID();
        // book.dropStudentID(student.getId()); // Drop student ID property from book instead of polling
    }
    
    
    /**
     * Evaluate if student can borrow book
     * 
     * @return boolean
     */
    public boolean evaluateStudent() {
        
        // Get bookID
        int bookID = book.getAutoID();
        
        // Block borrow if students limit is reached or are already borrowing book
        if ( !student.getBorrowSet().canAdd(bookID) ) {
            return false;
        }
        
        // Otherwise allow
        else {
            return true;
        }
    }
    
    /**
     * Return whether or not book is late
     * 
     * @return true/false 
     */
    public boolean isLate(){
        return this.returnState.isType(ReturnType.LATE);
    }
    
    
    @Override
    public boolean hasKey(String query) {
        return this.form.containsKey(query);
    }

    @Override
    public String getValue(String query) {
        if ( hasKey(query) ) {
            return this.form.get(query);
        }
        else {
            return null;
        }
    }
}
