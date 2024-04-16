package gui;

import entity.*;
import entity.Point;
import entity.effects.*;
import entity.effects.powerUps.*;
import entity.monsters.*;
import entity.objects.BombObject;
import entity.objects.BrickObject;
import entity.objects.ExplosionObject;
import entity.objects.SuperObject;
import handler.KeyHandler;

//import entity.effects.PowerUps.GhostPowerUp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

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

    public ArrayList<Effect> effects = new ArrayList<>();
    public ArrayList<ExplosionObject> activeExplosions = new ArrayList<>();



    int FPS = 60;


    public Game(ScreenNavigator screenNavigator, String player_name1, String player_name2, boolean threePlayers, String player_name3, String mapID, boolean p1AI, boolean p2AI, boolean p1Turn) {
        players.add(new Player(this, keyHandler, new Point(tileSize*1, tileSize*3), player_name1, 1));
        players.add(new Player(this, keyHandler, new Point(tileSize*4, tileSize*5), player_name2, 2));
        if(threePlayers){
            players.add(new Player(this, keyHandler, new Point(tileSize*1, tileSize*5), player_name3, 3));
        }

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
        effects.add(new GhostPowerUp(new Point(-5*tileSize, -5*tileSize), this));

        obj.add(new BrickObject(new Point(tileSize, tileSize), this));
        obj.add(new BrickObject(new Point(3*tileSize, 3*tileSize), this));



        monsters.add(new BasicMonster(this, 1, new Point(11 * tileSize, tileSize)));
        monsters.add(new GhostMonster(this, 2, new Point(7 * tileSize, 8 * tileSize)));
        monsters.add(new ChasingMonster(this, players, 3, new Point(8 * tileSize, 9 * tileSize)));
        monsters.add(new TipsyMonster(this, players, 4, new Point(tileSize, 10 * tileSize)));

        // Development: call the ghost powerUp
        for (Player player : players) {
            player.activateGhostPowerUp(player.ghostDuration);
        }

        for (Player player : players) {
            player.activateInvincibilityPowerUp(player.invincibilityDuration);
            System.out.println("Player " + player.name + " is invincible for " + player.invincibilityDuration + " seconds");
        }

        // checking roller skate powerUp
        players.getLast().speedBoosted = true;
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

            // Check for collisions with effects
            collisionChecker.checkPlayerToEffect(player);


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

        // check if chest should be replaced with effect
        Iterator<SuperObject> objIterator = obj.iterator();
        while(objIterator.hasNext()){
            SuperObject superObject = objIterator.next();
            if (superObject instanceof BrickObject && ((BrickObject) superObject).shouldBeRemoved) {
                objIterator.remove();
                replaceObjectWithEffect(superObject.position, new GhostPowerUp(superObject.position, this));
            }
        }
    }

    private void blowUpBombs() {
        for(int i = obj.size() - 1; i >= 0; i--){
            SuperObject superObject = obj.get(i);
            if(superObject instanceof BombObject){
                BombObject bomb = (BombObject) superObject;
                if(bomb.blowTime < System.currentTimeMillis() && i < obj.size()){
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

        // add the tiles in the blow radius
        int posX, posY;
        boolean leftStop, rightStop, upStop, downStop;
        leftStop = rightStop = upStop = downStop = false;
        for (int i = 1; i <= bomb.blowRadius; i++){
            tilesToBlow.put(i, new ArrayList<>());

            if(!leftStop){
                posX = bombPosition.getX() - i;
                posY = bombPosition.getY();
                if(isOutOfBound(posX, posY) || checkMaterial(posX, posY, "wall")){
                    leftStop = true;
                }else{
                    if(checkMaterial(posX, posY, "brick")){
                        gameMap.mapCells[posY][posX] = "grass";
                        leftStop = true;
                    }else{
                        tilesToBlow.get(i).add(new Rectangle(posX*tileSize, posY*tileSize, tileSize, tileSize));
                    }

                }
            }

            if(!rightStop){
                posX = bombPosition.getX() + i;
                posY = bombPosition.getY();
                if(isOutOfBound(posX, posY) || checkMaterial(posX, posY, "wall")){
                    rightStop = true;
                }else{
                    if(checkMaterial(posX, posY, "brick")){
                        gameMap.mapCells[posY][posX] = "grass";
                        rightStop = true;
                    }else {
                        tilesToBlow.get(i).add(new Rectangle(posX * tileSize, posY * tileSize, tileSize, tileSize));
                    }
                }
            }

            if(!upStop){
                posX = bombPosition.getX();
                posY = bombPosition.getY() - i;
                if(isOutOfBound(posX, posY) || checkMaterial(posX, posY, "wall")){
                    upStop = true;
                }else{
                    if (checkMaterial(posX, posY, "brick")) {
                        gameMap.mapCells[posY][posX] = "grass";
                        upStop = true;
                    } else {
                        tilesToBlow.get(i).add(new Rectangle(posX * tileSize, posY * tileSize, tileSize, tileSize));
                    }
                }
            }

            if(!downStop){
                posX = bombPosition.getX();
                posY = bombPosition.getY() + i;
                if(isOutOfBound(posX, posY) || checkMaterial(posX, posY, "wall")){
                    downStop = true;
                }else{
                    if (checkMaterial(posX, posY, "brick")) {
                        gameMap.mapCells[posY][posX] = "grass";
                        downStop = true;
                    } else {
                        tilesToBlow.get(i).add(new Rectangle(posX * tileSize, posY * tileSize, tileSize, tileSize));
                    }
                }
            }
        }

        for(int i=0; i<= bomb.blowRadius; i++){
            for(Rectangle tile : tilesToBlow.get(i)){
                for(Player player : players){

                    Rectangle playerSolidArea = new Rectangle(player.position.getX() + player.solidArea.x, player.position.getY() + player.solidArea.y, player.solidArea.width, player.solidArea.height);
                    if(tile.intersects(playerSolidArea) && player.invincibilityDuration <= 0){ // if the player is invincible, the bomb won't affect him
                        player.shouldBeRemoved = true;
                    }
                }

                for(Monster monster : monsters){
                    Rectangle monsterSolidArea = new Rectangle(monster.position.getX() + monster.solidArea.x, monster.position.getY() + monster.solidArea.y, monster.solidArea.width, monster.solidArea.height);
                    if(tile.intersects(monsterSolidArea)){
                        monster.shouldBeRemoved = true;
                    }
                }

                // Chest blow up, if it blows up, it will be replaced by an effect
                for(SuperObject superObject : obj){
                    if(superObject instanceof BrickObject){
                        BrickObject chest = (BrickObject) superObject;
                        Rectangle chestSolidArea = new Rectangle(chest.position.getX(), chest.position.getY(), tileSize, tileSize);
                        if(tile.intersects(chestSolidArea)){
                            chest.shouldBeRemoved = true;
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


    public void replaceTile(Point position, Effect effect) {
        // Find and remove the ChestObject at the given position
        for (int i = 0; i < obj.size(); i++) {
            SuperObject superObject = obj.get(i);
            if (superObject instanceof BrickObject && superObject.position.equals(position)) {
                obj.remove(i);
                break;
            }
        }

        // Then, add the effect to the game
        effects.add(effect); // This adds the effect so it will be drawn on the next paintComponent call
    }



    private boolean isOutOfBound(int i, int y) {
        return i < 0 || i >= gameMap.mapDimensions[0] || y < 0 || y >= gameMap.mapDimensions[1];
    }

    private boolean checkMaterial(Integer x, Integer y, String material){
        return gameMap.mapCells[y][x].equals(material);
    }

    public void paintComponent(Graphics g){
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

        for(Player player : players){
            player.draw(g2d);
        }

        for (ExplosionObject explosion : activeExplosions) {
            if (explosion.image != null) {
                g.drawImage(explosion.image.getImage(), explosion.position.getX() * tileSize, explosion.position.getY() * tileSize, tileSize, tileSize, this);
            }
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


    // Method to replace a ChestObject with an Effect (PowerUp or Curse)
    public void replaceObjectWithEffect(Point position, Effect effect) {
        // Find and remove the ChestObject at the given position
        for (int i = 0; i < obj.size(); i++) {
            SuperObject superObject = obj.get(i);
            if (superObject instanceof BrickObject && superObject.position.equals(position)) {
                obj.remove(i);
                break;
            }
        }

        // Then, add the effect to the game
        effects.add(effect); // This adds the effect, so it will be drawn on the next paintComponent call
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

}
