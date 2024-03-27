package entity.effects.PowerUps;

import entity.effects.Effect;

public class GhostPowerUp extends Effect {
    public int ghostDuration = 10;

    protected GhostPowerUp(int speedChange, int blastRangeChange) {
        super(speedChange, blastRangeChange);
    }
}
