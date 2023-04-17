/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package talmor;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test void appHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
    }

    @Test void appHasAChoise () {
        App classUnderTest = new App();
        assertNotNull(classUnderTest.loop("exit"), "app should have a choise");
    }

    @Test void appAboutWorks (){
        App classUnderTest = new App();
        assertFalse(classUnderTest.loop("about").equals(""), "app should have about");

    }
}
