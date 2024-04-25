package entity.objects;

import entity.Point;
//import entity.effects.Curses.ShortBlastCurse;
//import entity.effects.PowerUps.GhostPowerUp;
import gui.Game;

import javax.imageio.ImageIO;
import java.io.IOException;

public class BrickObject extends SuperObject{

    public String owner;

    public BrickObject(Point position, Game game) {
        super(position, game);
        this.name = "brick";
        try{
            this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/brick.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // Plantable brick
    public BrickObject(Point position, Game game, String owner) {
        super(position, game);
        this.owner = owner != null ? owner : "unknown"; // default owner to "unknown" if null
        this.name = "brick";
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/brick.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
