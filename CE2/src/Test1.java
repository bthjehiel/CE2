import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;


public class Test1 {

    @Test
    public void test() throws IOException{
        assertEquals("all content deleted from test.txt", CE2.clear());
        assertEquals("added to test.txt: this", CE2.addTask("this"));
        assertEquals("added to test.txt: that", CE2.addTask("that"));
        assertEquals("added to test.txt: those", CE2.addTask("those"));
        assertEquals("1. this\n2. that\n3. those", CE2.displayTasks());
        assertEquals("Pls enter a valid task number", CE2.deleteTask("4"));
        assertEquals("Pls enter a valid task number", CE2.deleteTask("-1"));
        assertEquals("Pls enter a valid task number", CE2.deleteTask("2.3"));
        assertEquals("deleted from test.txt: \"that\"", CE2.deleteTask("2"));
        assertEquals("added to test.txt: that", CE2.addTask("that"));
        assertEquals("1. this\n2. those\n3. that", CE2.displayTasks());
        assertEquals("all content deleted from test.txt", CE2.clear());
        assertEquals("", CE2.displayTasks());
        assertEquals("No tasks to delete", CE2.deleteTask("2"));
        assertEquals("added to test.txt: cbc a", CE2.addTask("cbc a"));
        assertEquals("added to test.txt: bbc a", CE2.addTask("bbc a"));
        assertEquals("added to test.txt: abc a", CE2.addTask("abc a"));
        assertEquals("added to test.txt: abc b", CE2.addTask("abc b"));
        assertEquals("1. cbc a\n2. bbc a\n3. abc a\n4. abc b", CE2.displayTasks());
        CE2.sort();
        assertEquals("1. abc a\n2. abc b\n3. bbc a\n4. cbc a", CE2.displayTasks());
        //fail("Not yet implemented");
    }
}
