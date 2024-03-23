package entity;

import entity.objects.BombObject;
import gui.Game;
import handler.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    Game gp;
    KeyHandler keyHandler;
    public int bombsNum = 1;
    public int speed = 3;
    String name;
    int playerNumber;


    public Player(Game gp, KeyHandler keyHandler, Point position, String name, int playerNumber) {
        this.gp = gp;
        this.keyHandler = keyHandler;
        this.name = name;
        this.playerNumber = playerNumber;

        solidArea = new Rectangle(8, 16, 32, 32);

        this.position = position;
        this.direction = "down";
        getPlayerImage();
    }

    public void getPlayerImage(){
        String basePath = "/player" + playerNumber + "/player_";
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream(basePath + "up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream(basePath + "up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream(basePath + "down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream(basePath + "down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream(basePath + "left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream(basePath + "left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream(basePath + "right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream(basePath + "right_2.png"));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (name.equals("player1")) {
            if(keyHandler.e) {
                plantBomb();
                keyHandler.e = false;
            }
            movePlayer(keyHandler.w, keyHandler.s, keyHandler.a, keyHandler.d);
        }

        else if (name.equals("player2")) {
            if(keyHandler.plant) {
                plantBomb();
                keyHandler.plant = false;
            }
            movePlayer(keyHandler.up, keyHandler.down, keyHandler.left, keyHandler.right);
        }
    }

    private void movePlayer(boolean up, boolean down, boolean left, boolean right) {
        if(up || down || left || right){
            if(up){
                direction = "up";
            }
            if(down){
                direction = "down";
            }
            if(left){
                direction = "left";
            }
            if(right){
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

    private void plantBomb(){
        int posX = (position.getX() + solidArea.x)/ gp.tileSize;
        int posY = (position.getY() + solidArea.y)/ gp.tileSize;

        int coordX = posX * gp.tileSize;
        int coordY = posY * gp.tileSize;
        // check if there is already a bomb in the same position
        if(gp.positionOccupied(coordX, coordY)){
           return;
        }

        gp.obj.add(new BombObject(new Point(coordX, coordY), gp));
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
