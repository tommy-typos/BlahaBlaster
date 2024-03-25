package entity;

import gui.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

// Superclass for player and monster
public class Entity {

    Game gp;
    Point position;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(10, 15, 28, 32);
    public boolean collisionOn = false;

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public Entity(Game gp){
        this.gp = gp;
    }
}
