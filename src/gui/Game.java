package gui;

import entity.*;
import entity.Point;
import entity.monsters.*;
import entity.objects.BombObject;
import entity.objects.ChestObject;
import entity.objects.SuperObject;
import handler.KeyHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Game extends JPanel implements Runnable{

    /**
     * navigator.goto_screen_ACTUAL_GAME("player1 name", "player2 name",
     *                 "blaha_map_unique_id",true, true, true);
     * */

    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public int screenWidth;
    public int screenHeight;

    public CollisionChecker collisionChecker = new CollisionChecker(this);
    Thread gameThread;
    public GameMap gameMap;
    public TileManager tileManager;
    KeyHandler keyHandler = new KeyHandler();
    public ArrayList<SuperObject> obj = new ArrayList<>();
    public ArrayList<Monster> monsters = new ArrayList<>();

    public ArrayList<Player> players = new ArrayList<>();

    int FPS = 60;


    public Game(ScreenNavigator screenNavigator, String player_name1, String player_name2, String mapID, boolean p1AI, boolean p2AI, boolean p1Turn) {
        players.add(new Player(this, keyHandler, new Point(tileSize*1, tileSize*3), player_name1, 1));
        players.add(new Player(this, keyHandler, new Point(tileSize*4, tileSize*5), player_name2, 2));
        players.add(new Player(this, keyHandler, new Point(tileSize*1, tileSize*5), "player3", 3));

        this.gameMap = MapsController.getMapById(mapID);
        this.screenWidth = gameMap.mapDimensions[1] * tileSize;
        this.screenHeight = gameMap.mapDimensions[0] * tileSize;
        this.tileManager = new TileManager(this, gameMap);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.setUpGame(players);
    }

    public void setUpGame(ArrayList<Player> players){
        obj.add(new ChestObject(new Point(tileSize, tileSize), this));

        obj.add(new ChestObject(new Point(3*tileSize, 3*tileSize), this));

        monsters.add(new BasicMonster(this));
        monsters.add(new GhostMonster(this));
        monsters.add(new ChasingMonster(this, players));
        monsters.add(new TipsyMonster(this, players));
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
        blowUpBombs();
        Iterator<Player> playerIterator = players.iterator();
        while (playerIterator.hasNext()) {
            Player player = playerIterator.next();
            player.update();

            if (player.shouldBeRemoved) {
                playerIterator.remove();
            }
        }

        Iterator<Monster> monsterIterator = monsters.iterator();
        while(monsterIterator.hasNext()){
            Monster monster = monsterIterator.next();
            monster.update();

            if(monster.shouldBeRemoved){
                monsterIterator.remove();
            }
        }
    }

    private void blowUpBombs() {
        for(int i = 0; i < obj.size(); i++){
            SuperObject superObject = obj.get(i);
            if(superObject instanceof BombObject){
                BombObject bomb = (BombObject) superObject;
                if(bomb.blowTime < System.currentTimeMillis()){
                    blowEntities(bomb);
                    obj.remove(i);
                }
            }
        }
    }

    private void blowEntities(BombObject bomb){
        HashMap<Integer, ArrayList<Rectangle>> tilesToBlow = new HashMap<>();

        Point bombPosition = new Point(bomb.position.getX()/tileSize, bomb.position.getY()/tileSize);

        // add the tile where the bomb is placed
        tilesToBlow.put(0, new ArrayList<>());
        tilesToBlow.get(0).add(new Rectangle(bombPosition.getX()*tileSize, bombPosition.getY()*tileSize, tileSize, tileSize));

        int posX, posY;
        for (int i = 1; i <= bomb.blowRadius; i++){
            tilesToBlow.put(i, new ArrayList<>());

            posX = bombPosition.getX() + i;
            posY = bombPosition.getY();
            if(!isOutOfBound(posX, posY) && !checkMaterial(posX, posY, "wall")){
                tilesToBlow.get(i).add(new Rectangle(posX*tileSize, posY*tileSize, tileSize, tileSize));
            }

            posX = bombPosition.getX() - i;
            posY = bombPosition.getY();
            if(!isOutOfBound(posX, posY) && !checkMaterial(posX, posY, "wall")){
                tilesToBlow.get(i).add(new Rectangle(posX*tileSize, posY*tileSize, tileSize, tileSize));
            }

            posX = bombPosition.getX();
            posY = bombPosition.getY() + i;
            if(!isOutOfBound(posX, posY) && !checkMaterial(posX, posY, "wall")){
                tilesToBlow.get(i).add(new Rectangle(posX*tileSize, posY*tileSize, tileSize, tileSize));
            }

            posX = bombPosition.getX();
            posY = bombPosition.getY() - i;
            if(!isOutOfBound(posX, posY) && !checkMaterial(posX, posY, "wall")){
                tilesToBlow.get(i).add(new Rectangle(posX*tileSize, posY*tileSize, tileSize, tileSize));
            }
        }

        for(int i=0; i<= bomb.blowRadius; i++){
            for(Rectangle tile : tilesToBlow.get(i)){
                for(Player player : players){

                    Rectangle playerSolidArea = new Rectangle(player.position.getX() + player.solidArea.x, player.position.getY() + player.solidArea.y, player.solidArea.width, player.solidArea.height);
                    if(tile.intersects(playerSolidArea)){
                        player.shouldBeRemoved = true;
                    }
                }

                for(Monster monster : monsters){
                    Rectangle monsterSolidArea = new Rectangle(monster.position.getX() + monster.solidArea.x, monster.position.getY() + monster.solidArea.y, monster.solidArea.width, monster.solidArea.height);
                    if(tile.intersects(monsterSolidArea)){
                        monster.shouldBeRemoved = true;
                    }
                }
            }
        }

    }

    private boolean isOutOfBound(int i, int y) {
        return i < 0 || i >= gameMap.mapDimensions[0] || y < 0 || y >= gameMap.mapDimensions[1];
    }

    private boolean checkMaterial(Integer x, Integer y, String material){
        return gameMap.mapCells[x][y].equals(material);
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

        for (Monster monster : monsters) {
            monster.draw(g2d);
        }

        for(Player player : players){
            player.draw(g2d);
        }

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
