package entity.effects.powerUps;

import entity.Player;
import entity.Point;
import entity.effects.Effect;
import gui.Game;

public class BombSlotIncreasePowerUp extends Effect {

    private int bombSlotIncrease = 1;

    @Override
    public boolean isBombSlotIncreasePowerUp() {
        return true;
    }
    protected BombSlotIncreasePowerUp(Point position, Game game) {
        super(position, game);
    }

    public void applyEffect(Player player) {
        player.bombsNum += bombSlotIncrease;
    }
}
