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
        player = new Player(2, 3);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void playerConstructor() {
        assertEquals(2, player.getRow());
        assertEquals(3, player.getCol());
        assertEquals(0, player.getMoves());

    }

    @Test
    public void move() {
        player.move(3, 3);
        assertEquals(3, player.getRow());
        assertEquals(3, player.getCol());
        assertEquals(1, player.getMoves());
        player.move(2, 4);
        assertEquals(2, player.getRow());
        assertEquals(4, player.getCol());
        assertEquals(2, player.getMoves());

    }
}
