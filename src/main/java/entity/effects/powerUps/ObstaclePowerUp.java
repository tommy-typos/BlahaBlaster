package main.java.entity.effects.powerUps;

import main.java.entity.Player;
import main.java.entity.Point;
import main.java.gui.Game;
import main.java.entity.effects.Effect;

public class ObstaclePowerUp extends Effect {

    public ObstaclePowerUp(Point position, Game game) {
        super(position, game);
    }

    @Override
    public boolean isObstaclePowerUp() {
        return true;
    }

    public void applyEffect(Player player) {
        player.canPutObstacles = true;
    }

}
