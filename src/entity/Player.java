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
    private boolean shouldBeRemoved = false;


    // PowerUp and Curse related variables
    public boolean hasDetonator = false;

    private boolean ghostModeActive = false;
    public int ghostDuration = 10;
    private long ghostEffectStartTime = 0;
    private boolean canPassThroughWallOnce = false;


    public int blastRange = 1;
    public int speedBoost = 0;
    public int bombFreezeTime = 0;
    public int nextBombCanBePlaced = 0;
    public int bombPlacmentDelay = 0;

    public int invincibilityDuration = 0;
    private long invincibilityEffectStartTime = 10;

    public int maxObstacles = 0;



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

        if (ghostDuration > 0) {
            long currentTime = System.currentTimeMillis();
            long elapsedTimeInSeconds = (currentTime - ghostEffectStartTime) / 1000;

            if (elapsedTimeInSeconds >= ghostDuration) {
                ghostDuration = 0; // Reset ghostDuration after ghostDuration ends
                canPassThroughWallOnce = true; // Allow passing through wall once after effect ends
            }
        }

        if (invincibilityDuration > 0) {
            long currentTime = System.currentTimeMillis();
            long elapsedTimeInSeconds = (currentTime - invincibilityEffectStartTime) / 1000;

            if (elapsedTimeInSeconds >= invincibilityDuration) {
                invincibilityDuration = 0; // Reset invincibilityDuration after it ends
            }
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

            boolean passedThroughWallThisMove = false;

            if (ghostDuration <= 0 && !canPassThroughWallOnce) {
                collisionOn = false;
                gp.collisionChecker.checkTile(this);

                int npcIndex = gp.collisionChecker.checkEntity(this, gp.monsters);
                interactWithMonster(npcIndex);
//                canPassThroughWallOnce = true;
            } else if (canPassThroughWallOnce ) {
                System.out.println("Player can pass through wall once");
                passedThroughWallThisMove = true;
            }

            adjustMovementAtMapEdges();

            // if the player has a Detona
            if(!collisionOn || ghostDuration > 0) { // || ghostDuration > 0  -->  if the player is a ghost, they can move through obstacles
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

            if (passedThroughWallThisMove) {
                canPassThroughWallOnce = false;
            }

////             After moving, check if on a grass tile to possibly update canPassThroughWallOnce
//            int tileX = position.getX() / gp.tileSize;
//            int tileY = position.getY() / gp.tileSize;
//            if (gp.isPlantable(tileX, tileY)) {
//                canPassThroughWallOnce = false; // Disable wall pass-through after moving onto a grass tile
//            }


            updateSpriteImage();
        }
    }


    private void adjustMovementAtMapEdges() {
        int mapWidth = gp.screenWidth;
        int mapHeight = gp.screenHeight;
        int tileSize = gp.tileSize;

        // Right edge
        if (position.getX() >= mapWidth - 2 * tileSize && direction.equals("right")) {
            position.setX(mapWidth - 2 * tileSize);
        }
        // Left edge
        else if (position.getX() <= tileSize && direction.equals("left")) {
            position.setX(tileSize);
        }
        // Bottom edge
        if (position.getY() >= mapHeight - 2 * tileSize && direction.equals("down")) {
            position.setY(mapHeight - 2 * tileSize);
        }
        // Top edge
        else if (position.getY() <= tileSize && direction.equals("up")) {
            position.setY(tileSize);
        }
    }


    private void interactWithMonster(int npcIndex) {
        if (npcIndex != 999) {
            shouldBeRemoved = true;
        }
    }

    public boolean shouldBeRemoved() {
        return shouldBeRemoved;
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



    // PowerUp and Curse related methods

    public void activateGhostPowerUp(int duration) {
        this.ghostDuration = duration;
        this.ghostEffectStartTime = System.currentTimeMillis(); // Set start time
    }


    public void activateInvincibilityPowerUp(int duration) {
        this.invincibilityDuration = duration;
        this.invincibilityEffectStartTime = System.currentTimeMillis(); // Set start time
    }

}
