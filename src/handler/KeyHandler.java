package handler;

import gui.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    Game game;
    public boolean w, a, s, d, e, up, down, left, right, plant, num_up, num_down, num_left, num_right, num_plant, enterPressed;

    public KeyHandler(Game game){
        this.game = game;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent ev) {
        int key = ev.getKeyCode();
        if(game.gameState  == game.pauseState){
            pauseState(key);
        }else if(game.gameState == game.gameOverState){
            gameOverState(key);
        }else{
            if(key == KeyEvent.VK_ESCAPE){
                if(game.gameState == game.playState) {
                    game.gameState = game.pauseState;
                }else if(game.gameState == game.pauseState){
                    game.gameState = game.playState;
                }
            }
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
                plant = true;
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
    }

    public void pauseState(int key){
        if(key == KeyEvent.VK_ESCAPE){
            game.gameState = game.playState;
        }

        if(key == KeyEvent.VK_ENTER){
            enterPressed = true;
        }

        int maxCommandNum = 2;
        navigateChoices(maxCommandNum, key);
    }

    private void gameOverState(int key) {
        if(key == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        int maxCommandNum = 1;
        navigateChoices(maxCommandNum, key);
    }

    private void navigateChoices(int maxCommandNum, int key){
        if(key == KeyEvent.VK_UP){
            game.ui.commandNum--;
            if(game.ui.commandNum < 0)
                game.ui.commandNum = maxCommandNum;
        }
        if(key == KeyEvent.VK_DOWN){
            game.ui.commandNum++;
            if(game.ui.commandNum > maxCommandNum)
                game.ui.commandNum = 0;
        }
    }
}
