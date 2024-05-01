package main.java.entity.effects.curses;

import main.java.entity.Player;
import main.java.entity.Point;
import main.java.gui.Game;
import main.java.entity.effects.Effect;

public class BombPlacementTimeLimitCurse extends Effect {

    public BombPlacementTimeLimitCurse(Point position, Game game) {
        super(position, game);
    }

    @Override
    public boolean isBombPlacementTimeLimitCurse() {
        return true;
    }

    // Optionally, override applyEffect() if BombPlacementTimeLimitCurse has a unique effect
    @Override
    public void applyEffect(Player player) {
        // Specific effect application logic here
    }
}
