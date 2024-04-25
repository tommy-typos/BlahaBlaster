package entity.objects;

import entity.Player;
import entity.Point;
import gui.Game;

import javax.imageio.ImageIO;
import java.io.IOException;

public class BombObject extends SuperObject{

    public String owner;
    public Long blowTime = System.currentTimeMillis() + 3000;
    public Long timeToLeave = System.currentTimeMillis() + 1000;

    public int blowRadius = 1;

    public BombObject(Point position, Game game, Player owner, int blowRadius) {
        super(position, game);
        this.name = "bomb";
        this.owner = owner.name;
        this.blowRadius = blowRadius;
        try{
            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/bomb.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void detonate() {
        game.blowEntities(this); // Handles the explosion effects
        shouldBeRemoved = true; // Flag this bomb to be removed from game objects
    }

}
