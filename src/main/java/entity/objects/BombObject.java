package entity.objects;

import entity.Point;
import gui.Game;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * The BombObject class represents a bomb object in the game.
 * It extends the SuperObject class and initializes bomb properties.
 */
public class BombObject extends SuperObject {

    public String owner;
    public Long blowTime = System.currentTimeMillis() + 3000;
    public Long timeToLeave = System.currentTimeMillis() + 1000;

    public int blowRadius = 2;

    /**
     * Constructs a BombObject object.
     *
     * @param position The position of the bomb.
     * @param game     The Game object associated with the bomb.
     * @param owner    The owner of the bomb.
     */
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
