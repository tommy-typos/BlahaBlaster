package main.java.entity.effects.curses;

import main.java.entity.Player;
import main.java.entity.Point;
import main.java.gui.Game;
import main.java.entity.effects.Effect;

public class BombFreezeCurse  extends Effect {

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
