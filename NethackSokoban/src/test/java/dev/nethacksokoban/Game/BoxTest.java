package dev.nethacksokoban.Game;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoxTest {

    public BoxTest() {
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
    public void newBox() {
        Box testBox = new Box(1, 2);
        Location expectedLocation = new Location(1, 2);
        assertEquals(expectedLocation, testBox.getLocation());
    }

    @Test
    public void setLocation() {
        Box testBox = new Box(3, 4);
        Location newLocation = new Location(6, 6);
        testBox.setLocation(newLocation);
        assertEquals(newLocation, testBox.getLocation());
    }

    @Test
    public void setRow() {
        Box testBox = new Box(3, 4);
        Location newLocation = new Location(5, 4);
        testBox.setRow(5);
        assertEquals(newLocation, testBox.getLocation());
    }

    @Test
    public void setCol() {
        Box testBox = new Box(5, 3);
        Location newLocation = new Location(5, 6);
        testBox.setCol(6);
        assertEquals(newLocation, testBox.getLocation());
    }
}
