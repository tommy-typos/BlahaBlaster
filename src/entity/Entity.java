package entity;

import gui.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

// Superclass for player and monster
public class Entity {

    protected Game gp;
    protected Point position;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(10, 15, 28, 32);
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public int solidAreaDefaultX = 10;
    public int solidAreaDefaultY = 15;

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public Entity(Game gp){
        this.gp = gp;
    }

    public void setAction(){};

    public void update(){
        setAction();


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
}
