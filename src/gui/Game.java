package gui;

import entity.*;
import entity.Point;
import entity.objects.BombObject;
import entity.objects.ChestObject;
import entity.objects.SuperObject;
import handler.KeyHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game extends JPanel implements Runnable{

    /**
     * navigator.goto_screen_ACTUAL_GAME("player1 name", "player2 name",
     *                 "blaha_map_unique_id",true, true, true);
     * */

    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    int screenWidth;
    int screenHeight;

    public CollisionChecker collisionChecker = new CollisionChecker(this);
    Thread gameThread;
    public GameMap gameMap;
    public TileManager tileManager;
    KeyHandler keyHandler = new KeyHandler();
    public ArrayList<SuperObject> obj = new ArrayList<>();
    Player player1 = new Player(this, keyHandler, new Point(tileSize*1, tileSize*3), "player1", 1);
    Player player2 = new Player(this, keyHandler, new Point(tileSize*4, tileSize*5), "player2", 2);
//    Player player2 = new Player(this, keyHandler, new Point(200, 2ы00), 4);

    int FPS = 60;

    public Game(ScreenNavigator screenNavigator, String player1, String player2, String mapID, boolean p1AI, boolean p2AI, boolean p1Turn) {
        this.gameMap = MapsController.getMapById(mapID);
        this.screenWidth = gameMap.mapDimensions[1] * tileSize;
        this.screenHeight = gameMap.mapDimensions[0] * tileSize;
        this.tileManager = new TileManager(this, gameMap);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

    }

    public void setUpGame(){
        obj.add(new ChestObject(new Point(2*tileSize, 2*tileSize), this));

        obj.add(new ChestObject(new Point(3*tileSize, 3*tileSize), this));

        obj.add(new BombObject(new Point(5*tileSize, 4*tileSize), this));
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double nextDraw = System.nanoTime() + drawInterval;
        while (gameThread != null){
            update();

            repaint();

            try {
                double remainingTime = nextDraw - System.nanoTime();
                remainingTime = remainingTime/1000000;
                if(remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDraw += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(){
        player1.update();
        player2.update();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        tileManager.draw(g2d);

        for (SuperObject superObject : obj) {
            if (superObject != null) {
                superObject.draw(g2d);
            }
        }

        player1.draw(g2d);
        player2.draw(g2d);

        g2d.dispose();
    }

    public boolean positionOccupied(int posX, int posY) {
        for (SuperObject superObject : obj) {
            if (!isPlantable(posX, posY) || superObject.position.getX() == posX && superObject.position.getY() == posY) {
                return true;
            }
        }
        return false;
    }

    public boolean isPlantable(int posX, int posY) {

        return gameMap.mapCells[posY / tileSize][posX / tileSize].equals("grass");
    }
}
