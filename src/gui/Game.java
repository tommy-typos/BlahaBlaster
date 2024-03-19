package gui;

import entity.Player;
import entity.Point;
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
    final int maxScreenCol = 20;
    final int maxScreenRow = 15;
    final int screenWidth = maxScreenCol * tileSize;
    final int screenHeight = maxScreenRow * tileSize;

    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    Player player1 = new Player(this, keyHandler, new Point(100, 100), 4);
//    Player player2 = new Player(this, keyHandler, new Point(200, 200), 4);

    int FPS = 60;

    public Game(ScreenNavigator screenNavigator, String player1, String player2, String mapID, boolean p1AI, boolean p2AI, boolean p1Turn) {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
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

        // player 1
        player1.draw(g2d);

        g2d.dispose();
    }
}
