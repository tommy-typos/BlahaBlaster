package entity;

import gui.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class TileManager {

    Game game;
    GameMap gameMap;
    public HashMap <String, Tile> tile;

    public TileManager(Game game, GameMap gameMap){
        this.game = game;
        tile = new HashMap<>();
        this.gameMap = gameMap;
        getTileImages();
    }





    public void getTileImages(){
        try{
            tile.put("grass", new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png")), false));

            tile.put("wall", new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png")), true));

            tile.put("brick", new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/brick.png")), true));
            tile.put("explosion", new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/explosion.gif")), false)
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // temporarily replace the images of tiles to explosion gif

    public boolean isTileCollision(String tileType){
        return tile.get(tileType).collision;
    }

    public void draw(Graphics2D g2d){
        for(int row = 0; row < gameMap.mapDimensions[0]; row++){
            for(int col = 0; col < gameMap.mapDimensions[1]; col++){
                BufferedImage img = tile.get(gameMap.mapCells[row][col]).image;
                g2d.drawImage(img, col * game.tileSize, row * game.tileSize, game.tileSize, game.tileSize, null);
            }
        }
    }
}
