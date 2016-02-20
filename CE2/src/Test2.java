import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;


public class Test2 {

    @Test
    public void test() throws IOException{
        assertEquals("all content deleted from test.txt", CE2.executeCommand("clear"));
        assertEquals("added to test.txt: \"this\"", CE2.executeCommand("add this"));
        assertEquals("added to test.txt: \"that\"", CE2.executeCommand("add that"));
        assertEquals("added to test.txt: \"those\"", CE2.executeCommand("add those"));
        assertEquals("Pls enter a valid command", CE2.executeCommand("add"));
        assertEquals("1. this\n2. that\n3. those", CE2.executeCommand("display"));
        assertEquals("Pls enter a valid command", CE2.executeCommand("display all"));
        assertEquals("Pls enter a valid task number", CE2.executeCommand("delete 4"));
        assertEquals("Pls enter a valid task number", CE2.executeCommand("delete -1"));
        assertEquals("Pls enter a valid task number", CE2.executeCommand("delete 2.3"));
        assertEquals("Pls enter a valid task number", CE2.executeCommand("delete"));
        assertEquals("deleted from test.txt: \"that\"", CE2.executeCommand("delete 2"));
        assertEquals("added to test.txt: \"that\"", CE2.executeCommand("add that"));
        assertEquals("1. this\n2. those\n3. that", CE2.executeCommand("display"));
        assertEquals("Pls enter a valid command", CE2.executeCommand("clear all"));
        assertEquals("all content deleted from test.txt", CE2.executeCommand("clear"));
        assertEquals("test.txt is empty", CE2.executeCommand("display"));
        assertEquals("No tasks to delete", CE2.executeCommand("delete 2"));
        assertEquals("added to test.txt: \"cbc a\"", CE2.executeCommand("add cbc a"));
        assertEquals("added to test.txt: \"bbc a\"", CE2.executeCommand("add bbc a"));
        assertEquals("added to test.txt: \"abc a\"", CE2.executeCommand("add abc a"));
        assertEquals("added to test.txt: \"abc b\"", CE2.executeCommand("add abc b"));
        assertEquals("1. cbc a\n2. bbc a\n3. abc a\n4. abc b", CE2.executeCommand("display"));
        assertEquals("Pls enter a valid command", CE2.executeCommand("sort all"));
        CE2.executeCommand("sort");
        assertEquals("1. abc a\n2. abc b\n3. bbc a\n4. cbc a", CE2.executeCommand("display"));
        assertEquals("1. abc a\n2. abc b", CE2.executeCommand("search ab"));
        assertEquals("1. bbc a", CE2.executeCommand("search bb"));
        assertEquals("No Such Tasks found", CE2.executeCommand("search agdw"));
        //fail("Not yet implemented");
    }
}
