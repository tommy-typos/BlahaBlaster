package entity.effects.powerUps;

import entity.Player;
import entity.Point;
import entity.effects.Effect;
import gui.Game;

public class InvincibilityPowerUp extends Effect {

    private int invincibilityModeDuration = 15;

    @Override
    public boolean isInvincibilityPowerUp() {
        return true;
    }

    protected InvincibilityPowerUp(Point position, Game game) {
        super(position, game);
    }

    @Override
    public void applyEffect(Player player) {
        player.invincibilityDuration = invincibilityModeDuration;
    }
}
