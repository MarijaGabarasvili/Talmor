import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test {

    @Test void appHasAChoise () {
        
        assertNotNull(Main.loop("exit"), "app should have a choise");
    }

    @Test void appAboutWorks (){
        
        assertFalse(Main.loop("about").equals(""), "app should have about");

    }
}