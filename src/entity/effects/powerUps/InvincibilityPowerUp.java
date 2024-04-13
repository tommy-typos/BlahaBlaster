package entity.effects.powerUps;

import entity.Point;
import entity.effects.Effect;
import gui.Game;

public class InvincibilityPowerUp extends Effect {

    @Override
    public boolean isInvincibilityPowerUp() {
        return true;
    }

    protected InvincibilityPowerUp(Point position, Game game) {
        super(position, game);
    }
}
