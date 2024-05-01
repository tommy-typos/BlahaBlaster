package main.java.entity.effects.powerUps;

import main.java.entity.Player;
import main.java.entity.Point;
import main.java.gui.Game;
import main.java.entity.effects.Effect;

public class InvincibilityPowerUp extends Effect {

    private int invincibilityModeDuration = 15;

    public InvincibilityPowerUp(Point position, Game game) {
        super(position, game);
    }

    @Override
    public boolean isInvincibilityPowerUp() {
        return true;
    }

    @Override
    public void applyEffect(Player player) {
        player.invincibilityDuration = invincibilityModeDuration;
    }
}
