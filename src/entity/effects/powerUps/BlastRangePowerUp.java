package entity.effects.powerUps;

import entity.Point;
import entity.effects.Effect;
import gui.Game;

public class BlastRangePowerUp extends Effect {

    public BlastRangePowerUp(Point position, Game game) {
        super(position, game);
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