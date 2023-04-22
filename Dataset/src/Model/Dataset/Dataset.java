/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Dataset;


// Datapoint interface & enums
import Controller.Request.Request;
import Model.Datapoint.*;
import Model.Dataset.IndexCollection.Dataset_Index;
import Model.Dataset.IndexCollection.IndexCollection;


// Supporting modules
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;


/**
 * Abstract dataset chain that acts a container for Datapoints in the form
 *  of a database which has a separate property for indexes
 * 
 * @author kenna
 */
public abstract class Dataset implements Clonable {
    
    // Atrributes datasets
    protected List<Datapoint> dataset;
    protected IndexCollection indexes; // Working on removing the need here


    // Current and next element in chain
    protected int level = 1;
    protected Dataset nextDataset;

    
    /**
     * Default constructor to allow datasets to be built
     */
    public Dataset(){
    
        // Initalize attributes
        this.dataset = new ArrayList<>();
    }
    
    
    /**
     * Construct dataset by consuming input list
     * 
     * @param datapoints 
     */
    public Dataset(List<Datapoint> datapoints) {
        
        // Consume input into dataset
        this.dataset = new ArrayList<>();
        while( datapoints.size() >= 1) {
            this.dataset.add( datapoints.remove(0) );
        }
    }
    

    /**
     * Construct dataset with index
     * 
     * @param datapoints
     * @param edge 
     */
    public Dataset(List<Datapoint> datapoints, Datapoint_Edge edge) {

        // Consume input into dataset
        this.dataset = new ArrayList<>();
        while( datapoints.size() >= 1) {
            this.dataset.add( datapoints.remove(0) );
        }
        this.indexes = new IndexCollection(edge, this.dataset);
    }
    
    /**
     * Set the next dataset
     * 
     * @param nextDataset 
     */
    protected void setNextDataset(Dataset nextDataset) {
        this.nextDataset = nextDataset;
    }
    
    
    /**
     * Return a datapoint iterator
     * 
     * @return Iterator as Datapoint
     */
    public abstract Iterator getIterator();
    
    
    protected abstract void addIndex();
    
    /**
     * Return the datapoint type of dataset
     * 
     * @return Datapoint_Type
     */
    public abstract Datapoint_Type whichType();
    
    /**
     * Return the Datapoint_Edge of dataset
     * 
     * @return Datapoint_Edge
     */
    public abstract Datapoint_Edge whichEdge();
    
    
    /**
     * Validate that queried datapoint matches the
     *  datapoint type of the dataset
     * 
     * @param query
     * @return boolean
     */
    public abstract boolean isType(Datapoint query);
    
    
    /**
     * Validate that queried datapoint matches the
     *  datapoint edge of the dataset
     * 
     * @param query
     * @return boolean
     */
    public abstract boolean isEdge(Datapoint query);
    
    
    /**
     * Get a Datapoint 
     * 
     * @param datapoint
     * @return Datapoint / Null
     */
    protected Datapoint getDatapoint(Datapoint datapoint) {
        int index = dataset.indexOf(datapoint);
        if (index >= 0) {
            return getDatapoint(index);
        }
        else {
            return null;
        }
    }
    
    
    /**
     * Query the index for input string
     * 
     * @param type
     * @param indexType
     * @param query
     * @return List-Datapoint
     */
    protected List<Datapoint> queryIndex(Datapoint_Edge type, Dataset_Index indexType, String query) {
        List<Integer> primaryKeys = indexes.queryIndex(indexType, query);
        return delegate_getByPK(type, primaryKeys);
    }
    
    
    /**
     * Query the index for input integer
     * 
     * @param type
     * @param indexType
     * @param query
     * @return List-Datapoint
     */
    protected List<Datapoint> queryIndex(Datapoint_Edge type, Dataset_Index indexType, int query) {
        List<Integer> primaryKeys = indexes.queryIndex(indexType, query);
        return delegate_getByPK(type, primaryKeys);
    }
    
    
    /**
     * Accept/Delegate querying an index for an int
     * 
     * @param type
     * @param indexType
     * @param query
     * @return List-Datapoint
     */
    public List<Datapoint> delegateIndexQuery(Datapoint_Edge type, Dataset_Index indexType, int query){
        
        // Accept request
        if( this.level == type.datasetLevel() ) {
            return queryIndex(type, indexType, query);
        }
        
        // Delegate request
        else {
            return nextDataset.delegateIndexQuery(type, indexType, query);
        }
    }
    
    
    /**
     * Accept/Delegate querying an index for a string
     * 
     * @param type
     * @param indexType
     * @param query
     * @return List-Datapoint
     */
    public List<Datapoint> delegateIndexQuery(Datapoint_Edge type, Dataset_Index indexType, String query){
        
        // Accept request
        if( this.level == type.datasetLevel() ) {
            return queryIndex(type, indexType, query);
        }
        
        // Delegate request
        else {
            return nextDataset.delegateIndexQuery(type, indexType, query);
        }
    }
    
    /**
     * Delegate/Accept fetching a data point
     * 
     * @param datapoint
     * @return Datapoint
     */
    public Datapoint delegate_getItem(Datapoint datapoint) {
        
        // Accept request
        if ( this.level == datapoint.whichEdge().datasetLevel() ) {
            return getDatapoint(datapoint);
        }
        
        // Delegate to next dataset
        else {
            return nextDataset.delegate_getItem(datapoint);
        }
    }

    
    /**
     * Get data point by index
     * 
     * @param index
     * @return Datapoint
     */
    protected Datapoint getDatapoint(int index) {
        return dataset.get(index);
    }
    
    /**
     * Delegate/Accept get datapoint by index
     * 
     * @param type
     * @param index
     * @return Datapoint
     */
    public Datapoint delegate_getByIndex(Datapoint_Edge type, int index) {
    
        // Accept request
        if( this.level == type.datasetLevel() ) {
            return getDatapoint(index);
        }
        
        // Delegate request
        else {
            return nextDataset.delegate_getByIndex(type, index);
        }
    }
    
    
    /**
     * Add a datapoint
     * 
     * @param datapoint 
     */
    protected void addDatapoint(Datapoint datapoint) {
        dataset.add(datapoint);
    }
    
    
    /**
     * Delegate/Accept add request
     * 
     * @param datapoint 
     */
    public void delegate_Add(Datapoint datapoint) {
        
        // Accept add request
        if( this.level == datapoint.whichEdge().datasetLevel() ) {
            addDatapoint(datapoint);
        }
        
        // Delegate add request
        else {
            nextDataset.delegate_Add(datapoint);
        }
    }
    
    
    /**
     * Drop a datapoint
     * 
     * @param datapoint 
     */
    protected void dropDatapoint(Datapoint datapoint) {
        dataset.remove(datapoint);
    }
    
    
    /**
     * Accept/Delegate drop request
     * 
     * @param datapoint 
     */
    public void delegate_Drop(Datapoint datapoint){
        
        // Accept drop request
        if ( this.level == datapoint.whichEdge().datasetLevel() ) {
            dropDatapoint(datapoint);
        }
        
        // Otherwise delegate to the next dataset
        else {
            nextDataset.delegate_Drop(datapoint);
        }
    }
    
    
    /**
     * Drop datapoint by index
     * 
     * @param index 
     */
    protected void dropDatapoint(int index) {
        dataset.remove(index);
    }
    
    
    /**
     * Accept/Delegate drop request
     * 
     * @param type
     * @param index 
     */
    public void delegate_DropByIndex(Datapoint_Edge type, int index) {
        
        // Accept request
        if ( this.level == type.datasetLevel() ) {
            dropDatapoint(index);
        }
        
        // Otherwise delegate to the next dataset
        else {
            nextDataset.delegate_DropByIndex(type, index);
        }
    }

        
    /**
     * Return the datapoint by value of primary key
     * 
     * @param rows
     * @return Datapoint
     */
    protected List<Datapoint> getByPrimaryKey(List<Integer> rows) {
        
        // Handle non null
        List<Datapoint> output = new ArrayList<>();
        if (rows != null) {
            for (int priKey : rows) {
                
                // Pass on old values
                output.add(dataset.get(priKey));
            }
            return output;
        }

        // Otherwise return null
        else {
            return null;
        }
    }
    
    
    /**
     * Accept/Delegate linear search on PK
     * 
     * @param type
     * @param rows
     * @return List-Datapoint
     */
    public List<Datapoint> delegate_getByPK(Datapoint_Edge type, List<Integer> rows) {
        
        // Accept request
        if ( this.level == type.datasetLevel() ) {
            return getByPrimaryKey(rows);
        }
        
        // Delegate request
        else {
            return nextDataset.delegate_getByPK(type, rows);
        }
    }
    
    
    /**
     * Get the core dataset
     * 
     * @return List-Datapoint
     */
    protected List<Datapoint> getDataset(){
        return this.dataset;
    }
    
    
    /**
     * Accept/Delegate get request for dataset
     * 
     * @param type
     * @return List-Datapoint
     */
    public List<Datapoint> delegate_getDataset(Datapoint_Edge type){
        
        // Accept request
        if ( this.level == type.datasetLevel() ) {
            return getDataset();
        }
        
        
        // Delegate request to next dataset
        else {
            return nextDataset.delegate_getDataset(type);
        }
    }
    
    
    /**
     * Return list of datapoints by order of index type
     * 
     * @param indexType
     * @param ascending
     * @return 
     */
    protected List<Datapoint> getDatasetByIndex(Dataset_Index indexType, boolean ascending) {
        
        // Initalize output & get primary keys
        List<Datapoint> output = new ArrayList<>();
        indexes.sortIndex(indexType, ascending);
        List<Integer> primaryKeys = indexes.getIndexes().get(indexType).getKeys();
        
        // Add objects for primary keys
        for (int pk : primaryKeys) {
            output.add( dataset.get(pk) );
        }
        
        // Return output
        return output;
    }
    
    
    /**
     * Delegate/Accept retrieving select all by index order (ascend/descend)
     * 
     * @param edge
     * @param indexType
     * @param ascending
     * @return List-Datapoint
     */
    public List<Datapoint> delegate_getDatasetByIndex(Datapoint_Edge edge, Dataset_Index indexType, boolean ascending) {
        if ( this.level == edge.datasetLevel() ) {
            return getDatasetByIndex(indexType, ascending);
        }
        else {
            return nextDataset.delegate_getDatasetByIndex(edge, indexType, ascending);
        }
    }
    
    /**
     * Get the primary key values for dataset
     * 
     * @param primaryKeys
     * @return List-String
     */
    protected List<Datapoint> getPrimaryKey(List<Integer> primaryKeys){
        
        // Handle size
        List<Datapoint> output = new ArrayList<>();
        if ( primaryKeys == null ) {
            return null;
        }
        
        // Otherwise fetch data
        for(int priKey : primaryKeys ) {
            Datapoint row = this.dataset.get(priKey);
            output.add(row);
        }
        
        // Return results
        return output;
    }
    
    
    /**
     * Accept/Delegate get request for PK
     * 
     * @param type
     * @param primaryKeys
     * @return List-String IDs
     */
    protected List<Datapoint> delegate_getPK(Datapoint_Edge type, List<Integer> primaryKeys){
        
        // Accept request
        if(this.level == type.datasetLevel()) {
            return getPrimaryKey(primaryKeys);
        }
        
        // Delegate to next dataset
        else {
            return nextDataset.delegate_getPK(type, primaryKeys);
        }
    }
    
    
    /**
     * Clear dataset
     */
    protected void clearDataset() {
        dataset.clear();
        indexes = null;
    }
    
    
    /**
     * Accept/Delegate deleting dataset
     * 
     * @param type 
     */
    public void delegate_dropDataset(Datapoint_Edge type) {
        
        // Accept
        if( this.level == type.datasetLevel()) {
            clearDataset();
        }
        
        // Delegate to next
        else {
            nextDataset.delegate_dropDataset(type);
        }
    }
    
    
    /**
     * Return dataset as a list of JSON strings
     * 
     * @return List-String-
     */
    protected List<String> jsonDump() {
    
        // Initalize output
        List<String> output = new ArrayList<>();
        for(Datapoint data : getDataset() ) {
            output.add(data.toJsonString());
        }
        return output;
    }
    
    
    /**
     * Accept/Delegate request to print dataset as JSON string
     * 
     * @param type
     * @return 
     */
    public List<String> delegate_JsonDump(Datapoint_Edge type){
        
        // Accept
        if (this.level == type.datasetLevel()) {
            return jsonDump();
        }
        
        // Delegate
        else {
            return nextDataset.delegate_JsonDump(type);
        }
    }
    

    /**
     * Return the JSON-dump file path of the configured dataset level
     * 
     * @return String
     */
    protected abstract String returnJsonFile();
    
    
    /**
     * Accept/Delegate local JSON-file path for dataset
     * 
     * @param type
     * @return String
     */
    public String delegate_JsonFile(Datapoint_Edge type) {
        
        // Accept
        if (this.level == type.datasetLevel()) {
            return returnJsonFile();
        }
        
        // Delegate
        else {
            return nextDataset.delegate_JsonFile(type);
        }
    }
    
    /**
     * Return the file path of the configured dataset level
     * 
     * @return String
     */
    protected abstract String returnSerializedFile();
    
    
    /**
     * Accept/delegate getting serialized-file path
     * 
     * @param type
     * @return String
     */
    public String delegate_getSerializedFile(Datapoint_Edge type) {
        
        // Accept
        if( this.level == type.datasetLevel() ) {
            return returnSerializedFile();
        }
        
        // Delegate
        else {
            return nextDataset.delegate_getSerializedFile(type);
        }
    }
    
    
    /**
     * Serialize dataset 
     * 
     * @throws IOException 
     */
    protected void serializeDataset() throws IOException {
    
        // Get file
        // System.out.println("\n\nSerialzing: " + dataset.get(0).whichEdge());
        // System.out.println("First enttry: " + dataset.get(0));
        File file = new File( returnSerializedFile() );
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch(IOException ex) {
                throw ex;
            }
        }
        
        // Add datapoints
        int counter = 0;
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
        for(Datapoint data : dataset) {
            if ( data.isEdge(Datapoint_Edge.BORROW)) {
                data.updateAutoID(counter);
            }
            outputStream.writeObject(data);
            counter++;
        }
        
        // Close stream
        // System.out.println("Serialized Objects: " + counter + ", file = " + file);
        outputStream.close();
    }
    
    /**
     * Accept/Delegate local serialized-file path for dataset
     * 
     * @param type
     * @throws java.io.IOException
     */
    public void delegate_SerializedFile(Datapoint_Edge type) throws IOException {
        
        // Accept
        if (this.level == type.datasetLevel()) {
            serializeDataset();
        }
        
        // Delegate
        else {
            nextDataset.delegate_SerializedFile(type);
        }
    }
    
    
    /**
     * Protected method to derserialize dataset
     * 
     * @return Dataset - Item/Activity - Book/Student/Borrow/Return
     * @throws java.io.IOException 
     * @throws java.lang.ClassNotFoundException 
     */
    private List<Datapoint> deserialize() throws IOException, ClassNotFoundException {
        
        // Initalize output
        int counter = 0;
        List<Datapoint> dataList = new ArrayList<>();
        
        // Read file
        ObjectInputStream inputStream;
        try {
            
            // Set stream & read until EOF
            inputStream = new ObjectInputStream(new FileInputStream(returnSerializedFile()));
            boolean EOF = false;
            while (!EOF) {
                
                // Add datapoints
                Datapoint data;
                try {
                    data = (Datapoint) inputStream.readObject();
                    dataList.add(data);
                    data.updateCurrentID(counter);
                    counter++;
                }
                
                // Catch quirky error for unreadable datapoint type
                catch (ClassNotFoundException ex) {
                    throw ex;
                }
                
                // Catch end of file
                catch (EOFException end) {
                    EOF = true;
                }
            }
            
            // Close stream and create dataset
            inputStream.close();
            
        }
        
        // Raise exception for reading serialized file
        catch(IOException ex) {
            throw ex;
        }
        
        // Return output
        return dataList;
    }
    
    
    /**
     * Accept/Delegate deserializing dataset
     * 
     * @param type
     * @return List Datapoint - Item/Activity - Book/Student/Borrow/Return
     * @throws java.io.IOException 
     * @throws java.lang.ClassNotFoundException 
     */
    public List<Datapoint> delegate_Deserialize(Datapoint_Edge type) throws IOException, ClassNotFoundException{
    
        // Accept
        if(this.level == type.datasetLevel()) {
            return deserialize();
        }
        
        // Delegate
        else {
            return nextDataset.delegate_Deserialize(type);
        }
    }
    
    /**
     * Dump dataset to appropriate JSON file
     * 
     * @throws IOException 
     */
    protected void writeDataset() throws IOException{
    
        // Set output file based on first datapoint
        int counter = 0;
        String targetFile = returnJsonFile();
        
        // Dump dataset as JSON to target
        BufferedWriter writer;
        try {
            
            // Instantiate a writer & get first -> last points
            writer = new BufferedWriter( new FileWriter(targetFile, false));
            List<String> dump = jsonDump();
            String first = dump.remove(0);
            String last = dump.remove( dump.size()-1 );
            
            // Initalize with first datapoint
            writer.write("["); writer.newLine();
            writer.write(first + ","); writer.newLine();
            counter++;
            
            // Consume from main 
            for(String data : dump ){
                writer.write(data + ",");
                writer.newLine();
                counter++;
            }
            
            // Close with last point
            writer.write(last); writer.newLine();
            counter++;
            writer.write("]");
            writer.close();
            
        }
        
        // Otherwise throw exception
        catch (IOException ex) {
            throw ex;
        }
        
        
        // Debugging: Log counter
        System.out.println("\nLines written to '" + targetFile + "' = " + counter);
    }
    
    
    /**
     * Accept/Delegate writing dataset
     * 
     * @param type
     * @throws IOException 
     */
    public void delegate_writeDataset(Datapoint_Edge type) throws IOException{
        
        // Accept
        if(this.level == type.datasetLevel()) {
            writeDataset();
        }
        
        // Delegate
        else {
            nextDataset.delegate_writeDataset(type);
        }
    }
    
    
    /**
     * Read dataset from JSON dump
     * 
     * @return List-Datapoint
     * @throws IOException 
     */
    protected abstract List<Datapoint> readDataset() throws IOException;
    
    
    /**
     * Accept/Delegate importing JSON dump
     * 
     * @param type
     * @return List-Datapoint
     * @throws IOException 
     */
    public List<Datapoint> delegate_importJSON(Datapoint_Edge type) throws IOException{
        
        // Accept
        if( this.level == type.datasetLevel()) {
            return readDataset();
        }
        
        // Delegate
        else {
            return nextDataset.delegate_importJSON(type);
        }
    }
    
    /**
     * Append dataset to appropriate JSON dump via call to the abstract returnJsonFile()
     * 
     * @throws IOException 
     */
    protected void appendDataset_JSON() throws IOException {
    
        // Set output file based on first datapoint
        int counter = 0; // For debugging
        long targetByte;
        RandomAccessFile fileWriter;
        String targetFile = returnJsonFile();
        
        // Find target byte and initlize set position
        targetByte = findLastJsonEntry(targetFile);
        fileWriter = new RandomAccessFile(targetFile, "rw");
        fileWriter.seek(targetByte);
        
        
        // Fetch first and results to write
        List<String> dump = jsonDump();
        System.out.println("Size to dump = " + dump.size() + ", Target byte = " + targetByte);
        String first = dump.remove(0);
        String last = dump.remove( dump.size()-1 );
        
        
        // Initalize with first datapoint
        fileWriter.writeBytes("},\n" + first + ",\n");
        counter++;
        
        // Write body
        for(String data : dump ) {
            fileWriter.writeBytes(data + ",\n");
            counter++;
        }
        
        // Write last and clost writer
        fileWriter.writeBytes(last + "\n]");
        counter++;
        fileWriter.close();
        
        
        // Debugging: Log counter
        System.out.println("\nExtended '" + targetFile + "' with N lines = " + counter);
    }
    
    
    /**
     * Accept/Delegate appending to JSON dump
     * 
     * @param type
     * @throws IOException 
     */
    public void delegate_appendJSON(Datapoint_Edge type) throws IOException{
        
        // Accept
        if( this.level == type.datasetLevel() ) {
            appendDataset_JSON();
        }
        
        // Delegate
        else {
            nextDataset.delegate_appendJSON(type);
        }
    }
    
    /**
     * Find the byte of the last JSON string entry to append the file from
     * 
     * @param inputFile
     * @return long-Byte
     * @throws IOException 
     */
    public long findLastJsonEntry(String inputFile) throws IOException {
    
        // Initalize variables
        boolean found = false;
        RandomAccessFile fileWriter;
        long output;
        String value;
        
        
        // Try search
        try {
            
            // Initalize writer and end of file
            fileWriter = new RandomAccessFile(inputFile, "rw");
            output = fileWriter.length();
            
            // Search for target until found or whole file is read
            while (!found && output > 0) {

                // Set position and fetch value
                fileWriter.seek(output);
                value = fileWriter.readLine();

                // Only check non-null values
                if (value != null) {
                    
                    // That contain JSON object charcter
                    if (value.contains("}")) {
                        found = true;
                    } 
                    
                    // Otherwise pass on non-null value
                    else {
                        output--;
                    }
                }
                
                // Otherwise pass on null
                else {
                    output--;
                }
            }
        
            // Close writer Return result
            fileWriter.close();
            return output;
        } 
        
        // Otherwise throw exception
        catch (IOException ex) {
            throw ex;
        }
    }
    
    
    /**
     * Return the size of dataset
     * 
     * @return int
     */
    protected int getDatasetSize(){
        return this.dataset.size();
    }
    
    
    /**
     * Accept/Delegate getting size of dataset
     * 
     * @param type
     * @return int
     */
    public int delegate_getSize(Datapoint_Edge type) {
        
        // Accept 
        if( this.level == type.datasetLevel() ) {
            return getDatasetSize();
        }
        
        // Delegate
        else {
            return nextDataset.delegate_getSize(type);
        }
    }
    
    
    /**
     * Set datapoints
     * 
     * @param datapoints 
     */
    protected abstract void setDataset(List<Datapoint> datapoints);
    
    
    /**
     * Accept/Delegate setting dataset
     * 
     * @param type
     * @param datapoints 
     */
    public void delegate_setDataset(Datapoint_Edge type, List<Datapoint> datapoints){
        
        // Accept
        if(this.level == type.datasetLevel()) {
            setDataset(datapoints);
        }
        
        // Delegate
        else {
            nextDataset.delegate_setDataset(type, datapoints);
        }
    }
    
    
    /**
     * Get datapoint by bookID
     * 
     * @param bookID
     * @return Datapoint - Item/Activity/null
     */
    protected abstract Datapoint getBook(int bookID);
    
    
    /**
     * Accept/Delegate getting a datapoint by book
     * 
     * @param type
     * @param bookID
     * @return Datapoint - Item/Activity/null
     */
    public Datapoint delegate_getBook(Datapoint_Edge type, int bookID) {
    
        // Accept
        if ( this.level == type.datasetLevel() ) {
            return getBook(bookID);
        }
    
        // Delegate
        else {
            return nextDataset.delegate_getBook(type, bookID);
        }
    }
    
    
    /**
     * Get activity datapoint by book & student ID
     * 
     * @param bookID
     * @param studentID
     * @return Datapoint - Activity/null
     */
    protected abstract Datapoint getBook(int bookID, int studentID);
    
    
    /**
     * Accept/Delegate activity datapoint involving book & student
     * 
     * @param type
     * @param bookID
     * @param studentID
     * @return Datapoint - Activity/null
     */
    public Datapoint delegate_getBook(Datapoint_Edge type, int bookID, int studentID) {
        if ( this.level == type.datasetLevel() ) {
            return getBook(bookID, studentID);
        }
        else {
            return nextDataset.delegate_getBook(type, bookID, studentID);
        }
    }
    
    
    /**
     * Get datapoint by studentID
     * 
     * @param studentID
     * @return Datapoint - Item/Activity/null
     */
    protected abstract Datapoint getStudent(int studentID);
    
    
    /**
     * Accept/delegate getting datapoint by studentID
     * 
     * @param type
     * @param studentID
     * @return Datapoint - Item/Activity/null
     */
    public Datapoint delegate_getStudent(Datapoint_Edge type, int studentID) {
    
        // Accept
        if ( this.level == type.datasetLevel() ) {
            return getStudent(studentID);
        }
    
        // Delegate
        else {
            return null;
        }
    }
    
    
    /**
     * Convert a datapoint to a request
     * 
     * @param datapoint
     * @return Request - Item/Activity
     */
    protected abstract Request convertToRequest(Datapoint datapoint);
    
    
    /**
     * Accept/delegate conversion of datapoint to a request
     * 
     * @param datapoint
     * @return 
     */
    public Request delegate_RequestConversion(Datapoint datapoint) {
        
        // Accept
        if ( this.level == datapoint.whichEdge().datasetLevel() ) {
            return convertToRequest(datapoint);
        }
        
        // Delegate
        else {
            return nextDataset.delegate_RequestConversion(datapoint);
        }
    }
    
    
    /**
     * Serve deep prototype of dataset
     * 
     * @return Dataset
     */
    protected abstract Dataset copyDataset();
    
    
    /**
     * Accept/Delegate deep cloning of dataset
     * 
     * @param type
     * @return Item/Activity Dataset - Book/Student/Borrow/Return
     */
    @Override
    public Dataset getClone(Datapoint_Edge type) {
        
        // Accept
        if ( this.level == type.datasetLevel() ) {
            return copyDataset();
        }
        
        // Delegate
        else {
            return nextDataset.getClone(type);
        }
    }
    
    
    /**
     * Add indexes from imported dataset
     */
    protected abstract void addIndexes();
    
    
    /**
     * Accept/Delegate adding indexes from imported dataset
     * 
     * @param type 
     */
    public void delegate_addIndexes(Datapoint_Edge type){
        if ( this.level == type.datasetLevel() ) {
            addIndexes();
        }
        else {
            nextDataset.delegate_addIndexes(type);
        }
    }
    
    
    /**
     * Get a the keys from an index
     * 
     * @param indexType
     * @return List-Primary Keys
     */
    private List<Integer> getIndexKeys(Dataset_Index indexType) {
        return indexes.getIndexes().get(indexType).getKeys();
    }
    
    
    /**
     * Accept/Delegate request for a set of keys from index
     * 
     * @param edge
     * @param indexType
     * @return 
     */
    public List<Integer> delegate_getIndexKeys(Datapoint_Edge edge, Dataset_Index indexType) {
        
        // Accept query
        if ( this.level == edge.datasetLevel() ) {
            return getIndexKeys(indexType);
        }
        
        // Delegate query
        else {
            return nextDataset.delegate_getIndexKeys(edge, indexType);
        }
    }
    
    
    /**
     * Method to permit inner joins on foreign keys
     * 
     * @param indexType
     * @return List Primary Keys
     */
    private List<Integer> get_ForeignKeys(Dataset_Index indexType) {
        
        // Pass if invalid
        if ( !indexType.isForeignKey() ) {
            return null;
        }
        
        // Initalize output
        List<Integer> foreignKeys = new ArrayList<>();
        for (Object foreignKey : indexes.getIndex(indexType)) {
            foreignKeys.add( (int) foreignKey);
        }
        
        // Return foreign keys
        return foreignKeys;
    }
    
    
    /**
     * Accept/Delegate initializing an inner join
     * 
     * @param edge
     * @param indexType
     * @return List Primary Keys
     */
    public List<Integer> delegate_getForeignKeys(Datapoint_Edge edge, Dataset_Index indexType) {
        
        // Accept query
        if (this.level == edge.datasetLevel()) {
            return get_ForeignKeys(indexType);
        }

        // Delegate query
        else {
            return nextDataset.delegate_getForeignKeys(edge, indexType);
        }
    }
    
    
    /**
     * Return datapoints corresponding to a foreign key 
     * 
     * @param foreignKey
     * @return List Datapoint
     */
    private List<Datapoint> innerJoin(Dataset_Index foreignKey) {
    
        // Check request is valid
        Datapoint_Edge edge = foreignKey.getForeignKeyPartner();
        if ( foreignKey.getForeignKeyPartner() == null ) {
            return null;
        }
        
        // Inner join tables
        List<Integer> foreignKeys = get_ForeignKeys(foreignKey);
        return nextDataset.delegate_getByPK(edge, foreignKeys);
    }
    
    
    /**
     * Accept/Delegate inner join request
     * 
     * @param edge
     * @param foreignKey
     * @return List Datapoint
     */
    public List<Datapoint> delegate_InnerJoin(Datapoint_Edge edge, Dataset_Index foreignKey) {
        if ( this.level == edge.datasetLevel() ) {
            return innerJoin(foreignKey);
        }
        else {
            return nextDataset.delegate_InnerJoin(edge, foreignKey);
        }
    }
}
