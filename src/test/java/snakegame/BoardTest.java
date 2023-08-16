package snakegame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;

public class BoardTest {
    private Board board = new Board();

    @Test
    public void testInitGame() {
        board.initGame();

        assertEquals(board.getDots(), 3);

        int appleX = board.getAppleX();
        int appleY = board.getAppleY(); 
        assertTrue(appleX >= 0 && appleX < board.B_WIDTH);
        assertTrue(appleY >= 0 && appleY < board.B_HEIGHT);

        assertTrue(board.getTimer().isRunning());
    }

    @Test
    // verify that the snake's length increases and that the apple is re-located to a new position.
    public void testCheckApple() {
        board.initGame();

        int initialDots = board.getDots();
        int initialAppleX = board.getAppleX();
        int initialAppleY = board.getAppleY();

        board.setHeadX(0, initialAppleX);
        board.setHeadY(0, initialAppleY);

        board.checkApple();

        assertEquals(initialDots + 1, board.getDots());
        assertNotEquals(initialAppleX, board.getAppleX());
        assertNotEquals(initialAppleY, board.getAppleY());
    }

    @Test
    public void testMove() {
        board.initGame();

        int initialX = board.getHeadX(0);
        int initialY = board.getHeadY(0);

        board.move();

        int expectedX = initialX + board.DOT_SIZE;
        assertEquals(expectedX, board.getHeadX(0));
        assertEquals(initialY, board.getHeadY(0));
    }
}
