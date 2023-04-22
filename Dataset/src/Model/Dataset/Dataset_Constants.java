/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Dataset;


/**
 * Constants to support seralization and interactions with 
 * dataset/factory chain
 * 
 * @author kenna
 */
public class Dataset_Constants {
    
    // Allowed books by student
    public static final int MAX_BORROW = 2;

    
    // Files for persistence
    public static final String BOOK_SER = "resources/dataset/item/book.ser";
    public static final String BOOK_JSON = "resources/dataset/item/book.json";
    
    public static final String STUDENT_SER = "resources/dataset/item/student.ser";
    public static final String STUDENT_JSON = "resources/dataset/item/student.json";
    
    public static final String BORROW_SER = "resources/dataset/activity/borrow.ser";
    public static final String BORROW_JSON = "resources/dataset/activity/borrow.json";
    
    public static final String RETURN_SER = "resources/dataset/activity/return.ser";
    public static final String RETURN_JSON = "resources/dataset/activity/return.json";
    
    
    // Levels on dataset chain
    public static final int BOOK_LEVEL = 1;
    public static final int STUDENT_LEVEL = 2;
    public static final int BORROW_LEVEL = 3;
    public static final int RETURN_LEVEL = 4;
    
    
    // Levels of datapoint factories
    public static final int ITEM_FACTORY_LEVEL = 1;
    public static final int ACTIVITY_FACTORY_LEVEL = 2;
    public static final int N_DATAPOINTS = 2;
    public static final int N_EDGES = 4;
}
