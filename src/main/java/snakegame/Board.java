package snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
    public final int B_WIDTH = 300; // the width of the board
    public final int B_HEIGHT = 300; // the height of the board
    public final int DOT_SIZE = 10; // the size of the apple and the dot of the snake
    private final int ALL_DOTS = (B_WIDTH * B_HEIGHT ) / (DOT_SIZE * DOT_SIZE); // the maximum number of possible dots
    private final int RANDOM_POSITION = 29; // used to calculate a random position for an apple
    private final int DELAY = 140; // used to determize the speed of the game

    //  store the x and y coordinates of all joints of a snake
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;

    private Image ball;
    private Image apple;
    private Image head;

    private Timer timer;
    private TAdapter tAdapter = new TAdapter();

    private boolean inGame = true;

    private int replayBtn_x;
    private int replayBtn_y;
    private int replayBtn_width;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(tAdapter);
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    // get the images for the game
    private void loadImages() {
        ImageIcon iidot = new ImageIcon("src/resources/dot.png");
        ball = iidot.getImage();

        ImageIcon iiapple = new ImageIcon("src/resources/apple.png");
        apple = iiapple.getImage();

        ImageIcon iihead = new ImageIcon("src/resources/head.png");
        head = iihead.getImage();
    }

    private void locateApple() {
        int r = (int) (Math.random() * RANDOM_POSITION);
        apple_x = r * DOT_SIZE;

        r = (int) (Math.random() * RANDOM_POSITION);
        apple_y = r * DOT_SIZE;
    }

    public void initGame() {
        // create a snake of 3 dots with init positions: 50 - 50, 40 - 50 and 30 - 50 
        dots = 3;
        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }
        // set the random position of the apple
        locateApple();

        // start the timer
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void checkApple() {
        // when the apple collides with the head, increase the number of joints of the snake and locate a new apple
        if (x[0] == apple_x && y[0] == apple_y) {
            dots++;
            locateApple();
        }
    }

    public void move() {
        // moves the snake joints up the chain
        for (int z = dots; z > 0; z--) {
            x[z] = x[z-1];
            y[z] = y[z-1];
        }

        // moves the head to the left/right/up/down
        if (tAdapter.leftDirection) {
            x[0] -= DOT_SIZE;
        }
        if (tAdapter.rightDirection) {
            x[0] += DOT_SIZE;
        }
        if (tAdapter.upDirection) {
            y[0] -= DOT_SIZE;
        }
        if (tAdapter.downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {
        // if the snake hits one of its joints with its head, the game is over.
        for (int z = dots; z > 0; z--) {
            if (z > 4 && x[0] == x[z] && y[0] == y[z]) {
                inGame = false;
            }
        }

        // if the snake hits the borders of the board, the game is over
        if (y[0] >= B_HEIGHT || y[0] < 0 || x[0] >= B_WIDTH || x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

             Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }

    private void resetGame() {
        // reset all variables to their initial state
        inGame = true;
        dots = 3;
        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }
        locateApple();
    
        // restart the timer
        timer.restart();
        
        // remove the mouse listener to prevent multiple listeners being added
        removeMouseListener(getMouseListeners()[0]);
        repaint();
    }

    private void checkReplay(MouseEvent me) {
        int mouseX = me.getX();
        int mouseY = me.getY();
    
        if (mouseX >= replayBtn_x && mouseX <= replayBtn_x + replayBtn_width &&
            mouseY >= replayBtn_y - getFontMetrics(new Font("Helvetica", Font.BOLD, 14)).getHeight() && mouseY <= replayBtn_y) {
            resetGame();
        }
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        String replayMsg = "Replay";

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metrics = getFontMetrics(small);

        replayBtn_width = metrics.stringWidth(replayMsg);
        replayBtn_x = (B_WIDTH - replayBtn_width) / 2;
        replayBtn_y = (B_HEIGHT / 2) + 20;

        g.setColor(Color.RED);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metrics.stringWidth(msg)) / 2, B_HEIGHT / 2);
        g.setColor(Color.WHITE);
        g.drawString(replayMsg, replayBtn_x, replayBtn_y);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                checkReplay(me);
            }
        });
    }

    public int getDots() {
        return dots;
    }

    public int getAppleX() {
        return apple_x;
    }

    public int getAppleY() {
        return apple_y;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setHeadX(int z, int value) {
        if (value >= 0 && value <= B_WIDTH) x[z]  = value;
    }

    public void setHeadY(int z, int value) {
        if (value >= 0 && value <= B_HEIGHT) y[z]  = value;
    }

    public int getHeadX(int z) {
        return x[z];
    } 

    public int getHeadY(int z) {
        return y[z];
    } 
}
  