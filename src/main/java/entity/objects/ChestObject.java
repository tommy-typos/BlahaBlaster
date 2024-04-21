package entity.objects;

import entity.Point;
import gui.Game;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ChestObject extends SuperObject {

  public ChestObject(Point position, Game game) {
    super(position, game);
    this.name = "chest";
    try {
      this.image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
