package snakegame;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class App 
{
    public static void main( String[] args )
    {
        EventQueue.invokeLater(() -> {
        JFrame ex = new SnakeGame();
        ex.setVisible(true);
    });
    }
}
