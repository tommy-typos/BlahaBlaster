package entity.effects.powerUps;

import entity.Player;
import entity.Point;
import entity.effects.Effect;
import gui.Game;

public class DetonatorPowerUp extends Effect {

    @Override
    public boolean isDetonatorPowerUp() {
        return true;
    }
    protected DetonatorPowerUp(Point position, Game game) {
        super(position, game);
    }

    @Override
    public void applyEffect(Player player) {
        player.hasDetonator = true;
    }
}
