package main.java.entity.effects.powerUps;

import main.java.entity.Player;
import main.java.entity.Point;
import main.java.gui.Game;
import main.java.entity.effects.Effect;

public class GhostPowerUp extends Effect {
    public int ghostModeDuration = 15;

    public GhostPowerUp(Point position, Game game) {
        super(position, game);
    }

    @Override
    public boolean isGhostPowerUp() {
        return true;
    }

    @Override
    public void applyEffect(Player player) {
        player.ghostDuration = ghostModeDuration;
        player.activateGhostPowerUp(player.ghostDuration);
    }
}
