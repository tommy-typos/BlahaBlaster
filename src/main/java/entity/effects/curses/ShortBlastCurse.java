package main.java.entity.effects.curses;

import main.java.entity.Player;
import main.java.entity.Point;
import main.java.gui.Game;
import main.java.entity.effects.Effect;

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
