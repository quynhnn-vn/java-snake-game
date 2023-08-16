package snakegame;

import javax.swing.JFrame;

public class SnakeGame extends JFrame {
    private void initUI() {
        add(new Board());

        // disable the ability to resize the game window
        setResizable(false);
        // resize the frame so that it fits Board component
        pack();

        // set the title of the game window to "Snake Game"
        setTitle("Snake Game");
        // center the game window on the screen
        setLocationRelativeTo(null);
        // when the user clicks the close button on the window, the application will exit
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public SnakeGame() {
        initUI();
    }
    
}
