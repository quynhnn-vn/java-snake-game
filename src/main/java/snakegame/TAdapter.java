package snakegame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TAdapter extends KeyAdapter{
    public boolean leftDirection = false;
    public boolean rightDirection = true;
    public boolean upDirection = false;
    public boolean downDirection = false;

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT && !rightDirection) {
            leftDirection = true;
            upDirection = false;
            downDirection = false; 
        } 

        if (key == KeyEvent.VK_RIGHT && !leftDirection) {
            rightDirection = true;
            upDirection = false;
            downDirection = false;
        }

        if (key == KeyEvent.VK_UP && !downDirection) {
            upDirection = true;
            leftDirection = false;
            rightDirection = false;
        }

        if (key == KeyEvent.VK_DOWN && !upDirection) {
            downDirection = true;
            leftDirection = false;
            rightDirection = false;
        }
    }
}
 