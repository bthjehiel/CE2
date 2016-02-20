import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class CE2 {
    

    private static final int NUMBER_SEARCH_PARAMETERS = 2;
    private static final String COMMAND_SEARCH = "search";
    private static final String MESSAGE_SORT = "Sorted tasks alphabetically";
    private static final int NUMBER_ADD_PARAMETERS = 2;
    private static final String COMMAND_SORT = "sort";
    private static final int NUMBER_SORT_PARAMETERS = 1;
    private static final int NUMBER_EXIT_PARAMETERS = 1;
    private static final int NUMBER_DELETE_PARAMETERS = 2;
    private static final int NUMBER_CLEAR_PARAMETERS = 1;
    private static final int NUMBER_DISPLAY_PARAMETERS = 1;
    private static final String SPACE = " ";
    private static final String EMPTY_STRING = "";
    private static final int PARAM_INVALID_INTEGER = -1;
    private static final String COMMAND_CLEAR = "clear";
    private static final String COMMAND_DISPLAY = "display";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_EXIT = "exit";
    private static final String MESSAGE_DISPLAY = "%1$s. %2$s";
    private static final String MESSAGE_DELETED = "deleted from %1$s: \"%2$s\"";
    private static final String MESSAGE_CLEAR = "all content deleted from %1$s";
    private static final String MESSAGE_ADDED = "added to %1$s: %2$s";
    private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %1$s is ready for use";
    private static final String MESSAGE_INVALID_TASK_NUMBER = "Pls enter a valid task number";
    private static final String MESSAGE_NO_TASKS_TO_DELETE = "No tasks to delete";
    private static final String MESSAGE_UNRECOGNISED_COMMAND = "Pls enter a valid command";
    private static ArrayList<String> texts = new ArrayList<String>();
    private static String textFile = "test.txt";
    
    public static void main(String args[]) throws IOException{
        //String textFile == args[0];
        File myFile = new File(textFile);
        if(!myFile.exists()){
            myFile.createNewFile();
        }
        displayMessage(String.format(MESSAGE_WELCOME, textFile));
        startTextBuddy();
        /*
        File textFile = new File("test.txt");
        if(!textFile.exists()){
            textFile.createNewFile();
        }*/
        
        //displayMessage(String.format(MESSAGE_WELCOME, textFile));
        //startTextBuddy(textFile);
        return;
    }

    public static void displayMessage(String message) {
        System.out.println(message);
    }

    public static void startTextBuddy() throws IOException {
        Scanner sc = new Scanner(System.in);
        String[] inputs = getUserInput(sc);
        ArrayList<String> texts = initialiseList();
        
        while(!isCommandExit(inputs)){ 
            displayMessage(executeCommand(inputs, texts));
            inputs = getUserInput(sc);
        }
        sc.close();
        return;
    }

    public static String executeCommand(String[] inputs, ArrayList<String> texts)
            throws IOException, FileNotFoundException {
        switch(inputs[0]){
        case COMMAND_ADD:
            if(!hasValidNumberOfParameters(inputs, NUMBER_ADD_PARAMETERS, true)){
                return MESSAGE_UNRECOGNISED_COMMAND;
            }
            return addTask(inputs[1]);
            
        case COMMAND_DELETE:
            if(!hasValidNumberOfParameters(inputs, NUMBER_DELETE_PARAMETERS, false)){
                return MESSAGE_INVALID_TASK_NUMBER;
            }
            else{
                return deleteTask(inputs[1]);
            }
            
        case COMMAND_DISPLAY:
            if(!hasValidNumberOfParameters(inputs, NUMBER_DISPLAY_PARAMETERS, false)){
                return MESSAGE_UNRECOGNISED_COMMAND;
            }
            return displayTasks();
            
        case COMMAND_CLEAR:
            if(!hasValidNumberOfParameters(inputs, NUMBER_CLEAR_PARAMETERS, false)){
                return MESSAGE_UNRECOGNISED_COMMAND;
            }
            return clear();
            
        case COMMAND_SORT:
            if(!hasValidNumberOfParameters(inputs, NUMBER_SORT_PARAMETERS, false)){
                return MESSAGE_UNRECOGNISED_COMMAND;
            }
            return sort();
            
        case COMMAND_SEARCH:
            if(!hasValidNumberOfParameters(inputs, NUMBER_SEARCH_PARAMETERS, true)){
                return MESSAGE_UNRECOGNISED_COMMAND;
            }
            return searchKeyword(inputs[1]);
            
        default:
            return MESSAGE_UNRECOGNISED_COMMAND;
        }
    }
    
    public static boolean isCommandExit(String[] inputs) {
        return (inputs[0].equalsIgnoreCase(COMMAND_EXIT)) && (hasValidNumberOfParameters(inputs, NUMBER_EXIT_PARAMETERS, true));
    }

    public static String[] getUserInput(Scanner sc) {
        String userInput = sc.nextLine();
        userInput = userInput.trim();
        String[] operation = userInput.split(SPACE,2);
        return operation;
    }

    public static ArrayList<String> initialiseList() throws IOException {
        Scanner fileScanner = new Scanner(new File(textFile));
        while(fileScanner.hasNext()){
                texts.add(fileScanner.nextLine());
        }
        
        fileScanner.close();
        return texts;
    }
    
    //checks for valid number of parameters in string and 
    //the boolean split determines if 2nd input parameter is a combination of strings
    //and does process them as separate strings e.g add this is a combination of strings
    public static boolean hasValidNumberOfParameters(String[] inputs, int numParameters, boolean split){
        //System.out.println(inputs.length);
        
        if((inputs.length == 1 && (numParameters == 1))){
            return true;
        }
        else if((inputs.length == 1) && (numParameters > 1)){
            return false;
        }
        if(!split){
            String[] splittedString = inputs[1].split(SPACE);
            if(splittedString.length == numParameters-1){
                return true;
            }
        }
        else{
            if(inputs.length==numParameters){
                return true;
            }
        }
        return false;
    }
    
    public static String addTask(String description) throws IOException {
        texts.add(new String(description));
        addTaskToFile(description);
        return String.format(MESSAGE_ADDED, textFile, description);
    }
    
    public static String deleteTask(String task) throws IOException {
        int taskNum = getTaskNum(task);
        if(texts.size()==0){
            return MESSAGE_NO_TASKS_TO_DELETE;        
        }
        if(taskNum == PARAM_INVALID_INTEGER){
            return MESSAGE_INVALID_TASK_NUMBER;
        }
        String deletedTask = texts.get(taskNum-1);
        texts.remove(taskNum-1);
        replaceContentsInFile();
        return String.format(MESSAGE_DELETED, textFile, deletedTask);
    }
    
    public static int getTaskNum(String taskNumber) {
        int taskNum =0;
        try{
            taskNum = Integer.parseInt(taskNumber);
        }
        catch(Exception e){
            return PARAM_INVALID_INTEGER;
        }
        
        if((taskNum>texts.size()) || (taskNum<1)){
            return PARAM_INVALID_INTEGER;
        }
        
        return taskNum;
    }

    public static String displayTasks(){
        String tasks = "";
        for(int i = 0; i < texts.size(); i++){
            tasks += String.format(MESSAGE_DISPLAY, i+1, texts.get(i));
            if(i!=texts.size()-1){
                tasks += "\n";
            }
        }
        return tasks;
    }
    
    public static String displayTasks(ArrayList<String> alteredList){
        String tasks = "";
        for(int i = 0; i < alteredList.size(); i++){
            tasks += String.format(MESSAGE_DISPLAY, i+1, alteredList.get(i));
            if(i!=alteredList.size()-1){
                tasks += "\n";
            }
        }
        return tasks;
    }
    
    public static String clear()throws IOException{
        texts.clear();
        clearFile();
        
        return String.format(MESSAGE_CLEAR, textFile);
    }

    public static void clearFile() throws IOException {
        FileWriter fileWriter = new FileWriter(textFile);
        // Always wrap FileWriter in BufferedWriter.
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        
        bufferedWriter.write(EMPTY_STRING);
        
        bufferedWriter.close();
        fileWriter.close();
    }
    
    public static String sort()throws IOException{
        Collections.sort(texts);
        replaceContentsInFile();
        
        return String.format(MESSAGE_SORT, textFile);
    }
    
    public static String searchKeyword(String keyword)throws IOException{
        ArrayList<String> searchedList = getTasksContainingKeyword(keyword);
        displayTasks(searchedList);
        
        return displayTasks(searchedList);
    }

    public static ArrayList<String> getTasksContainingKeyword(String keyword) {
        ArrayList<String> searchedList = new ArrayList<String>();
        for(int i =0; i< texts.size(); i++){
            if(texts.get(i).contains(keyword))
                searchedList.add(texts.get(i));
        }
        return searchedList;
    }
    
    public static void addTaskToFile(String text) throws IOException {
        FileWriter fileWriter = new FileWriter(textFile, true);
        // Always wrap FileWriter in BufferedWriter.
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        
        bufferedWriter.write(text);
        bufferedWriter.newLine();
        
        bufferedWriter.close();
        fileWriter.close();
        return;
    }
    
    public static void replaceContentsInFile() throws IOException {
        clearFile();
        addListToFile();
        
        return;
    }

    public static void addListToFile() throws IOException {
        FileWriter fileWriter = new FileWriter(textFile, true);
        // Always wrap FileWriter in BufferedWriter.
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for(int i = 0; i < texts.size(); i++){
            bufferedWriter.write(texts.get(i));
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
        fileWriter.close();
    }
}
