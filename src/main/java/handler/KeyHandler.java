package main.java.handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
    public boolean w, a, s, d, e, f, up, down, left, right, plantBomb, placeBrick, num_up, num_down, num_left, num_right, num_plant, num_brick;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent ev) {
        int key = ev.getKeyCode();
        if(key == KeyEvent.VK_E){
            e = true;
        }
        if(key == KeyEvent.VK_W){
            w = true;
        }
        if(key == KeyEvent.VK_A){
            a = true;
        }
        if(key == KeyEvent.VK_S){
            s = true;
        }
        if(key == KeyEvent.VK_D){
            d = true;
        }
        if(key == KeyEvent.VK_E){
            e = true;
        }
        if (key == KeyEvent.VK_F){
            f = true;
        }
        if(key == KeyEvent.VK_UP){
            up = true;
        }
        if(key == KeyEvent.VK_DOWN){
            down = true;
        }
        if(key == KeyEvent.VK_LEFT){
            left = true;
        }
        if(key == KeyEvent.VK_RIGHT){
            right = true;
        }
        if(key == KeyEvent.VK_ENTER){
            plantBomb = true;
        }
        if(key == KeyEvent.VK_SHIFT){
            placeBrick = true;
        }
        if(key == KeyEvent.VK_NUMPAD7){
            num_plant = true;
        }
        if(key == KeyEvent.VK_NUMPAD5){
            num_down = true;
        }
        if(key == KeyEvent.VK_NUMPAD4){
            num_left = true;
        }
        if(key == KeyEvent.VK_NUMPAD6){
            num_right = true;
        }
        if(key == KeyEvent.VK_NUMPAD8){
            num_up = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent ev) {
        int key = ev.getKeyCode();
        if(key == KeyEvent.VK_W){
            w = false;
        }
        if(key == KeyEvent.VK_A){
            a = false;
        }
        if(key == KeyEvent.VK_S){
            s = false;
        }
        if(key == KeyEvent.VK_D){
            d = false;
        }
        if(key == KeyEvent.VK_UP){
            up = false;
        }
        if(key == KeyEvent.VK_DOWN){
            down = false;
        }
        if(key == KeyEvent.VK_LEFT){
            left = false;
        }
        if(key == KeyEvent.VK_RIGHT){
            right = false;
        }
        if(key == KeyEvent.VK_NUMPAD5){
            num_down = false;
        }
        if(key == KeyEvent.VK_NUMPAD4){
            num_left = false;
        }
        if(key == KeyEvent.VK_NUMPAD6){
            num_right = false;
        }
        if(key == KeyEvent.VK_NUMPAD8){
            num_up = false;
        }
        if(key == KeyEvent.VK_ENTER){
            plantBomb = false;
        }

    }
}
