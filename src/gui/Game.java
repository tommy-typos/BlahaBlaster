package gui;

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

    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 20;
    final int maxScreenRow = 15;
    final int screenWidth = maxScreenCol * tileSize;
    final int screenHeight = maxScreenRow * tileSize;

    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();

    int FPS = 60;

    int player1X = 0;
    int player1Y = 0;

    int player2X = 50;
    int player2Y = 50;

    int playerSpeed = 4;

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
        // player 1 movement
        if(keyHandler.w){
            player1Y -= playerSpeed;
        }
        if(keyHandler.a){
            player1X -= playerSpeed;
        }
        if(keyHandler.s){
            player1Y += playerSpeed;
        }
        if(keyHandler.d){
            player1X += playerSpeed;
        }

        // player 2 movement
        if(keyHandler.up){
            player2Y -= playerSpeed;
        }
        if(keyHandler.left){
            player2X -= playerSpeed;
        }
        if(keyHandler.down){
            player2Y += playerSpeed;
        }
        if(keyHandler.right){
            player2X += playerSpeed;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // player 1
        g2d.setColor(Color.WHITE);
        g2d.fillRect(player1X, player1Y, tileSize, tileSize);

        // player 2
        g2d.setColor(Color.RED);
        g2d.fillRect(player2X, player2Y, tileSize, tileSize);
        g2d.dispose();
    }
}
