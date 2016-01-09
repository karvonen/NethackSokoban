package dev.nethacksokoban.util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MapValidatorTest {

    public MapValidatorTest() {
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
    public void validate() {
        char[][] testMap = new char[][]{
            {'#', '#', '#', '#', '#'},
            {'#', '0', '0', '.', '#'},
            {'#', '.', '@', '<', '#'},
            {'#', '.', '^', '.', '#'},
            {'#', '#', '#', '#', '#'}};

        MapValidator validator = new MapValidator(testMap);
        assertEquals(true, validator.validate());
    }

    @Test
    public void validateBrokenTopLeftCorner() {
        char[][] testMap = new char[][]{
            {'.', '#', '#', '#', '#'},
            {'#', '.', '.', '.', '#'},
            {'#', '.', '@', '<', '#'},
            {'#', '.', '.', '.', '#'},
            {'#', '#', '#', '#', '#'}};

        MapValidator validator = new MapValidator(testMap);
        assertEquals(false, validator.validate());
    }

    @Test
    public void validateBrokenTopRightCorner() {
        char[][] testMap = new char[][]{
            {'#', '#', '#', '#', '.'},
            {'#', '.', '.', '.', '#'},
            {'#', '.', '@', '<', '#'},
            {'#', '.', '.', '.', '#'},
            {'#', '#', '#', '#', '#'}};

        MapValidator validator = new MapValidator(testMap);
        assertEquals(false, validator.validate());
    }

    @Test
    public void validateBrokenBottomRightCorner() {
        char[][] testMap = new char[][]{
            {'#', '#', '#', '#', '#'},
            {'#', '.', '.', '.', '#'},
            {'#', '.', '@', '<', '#'},
            {'#', '.', '.', '.', '#'},
            {'#', '#', '#', '#', '.'}};

        MapValidator validator = new MapValidator(testMap);
        assertEquals(false, validator.validate());
    }

    @Test
    public void validateBrokenBottomLeftCorner() {
        char[][] testMap = new char[][]{
            {'#', '#', '#', '#', '#'},
            {'#', '.', '.', '.', '#'},
            {'#', '.', '@', '<', '#'},
            {'#', '.', '.', '.', '#'},
            {'.', '#', '#', '#', '#'}};

        MapValidator validator = new MapValidator(testMap);
        assertEquals(false, validator.validate());
    }

    @Test
    public void validateStartingPositions() {
        char[][] testMap = new char[][]{
            {'#', '#', '#', '#', '#'},
            {'#', '.', '.', '@', '#'},
            {'#', '.', '@', '<', '#'},
            {'#', '.', '.', '.', '#'},
            {'#', '#', '#', '#', '#'}};

        MapValidator validator = new MapValidator(testMap);
        assertEquals(false, validator.validate());
    }

    @Test
    public void validateMissingStartingPosition() {
        char[][] testMap = new char[][]{
            {'#', '#', '#', '#', '#'},
            {'#', '.', '.', '@', '#'},
            {'#', '.', '@', '<', '#'},
            {'#', '.', '.', '.', '#'},
            {'#', '#', '#', '#', '#'}};

        MapValidator validator = new MapValidator(testMap);
        assertEquals(false, validator.validate());
    }

    @Test
    public void validateMissingGoal() {
        char[][] testMap = new char[][]{
            {'#', '#', '#', '#', '#'},
            {'#', '.', '.', '^', '#'},
            {'#', '.', '@', '.', '#'},
            {'#', '.', '.', '.', '#'},
            {'#', '#', '#', '#', '#'}};

        MapValidator validator = new MapValidator(testMap);
        assertEquals(false, validator.validate());
    }

    @Test
    public void validateMultipleGoal() {
        char[][] testMap = new char[][]{
            {'#', '#', '#', '#', '#'},
            {'#', '<', '.', '^', '#'},
            {'#', '.', '@', '.', '#'},
            {'#', '.', '<', '.', '#'},
            {'#', '#', '#', '#', '#'}};

        MapValidator validator = new MapValidator(testMap);
        assertEquals(false, validator.validate());
    }

    @Test
    public void validateInvalidCharacter() {
        char[][] testMap = new char[][]{
            {'#', '#', '#', '#', '#'},
            {'#', '.', '.', '^', '#'},
            {'#', '.', '@', '.', '#'},
            {'#', '+', '<', '.', '#'},
            {'#', '#', '#', '#', '#'}};

        MapValidator validator = new MapValidator(testMap);
        assertEquals(false, validator.validate());
    }

}
