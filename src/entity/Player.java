package entity;

import entity.objects.BombObject;
import entity.objects.SuperObject;
import gui.Game;
import handler.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    KeyHandler keyHandler;
    public int bombsNum = 1;
    public String name;
    int playerNumber;

    public Player(Game gp, KeyHandler keyHandler, Point position, String name, int playerNumber) {
        super(gp);
        this.speed = 3;
        this.keyHandler = keyHandler;
        this.name = name;
        this.playerNumber = playerNumber;

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
        if (playerNumber == 1) {
            if(keyHandler.e) {
                plantBomb();
                keyHandler.e = false;
            }
            movePlayer(keyHandler.w, keyHandler.s, keyHandler.a, keyHandler.d);
        }
        if (playerNumber == 2) {
            if(keyHandler.plant) {
                plantBomb();
                keyHandler.plant = false;
            }
            movePlayer(keyHandler.up, keyHandler.down, keyHandler.left, keyHandler.right);
        }

        if (playerNumber == 3) {
            if(keyHandler.num_plant) {
                plantBomb();
                keyHandler.num_plant = false;
            }
            movePlayer(keyHandler.num_up, keyHandler.num_down, keyHandler.num_left, keyHandler.num_right);
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
            int objIndex = gp.collisionChecker.checkObject(this);

            int npcIndex = gp.collisionChecker.checkEntity(this, gp.monsters);
            interactWithMonster(npcIndex);

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

    private void interactWithMonster(int npcIndex) {
        if (npcIndex != 999) {
            shouldBeRemoved = true;
        }
    }

    private void plantBomb(){
        // check if user can plant more bombs
        int bombsPlanted = 0;
        for (SuperObject superObject : gp.obj) {
            if (superObject instanceof BombObject) {
                if (((BombObject) superObject).owner.equals(name)) {
                    bombsPlanted++;
                }
            }
        }

        if(bombsPlanted >= bombsNum){
            return;
        }
        // TODO: here is the part of the code for the detonator subtask

        // check if there is already a bomb in the same position
        int posX = (position.getX() + solidArea.x)/ gp.tileSize;
        int posY = (position.getY() + solidArea.y)/ gp.tileSize;

        int coordX = posX * gp.tileSize;
        int coordY = posY * gp.tileSize;
        if(gp.positionOccupied(coordX, coordY)){
           return;
        }

        gp.obj.add(new BombObject(new Point(coordX, coordY), gp, name));
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
        g2d.drawRect(solidArea.x + getX(), solidArea.y + getY(), solidArea.width, solidArea.height);
    }
}
