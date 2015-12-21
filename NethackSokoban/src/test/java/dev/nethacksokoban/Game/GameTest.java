package dev.nethacksokoban.Game;

import dev.nethacksokoban.Util.InputScanner;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    ByteArrayOutputStream stream;

    public GameTest() {
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
    public void createNewBoxLocation() {
        Game game = new Game(null, true);
        Box testBox = new Box(2, 3);
        Location expectedLocation = new Location(3, 3);
        assertEquals(expectedLocation, game.createNewBoxLocation(testBox, 2));
        expectedLocation = new Location(1, 3);
        assertEquals(expectedLocation, game.createNewBoxLocation(testBox, 8));
        expectedLocation = new Location(2, 2);
        assertEquals(expectedLocation, game.createNewBoxLocation(testBox, 4));
        expectedLocation = new Location(2, 4);
        assertEquals(expectedLocation, game.createNewBoxLocation(testBox, 6));
    }
        
//    @Test
    public void fullPlaythrough() {
        String input = formTestString("1", "3", "6", "6", "1", "4", "4", "7",
                "6", "6", "6", "6", "6", "6", "3", "999");
        Game testGame = new Game(new InputScanner(new Scanner(input)), true);
        testGame.startGame();
        String output = stream.toString();
        assertTrue("Printed: " + output, output.contains("Victory!")
                && output.contains("Moves used: 14"));
    }

//    @Test
    public void fullPlaythroughWithUneccessaryMoves() {
        String input = formTestString("1", "1", "3", "4", "1", "4", "4", "8",
                "6", "9", "4", "8", "4", "1", "4", "7", "8", "2", "6", "6", "6", "1",
                "2", "4", "1", "8", "8", "8", "6", "7", "9", "2", "6", "6", "6", "6",
                "1", "1", "4", "8", "7", "6", "6", "6", "6", "6", "3", "999");
        Game testGame = new Game(new InputScanner(new Scanner(input)), true);
        testGame.startGame();
        String output = stream.toString();
        assertTrue("Printed: " + output, output.contains("Victory!")
                && output.contains("Moves used: 32"));
    }

    public String formTestString(String... lines) {
        String linesWithEnter = "";
        for (String line : lines) {
            linesWithEnter += line + "\n";

        }
        return linesWithEnter;
    }
}
