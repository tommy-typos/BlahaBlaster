package main.java.entity;

import main.java.entity.objects.BombObject;
import main.java.entity.objects.BrickObject;
import main.java.entity.objects.SuperObject;
import main.java.gui.Game;
import main.java.handler.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Player extends Entity {
    KeyHandler keyHandler;
    public int bombsNum = 1;
    public String name;
    public int playerNumber;


    // PowerUp and Curse related variables
    public boolean hasDetonator = false;
    public ArrayList<BombObject> plantedBombs = new ArrayList<>();
    public boolean canPutObstacles = false;
    public int maxObstacles = 5;


    public int ghostDuration = 5;
    private long ghostEffectStartTime = 0;
    public boolean hasGhostPowerUp = false;


    public int blastRange = 1;
    public boolean speedBoosted = false;
    public int bombFreezeTime = 0;
    public int nextBombCanBePlaced = 0;
    public int bombPlacmentDelay = 0;

    public int invincibilityDuration = 0;
    private long invincibilityEffectStartTime = 0;




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
            if(keyHandler.plantBomb) {
                plantBomb();
                keyHandler.plantBomb = false;
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

        if (this.canPutObstacles) {
            if (playerNumber == 1) {
                if (keyHandler.f) {
                    placeBrick();
                    keyHandler.f = false;
                }
            }
            if (playerNumber == 2) {
                if (keyHandler.placeBrick) {
                    placeBrick();
                    keyHandler.placeBrick = false;
                }
            }
            if (playerNumber == 3) {
                if (keyHandler.num_brick) {
                    placeBrick();
                    keyHandler.num_brick = false;
                }
            }
        }

        if (ghostDuration > 0) {
            long currentTime = System.currentTimeMillis();
            long elapsedTimeInSeconds = (currentTime - ghostEffectStartTime) / 1000;

            if (elapsedTimeInSeconds >= ghostDuration) {
                    ghostDuration = 0; // Reset ghostDuration after ghostDuration ends
            }
        }

        if (invincibilityDuration > 0) {
            long currentTime = System.currentTimeMillis();
            long elapsedTimeInSeconds = (currentTime - invincibilityEffectStartTime) / 1000;

            if (elapsedTimeInSeconds >= invincibilityDuration) {
                invincibilityDuration = 0; // Reset invincibilityDuration after it ends
                System.out.println("Player " + name + " is no longer invincible");
            }
        }

        // Roller skate check
        if (speedBoosted) {
            speed = 6;
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


            if (ghostDuration <= 0 ) {
                collisionOn = false;
                gp.collisionChecker.checkTile(this);
            gp.collisionChecker.checkEntityToEntity(this);
            int objIndex = gp.collisionChecker.checkObject(this);

                if (invincibilityDuration <= 0) {
                    int npcIndex = gp.collisionChecker.checkEntityToMonsters(this, gp.monsters);
                    interactWithMonster(npcIndex);
                }

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

    private void plantBomb() {
        int bombsPlanted = 0;
        for (SuperObject superObject : gp.obj) {
            if (superObject instanceof BombObject && ((BombObject) superObject).owner.equals(this.name)) {
                bombsPlanted++;
            }
        }

        if (bombsPlanted >= bombsNum) {
            if (hasDetonator) {
                detonateAllBombs();  // Detonate all bombs when the max number is reached and player plants another
            }
            return;
        }

        int posX = (position.getX() + solidArea.x) / gp.tileSize;
        int posY = (position.getY() + solidArea.y) / gp.tileSize;
        int coordX = posX * gp.tileSize;
        int coordY = posY * gp.tileSize;

        if (gp.positionOccupied(coordX, coordY)) {
            return;
        }

        BombObject newBomb = new BombObject(new Point(coordX, coordY), gp, this, this.blastRange);
        if (hasDetonator) {
            newBomb.blowTime = Long.MAX_VALUE; // to prevent bomb from detonating automatically
            plantedBombs.add(newBomb); // track the bomb for detonation
        }
        gp.obj.add(newBomb);
    }

    private void detonateAllBombs() {
        Iterator<BombObject> it = plantedBombs.iterator();
        while (it.hasNext()) {
            BombObject bomb = it.next();
            bomb.detonate();
            it.remove(); // Remove from the list after detonation
        }
    }



    private void placeBrick() {
        int bricksPlaced = 0;
        for (SuperObject superObject : gp.obj) {
            if (superObject instanceof BrickObject brick) {
                if (brick.owner != null && brick.owner.equals(name)) {
                    bricksPlaced++;
                }
            }
        }

        if (bricksPlaced >= maxObstacles) {
            return;
        }

        // Calculate the position of the next tile in the direction the player is facing
        int posX = (position.getX() + solidArea.x) / gp.tileSize;
        int posY = (position.getY() + solidArea.y) / gp.tileSize;

        // Adjust position based on direction
        switch (direction) {
            case "up":
                posY -= 1;
                break;
            case "down":
                posY += 1;
                break;
            case "left":
                posX -= 1;
                break;
            case "right":
                posX += 1;
                break;
        }

        int coordX = posX * gp.tileSize;
        int coordY = posY * gp.tileSize;

        // Check if the position is already occupied or not plantable
        if (gp.positionOccupied(coordX, coordY) || !gp.isPlantable(coordX, coordY)) {
            return;
        }

        // Add the brick object to the game
//        gp.obj.add(new BrickObject(new Point(coordX, coordY), gp, name));
        // Add the brick object to the game
//        BrickObject newBrick = new BrickObject(new Point(coordX, coordY), gp, name);
//        gp.obj.add(newBrick);
        // Update the gameMap to reflect the presence of a new brick
        gp.gameMap.mapCells[coordY / gp.tileSize][coordX / gp.tileSize] = "brick";
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



    // check if player standing on grass
    public boolean isPlayerOnGrass(){
        int tileX = position.getX() / gp.tileSize;
        int tileY = position.getY() / gp.tileSize;
        return gp.isPlantable(tileX, tileY);
    }


    // PowerUp and Curse related methods

    // activateGhostPowerUp method
    public void activateGhostPowerUp(int duration) {
        this.ghostDuration = duration;
        this.ghostEffectStartTime = System.currentTimeMillis(); // Set start time
        this.hasGhostPowerUp = true;
    }

    // activateInvincibilityPowerUp method
    public void activateInvincibilityPowerUp(int duration) {
        this.invincibilityDuration = duration;
        this.invincibilityEffectStartTime = System.currentTimeMillis(); // Set start time
    }

    // activate Roller Skate PowerUp
    public void activateRollerSkatePowerUp() {
        this.speedBoosted = true;
    }
}
