package main.java.entity.objects;

import main.java.entity.Point;
import main.java.gui.Game;

import java.io.IOException;
import javax.imageio.ImageIO;

public class BombObject extends SuperObject {

    public String owner;
    public Long blowTime = System.currentTimeMillis() + 3000;
    public Long timeToLeave = System.currentTimeMillis() + 1000;

    public int blowRadius = 2;

    public BombObject(Point position, Game game, String owner) {
        super(position, game);
        this.name = "bomb";
        this.owner = owner;
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/bomb.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
