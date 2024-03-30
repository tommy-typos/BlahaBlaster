package entity.monsters;

import entity.Entity;
import gui.Game;


import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class Monster extends Entity {
    // Images for different states
    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    protected int actionLockCounter;
    protected int spriteCounter;
    protected int spriteNum;

    public Monster(Game gp) {
        super(gp);
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

    public abstract void update();

    public void setAction() {
        // Implementation can vary for different monsters.
    }

    protected void updateSpriteImage() {
        // Common sprite updating logic here.
    }
}
