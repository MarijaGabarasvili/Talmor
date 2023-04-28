import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class test {

    @Test
    void hasAChoise() {

        assertNotNull(Main.loop("exit"), "app should have a choise");
    }

    @Test
    void aboutWorks() {

        assertFalse(Main.loop("about").equals(""), "app should have about");

    }

    @Test
    void readWorks() {

        assertEquals(Main.fileUtils.read("src/test/java/test.txt"), "test text");

    }

    // @Test
    // void symbolCountWorks() {
    //     Main.Symbol symbol = new Main.Symbol();
    //     symbol.character = 'c';
    //     assertEquals(symbol.count("jidhfjasahjscjkljlkjsclkjjlkc"), 3);
    // }

    @Test
    void equalWorks() {
        assertTrue(Main.fileUtils.equal("src/test/java/test.txt", "src/test/java/test.txt"));
    }

    @Test
    void notEqualWorks() {
        assertFalse(Main.fileUtils.equal("src/test/java/test.txt", "README.md"));
    }
    @Test
    void writeWorks() {
        Main.fileUtils.write("src/test/java/testw.txt", "test text");
        assertEquals(Main.fileUtils.read("src/test/java/testw.txt"), "test text");
    }

}