package dev.nethacksokoban.Game;

import dev.nethacksokoban.UI.GUI;
import javax.swing.SwingUtilities;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    Game game;
    Level level;
    Game executeGameCommandTestGame;

    public GameTest() {
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

        char[][] executeCommandMovementTestMap = new char[][]{
            {'#', '#', '#', '#', '#'},
            {'#', '.', '.', '.', '#'},
            {'#', '.', '@', '.', '#'},
            {'#', '.', '.', '.', '#'},
            {'#', '#', '#', '#', '#'}};
        executeGameCommandTestGame = new Game();
        executeGameCommandTestGame.setLevel(new Level(executeCommandMovementTestMap));

    }

    @After
    public void tearDown() {
    }

    //This test works fine but makes running PIT-report totally crazy and causes it
    //to run a few minutes instead of couple of seconds.
//    @Test
    public void startGameWithLevelIndex() {
        Game testGame = new Game();
        GUI gui = new GUI(testGame);
        testGame.setGUI(gui);
        SwingUtilities.invokeLater(gui);
        testGame.startGame();
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
    public void levelLoadedCorrectly() {
        Game testGame = new Game();
        testGame.loadLevels();
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

        assertArrayEquals(expectedMap, testGame.getLevels().get(2));
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
    public void executeGameCommand1() {
        executeGameCommandTestGame.executeGameCommand('1');
        Location expectedPlayerLocation = new Location(3, 1);
        assertEquals(expectedPlayerLocation, executeGameCommandTestGame.getLevel().getPlayer().getLocation());
    }

    @Test
    public void executeGameCommand2() {
        executeGameCommandTestGame.executeGameCommand('2');
        Location expectedPlayerLocation = new Location(3, 2);
        assertEquals(expectedPlayerLocation, executeGameCommandTestGame.getLevel().getPlayer().getLocation());
    }

    @Test
    public void executeGameCommand3() {
        executeGameCommandTestGame.executeGameCommand('3');
        Location expectedPlayerLocation = new Location(3, 3);
        assertEquals(expectedPlayerLocation, executeGameCommandTestGame.getLevel().getPlayer().getLocation());
    }

    @Test
    public void executeGameCommand4() {
        executeGameCommandTestGame.executeGameCommand('4');
        Location expectedPlayerLocation = new Location(2, 1);
        assertEquals(expectedPlayerLocation, executeGameCommandTestGame.getLevel().getPlayer().getLocation());
    }

    @Test
    public void executeGameCommand6() {
        executeGameCommandTestGame.executeGameCommand('6');
        Location expectedPlayerLocation = new Location(2, 3);
        assertEquals(expectedPlayerLocation, executeGameCommandTestGame.getLevel().getPlayer().getLocation());
    }

    @Test
    public void executeGameCommand7() {
        executeGameCommandTestGame.executeGameCommand('7');
        Location expectedPlayerLocation = new Location(1, 1);
        assertEquals(expectedPlayerLocation, executeGameCommandTestGame.getLevel().getPlayer().getLocation());
    }

    @Test
    public void executeGameCommand8() {
        executeGameCommandTestGame.executeGameCommand('8');
        Location expectedPlayerLocation = new Location(1, 2);
        assertEquals(expectedPlayerLocation, executeGameCommandTestGame.getLevel().getPlayer().getLocation());
    }

    @Test
    public void executeGameCommand9() {
        executeGameCommandTestGame.executeGameCommand('9');
        Location expectedPlayerLocation = new Location(1, 3);
        assertEquals(expectedPlayerLocation, executeGameCommandTestGame.getLevel().getPlayer().getLocation());
    }

    @Test
    public void cantMoveDiagonallyBetweenTwoWallsDirection1() {
        char[][] diagonalTestMap = new char[][]{
            {'#', '#', '#', '#', '#'},
            {'#', '.', '.', '.', '#'},
            {'#', '#', '@', '.', '#'},
            {'#', '.', '#', '.', '#'},
            {'#', '#', '#', '#', '#'}};
        Game diagonal = new Game();
        diagonal.setLevel(new Level(diagonalTestMap));
        assertEquals(false, diagonal.checkDiagonal(1));
    }

    @Test
    public void cantMoveDiagonallyBetweenTwoWallsDirection3() {
        char[][] diagonalTestMap = new char[][]{
            {'#', '#', '#', '#', '#'},
            {'#', '.', '.', '.', '#'},
            {'#', '.', '@', '#', '#'},
            {'#', '.', '#', '.', '#'},
            {'#', '#', '#', '#', '#'}};
        Game diagonal = new Game();
        diagonal.setLevel(new Level(diagonalTestMap));
        assertEquals(false, diagonal.checkDiagonal(3));
    }

    @Test
    public void cantMoveDiagonallyBetweenTwoWallsDirection7() {
        char[][] diagonalTestMap = new char[][]{
            {'#', '#', '#', '#', '#'},
            {'#', '.', '#', '.', '#'},
            {'#', '#', '@', '.', '#'},
            {'#', '.', '.', '.', '#'},
            {'#', '#', '#', '#', '#'}};
        Game diagonal = new Game();
        diagonal.setLevel(new Level(diagonalTestMap));
        assertEquals(false, diagonal.checkDiagonal(7));
    }

    @Test
    public void cantMoveDiagonallyBetweenTwoWallsDirection9() {
        char[][] diagonalTestMap = new char[][]{
            {'#', '#', '#', '#', '#'},
            {'#', '.', '#', '.', '#'},
            {'#', '.', '@', '#', '#'},
            {'#', '.', '.', '.', '#'},
            {'#', '#', '#', '#', '#'}};
        Game diagonal = new Game();
        diagonal.setLevel(new Level(diagonalTestMap));
        assertEquals(false, diagonal.checkDiagonal(9));
    }

    @Test
    public void cantMoveDiagonallyBetweenTwoBoxes() {
        char[][] diagonalTestMap = new char[][]{
            {'#', '#', '#', '#', '#'},
            {'#', '.', '0', '.', '#'},
            {'#', '#', '@', '0', '#'},
            {'#', '.', '#', '.', '#'},
            {'#', '#', '#', '#', '#'}};
        Game diagonal = new Game();
        diagonal.setLevel(new Level(diagonalTestMap));
        assertEquals(false, diagonal.checkDiagonal(9));
    }

    @Test
    public void cantMoveDiagonallyBetweenBoxAndWall() {
        char[][] diagonalTestMap = new char[][]{
            {'#', '#', '#', '#', '#'},
            {'#', '#', '0', '#', '#'},
            {'#', '#', '@', '0', '#'},
            {'#', '#', '#', '.', '#'},
            {'#', '#', '#', '#', '#'}};
        Game diagonal = new Game();
        diagonal.setLevel(new Level(diagonalTestMap));
        assertEquals(false, diagonal.checkDiagonal(3));
    }

    @Test
    public void canMoveDiagonally() {
        char[][] diagonalTestMap = new char[][]{
            {'#', '#', '#', '#', '#'},
            {'#', '.', '.', '.', '#'},
            {'#', '.', '@', '.', '#'},
            {'#', '.', '.', '.', '#'},
            {'#', '#', '#', '#', '#'}};
        Game diagonal = new Game();
        diagonal.setLevel(new Level(diagonalTestMap));
        assertEquals(true, diagonal.checkDiagonal(1));
        assertEquals(true, diagonal.checkDiagonal(7));
        assertEquals(true, diagonal.checkDiagonal(9));
        assertEquals(true, diagonal.checkDiagonal(3));
    }

    @Test
    public void executeDiagonal() {
        Game testGame = new Game();
        testGame.createCurrentLevel(1);

        testGame.executeGameCommand('3');
        testGame.executeGameCommand('6');
        testGame.executeGameCommand('6');
        testGame.executeGameCommand('4');
        testGame.executeGameCommand('3');
        testGame.executeGameCommand('7');

        char[][] expectedMap = new char[][]{
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
            {'#', '.', '.', '.', '.', '.', '#', '#', '.', '#'},
            {'#', '.', '.', '.', '.', '.', '*', '^', '.', '#'},
            {'#', '.', '.', '.', '.', '.', '#', '#', '<', '#'},
            {'#', '.', '.', '.', '.', '.', '#', '#', '.', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}};
        assertArrayEquals(expectedMap, testGame.getLevel().getMap());

        Location expectedPlayerLocation = new Location(2, 4);
        assertEquals(expectedPlayerLocation, testGame.getLevel().getPlayer().getLocation());
    }

    @Test
    public void fullPlayThrough() {
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
        game.executeGameCommand('9');
        game.executeGameCommand('7');
        game.executeGameCommand('1');
        game.executeGameCommand('6');
        game.executeGameCommand('6');
        game.executeGameCommand('2');
        game.executeGameCommand('4');
        game.executeGameCommand('9');
        game.executeGameCommand('6');
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
        Location expectedPlayerLocation = new Location(3, 8);
        assertEquals(expectedPlayerLocation, game.getLevel().getPlayer().getLocation());
    }

    @Test
    public void fullPlayThrough2() {
        Game testGame = new Game();
        testGame.createCurrentLevel(1);

        testGame.executeGameCommand('1');
        testGame.executeGameCommand('3');
        testGame.executeGameCommand('9');
        testGame.executeGameCommand('3');
        testGame.executeGameCommand('1');
        testGame.executeGameCommand('7');
        testGame.executeGameCommand('2');
        testGame.executeGameCommand('1');
        testGame.executeGameCommand('3');
        testGame.executeGameCommand('9');
        testGame.executeGameCommand('8');
        testGame.executeGameCommand('6');
        testGame.executeGameCommand('6');
        testGame.executeGameCommand('8');
        testGame.executeGameCommand('9');
        testGame.executeGameCommand('9');
        testGame.executeGameCommand('3');
        testGame.executeGameCommand('4');
        testGame.executeGameCommand('4');
        testGame.executeGameCommand('4');
        testGame.executeGameCommand('7');
        testGame.executeGameCommand('1');
        testGame.executeGameCommand('6');
        testGame.executeGameCommand('6');
        testGame.executeGameCommand('6');
        testGame.executeGameCommand('6');
        testGame.executeGameCommand('6');
        testGame.executeGameCommand('6');
        testGame.executeGameCommand('9');
        testGame.executeGameCommand('2');
        testGame.executeGameCommand('2');

        char[][] expectedMap = new char[][]{
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
            {'#', '.', '.', '.', '.', '.', '#', '#', '.', '#'},
            {'#', '.', '.', '.', '.', '.', '*', '*', '.', '#'},
            {'#', '.', '.', '.', '.', '.', '#', '#', '<', '#'},
            {'#', '.', '.', '.', '.', '.', '#', '#', '.', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}};
        assertArrayEquals(expectedMap, testGame.getLevel().getMap());

        Location expectedPlayerLocation = new Location(3, 8);
        assertEquals(expectedPlayerLocation, testGame.getLevel().getPlayer().getLocation());
    }
}
