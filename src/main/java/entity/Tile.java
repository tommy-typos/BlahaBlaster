package entity;

import java.awt.image.BufferedImage;

// Material in the uml diagram
public class Tile {
  public BufferedImage image;
  public boolean collision = false;

  public Tile(BufferedImage image, boolean collision) {
    this.image = image;
    this.collision = collision;
  }
}
