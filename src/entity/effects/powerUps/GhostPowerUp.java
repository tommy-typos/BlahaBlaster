package entity.effects.powerUps;

import entity.Point;
import entity.effects.Effect;
import gui.Game;

public class GhostPowerUp extends Effect {
    public int ghostDuration = 10;

    @Override
    public boolean isGhostPowerUp() {
        return true;
    }

    public GhostPowerUp(Point position, Game game) {
        super(position, game);
    }
}
