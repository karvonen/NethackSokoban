/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.nethacksokoban.game;

import dev.nethacksokoban.game.Location;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author AAA
 */
public class LocationTest {

    public LocationTest() {
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
    public void differentRowComparison() {
        Location locationTest1 = new Location(1, 2);
        Location locationTest2 = new Location(2, 2);
        assertEquals(false, locationTest2.equals(locationTest1));
    }

    @Test
    public void differentColComparison() {
        Location locationTest1 = new Location(2, 3);
        Location locationTest2 = new Location(2, 2);
        assertEquals(false, locationTest2.equals(locationTest1));
    }

    @Test
    public void differentComparison() {
        Location locationTest1 = new Location(1, 2);
        Location locationTest2 = new Location(2, 3);
        assertEquals(false, locationTest2.equals(locationTest1));
    }

    @Test
    public void locationsComparison() {
        Location locationTest1 = new Location(2, 2);
        Location locationTest2 = new Location(2, 2);
        assertEquals(true, locationTest2.equals(locationTest1));
    }

    @Test
    public void locationsComparisonWithNull() {
        Location locationTest1 = null;
        Location locationTest2 = new Location(2, 2);
        assertEquals(false, locationTest2.equals(locationTest1));
    }

    @Test
    public void locationsComparisonWhenOneIsNotLocation() {
        int locationTest1 = 1;
        Location locationTest2 = new Location(2, 2);
        assertEquals(false, locationTest2.equals(locationTest1));
    }
}
