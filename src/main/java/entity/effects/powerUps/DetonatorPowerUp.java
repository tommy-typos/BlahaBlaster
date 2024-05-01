package main.java.entity.effects.powerUps;

import main.java.entity.Player;
import main.java.entity.Point;
import main.java.gui.Game;
import main.java.entity.effects.Effect;

public class DetonatorPowerUp extends Effect {

    public DetonatorPowerUp(Point position, Game game) {
        super(position, game);
    }

    @Override
    public boolean isDetonatorPowerUp() {
        return true;
    }

    @Override
    public void applyEffect(Player player) {
        player.hasDetonator = true;
    }
}
