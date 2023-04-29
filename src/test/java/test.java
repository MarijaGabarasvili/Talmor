import org.junit.jupiter.api.Test;

import java.io.File;

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
        Main.fileUtils.write("src/test/java/testw.txt","test text");
        assertEquals(Main.fileUtils.read("src/test/java/testw.txt"),"test text");
        File file = new File("src/test/java/testw.txt");
        if(file.delete()) System.out.println("file deleted");
    }

    @Test void compileAndDecompileWorks(){
        Main.fileUtils.write("src/test/java/test.txt","test text");
        assertEquals(Main.fileUtils.comp("src/test/java/test.txt","src/test/java/testc.txt"), "success");
        assertEquals(Main.fileUtils.decomp("src/test/java/testc.txt","src/test/java/testd.txt"), "success");
        assertEquals(Main.fileUtils.read("src/test/java/testd.txt"),"test text");
    }

    @Test void compileAndDecompileWorks1(){
        assertEquals(Main.fileUtils.comp("File1.html","File1.dat"), "success");
        assertEquals(Main.fileUtils.decomp("File1.dat","File1c.html"), "success");
        assertTrue(Main.fileUtils.equal("File1.html", "File1c.html"));
    }
    @Test void compileAndDecompileWorks2(){
        assertEquals(Main.fileUtils.comp("File2.html","File2.dat"), "success");
        assertEquals(Main.fileUtils.decomp("File2.dat","File2c.html"), "success");
        assertTrue(Main.fileUtils.equal("File2.html", "File2c.html"));
    }

    @Test void compileAndDecompileWorks3(){
        assertEquals(Main.fileUtils.comp("File3.html","File3.dat"), "success");
        assertEquals(Main.fileUtils.decomp("File3.dat","File3c.html"), "success");
        assertTrue(Main.fileUtils.equal("File3.html", "File3c.html"));
    }

    @Test void compileAndDecompileWorks4(){
        assertEquals(Main.fileUtils.comp("File4.html","File4.dat"), "success");
        assertEquals(Main.fileUtils.decomp("File4.dat","File4c.html"), "success");
        assertTrue(Main.fileUtils.equal("File4.html", "File4c.html"));
    }
}