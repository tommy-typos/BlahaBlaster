package entity.effects.PowerUps;

import entity.effects.Effect;

public class GhostPowerUp extends Effect {
    public int ghostDuration = 10;

    @Override
    public boolean isGhostPowerUp() {
        return true;
    }

    public GhostPowerUp() {
        super();
    }
}
