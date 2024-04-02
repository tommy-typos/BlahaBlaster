package entity.monsters;

import entity.Entity;
import gui.Game;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;


public abstract class Monster extends Entity {
    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    protected int spriteCounter;
    protected int spriteNum;

    public Monster(Game gp) {
        super(gp);
        direction = "down";
        getMonsterImage();
    }

    public void draw(Graphics2D g2d) {
        BufferedImage img = null;
        switch (direction){
            case "up":
                if(spriteNum == 1)
                    img = up1;
                else
                    img = up2;
                break;
            case "down":
                if(spriteNum == 1)
                    img = down1;
                else
                    img = down2;
                break;
            case "left":
                if(spriteNum == 1)
                    img = left1;
                else
                    img = left2;
                break;
            case "right":
                if(spriteNum == 1)
                    img = right1;
                else
                    img = right2;
                break;
        }
        g2d.drawImage(img, getX(), getY(), gp.tileSize, gp.tileSize, null);
        g2d.drawRect(solidArea.x + getX(), solidArea.y + getY(), solidArea.width, solidArea.height);
    }


    public abstract void getMonsterImage();

    protected void move() {
        switch (direction) {
            case "up":
                position.setY(position.getY() - speed);
                break;
            case "down":
                position.setY(position.getY() + speed);
                break;
            case "left":
                position.setX(position.getX() - speed);
                break;
            case "right":
                position.setX(position.getX() + speed);
                break;
        }
    }

    protected void updateSpriteImage() {
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }
}
