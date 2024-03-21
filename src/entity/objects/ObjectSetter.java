package entity.objects;

import entity.Point;
import gui.Game;

public class ObjectSetter {

    Game game;

    public ObjectSetter(Game game){
        this.game = game;
    }

    public void setObjects() {
        game.obj[0] = new ChestObject(new Point(2*game.tileSize, 2*game.tileSize), game);

        game.obj[1] = new ChestObject(new Point(3*game.tileSize, 3*game.tileSize), game);
    }
}
