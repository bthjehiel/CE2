import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class Test1 {

    @Test
    public void test() throws IOException{
        assertEquals("all content deleted from test.txt", CE2.executeCommand("clear"));
        assertEquals("added to test.txt: \"little brown fox\"", CE2.executeCommand("add little brown fox"));
        assertEquals("1. little brown fox", CE2.executeCommand("display"));
        assertEquals("added to test.txt: \"jumped over the moon\"", CE2.executeCommand("add jumped over the moon"));
        assertEquals("1. little brown fox\n2. jumped over the moon", CE2.executeCommand("display"));
        assertEquals("deleted from test.txt: \"jumped over the moon\"", CE2.executeCommand("delete 2"));
        assertEquals("Pls enter a valid command", CE2.executeCommand("display all"));
        assertEquals("1. little brown fox", CE2.executeCommand("display"));
        assertEquals("all content deleted from test.txt", CE2.executeCommand("clear"));
        assertEquals("test.txt is empty", CE2.executeCommand("display"));
        //fail("Not yet implemented");
    }

}
