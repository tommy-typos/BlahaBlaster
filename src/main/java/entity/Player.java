package entity;

import entity.objects.BombObject;
import entity.objects.SuperObject;
import gui.Game;
import handler.KeyHandler;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends Entity {
  KeyHandler keyHandler;
  public int bombsNum = 1;
  public String name;
  public int playerNumber;

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

  public void getPlayerImage() {
    String basePath = "/player" + playerNumber + "/player_";
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

  // Getters and setters
  public Point getPosition() {
    return position;
  }

  public KeyHandler getKeyHandler() {
    return keyHandler;
  }

  public void setPosition(Point position) {
    this.position = position;
  }

  public void update() {
    if (playerNumber == 1) {
      if (keyHandler.e) {
        plantBomb();
        keyHandler.e = false;
      }
      movePlayer(keyHandler.w, keyHandler.s, keyHandler.a, keyHandler.d);
    }
    if (playerNumber == 2) {
      if (keyHandler.plant) {
        plantBomb();
        keyHandler.plant = false;
      }
      movePlayer(keyHandler.up, keyHandler.down, keyHandler.left, keyHandler.right);
    }

    if (playerNumber == 3) {
      if (keyHandler.num_plant) {
        plantBomb();
        keyHandler.num_plant = false;
      }
      movePlayer(keyHandler.num_up, keyHandler.num_down, keyHandler.num_left, keyHandler.num_right);
    }
  }

  private void movePlayer(boolean up, boolean down, boolean left, boolean right) {
    if (up || down || left || right) {
      if (up) {
        direction = "up";
      }
      if (down) {
        direction = "down";
      }
      if (left) {
        direction = "left";
      }
      if (right) {
        direction = "right";
      }

      collisionOn = false;
      gp.collisionChecker.checkTile(this);
      gp.collisionChecker.checkEntityToEntity(this);
      int objIndex = gp.collisionChecker.checkObject(this);

      int npcIndex = gp.collisionChecker.checkEntityToMonsters(this, gp.monsters);
      interactWithMonster(npcIndex);

      if (!collisionOn) {
        move(direction);
      }
      updateSpriteImage();
    }
  }

  private void interactWithMonster(int npcIndex) {
    if (npcIndex != 999) {
      shouldBeRemoved = true;
    }
  }

  private void plantBomb() {
    // check if user can plant more bombs
    int bombsPlanted = 0;
    for (SuperObject superObject : gp.obj) {
      if (superObject instanceof BombObject) {
        if (((BombObject) superObject).owner.equals(name)) {
          bombsPlanted++;
        }
      }
    }

    if (bombsPlanted >= bombsNum) {
      return;
    }
    // TODO: here is the part of the code for the detonator subtask

    // check if there is already a bomb in the same position
    int posX = (position.getX() + solidArea.x) / gp.tileSize;
    int posY = (position.getY() + solidArea.y) / gp.tileSize;

    int coordX = posX * gp.tileSize;
    int coordY = posY * gp.tileSize;
    if (gp.positionOccupied(coordX, coordY)) {
      return;
    }

    gp.obj.add(new BombObject(new Point(coordX, coordY), gp, name));
  }

  public void updateSpriteImage() {
    spriteCounter++;
    if (spriteCounter > 12) {
      if (spriteNum == 1) spriteNum = 2;
      else spriteNum = 1;
      spriteCounter = 0;
    }
  }
}
