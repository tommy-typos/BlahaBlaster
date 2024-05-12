package gui;

import custom.Slate;
import entity.*;
import entity.Point;
import entity.effects.Effect;
import entity.effects.powerUps.*;
import entity.monsters.*;
import entity.objects.BombObject;
import entity.objects.BrickObject;
import entity.objects.ExplosionObject;
import entity.objects.SuperObject;
import handler.KeyHandler;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class Game extends JPanel implements Runnable {
  /**
   * navigator.goto_screen_ACTUAL_GAME("player1 name", "player2 name", "blaha_map_unique_id",true,
   * true, true);
   */
  private List<SuperObject> gameObjects = new ArrayList<>();

  public List<SuperObject> getObjects() {
    return gameObjects;
  }

  public void addGameObject(SuperObject object) {
    gameObjects.add(object);
  }

  final int originalTileSize = 16;
  final int scale = 3;

  public final int tileSize = originalTileSize * scale;
  public int screenWidth;
  public int screenHeight;

  public CollisionChecker collisionChecker = new CollisionChecker(this);
  Thread gameThread;
  public GameMap gameMap;
  public TileManager tileManager;
  public KeyHandler keyHandler = new KeyHandler(this);
  public ArrayList<SuperObject> obj = new ArrayList<>();
  public ArrayList<Monster> monsters = new ArrayList<>();
  public UI ui;

  public ArrayList<Player> players = new ArrayList<>();

  public ArrayList<Effect> effects = new ArrayList<>();
  public ArrayList<ExplosionObject> activeExplosions = new ArrayList<>();
  private final int maxPowerUps = 10;
  private int currentPowerUps = 0;
  private final Random random = new Random();

  int FPS = 60;
  private HashMap<String, String> playerNames = new HashMap<>();
  public int gameState;
  public final int playState = 1;
  public final int pauseState = 2;
  public final int gameOverState = 3;
  public ScreenNavigator screenNavigator;
  public long timeToFinish;
  private boolean intelligent_monsters, advanced_powerups, hindering_curses;

  public Game(
      ScreenNavigator screenNavigator,
      String player_name1,
      String player_name2,
      boolean threePlayers,
      String player_name3,
      String mapID,
      boolean intelligent_monsters,
      boolean advanced_powerups,
      boolean hindering_curses) {
    this.screenNavigator = screenNavigator;
    this.gameMap = MapsController.getMapById(mapID);

    if (this.gameMap == null) {
      throw new IllegalArgumentException("Invalid map ID provided: " + mapID);
    }

    this.screenWidth = gameMap.mapDimensions[1] * tileSize;
    this.screenHeight = gameMap.mapDimensions[0] * tileSize;
    this.tileManager = new TileManager(this, gameMap);
    this.ui = new UI(this);

    //    The below was instead of some part of above and below.

    //    KeyHandler keyHandler = new KeyHandler();
    //    public ArrayList<SuperObject> obj = new ArrayList<>();
    //    public ArrayList<Monster> monsters = new ArrayList<>();
    //
    //    public ArrayList<Player> players = new ArrayList<>();
    //
    //    public ArrayList<Effect> effects = new ArrayList<>();
    //    public ArrayList<ExplosionObject> activeExplosions = new ArrayList<>();
    //    private int maxPowerUps = 10;
    //    private int currentPowerUps = 0;
    //    private final Random random = new Random();
    //
    //
    //
    //
    //    int FPS = 60;
    //
    //
    //    public Game(ScreenNavigator screenNavigator, String player_name1, String player_name2,
    // boolean threePlayers, String player_name3, String mapID, boolean p1AI, boolean p2AI, boolean
    // p1Turn) {
    //        players.add(new Player(this, keyHandler, new Point(tileSize*1, tileSize*3),
    // player_name1, 1));
    //        players.add(new Player(this, keyHandler, new Point(tileSize*4, tileSize*5),
    // player_name2, 2));
    //        if(threePlayers){
    //            players.add(new Player(this, keyHandler, new Point(tileSize*1, tileSize*5),
    // player_name3, 3));
    //        }
    //
    //        this.gameMap = MapsController.getMapById(mapID);
    //        this.screenWidth = gameMap.mapDimensions[1] * tileSize;
    //        this.screenHeight = gameMap.mapDimensions[0] * tileSize;
    //        this.tileManager = new TileManager(this, gameMap);
    //
    //
    //

    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setDoubleBuffered(true);
    this.addKeyListener(keyHandler);
    this.setFocusable(true);
    this.setBackground(Slate._950);

    this.intelligent_monsters = intelligent_monsters;
    this.advanced_powerups = advanced_powerups;
    this.hindering_curses = hindering_curses;

    this.playerNames.put("player1", player_name1);
    this.playerNames.put("player2", player_name2);
    if (threePlayers) {
      this.playerNames.put("player3", player_name3);
    } else {
      this.playerNames.put("player3", "");
    }
    this.setUpGame();
  }

  public void setUpGame() {
    effects.add(new GhostPowerUp(new Point(1 * tileSize, 1 * tileSize), this));
    effects.add(new InvincibilityPowerUp(new Point(3 * tileSize, 3 * tileSize), this));


    timeToFinish = 0;
    players.add(
        new Player(
            this, keyHandler, new Point(tileSize, tileSize * 3), playerNames.get("player1"), 1));
    players.add(
        new Player(
            this,
            keyHandler,
            new Point(tileSize * 4, tileSize * 5),
            playerNames.get("player2"),
            2));
    if (!playerNames.get("player3").isEmpty()) {
      players.add(
          new Player(
              this, keyHandler, new Point(tileSize, tileSize * 5), playerNames.get("player3"), 3));
    }

    gameState = playState;

//    obj.add(new BrickObject(new Point(tileSize, tileSize), this));
//    obj.add(new BrickObject(new Point(3 * tileSize, 3 * tileSize), this));

    monsters.add(new BasicMonster(this, 1, new Point(11 * tileSize, tileSize)));
    if (intelligent_monsters) {
      monsters.add(new GhostMonster(this, 2, new Point(7 * tileSize, 8 * tileSize)));
      monsters.add(new ChasingMonster(this, players, 3, new Point(8 * tileSize, 9 * tileSize)));
      monsters.add(new TipsyMonster(this, players, 4, new Point(tileSize, 10 * tileSize)));
    }
  }

  public void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
  }

  @Override
  public void run() {
    double drawInterval = 1000000000 / FPS;
    double nextDraw = System.nanoTime() + drawInterval;
    while (gameThread != null) {
      update();

      repaint();

      try {
        double remainingTime = nextDraw - System.nanoTime();
        remainingTime = remainingTime / 1000000;
        if (remainingTime < 0) {
          remainingTime = 0;
        }
        Thread.sleep((long) remainingTime);
        nextDraw += drawInterval;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void update() {
    blowUpBombs();
    Iterator<Player> playerIterator = players.iterator();
    while (playerIterator.hasNext()) {
      Player player = playerIterator.next();
      player.update();

      // Check for collisions with effects
      collisionChecker.checkPlayerToEffect(player);

      if (player.shouldBeRemoved) {
        playerIterator.remove();
      }
    }

    Iterator<Monster> monsterIterator = monsters.iterator();
    while (monsterIterator.hasNext()) {
      Monster monster = monsterIterator.next();
      monster.update();

      if (monster.shouldBeRemoved) {
        monsterIterator.remove();
      }
    }

    Iterator<SuperObject> it = obj.iterator();
    while (it.hasNext()) {
      SuperObject obj = it.next();
      if (obj.shouldBeRemoved) {
        it.remove(); // This includes bombs after explosion
      }
    }


  }

  private void blowUpBombs() {
    // Instead of modifying the list directly, use an iterator
    for (Iterator<SuperObject> iterator = obj.iterator(); iterator.hasNext(); ) {
      SuperObject superObject = iterator.next();
      if (superObject instanceof BombObject) {
        BombObject bomb = (BombObject) superObject;
        if (bomb.blowTime <= System.currentTimeMillis()) {
          blowEntities(bomb);
          iterator.remove(); // Use iterator's remove method to avoid ConcurrentModificationException
        }
      }
    }
  }

  public void blowEntities(BombObject bomb) {
    HashMap<Integer, ArrayList<Rectangle>> tilesToBlow = new HashMap<>();

    Point bombPosition =
        new Point(bomb.position.getX() / tileSize, bomb.position.getY() / tileSize);

    // add the tile where the bomb is placed
    tilesToBlow.put(0, new ArrayList<>());
    tilesToBlow
        .get(0)
        .add(
            new Rectangle(
                bombPosition.getX() * tileSize,
                bombPosition.getY() * tileSize,
                tileSize,
                tileSize));

    // add the tiles in the blow radius
    int posX, posY;
    boolean leftStop, rightStop, upStop, downStop;
    leftStop = rightStop = upStop = downStop = false;
    for (int i = 1; i <= bomb.blowRadius; i++) {
      tilesToBlow.put(i, new ArrayList<>());

      if (!leftStop) {
        posX = bombPosition.getX() - i;
        posY = bombPosition.getY();
        if (isOutOfBound(posX, posY) || tileManager.checkMaterial(posX, posY, "wall")) {
          leftStop = true;
        } else {
          if (tileManager.checkMaterial(posX, posY, "brick")) {
            gameMap.mapCells[posY][posX] = "grass";
            if (shouldPlacePowerUp()) {
              Effect powerUp = getRandomPowerUp(new Point(posX * tileSize, posY * tileSize));
              if (powerUp != null) {
                effects.add(powerUp);
              }
            }
            leftStop = true;
          } else {
            tilesToBlow
                .get(i)
                .add(new Rectangle(posX * tileSize, posY * tileSize, tileSize, tileSize));
          }
        }
      }

      if (!rightStop) {
        posX = bombPosition.getX() + i;
        posY = bombPosition.getY();
        if (isOutOfBound(posX, posY) || tileManager.checkMaterial(posX, posY, "wall")) {
          rightStop = true;
        } else {
          if (tileManager.checkMaterial(posX, posY, "brick")) {
            gameMap.mapCells[posY][posX] = "grass";
            if (shouldPlacePowerUp()) {
              Effect powerUp = getRandomPowerUp(new Point(posX * tileSize, posY * tileSize));
              if (powerUp != null) {
                effects.add(powerUp);
              }
            }
            rightStop = true;
          } else {
            tilesToBlow
                .get(i)
                .add(new Rectangle(posX * tileSize, posY * tileSize, tileSize, tileSize));
          }
        }
      }

      if (!upStop) {
        posX = bombPosition.getX();
        posY = bombPosition.getY() - i;
        if (isOutOfBound(posX, posY) || tileManager.checkMaterial(posX, posY, "wall")) {
          upStop = true;
        } else {
          if (tileManager.checkMaterial(posX, posY, "brick")) {
            gameMap.mapCells[posY][posX] = "grass";
            if (shouldPlacePowerUp()) {
              Effect powerUp = getRandomPowerUp(new Point(posX * tileSize, posY * tileSize));
              if (powerUp != null) {
                effects.add(powerUp);
              }
            }
            upStop = true;
          } else {
            tilesToBlow
                .get(i)
                .add(new Rectangle(posX * tileSize, posY * tileSize, tileSize, tileSize));
          }
        }
      }

      if (!downStop) {
        posX = bombPosition.getX();
        posY = bombPosition.getY() + i;
        if (isOutOfBound(posX, posY) || tileManager.checkMaterial(posX, posY, "wall")) {
          downStop = true;
        } else {
          if (tileManager.checkMaterial(posX, posY, "brick")) {
            gameMap.mapCells[posY][posX] = "grass";
            if (shouldPlacePowerUp()) {
              Effect powerUp = getRandomPowerUp(new Point(posX * tileSize, posY * tileSize));
              if (powerUp != null) {
                effects.add(powerUp);
              }
            }
            downStop = true;
          } else {
            tilesToBlow
                .get(i)
                .add(new Rectangle(posX * tileSize, posY * tileSize, tileSize, tileSize));
          }
        }
      }
    }

    for (int i = 0; i <= bomb.blowRadius; i++) {
      for (Rectangle tile : tilesToBlow.get(i)) {
        for (Player player : players) {

          Rectangle playerSolidArea =
              new Rectangle(
                  player.position.getX() + player.solidArea.x,
                  player.position.getY() + player.solidArea.y,
                  player.solidArea.width,
                  player.solidArea.height);
          if (tile.intersects(playerSolidArea) && player.invincibilityDuration <= 0) {
            player.shouldBeRemoved = true;
          }
        }

        for (Monster monster : monsters) {
          Rectangle monsterSolidArea =
              new Rectangle(
                  monster.position.getX() + monster.solidArea.x,
                  monster.position.getY() + monster.solidArea.y,
                  monster.solidArea.width,
                  monster.solidArea.height);
          if (tile.intersects(monsterSolidArea)) {
            monster.shouldBeRemoved = true;
          }
        }

        // Chest blow up, if it blows up, it will be replaced by an effect
        for (SuperObject superObject : obj) {
          if (superObject instanceof BrickObject brick) {
            Rectangle brickSolidArea =
                new Rectangle(brick.position.getX(), brick.position.getY(), tileSize, tileSize);
            if (tile.intersects(brickSolidArea)) {
              brick.shouldBeRemoved = true;
            }
          }
        }

        for (SuperObject superObject : obj) {
          if (superObject instanceof BombObject && superObject != bomb) {
            BombObject otherBomb = (BombObject) superObject;
            // Get the tile position of the other bomb
            Point otherBombTile =
                new Point(
                    otherBomb.position.getX() / tileSize, otherBomb.position.getY() / tileSize);
            Rectangle otherBombRect =
                new Rectangle(
                    otherBombTile.getX() * tileSize,
                    otherBombTile.getY() * tileSize,
                    tileSize,
                    tileSize);

            // Check if the other bomb's tile is in the blow radius
            for (ArrayList<Rectangle> blowList : tilesToBlow.values()) {
              for (Rectangle blowTile : blowList) {
                if (blowTile.intersects(otherBombRect)) {
                  otherBomb.blowTime =
                      System.currentTimeMillis(); // Set blowTime to trigger it immediately
                  break;
                }
              }
            }
          }
        }

        // replace the affected tiles' image with the explosion gif
        for (Rectangle tileToBlow : tilesToBlow.get(i)) {
          // Calculate the tile position
          int tileX = tileToBlow.x / tileSize;
          int tileY = tileToBlow.y / tileSize;
          Point tilePosition = new Point(tileX, tileY);

          // Skip the tile that will be replaced by an effect
          if (isTileToBeReplacedWithEffect(tilePosition)) {
            continue;
          }

          // Replace the tile with an explosion
          ExplosionObject explosion = new ExplosionObject(tilePosition, this);
          activeExplosions.add(explosion);

          // Schedule to remove this explosion object after 2 seconds
          Timer timer = new Timer(2000, e -> activeExplosions.remove(explosion));
          timer.setRepeats(false); // Only execute once
          timer.start();
        }
      }
    }
  }

  private boolean isOutOfBound(int i, int y) {
    return i < 0 || i >= gameMap.mapDimensions[0] || y < 0 || y >= gameMap.mapDimensions[1];
  }

  private boolean checkMaterial(Integer x, Integer y, String material) {
    return gameMap.mapCells[y][x].equals(material);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    tileManager.draw(g2d);

    for (Effect effect : effects) { // Assuming you have a collection of effects somewhere
      if (effect.image != null) {
        g.drawImage(effect.image.getImage(), effect.position.getX(), effect.position.getY(), this);
      }
    }

    for (SuperObject superObject : obj) {
      if (superObject != null) {
        superObject.draw(g2d);
      }
    }

    for (Monster monster : monsters) {
      monster.draw(g2d);
    }

    for (Player player : players) {
      player.draw(g2d);
    }

    for (ExplosionObject explosion : activeExplosions) {
      if (explosion.image != null) {
        g.drawImage(
            explosion.image.getImage(),
            explosion.position.getX() * tileSize,
            explosion.position.getY() * tileSize,
            tileSize,
            tileSize,
            this);
      }
    }

    ui.draw(g2d);
    g2d.dispose();
  }

  public boolean positionOccupied(int posX, int posY) {
    for (SuperObject superObject : obj) {
      if (!isPlantable(posX, posY)
          || superObject.position.getX() == posX && superObject.position.getY() == posY) {
        return true;
      }
    }
    return false;
  }

  public boolean isPlantable(int posX, int posY) {
    return gameMap.mapCells[posY / tileSize][posX / tileSize].equals("grass");
  }

  // Method to replace a ChestObject with an Effect (PowerUp or Curse)
  public void replaceObjectWithEffect(Point position) {
    // Ensure not to exceed the maximum number of power-ups
    if (currentPowerUps >= maxPowerUps) {
      return;
    }

    // Remove the BrickObject first
    obj.removeIf(
        superObject -> superObject instanceof BrickObject && superObject.position.equals(position));

    // Randomly select a power-up to create
    Effect effect = getRandomPowerUp(position);
    if (effect != null) {
      effects.add(effect); // Add to the effects to be drawn and interacted with
      currentPowerUps++; // Increment the count of active power-ups
    }
  }

  private Effect getRandomPowerUp(Point position) {
    int pick = random.nextInt(7); // Adjust this based on the number of power-up types
    switch (pick) {
      case 0:
        return new BlastRangePowerUp(position, this);
      case 1:
        return new BombSlotIncreasePowerUp(position, this);
      case 2:
        return new DetonatorPowerUp(position, this);
      case 3:
        return new GhostPowerUp(position, this);
      case 4:
        return new InvincibilityPowerUp(position, this);
      case 5:
        return new ObstaclePowerUp(position, this);
      case 6:
        return new RollerSkatePowerUp(position, this);
      default:
        return null;
    }
  }

  private boolean isTileToBeReplacedWithEffect(Point position) {
    // Check if the tile at the given position should be replaced with an effect
    for (Effect effect : effects) {
      if (effect.position.equals(position)) {
        return true;
      }
    }
    return false;
  }

  private boolean shouldPlacePowerUp() {
    return random.nextBoolean(); // 50% chance
  }

  public void restart() {
    obj.clear();
    monsters.clear();
    players.clear();
    gameMap = MapsController.getMapById(gameMap.id);
    tileManager = new TileManager(this, gameMap);
    this.setUpGame();
  }

  public void checkGameOver() {
    if (players.size() == 1) {
      if (timeToFinish == 0) {
        timeToFinish = System.currentTimeMillis() + 2000;
      } else if (timeToFinish < System.currentTimeMillis()) {
        gameState = gameOverState;
      }
    } else if (players.size() == 0) {
      gameState = gameOverState;
    }
  }

  public Object getTileSize() {
    return tileSize;
  }

  public List<Player> getPlayers() {
    return players;
  }
}
