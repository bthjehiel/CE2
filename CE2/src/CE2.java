import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CE2 {
    
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
    private static final String MESSAGE_UNRECOGNISED_COMMAND = "Pls enter a valid command";
    private static ArrayList<String> texts = new ArrayList<String>();
    
    public static void main(String args[]) throws IOException{
        displayMessage(String.format(MESSAGE_WELCOME, "test.txt"));
        startTextBuddy("test.txt");
        /*
        File textFile = new File("test.txt");
        if(!textFile.exists()){
            textFile.createNewFile();
        }*/
        //displayMessage(String.format(MESSAGE_WELCOME, args[0]));
        //startTextBuddy(args[0]);
        return;
    }

    public static void displayMessage(String message) {
        System.out.println(message);
    }

    public static void startTextBuddy(String textFile) throws IOException {
        Scanner sc = new Scanner(System.in);
        String[] inputs = getUserInput(sc);
        ArrayList<String> texts = initialiseList(textFile);
        
        while(!isCommandExit(inputs)){
        //while(isCommandExit(inputs)){   
            displayMessage(executeCommand(textFile, inputs, texts));
            inputs = getUserInput(sc);
        }
        sc.close();
        return;
    }

    public static String executeCommand(String textFile, String[] inputs, ArrayList<String> texts)
            throws IOException, FileNotFoundException {
        switch(inputs[0]){
        case COMMAND_ADD:
            return addTask(inputs[1], textFile);
            //break;
            
        case COMMAND_DELETE:
            if(!hasValidNumberOfParameters(inputs, NUMBER_DELETE_PARAMETERS)){
                return MESSAGE_UNRECOGNISED_COMMAND;
                //break;
            }
            else{
                return deleteTask(textFile, inputs[1]);
            }
            //break;
            
        case COMMAND_DISPLAY:
            if(!hasValidNumberOfParameters(inputs, NUMBER_DISPLAY_PARAMETERS)){
                return MESSAGE_UNRECOGNISED_COMMAND;
                //break;
            }
            return displayTasks();
            //break;
            
        case COMMAND_CLEAR:
            if(!hasValidNumberOfParameters(inputs, NUMBER_CLEAR_PARAMETERS)){
                return MESSAGE_UNRECOGNISED_COMMAND;
                //break;
            }
            return clear(textFile);
            //break;
            
        default:
            return MESSAGE_UNRECOGNISED_COMMAND;
            //break;
        }
        //return "";
    }
    
    public static boolean isCommandExit(String[] inputs) {
        return (inputs[0].equalsIgnoreCase(COMMAND_EXIT)) && (hasValidNumberOfParameters(inputs, NUMBER_EXIT_PARAMETERS));
    }

    public static String[] getUserInput(Scanner sc) {
        String userInput = sc.nextLine();
        String[] operation = userInput.split(SPACE);
        return operation;
    }

    public static ArrayList<String> initialiseList(String textFile) throws IOException {
        Scanner fileScanner = new Scanner(new File(textFile));
        while(fileScanner.hasNext()){
                texts.add(fileScanner.nextLine());
        }
        
        fileScanner.close();
        return texts;
    }
    
    public static boolean hasValidNumberOfParameters(String[] inputs, int numParameters){
        if(inputs.length == numParameters){
            return true;
        }
        return false;
    }
    
    public static String addTask(String description, String textFile) throws IOException {
        texts.add(new String(description));
        addTaskToFile(textFile, description);
        //String message = ;
        //System.out.println(message);
        return String.format(MESSAGE_ADDED, textFile, description);
    }
    
    public static String deleteTask(String textFile, String task) throws IOException {
        int taskNum = getTaskNum(task);
        if(taskNum == PARAM_INVALID_INTEGER){
            return MESSAGE_INVALID_TASK_NUMBER;
        }
        String deletedTask = texts.get(taskNum-1);
        texts.remove(taskNum-1);
        //displayMessage(String.format(MESSAGE_DELETED, textFile, deletedTask));
        updateFile(textFile);
        return String.format(MESSAGE_DELETED, textFile, deletedTask);
    }
    
    public static int getTaskNum(String taskNumber) {
        int taskNum =0;
        try{
            taskNum = Integer.parseInt(taskNumber);
        }
        catch(Exception e){
            //System.out.println(MESSAGE_INVALID_TASK_NUMBER);
            return PARAM_INVALID_INTEGER;
        }
        
        if((taskNum>texts.size()) || (taskNum<1)){
            //System.out.println(texts.size());
            //System.out.println(MESSAGE_INVALID_TASK_NUMBER);
            return PARAM_INVALID_INTEGER;
        }
        
        return taskNum;
    }

    public static String displayTasks(){
        String allTasks = "";
        for(int i = 0; i < texts.size(); i++){
            allTasks += String.format(MESSAGE_DISPLAY, i+1, texts.get(i)) +"\n";
            
            //System.out.println(String.format(MESSAGE_DISPLAY, i+1, texts.get(i)));;
        }
        return allTasks;
    }
    
    public static String clear(String textFile)throws IOException{
        texts.clear();
        FileWriter fileWriter = new FileWriter(textFile);
        // Always wrap FileWriter in BufferedWriter.
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        
        bufferedWriter.write(EMPTY_STRING);
        
        bufferedWriter.close();
        fileWriter.close();
        
        
        return String.format(MESSAGE_CLEAR, textFile);
    }
    
    public static void addTaskToFile(String textFile, String text) throws IOException {
        FileWriter fileWriter = new FileWriter(textFile, true);
        // Always wrap FileWriter in BufferedWriter.
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        
        bufferedWriter.write(text);
        bufferedWriter.newLine();
        
        bufferedWriter.close();
        fileWriter.close();
        return;
    }
    
    //Saves new list into the file
    public static void updateFile(String textFile) throws IOException {
        FileWriter fileWriter = new FileWriter(textFile, true);
        // Always wrap FileWriter in BufferedWriter.
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for(int i = 0; i < texts.size(); i++){
            bufferedWriter.write(texts.get(i));
        }
        bufferedWriter.close();
        fileWriter.close();
        
        return;
    }
}
