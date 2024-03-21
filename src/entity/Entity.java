package entity;

import java.awt.image.BufferedImage;

// Superclass for player and monster
public class Entity {
    Point position;
    int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }
}
