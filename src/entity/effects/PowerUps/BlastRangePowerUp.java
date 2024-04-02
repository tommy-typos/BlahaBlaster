package entity.effects.PowerUps;

import entity.effects.Effect;

public class BlastRangePowerUp extends Effect {

    public BlastRangePowerUp() {
        super();
        this.blastRangeChange = 1;
    }

    @Override
    public void applyEffect() {
        // Apply the effect to the player
    }

    @Override
    public boolean isBlastRangePowerUp() {
        return true;
    }



}