package gui;

import java.awt.event.KeyEvent;

public class CustomControl {
  public String id;
  public int go_up;
  public int go_left;
  public int go_down;
  public int go_right;
  public int place_bomb;
  public int place_brick;

  public CustomControl(
      String id,
      int go_up,
      int go_left,
      int go_down,
      int go_right,
      int place_bomb,
      int place_brick) {
    this.id = id;
    this.go_up = go_up;
    this.go_left = go_left;
    this.go_down = go_down;
    this.go_right = go_right;
    this.place_bomb = place_bomb;
    this.place_brick = place_brick;
  }

  @Override
  public String toString() {
    return this.id
        + ": "
        + this.go_up
        + ", "
        + this.go_left
        + ", "
        + this.go_down
        + ", "
        + this.go_right
        + ", "
        + this.place_bomb
        + ", "
        + this.place_brick;
  }

  public void changeAttrValue(String attr, int value) {
    if (attr.equals("go_up")) {
      this.go_up = value;
    } else if (attr.equals("go_left")) {
      this.go_left = value;
    } else if (attr.equals("go_down")) {
      this.go_down = value;
    } else if (attr.equals("go_right")) {
      this.go_right = value;
    } else if (attr.equals("place_bomb")) {
      this.place_bomb = value;
    } else if (attr.equals("place_brick")) {
      this.place_brick = value;
    }
  }

  public void changePlayerControlBtnsTexts(PlayerControl playerControl) {
    playerControl.button_up.setText("⬆ (" + KeyEvent.getKeyText(this.go_up) + ")");
    playerControl.button_left.setText("⬅ (" + KeyEvent.getKeyText(this.go_left) + ")");
    playerControl.button_down.setText("⬇ (" + KeyEvent.getKeyText(this.go_down) + ")");
    playerControl.button_right.setText("➡ (" + KeyEvent.getKeyText(this.go_right) + ")");
    playerControl.button_bomb.setText("bomb (" + KeyEvent.getKeyText(this.place_bomb) + ")");
    playerControl.button_brick.setText("brick (" + KeyEvent.getKeyText(this.place_brick) + ")");
  }
}
