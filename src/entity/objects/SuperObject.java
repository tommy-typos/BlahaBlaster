package entity.objects;

import entity.Point;
import gui.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public Game game;
    public boolean collision;
    public Point position;
    public boolean shouldBeRemoved = false;

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
