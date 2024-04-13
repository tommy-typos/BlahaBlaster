package entity.effects.powerUps;

import entity.Point;
import entity.effects.Effect;
import gui.Game;

public class BombSlotIncreasePowerUp extends Effect {

    @Override
    public boolean isBombSlotIncreasePowerUp() {
        return true;
    }

    protected BombSlotIncreasePowerUp(Point position, Game game) {
        super(position, game);
    }
}
