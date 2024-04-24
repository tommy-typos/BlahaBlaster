package gui;

import custom.Slate;
import entity.*;
import entity.Point;
import entity.monsters.*;
import entity.objects.BombObject;
import entity.objects.ChestObject;
import entity.objects.SuperObject;
import handler.KeyHandler;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

  int FPS = 60;
  private HashMap<String, String> playerNames = new HashMap<>();
  public int gameState;
  public final int playState = 1;
  public final int pauseState = 2;
  public final int gameOverState = 3;
  public ScreenNavigator screenNavigator;
  public long timeToFinish;

  public Game(
      ScreenNavigator screenNavigator,
      String player_name1,
      String player_name2,
      boolean threePlayers,
      String player_name3,
      String mapID,
      boolean p1AI,
      boolean p2AI,
      boolean p1Turn) {
    this.screenNavigator = screenNavigator;
    this.gameMap = MapsController.getMapById(mapID);

    if (this.gameMap == null) {
      throw new IllegalArgumentException("Invalid map ID provided: " + mapID);
    }

    this.screenWidth = gameMap.mapDimensions[1] * tileSize;
    this.screenHeight = gameMap.mapDimensions[0] * tileSize;
    this.tileManager = new TileManager(this, gameMap);
    this.ui = new UI(this);
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setDoubleBuffered(true);
    this.addKeyListener(keyHandler);
    this.setFocusable(true);
    this.setBackground(Slate._950);

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

    timeToFinish = 0;
    players.add(
        new Player(
            this,
            keyHandler,
            new Point(tileSize * 1, tileSize * 3),
            playerNames.get("player1"),
            1));
    players.add(
        new Player(
            this,
            keyHandler,
            new Point(tileSize * 4, tileSize * 5),
            playerNames.get("player2"),
            2));
    if (!playerNames.get("player3").equals("")) {
      players.add(
          new Player(
              this,
              keyHandler,
              new Point(tileSize * 1, tileSize * 5),
              playerNames.get("player3"),
              3));
    }

    gameState = playState;

    obj.add(new ChestObject(new Point(tileSize, tileSize), this));
    obj.add(new ChestObject(new Point(3 * tileSize, 3 * tileSize), this));

    monsters.add(new BasicMonster(this, 1, new Point(11 * tileSize, tileSize)));
    monsters.add(new GhostMonster(this, 2, new Point(7 * tileSize, 8 * tileSize)));
    monsters.add(new ChasingMonster(this, players, 3, new Point(8 * tileSize, 9 * tileSize)));
    monsters.add(new TipsyMonster(this, players, 4, new Point(tileSize, 10 * tileSize)));
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
    if (gameState == playState) {
      blowUpBombs();
      Iterator<Player> playerIterator = players.iterator();
      while (playerIterator.hasNext()) {
        Player player = playerIterator.next();
        player.update();

        if (player.shouldBeRemoved) {
          playerIterator.remove();
        }
      }
      checkGameOver();

      Iterator<Monster> monsterIterator = monsters.iterator();
      while (monsterIterator.hasNext()) {
        Monster monster = monsterIterator.next();
        monster.update();

        if (monster.shouldBeRemoved) {
          monsterIterator.remove();
        }
      }
    }
  }

  private void blowUpBombs() {
    for (int i = 0; i < obj.size(); i++) {
      SuperObject superObject = obj.get(i);
      if (superObject instanceof BombObject) {
        BombObject bomb = (BombObject) superObject;
        if (bomb.blowTime < System.currentTimeMillis()) {
          blowEntities(bomb);
          obj.remove(i);
        }
      }
    }
  }

  private void blowEntities(BombObject bomb) {
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

    int posX, posY;
    boolean leftStop, rightStop, upStop, downStop;
    leftStop = rightStop = upStop = downStop = false;
    for (int i = 1; i <= bomb.blowRadius; i++) {
      tilesToBlow.put(i, new ArrayList<>());

      if (!leftStop) {
        posX = bombPosition.getX() - i;
        posY = bombPosition.getY();
        if (isOutOfBound(posX, posY) || checkMaterial(posX, posY, "wall")) {
          leftStop = true;
        } else {
          if (checkMaterial(posX, posY, "brick")) {
            gameMap.mapCells[posY][posX] = "grass";
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
        if (isOutOfBound(posX, posY) || checkMaterial(posX, posY, "wall")) {
          rightStop = true;
        } else {
          if (checkMaterial(posX, posY, "brick")) {
            gameMap.mapCells[posY][posX] = "grass";
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
        if (isOutOfBound(posX, posY) || checkMaterial(posX, posY, "wall")) {
          upStop = true;
        } else {
          if (checkMaterial(posX, posY, "brick")) {
            gameMap.mapCells[posY][posX] = "grass";
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
        if (isOutOfBound(posX, posY) || checkMaterial(posX, posY, "wall")) {
          downStop = true;
        } else {
          if (checkMaterial(posX, posY, "brick")) {
            gameMap.mapCells[posY][posX] = "grass";
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
          if (tile.intersects(playerSolidArea)) {
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
}
