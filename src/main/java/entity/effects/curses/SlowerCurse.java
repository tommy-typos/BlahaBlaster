package main.java.entity.effects.curses;

import main.java.entity.Point;
import main.java.gui.Game;
import main.java.entity.effects.Effect;

public class SlowerCurse extends Effect {

    @Override
    public boolean isSlowerCurse() {
        return true;
    }

    protected SlowerCurse(Point position, Game game) {
        super(position, game);
    }
}