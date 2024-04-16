package entity.effects.curses;

import entity.Player;
import entity.effects.Effect;
import entity.Point;
import gui.Game;

public class ShortBlastCurse extends Effect {

    public ShortBlastCurse(Point position, Game game) {
        super(position, game);
    }

    @Override
    public boolean isShortBlastCurse() {
        return true;
    }

    // Optionally, override applyEffect() if ShortBlastCurse has a unique effect
    @Override
    public void applyEffect(Player player) {
        // Specific effect application logic here
    }
}
