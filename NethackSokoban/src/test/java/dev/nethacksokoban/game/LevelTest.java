package dev.nethacksokoban.game;

import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LevelTest {

    private Level level;
    private Game game;

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

        game = new Game();
        game.createCurrentLevel(1);

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
    public void isTileFreeToBeMovedOn() {
        assertEquals(true, level.isTileFreeToBeMovedOn(new Location(1, 1)));
    }

    @Test
    public void playerDoesntBlockMovement() {
        assertEquals(true, level.isTileFreeToBeMovedOn(level.getPlayer().getLocation()));
    }

    @Test
    public void victoryTileCanBeMovedOn() {
        assertEquals(true, level.isTileFreeToBeMovedOn(new Location(3, 8)));
    }

    @Test
    public void wallsBlockMovement() {
        assertEquals(false, level.isTileFreeToBeMovedOn(new Location(0, 1)));
    }

    @Test
    public void replaceWithOpenSpot() {
        level.replaceWithOpenSpot(1, 6);
        assertEquals('.', level.getTileFromLocation(new Location(1, 6)));
    }

    @Test
    public void playerIsCreatedCorrectly() {
        Location expectedPlayerLocation = new Location(1, 2);
        assertEquals(expectedPlayerLocation, level.getPlayer().getLocation());
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

    @Test
    public void checkVictoryOnVictoryTile() {
        level = game.getLevel();
        game.getLevel().getPlayer().setPlayerLocation(new Location(3, 8));
        assertEquals(true, level.checkVictory(game.getLevel().getPlayer().getLocation()));
    }

    @Test
    public void checkVictoryOnNormalTile() {
        level = game.getLevel();
        game.getLevel().getPlayer().setPlayerLocation(new Location(2, 8));
        assertEquals(false, level.checkVictory(game.getLevel().getPlayer().getLocation()));
    }

    @Test
    public void checkVictoryOnFilledTrapTile() {
        level = game.getLevel();
        game.getLevel().fillTrap(new Location(2, 7));
        game.getLevel().getPlayer().setPlayerLocation(new Location(2, 7));
        assertEquals(false, level.checkVictory(game.getLevel().getPlayer().getLocation()));
    }
}
