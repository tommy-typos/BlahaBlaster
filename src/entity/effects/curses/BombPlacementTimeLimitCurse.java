package entity.effects.curses;

import entity.Player;
import entity.Point;
import entity.effects.Effect;
import gui.Game;

public class BombPlacementTimeLimitCurse extends Effect  {

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
