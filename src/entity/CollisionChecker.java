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
                tile1 = game.gameMap.mapCells[leftCol][topRow];
                tile2 = game.gameMap.mapCells[rightCol][topRow];
                System.out.println("leftCol: " + leftCol + " topRow: " + topRow);
                System.out.println("rightCol: " + rightCol + " topRow: " + topRow);
                break;
            case "down":
                bottomRow = (entityBottomY + entity.speed) / game.tileSize;
                tile1 = game.gameMap.mapCells[leftCol][bottomRow];
                tile2 = game.gameMap.mapCells[rightCol][bottomRow];
                System.out.println("leftCol: " + leftCol + " bottomRow: " + bottomRow);
                System.out.println("rightCol: " + rightCol + " bottomRow: " + bottomRow);
                break;
            case "left":
                leftCol = (entityLeftX - entity.speed) / game.tileSize;
                tile1 = game.gameMap.mapCells[leftCol][topRow];
                tile2 = game.gameMap.mapCells[leftCol][bottomRow];
                System.out.println("leftCol: " + leftCol + " topRow: " + topRow);
                System.out.println("leftCol: " + leftCol + " bottomRow: " + bottomRow);
                break;
            case "right":
                rightCol = (entityRightX + entity.speed) / game.tileSize;
                tile1 = game.gameMap.mapCells[rightCol][topRow];
                tile2 = game.gameMap.mapCells[rightCol][bottomRow];
                System.out.println("rightCol: " + rightCol + " topRow: " + topRow);
                System.out.println("rightCol: " + rightCol + " bottomRow: " + bottomRow);
                break;
        }
        checkCollision(tile1, tile2, entity);
    }

    private void checkCollision(String tile1, String tile2, Entity entity){
        entity.collisionOn = game.tileManager.isTileCollision(tile1) || game.tileManager.isTileCollision(tile2);
        if(entity.collisionOn){
            System.out.println("Collision detected " + entity.direction + " " + tile1 + " " + tile2);
        }else{
            System.out.println("No collision detected " + entity.direction + " " + tile1 + " " + tile2);
        }

    }
}
