package dev.nethacksokoban.Util;

import dev.nethacksokoban.UI.UI;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class InputScannerTest {

    ByteArrayOutputStream stream;

    public InputScannerTest() {
        stream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stream));
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void selectLevelOnlyReturnsIntegersBetweenOneAndGivenParameter() {
        String input = formTestString("a", "2");
        InputScanner is = new InputScanner(new Scanner(input));
        UI ui = new UI(is);
        is.setUi(ui);
        assertEquals(2, is.selectLevel(3));
        String output = stream.toString();
        assertTrue("Printed: " + output, output.contains("Select level between 1 and 3"));
    }

    @Test
    public void selectLevelCanReturnValueUpToGivenParameter() {
        String input = formTestString("3", "bbb", "8", "-1", "2");
        InputScanner is = new InputScanner(new Scanner(input));
        UI ui = new UI(is);
        is.setUi(ui);
        assertEquals(3, is.selectLevel(3));
    }

    @Test
    public void selectLevelCantReturnZero() {
        String input = formTestString("0", "0", "21", "-1", "7", "4");
        InputScanner is = new InputScanner(new Scanner(input));
        UI ui = new UI(is);
        is.setUi(ui);
        assertEquals(7, is.selectLevel(7));
    }

    @Test
    public void readCharReturnsFirstCharOfString() {
        String input = "x2634312";
        InputScanner is = new InputScanner(new Scanner(input));
        assertEquals('x', is.readChar());
    }

    @Test
    public void readCharReturnsFirstCharOfString2() {
        String input = "3312qweadcas";
        InputScanner is = new InputScanner(new Scanner(input));
        assertEquals('3', is.readChar());
    }

    public String formTestString(String... lines) {
        String linesWithEnter = "";
        for (String line : lines) {
            linesWithEnter += line + "\n";
        }
        return linesWithEnter;
    }
}
