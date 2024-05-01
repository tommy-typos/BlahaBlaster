package main.java.entity.effects.powerUps;

import main.java.entity.Player;
import main.java.entity.Point;
import main.java.gui.Game;
import main.java.entity.effects.Effect;

public class BlastRangePowerUp extends Effect {

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
        player.blastRange += 1;
    }

}