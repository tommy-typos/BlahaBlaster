package main.java.handler;

import main.java.gui.CustomControl;
import main.java.gui.CustomControlsJson;
import main.java.gui.Game;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
  public CustomControl p1Controls;
  public CustomControl p2Controls;
  public CustomControl p3Controls;

  Game game;
  public boolean w,
      a,
      s,
      d,
      e,
      up,
      down,
      left,
      right,
      plant,
      num_up,
      num_down,
      num_left,
      num_right,
      num_plant,
      enterPressed;

  public KeyHandler(Game game) {
    this.game = game;

    CustomControl[] jsonControls = CustomControlsJson.readControlsFromJson();
    p1Controls = jsonControls[0];
    p2Controls = jsonControls[1];
    p3Controls = jsonControls[2];
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  /**
   * w, a, s, d, e - player 1 (blue) up, down, left, right, plant - player 2 (red) num_up, num_down,
   * num_left, num_right, num_plant - player 3 (green)
   */
  @Override
  public void keyPressed(KeyEvent ev) {
    int key = ev.getKeyCode();
    if (game.gameState == game.pauseState) {
      pauseState(key);
    } else if (game.gameState == game.gameOverState) {
      gameOverState(key);
    } else if (game.gameState == game.playState) {
      if (key == KeyEvent.VK_ESCAPE) {
        game.gameState = game.pauseState;
      }
      if (key == p1Controls.go_up) {
        w = true;
      }
      if (key == p1Controls.go_left) {
        a = true;
      }
      if (key == p1Controls.go_down) {
        s = true;
      }
      if (key == p1Controls.go_right) {
        d = true;
      }
      if (key == p1Controls.place_bomb) {
        e = true;
      }

      if (key == p2Controls.go_up) {
        up = true;
      }
      if (key == p2Controls.go_left) {
        left = true;
      }
      if (key == p2Controls.go_down) {
        down = true;
      }
      if (key == p2Controls.go_right) {
        right = true;
      }
      if (key == p2Controls.place_bomb) {
        plant = true;
      }

      if (key == p3Controls.go_up) {
        num_up = true;
      }
      if (key == p3Controls.go_left) {
        num_left = true;
      }
      if (key == p3Controls.go_down) {
        num_down = true;
      }
      if (key == p3Controls.go_right) {
        num_right = true;
      }
      if (key == p3Controls.place_bomb) {
        num_plant = true;
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent ev) {
    int key = ev.getKeyCode();
    if (key == p1Controls.go_up) {
      w = false;
    }
    if (key == p1Controls.go_left) {
      a = false;
    }
    if (key == p1Controls.go_down) {
      s = false;
    }
    if (key == p1Controls.go_right) {
      d = false;
    }

    if (key == p2Controls.go_up) {
      up = false;
    }
    if (key == p2Controls.go_left) {
      left = false;
    }
    if (key == p2Controls.go_down) {
      down = false;
    }
    if (key == p2Controls.go_right) {
      right = false;
    }

    if (key == p3Controls.go_up) {
      num_up = false;
    }
    if (key == p3Controls.go_left) {
      num_left = false;
    }
    if (key == p3Controls.go_down) {
      num_down = false;
    }
    if (key == p3Controls.go_right) {
      num_right = false;
    }
  }

  public void pauseState(int key) {
    if (key == KeyEvent.VK_ESCAPE) {
      game.gameState = game.playState;
    }

    if (key == KeyEvent.VK_ENTER) {
      enterPressed = true;
    }

    int maxCommandNum = 2;
    navigateChoices(maxCommandNum, key);
  }

  private void gameOverState(int key) {
    if (key == KeyEvent.VK_ENTER) {
      enterPressed = true;
    }
    int maxCommandNum = 1;
    navigateChoices(maxCommandNum, key);
  }

  private void navigateChoices(int maxCommandNum, int key) {
    if (key == KeyEvent.VK_UP) {
      game.ui.commandNum--;
      if (game.ui.commandNum < 0) game.ui.commandNum = maxCommandNum;
    }
    if (key == KeyEvent.VK_DOWN) {
      game.ui.commandNum++;
      if (game.ui.commandNum > maxCommandNum) game.ui.commandNum = 0;
    }
  }
}
