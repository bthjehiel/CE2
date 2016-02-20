import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class CE2 {
    
    private static final String SPACE = " ";
    private static final String EMPTY_STRING = "";
    private static final int NUMBER_SORT_PARAMETERS = 1;
    private static final int NUMBER_EXIT_PARAMETERS = 1;
    private static final int NUMBER_DELETE_PARAMETERS = 2;
    private static final int NUMBER_CLEAR_PARAMETERS = 1;
    private static final int NUMBER_DISPLAY_PARAMETERS = 1;
    private static final int NUMBER_ADD_PARAMETERS = 2;
    private static final int NUMBER_SEARCH_PARAMETERS = 2;
    private static final int PARAM_INVALID_INTEGER = -1;
    private static final String COMMAND_CLEAR = "clear";
    private static final String COMMAND_DISPLAY = "display";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_SEARCH = "search";
    private static final String COMMAND_SORT = "sort";
    private static final String COMMAND_EXIT = "exit";
    private static final String MESSAGE_DISPLAY = "%1$s. %2$s";
    private static final String MESSAGE_DELETED = "deleted from %1$s: \"%2$s\"";
    private static final String MESSAGE_CLEAR = "all content deleted from %1$s";
    private static final String MESSAGE_ADDED = "added to %1$s: \"%2$s\"";
    private static final String MESSAGE_EXIT = "Terminating textbuddy";
    private static final String MESSAGE_SORT = "Sorted tasks alphabetically";
    private static final String MESSAGE_EMPTY_FILE = "%1$s is empty";
    private static final String MESSAGE_NO_TASKS_CONTAINING_KEYWORD = "No Such Tasks found";
    private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %1$s is ready for use";
    private static final String MESSAGE_INVALID_TASK_NUMBER = "Pls enter a valid task number";
    private static final String MESSAGE_NO_TASKS_TO_DELETE = "No tasks to delete";
    private static final String MESSAGE_UNRECOGNISED_COMMAND = "Pls enter a valid command";
    private static String[] userInputs;
    private static ArrayList<String> texts = new ArrayList<String>();
    private static String textFile = "test.txt";
    
    public static void main(String args[]) throws IOException{
        //String textFile == args[0];
        createFileIfNotFound();
        displayMessage(String.format(MESSAGE_WELCOME, textFile));
        startTextBuddy();
        
        return;
    }

    public static void createFileIfNotFound() throws IOException {
        File myFile = new File(textFile);
        if(!myFile.exists()){
            myFile.createNewFile();
        }
    }

    public static void displayMessage(String message) {
        System.out.println(message);
    }

    public static void startTextBuddy() throws IOException {
        Scanner sc = new Scanner(System.in);
        texts = initialiseList();
        
        do{ 
            displayMessage(executeCommand(sc.nextLine()));
        }while(!isCommandExit());
        sc.close();
        return;
    }
    
    public static String[] getUserInput(String rawCommand) {
        String[] operation = rawCommand.trim().split(SPACE,2);
        return operation;
    }
    
    public static String executeCommand(String rawCommand) throws IOException, FileNotFoundException {
        userInputs = getUserInput(rawCommand);
        String command = userInputs[0];
        
        return determineCommand(command);
    }

    public static String determineCommand(String command) throws IOException {
        switch(command){
        case COMMAND_ADD:
            return addTask();
            
        case COMMAND_DELETE:
                return deleteTask();
            
        case COMMAND_DISPLAY:
            return displayTasks();
            
        case COMMAND_CLEAR:
            return clear();
            
        case COMMAND_SORT:
            return sort();
            
        case COMMAND_SEARCH:
            return searchKeyword();
            
        case COMMAND_EXIT:
            return exitTextbuddy();
            
        default:
            return MESSAGE_UNRECOGNISED_COMMAND;
        }
    }
    
    public static boolean isCommandExit() {
        return (userInputs[0].equalsIgnoreCase(COMMAND_EXIT)) && (hasValidNumberOfParameters(NUMBER_EXIT_PARAMETERS));
    }
    
    public static String exitTextbuddy() throws IOException {
        if(!hasValidNumberOfParameters(NUMBER_EXIT_PARAMETERS)){
            return MESSAGE_UNRECOGNISED_COMMAND;
        }
        
        return MESSAGE_EXIT;
    }

    public static ArrayList<String> initialiseList() throws IOException {
        Scanner fileScanner = new Scanner(new File(textFile));
        while(fileScanner.hasNext()){
                texts.add(fileScanner.nextLine());
        }
        
        fileScanner.close();
        return texts;
    }
    

    public static boolean hasValidNumberOfParameters(int numParameters){
        return (userInputs.length==numParameters);
    }
    
    public static String addTask() throws IOException {
        if(!specifiedDescription()){
            return MESSAGE_UNRECOGNISED_COMMAND;
        }
        String taskDescription = userInputs[1];
        texts.add(new String(taskDescription));
        addTaskToFile(taskDescription);
        return String.format(MESSAGE_ADDED, textFile, taskDescription);
    }
    
    public static boolean specifiedDescription(){
        return (userInputs.length>1);
    }
    
    public static String deleteTask() throws IOException {
        if(!hasValidNumberOfParameters(NUMBER_DELETE_PARAMETERS)){
            return MESSAGE_INVALID_TASK_NUMBER;
        }
        int taskNum = getTaskNum(userInputs[1]);
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
        if(!hasValidNumberOfParameters(NUMBER_DISPLAY_PARAMETERS)){
            return MESSAGE_UNRECOGNISED_COMMAND;
        }
        if(texts.size() == 0){
            return String.format(MESSAGE_EMPTY_FILE, textFile);
        }
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
        if(alteredList.size() == 0){
            return String.format(MESSAGE_NO_TASKS_CONTAINING_KEYWORD);
        }
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
        if(!hasValidNumberOfParameters(NUMBER_CLEAR_PARAMETERS)){
            return MESSAGE_UNRECOGNISED_COMMAND;
        }
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
        if(!hasValidNumberOfParameters(NUMBER_SORT_PARAMETERS)){
            return MESSAGE_UNRECOGNISED_COMMAND;
        }
        Collections.sort(texts);
        replaceContentsInFile();
        
        return String.format(MESSAGE_SORT, textFile);
    }
    
    public static String searchKeyword()throws IOException{
        if(!hasValidNumberOfParameters(NUMBER_SEARCH_PARAMETERS)){
            return MESSAGE_UNRECOGNISED_COMMAND;
        }
        String keyword = userInputs[1];
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
