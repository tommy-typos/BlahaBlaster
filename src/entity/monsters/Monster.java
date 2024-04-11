package entity.monsters;

import entity.Entity;
import gui.Game;

import javax.imageio.ImageIO;
import java.io.IOException;
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
        loadMonsterImages();
    }

    protected void loadMonsterImages() {
        String basePath = "/monsters/" + getMonsterType() + "/";
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream(basePath + "up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream(basePath + "up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream(basePath + "down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream(basePath + "down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream(basePath + "left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream(basePath + "left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream(basePath + "right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream(basePath + "right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract String getMonsterType();

    @Override
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
        super.draw(g2d);
    }

    protected void move() {
        move(direction);
    }

    protected void updateSpriteImage() {
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }
}
