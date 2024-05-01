package main.java.entity.objects;

import main.java.entity.Point;
import main.java.gui.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public Game game;
    public boolean collision = true;
    public Point position;
    public boolean shouldBeRemoved = false;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public SuperObject(Point position, Game game){
        this.position = position;
        this.game = game;
    }
    public int getX(){
        return position.getX();
    }

    public int getY(){
        return position.getY();
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(image, getX(), getY(), game.tileSize, game.tileSize,null);

    }
}
