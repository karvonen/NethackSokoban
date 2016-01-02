package dev.nethacksokoban.Util;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class FileScannerTest {

    private FileScanner fileScanner;

    public FileScannerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        fileScanner = new FileScanner();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void loadMapsLoadsAllMaps() {
        ArrayList<char[][]> loadedMaps = fileScanner.loadMaps();
        assertEquals(8, loadedMaps.size());
    }

    @Test
    public void readAndParseParse() {
        char[][] expectedMap = new char[][]{
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
            {'#', '.', '.', '.', '.', '#', '#', '#', '#', '.', '.', '.', '#', '#'},
            {'#', '.', '0', '.', '.', '#', '#', '#', '#', '.', '0', '.', '#', '#'},
            {'#', '.', '0', '.', '.', '.', '.', '.', '.', '0', '.', '.', '#', '#'},
            {'#', '.', '.', '#', '#', '#', '@', '#', '#', '#', '0', '.', '#', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '.', '#', '#', '#'},
            {'#', '.', '.', '^', '^', '^', '<', '#', '.', '.', '.', '.', '.', '#'},
            {'#', '.', '.', '#', '#', '#', '#', '#', '0', '.', '.', '.', '.', '#'},
            {'#', '#', '^', '#', '#', '#', '#', '#', '.', '0', '.', '.', '.', '#'},
            {'#', '#', '^', '#', '#', '#', '#', '#', '.', '0', '.', '.', '.', '#'},
            {'#', '#', '.', '.', '^', '^', '^', '^', '0', '.', '0', '.', '.', '#'},
            {'#', '#', '.', '.', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}};

        assertArrayEquals(expectedMap, fileScanner.readAndParseFile("src/levels/2.txt"));
    }
    
    @Test
    public void readAndParseReturnsNullIfNoFileFound() {
        assertArrayEquals(null, fileScanner.readAndParseFile("src/levels/66.txt"));
    }
}
