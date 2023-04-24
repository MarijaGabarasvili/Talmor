import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class test {

    @Test void hasAChoise () {
        
        assertNotNull(Main.loop("exit"), "app should have a choise");
    }

    @Test void aboutWorks (){
        
        assertFalse(Main.loop("about").equals(""), "app should have about");

    }

    @Test void readWorks (){
        try {
            assertEquals(Main.fileUtils.read("src/test/java/test.txt"),"test text");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Test void symbolCountWorks(){
        Main.Symbol symbol= new Main.Symbol();
        symbol.character='c';
        assertEquals(symbol.count("jidhfjasahjscjkljlkjsclkjjlkc"),3);
    }

    @Test void equalWorks(){
        assertTrue(Main.fileUtils.equal("src/test/java/test.txt","src/test/java/test.txt"));
    }

    @Test void notEqualWorks(){
        assertFalse(Main.fileUtils.equal("src/test/java/test.txt","README.md"));
    }

    // @Test void nodePrintWorks(){
        // Main.Node node = new Main.Node(false, 'c', -1);
        // assertEquals(node.print(),"false c -1");
    // }
}