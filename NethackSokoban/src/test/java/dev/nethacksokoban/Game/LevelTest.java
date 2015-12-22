package dev.nethacksokoban.Game;

import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LevelTest {

    private Level level;

    private char[][] testLevel1 = new char[][]{
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
        {'#', '.', '@', '.', '.', '.', '#', '.', '.', '#'},
        {'#', '.', '0', '.', '0', '.', '^', '^', '.', '#'},
        {'#', '.', '.', '.', '.', '.', '#', '#', '<', '#'},
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}};

    public LevelTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        level = new Level(testLevel1);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void constructorRemovesStartingPositionAndBoxMarkers() {
        for (char[] row : level.getMap()) {
            assertEquals(false, Arrays.toString(row).contains("@"));
            assertEquals(false, Arrays.toString(row).contains("0"));
        }
    }

    @Test
    public void allBoxObjectsAreCreated() {
        assertEquals(2, level.getBoxes().size());
    }

    @Test
    public void allBoxesAreCreatedWithRightCoordinates() {
        assertEquals(2, level.getBoxes().get(0).getRow());
        assertEquals(2, level.getBoxes().get(0).getCol());
        assertEquals(2, level.getBoxes().get(1).getRow());
        assertEquals(4, level.getBoxes().get(1).getCol());
    }

    @Test
    public void getBoxInLocationReturnsNullIfNoBox() {
        assertEquals(null, level.getBoxInLocation(new Location(3, 1)));
    }
}
