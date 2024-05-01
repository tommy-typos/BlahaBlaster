package main.java.entity.effects.powerUps;

import main.java.entity.Player;
import main.java.entity.Point;
import main.java.gui.Game;
import main.java.entity.effects.Effect;

public class BombSlotIncreasePowerUp extends Effect {

    private int bombSlotIncrease = 1;

    public BombSlotIncreasePowerUp(Point position, Game game) {
        super(position, game);
    }

    @Override
    public boolean isBombSlotIncreasePowerUp() {
        return true;
    }

    public void applyEffect(Player player) {
        player.bombsNum += bombSlotIncrease;
    }
}
