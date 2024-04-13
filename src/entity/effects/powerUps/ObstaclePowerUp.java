package entity.effects.powerUps;

import entity.Point;
import entity.effects.Effect;
import gui.Game;

public class ObstaclePowerUp extends Effect {
    @Override
    public boolean isObstaclePowerUp() {
        return true;
    }

    public ObstaclePowerUp(Point position, Game game) {
        super(position, game);
    }
}
