package entity;

import gui.Game;

public class CollisionChecker {

    Game game;

    public CollisionChecker(Game game){
        this.game = game;
    }

    public void checkTile(Entity entity){
        int entityLeftX = entity.getX() + entity.solidArea.x;
        int entityRightX = entity.getX() + entity.solidArea.x + entity.solidArea.width;
        int entityTopY = entity.getY() + entity.solidArea.y;
        int entityBottomY = entity.getY() + entity.solidArea.y + entity.solidArea.height;

        int leftCol = entityLeftX / game.tileSize;
        int rightCol = entityRightX / game.tileSize;
        int topRow = entityTopY / game.tileSize;
        int bottomRow = entityBottomY / game.tileSize;

        String tile1 = null, tile2 = null;

        switch (entity.direction){
            case "up":
                topRow = (entityTopY - entity.speed) / game.tileSize;
                tile1 = game.gameMap.mapCells[topRow][leftCol];
                tile2 = game.gameMap.mapCells[topRow][rightCol];
                break;
            case "down":
                bottomRow = (entityBottomY + entity.speed) / game.tileSize;
                tile1 = game.gameMap.mapCells[bottomRow][leftCol];
                tile2 = game.gameMap.mapCells[bottomRow][rightCol];
                break;
            case "left":
                leftCol = (entityLeftX - entity.speed) / game.tileSize;
                tile1 = game.gameMap.mapCells[topRow][leftCol];
                tile2 = game.gameMap.mapCells[bottomRow][leftCol];
                break;
            case "right":
                rightCol = (entityRightX + entity.speed) / game.tileSize;
                tile1 = game.gameMap.mapCells[topRow][rightCol];
                tile2 = game.gameMap.mapCells[bottomRow][rightCol];
                break;
        }
        checkCollision(tile1, tile2, entity);
    }

    private void checkCollision(String tile1, String tile2, Entity entity){
        entity.collisionOn = game.tileManager.isTileCollision(tile1) || game.tileManager.isTileCollision(tile2);
    }
}
