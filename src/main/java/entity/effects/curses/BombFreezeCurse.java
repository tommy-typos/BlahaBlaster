package entity.effects.curses;

import entity.Player;
import entity.Point;
import entity.effects.Effect;
import gui.Game;

public class BombFreezeCurse extends Effect {

  public BombFreezeCurse(Point position, Game game) {
    super(position, game);
  }

  @Override
  public boolean isBombFreezeCurse() {
    return true;
  }

  @Override
  public void applyEffect(Player player) {
    // Specific effect application logic here
  }
}
