package main.java.entity.effects.powerUps;

import main.java.entity.Player;
import main.java.entity.Point;
import main.java.gui.Game;
import main.java.entity.effects.Effect;

public class RollerSkatePowerUp extends Effect {

//    private int speedIncrease = 2;

    public RollerSkatePowerUp(Point position, Game game) {
        super(position, game);
    }

    @Override
    public boolean isRollerSkatePowerUp() {
        return true;
    }

    public void applyEffect(Player player) {
//        player.speed += speedIncrease;
        player.activateRollerSkatePowerUp();

    }
}
