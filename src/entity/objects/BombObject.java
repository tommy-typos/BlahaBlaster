package entity.objects;

import entity.Point;
import gui.Game;

import javax.imageio.ImageIO;
import java.io.IOException;

public class BombObject extends SuperObject{

    public BombObject(Point position, Game game) {
        super(position, game);
        this.name = "bomb";
        try{
            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/bomb.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
