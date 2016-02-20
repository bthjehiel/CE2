import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;


public class Test1 {

    @Test
    public void test() throws IOException{
        assertEquals("all content deleted from test.txt", CE2.clear("test.txt"));
        assertEquals("added to test.txt: this", CE2.addTask("this", "test.txt"));
        assertEquals("added to test.txt: that", CE2.addTask("that", "test.txt"));
        assertEquals("added to test.txt: those", CE2.addTask("those", "test.txt"));
        assertEquals("1. this\n2. that\n3. those\n", CE2.displayTasks());
        assertEquals("Pls enter a valid task number", CE2.deleteTask("test.txt", "4"));
        assertEquals("Pls enter a valid task number", CE2.deleteTask("test.txt", "-1"));
        assertEquals("Pls enter a valid task number", CE2.deleteTask("test.txt", "2.3"));

        //fail("Not yet implemented");
    }
}
