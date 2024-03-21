package entity;

import gui.Game;
import handler.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    Game gp;
    KeyHandler keyHandler;

    public Player(Game gp, KeyHandler keyHandler, Point position, int speed) {
        this.gp = gp;
        this.keyHandler = keyHandler;

        solidArea = new Rectangle(8, 16, 32, 30);

        this.position = position;
        this.speed = speed;
        this.direction = "down";
        getPlayerImage();
    }

    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/player1/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player1/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player1/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player1/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player1/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player1/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player1/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player1/boy_right_2.png"));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(){
        if(keyHandler.w || keyHandler.a || keyHandler.s || keyHandler.d){
            if(keyHandler.w){
                direction = "up";
            }
            if(keyHandler.s){
                direction = "down";
            }
            if(keyHandler.a){
                direction = "left";
            }
            if(keyHandler.d){
                direction = "right";
            }

            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            if(!collisionOn){
                switch(direction){
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
            updateSpriteImage();
        }

    }

    private void updateSpriteImage(){
        spriteCounter++;
        if(spriteCounter > 12){
            if(spriteNum == 1)
                spriteNum = 2;
            else
                spriteNum = 1;
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2d){
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
    }
}
