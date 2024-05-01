package main.java.entity;

import gui.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

// Superclass for player and monster
public class Entity {

    protected Game gp;
    public Point position;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public final int solidAreaDefaultX = 10;
    public final int solidAreaDefaultY = 20;

    // 48 as the tile size
    private final int width = 48 - 2 * solidAreaDefaultX;
    private final int height = 48 - solidAreaDefaultY - 1;
    public Rectangle solidArea = new Rectangle(solidAreaDefaultX, solidAreaDefaultY, width, height);
    public boolean shouldBeRemoved = false;

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public Entity(Game gp) {
        this.gp = gp;
    }

    public void setAction() {
    }

    public void update() {
        setAction();
    }

    public void draw(Graphics2D g2d) {
        BufferedImage img = null;
        switch (direction) {
            case "up":
                img = (spriteNum == 1) ? up1 : up2;
                break;
            case "down":
                img = (spriteNum == 1) ? down1 : down2;
                break;
            case "left":
                img = (spriteNum == 1) ? left1 : left2;
                break;
            case "right":
                img = (spriteNum == 1) ? right1 : right2;
                break;
        }
        g2d.drawImage(img, getX(), getY(), gp.tileSize, gp.tileSize, null);
        g2d.drawRect(solidArea.x + getX(), solidArea.y + getY(), solidArea.width, solidArea.height);
    }

    protected void move(String direction) {
        if (!collisionOn) {
            switch (direction) {
                case "up":
                    position.y -= speed;
                    break;
                case "down":
                    position.y += speed;
                    break;
                case "left":
                    position.x -= speed;
                    break;
                case "right":
                    position.x += speed;
                    break;
            }
        }
    }
}
