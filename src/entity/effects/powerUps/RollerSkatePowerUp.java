package entity.effects.powerUps;

import entity.Player;
import entity.Point;
import entity.effects.Effect;
import gui.Game;

public class RollerSkatePowerUp extends Effect {

    private int speedIncrease = 2;

    @Override
    public boolean isRollerSkatePowerUp() {
        return true;
    }

    public RollerSkatePowerUp(Player player, Point position, Game game) {
        super(position, game);
    }

    public void applyEffect(Player player) {
        player.speed += speedIncrease;
    }
}
