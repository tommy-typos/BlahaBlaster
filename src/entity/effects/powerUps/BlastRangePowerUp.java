package entity.effects.powerUps;

import entity.Player;
import entity.Point;
import entity.effects.Effect;
import gui.Game;

public class BlastRangePowerUp extends Effect {

    private int blastRangeIncrease = 1;

    public BlastRangePowerUp(Point position, Game game) {
        super(position, game);
        this.blastRangeChange = 1;
    }

    @Override
    public boolean isBlastRangePowerUp() {
        return true;
    }

    @Override
    public void applyEffect(Player player) {
        player.blastRange += blastRangeIncrease;
    }

}