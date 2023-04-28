import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class test {

    @Test void hasAChoise () {
        
        assertNotNull(Main.loop("exit"), "app should have a choise");
    }

    @Test void aboutWorks (){
        
        assertFalse(Main.loop("about").equals(""), "app should have about");

    }

    @Test void readWorks (){
        assertEquals(Main.fileUtils.read("src/test/java/test.txt"),"test text");
        
    }

    @Test void equalWorks(){
        assertTrue(Main.fileUtils.equal("src/test/java/test.txt","src/test/java/test.txt"));
    }

    @Test void notEqualWorks(){
        assertFalse(Main.fileUtils.equal("src/test/java/test.txt","README.md"));
    }

    @Test void writeWorks(){
        Main.fileUtils.write("src/test/java/test.txt","test text");
        assertEquals(Main.fileUtils.read("src/test/java/test.txt"),"test text");
    }

    @Test void compileAndDecompileWorks(){
        Main.fileUtils.write("src/test/java/test.txt","test text");
        assertEquals(Main.fileUtils.comp("src/test/java/test.txt","src/test/java/testc.txt"), "success");
        assertEquals(Main.fileUtils.decomp("src/test/java/testc.txt","src/test/java/testd.txt"), "success");
        assertEquals(Main.fileUtils.read("src/test/java/testd.txt"),"test text");
    }


}