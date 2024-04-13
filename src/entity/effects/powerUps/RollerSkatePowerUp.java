package entity.effects.powerUps;

import entity.Player;
import entity.Point;
import entity.effects.Effect;
import gui.Game;

public class RollerSkatePowerUp extends Effect {

    @Override
    public boolean isRollerSkatePowerUp() {
        return true;
    }

    protected RollerSkatePowerUp(Player player, Point position, Game game) {
        super(position, game);
    }
}
