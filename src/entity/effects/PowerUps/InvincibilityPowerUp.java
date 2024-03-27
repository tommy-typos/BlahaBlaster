package entity.effects.PowerUps;

import entity.effects.Effect;

public class InvincibilityPowerUp extends Effect {

    @Override
    public boolean isInvincibilityPowerUp() {
        return true;
    }

    protected InvincibilityPowerUp(int speedChange, int blastRangeChange) {
        super(speedChange, blastRangeChange);
    }
}
