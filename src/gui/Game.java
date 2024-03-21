package gui;

import entity.*;
import entity.Point;
import entity.objects.ObjectSetter;
import entity.objects.SuperObject;
import handler.KeyHandler;

import javax.swing.*;
import java.awt.*;

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
    ObjectSetter objectSetter = new ObjectSetter(this);
    public SuperObject obj[] = new SuperObject[15];
    Player player1 = new Player(this, keyHandler, new Point(tileSize*1, tileSize*3), 3);
//    Player player2 = new Player(this, keyHandler, new Point(200, 200), 4);

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
        objectSetter.setObjects();
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
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        tileManager.draw(g2d);

        for(int i = 0; i < obj.length; i++){
            if(obj[i] != null){
                obj[i].draw(g2d);
            }
        }

        player1.draw(g2d);

        g2d.dispose();
    }
}
