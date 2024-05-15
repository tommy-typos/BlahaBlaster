package entity;

import entity.objects.BombObject;
import entity.objects.SuperObject;
import gui.Game;
import handler.KeyHandler;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

/** Represents a player entity in the game. */
public class Player extends Entity {
  KeyHandler keyHandler;
  public int bombsNum = 1;
  public String name;
  public int playerNumber;

  /**
   * Constructs a player object.
   *
   * @param gp The game instance.
   * @param keyHandler The key handler for player input.
   * @param position The initial position of the player.
   * @param name The name of the player.
   * @param playerNumber The player number.
   */
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

  /** Loads the player's image based on the player number. */
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

  /**
   * Gets the position of the player.
   *
   * @return The position of the player.
   */
  public Point getPosition() {
    return position;
  }

  /**
   * Gets the key handler associated with the player.
   *
   * @return The key handler.
   */
  public KeyHandler getKeyHandler() {
    return keyHandler;
  }

  /**
   * Sets the position of the player.
   *
   * @param position The new position of the player.
   */
  public void setPosition(Point position) {
    this.position = position;
  }

  /** Updates the player's state based on player input and game logic. */
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

  /**
   * Moves the player based on input from the key handler.
   *
   * @param up Whether the up arrow key is pressed.
   * @param down Whether the down arrow key is pressed.
   * @param left Whether the left arrow key is pressed.
   * @param right Whether the right arrow key is pressed.
   */
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

  /**
   * Handles interaction with a monster.
   *
   * @param npcIndex The index of the monster in the list of monsters.
   */
  protected void interactWithMonster(int npcIndex) {
    if (npcIndex != 999) {
      shouldBeRemoved = true;
    }
  }

  /** Plants a bomb at the player's current position. */
  protected void plantBomb() {
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

  /** Updates the player's sprite image for animation. */
  public void updateSpriteImage() {
    spriteCounter++;
    if (spriteCounter > 12) {
      if (spriteNum == 1) spriteNum = 2;
      else spriteNum = 1;
      spriteCounter = 0;
    }
  }
}
