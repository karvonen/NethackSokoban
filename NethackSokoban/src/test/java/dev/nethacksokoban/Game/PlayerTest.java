package dev.nethacksokoban.Game;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

    private Player player;

    public PlayerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        player = new Player(new Location(2, 3));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void playerConstructor() {
        Location expectedLocation = new Location(2, 3);
        assertEquals(expectedLocation, player.getLocation());
        assertEquals(0, player.getMoves());
    }

    @Test
    public void playerMove() {
        Location move1 = new Location(3, 3);
        player.setPlayerLocation(move1);
        assertEquals(move1, player.getLocation());
        assertEquals(1, player.getMoves());
        Location move2 = new Location(2, 4);
        player.setPlayerLocation(move2);
        assertEquals(move2, player.getLocation());
        assertEquals(2, player.getMoves());
    }
}
