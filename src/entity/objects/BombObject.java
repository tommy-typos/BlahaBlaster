package entity.objects;

import entity.Point;
import gui.Game;

import javax.imageio.ImageIO;
import java.io.IOException;

public class BombObject extends SuperObject{

    public String owner;
    public Long blowTime = System.currentTimeMillis() + 3000;

    public int blowRadius = 2;

    public BombObject(Point position, Game game, String owner) {
        super(position, game);
        this.name = "bomb";
        this.owner = owner;
        try{
            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/bomb.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
