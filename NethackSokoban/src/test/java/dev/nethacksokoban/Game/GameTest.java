package dev.nethacksokoban.Game;

import dev.nethacksokoban.UI.GUI;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javax.swing.SwingUtilities;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    ByteArrayOutputStream stream;
    Game game;
    Level level;

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
        game = new Game();
        game.createCurrentLevel(1);
        level = game.getLevel();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void startGameWithLevelIndex() {
        Game testGame = new Game();
        GUI gui = new GUI(testGame);
        testGame.setGUI(gui);
        SwingUtilities.invokeLater(gui);
        testGame.loadLevelsAndStartGame();
        testGame.startNewMapWithIndex(4);
        Location expectedPlayerLocation = new Location(2, 12);
        assertEquals(expectedPlayerLocation, testGame.getLevel().getPlayer().getLocation());
        assertEquals('<', testGame.getLevel().getTileFromLocation(new Location(4, 24)));
    }

    @Test
    public void loadLevels() {
        Game testGame = new Game();
        testGame.loadLevels();
        assertEquals(8, testGame.getLevels().size());
    }

    @Test
    public void playerCantGoThroughBoxes() {
        Box testBox = level.getBoxInLocation(2, 4);
        game.attemptPlayerMove(new Location(2, 4), 2);
        assertTrue(testBox.getLocation() != game.getLevel().getPlayer().getLocation());
    }

    @Test
    public void trapIsFilledWhenBoxIsMovedOnTopOfIt() {
        Box testBox = level.getBoxInLocation(2, 4);
        game.attemptBoxMove(new Location(2, 6), testBox);
        assertEquals('*', level.getTileFromLocation(new Location(2, 6)));
    }

    @Test
    public void boxIsRemovedWhenItFillsTrap() {
        Box testBox = level.getBoxInLocation(2, 4);
        game.attemptBoxMove(new Location(2, 6), testBox);
        assertEquals(false, level.getBoxes().contains(testBox));
    }

    @Test
    public void boxIsNotDuplicatedWhenMoved() {
        Box testBox = level.getBoxInLocation(new Location(2, 2));
        Location newBoxLocation = new Location(2, 3);
        game.attemptBoxMove(newBoxLocation, testBox);
        assertEquals(newBoxLocation, testBox.getLocation());
        assertEquals(3, level.getBoxes().size());
    }
    
    @Test
    public void checkVictory() {
        game.getLevel().getPlayer().setPlayerLocation(new Location(3, 8));
        assertEquals(true, game.checkVictory(new Location(3, 8)));
    }

    @Test
    public void attemptBoxMoveTowardsFreeTile() {
        assertEquals(true, game.attemptBoxMove(new Location(2, 3), level.getBoxInLocation(2, 2)));
        assertEquals('.', level.getTileFromLocation(new Location(2, 2)));
        assertTrue(level.getBoxInLocation(new Location(2, 3)) != null);
    }

    @Test
    public void attemptBoxMoveTowardsWall() {
        assertEquals(false, game.attemptBoxMove(new Location(3, 0), level.getBoxInLocation(3, 1)));
        assertEquals('#', level.getTileFromLocation(new Location(3, 0)));
        assertTrue(level.getBoxInLocation(new Location(3, 0)) == null);
        assertTrue(level.getBoxInLocation(new Location(3, 1)) != null);
    }

    @Test
    public void attemptBoxMoveTowardsTrap() {
        Box testBox = level.getBoxInLocation(2, 4);
        game.attemptBoxMove(new Location(2, 5), testBox);
        game.attemptBoxMove(new Location(2, 6), testBox);
        assertEquals('*', level.getTileFromLocation(new Location(2, 6)));
        assertEquals(false, game.getLevel().getBoxes().contains(testBox));
    }

    @Test
    public void attemptBoxMoveTowardsFilledTrap() {
        Box testBox = level.getBoxInLocation(2, 4);
        game.attemptBoxMove(new Location(2, 5), testBox);
        game.attemptBoxMove(new Location(2, 6), testBox);
        game.attemptBoxMove(new Location(2, 3), testBox);
        game.attemptBoxMove(new Location(2, 4), testBox);
        game.attemptBoxMove(new Location(2, 5), testBox);
        game.attemptBoxMove(new Location(2, 6), testBox);
        assertEquals('*', level.getTileFromLocation(new Location(2, 6)));
        assertEquals(false, level.getBoxes().contains(testBox));
    }

    @Test
    public void attemptBoxMoveTowardsAnotherBox() {
        Box testBox1 = level.getBoxInLocation(2, 2);
        Box testBox2 = level.getBoxInLocation(2, 4);
        game.attemptBoxMove(new Location(2, 3), testBox1);
        assertEquals(false, game.attemptBoxMove(testBox2.getLocation(), testBox1));
        assertEquals(3, level.getBoxes().size());
        assertTrue(level.getBoxInLocation(new Location(2, 2)) == null);
    }

    @Test
    public void attemptPlayerMoveTowardsFreeTile() {
        game.attemptPlayerMove(new Location(1, 3), 6);
        Location expectedLocation = new Location(1, 3);
        assertEquals(expectedLocation, game.getLevel().getPlayer().getLocation());
    }

    @Test
    public void attemptPlayerMoveTowardsWall() {
        game.attemptPlayerMove(new Location(0, 2), 8);
        Location expectedLocation = new Location(1, 2);
        assertEquals(expectedLocation, game.getLevel().getPlayer().getLocation());
    }

    @Test
    public void attemptPlayerMoveTowardsBox() {
        game.attemptPlayerMove(new Location(2, 2), 2);
        Location expectedLocation = new Location(2, 2);
        assertEquals(expectedLocation, game.getLevel().getPlayer().getLocation());
        assertTrue(level.getBoxInLocation(expectedLocation) == null);
        assertTrue(level.getBoxInLocation(new Location(3, 2)) != null);
    }

    @Test
    public void attemptPlayerMoveTowardsBoxThatIsAgainstAnotherBox() {
        game.attemptPlayerMove(new Location(2, 2), 2);
        game.attemptPlayerMove(new Location(3, 3), 0);
        game.attemptPlayerMove(new Location(3, 2), 4);
        Location expectedLocation = new Location(3, 3);
        assertEquals(expectedLocation, game.getLevel().getPlayer().getLocation());
        assertEquals(3, level.getBoxes().size());
        assertTrue(level.getBoxInLocation(new Location(3, 2)) != null);
        assertTrue(level.getBoxInLocation(new Location(3, 1)) != null);
    }

    @Test
    public void createNewBoxLocation() {
        game = new Game();
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

    @Test
    public void executeGameCommand() {
        game.executeGameCommand('3');
        game.executeGameCommand('1');
        game.executeGameCommand('6');
        game.executeGameCommand('8');
        game.executeGameCommand('6');
        game.executeGameCommand('6');
        game.executeGameCommand('1');
        game.executeGameCommand('4');
        game.executeGameCommand('4');
        game.executeGameCommand('7');
        game.executeGameCommand('6');
        game.executeGameCommand('6');
        game.executeGameCommand('2');
        game.executeGameCommand('4');
        game.executeGameCommand('9');
        game.executeGameCommand('6');
        game.executeGameCommand('6');
        game.executeGameCommand('6');
        game.executeGameCommand('3');
        char[][] expectedMap = new char[][]{
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
            {'#', '.', '.', '.', '.', '.', '#', '#', '.', '#'},
            {'#', '.', '.', '.', '.', '.', '*', '*', '.', '#'},
            {'#', '.', '.', '.', '.', '.', '#', '#', '<', '#'},
            {'#', '.', '.', '.', '.', '.', '#', '#', '.', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}};
        assertArrayEquals(expectedMap, game.getLevel().getMap());
    }
}
