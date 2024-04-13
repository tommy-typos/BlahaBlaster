package entity.effects.curses;

import entity.Point;
import entity.effects.Effect;
import gui.Game;

public class BombFreezeCurse  extends Effect {

    public BombFreezeCurse(Point position, Game game) {
        super(position, game);
    }

    @Override
    public boolean isBombFreezeCurse() {
        return true;
    }

    @Override
    public void applyEffect() {
        // Specific effect application logic here
    }

}
