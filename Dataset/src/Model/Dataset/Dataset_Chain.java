/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Dataset;

import Model.Datasets.StudentDataset;
import Model.Datasets.ReturnDataset;
import Model.Datasets.BorrowDataset;
import Model.Datasets.BookDataset;
import Model.Datapoint.Datapoint;
import java.util.List;


/**
 * Serve a collection of datasets in a chain, resulting object treated as a database
 * 
 * @author kenna
 */
public class Dataset_Chain {
    
    
    /**
     * Construct database for list of datapoints
     * 
     * @param books
     * @param students
     * @param borrows
     * @param returns
     * @return Dataset Chain
     */
    public static Dataset getDatasetChain(List<Datapoint> books, List<Datapoint> students, List<Datapoint> borrows, List<Datapoint> returns) {
        
        // Instantiate Item Datasets
        Dataset bookDataset = new BookDataset(books);
        Dataset studentDataset = new StudentDataset(students);
        
        // Instantiate Activity Dataset
        Dataset borrowDataset = new BorrowDataset(borrows);
        Dataset returnDataset = new ReturnDataset(returns);

        // Set next dataset
        bookDataset.setNextDataset(studentDataset);
        studentDataset.setNextDataset(borrowDataset);
        borrowDataset.setNextDataset(returnDataset);
        returnDataset.setNextDataset(bookDataset);
        
        // Return configured dataset chain
        return bookDataset;
    }

    
    /**
     * Construct empty database/dataset chain
     * 
     * @return Dataset Chain
     */
    public static Dataset getNullDatasetChain() {
        
        // Instantiate Item Datasets
        Dataset bookDataset = new BookDataset();
        Dataset studentDataset = new StudentDataset();
        
        // Instantiate Activity Dataset
        Dataset borrowDataset = new BorrowDataset();
        Dataset returnDataset = new ReturnDataset();

        // Set next dataset
        bookDataset.setNextDataset(studentDataset);
        studentDataset.setNextDataset(borrowDataset);
        borrowDataset.setNextDataset(returnDataset);
        returnDataset.setNextDataset(bookDataset);
        
        // Return configured dataset chain
        return bookDataset;
    }
}
