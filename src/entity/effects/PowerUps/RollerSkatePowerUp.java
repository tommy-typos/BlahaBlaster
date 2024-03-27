package entity.effects.PowerUps;

import entity.effects.Effect;

public class RollerSkatePowerUp extends Effect {

    @Override
    public boolean isRoolerSkatePowerUp() {
        return true;
    }

    protected RollerSkatePowerUp(int speedChange, int blastRangeChange) {
        super(speedChange, blastRangeChange);
    }
}
