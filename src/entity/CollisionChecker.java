package entity;

import entity.monsters.Monster;
import entity.objects.BombObject;
import entity.objects.SuperObject;
import gui.Game;

import java.util.ArrayList;

public class CollisionChecker {

    Game game;

    public CollisionChecker(Game game){
        this.game = game;
    }

    public void checkTile(Entity entity){
        int entityLeftX = entity.getX() + entity.solidArea.x;
        int entityRightX = entityLeftX + entity.solidArea.width;
        int entityTopY = entity.getY() + entity.solidArea.y;
        int entityBottomY = entityTopY + entity.solidArea.height;

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

    public int checkObject(Entity entity){
        int index = 999;

        for(SuperObject obj: game.obj){

            if(obj != null){
                if(entity instanceof Player && obj instanceof BombObject){
                    Player player = (Player) entity;
                    BombObject bomb = (BombObject) obj;
                    long leaveTime = bomb.timeToLeave - System.currentTimeMillis();
                    if(leaveTime > 0 && bomb.owner.equals(player.name)){
                        continue;
                    }
                }
                entity.solidArea.x = entity.getX() + entity.solidArea.x;
                entity.solidArea.y = entity.getY() + entity.solidArea.y;

                obj.solidArea.x = obj.getX() + obj.solidArea.x;
                obj.solidArea.y = obj.getY() + obj.solidArea.y;

                switch (entity.direction){
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if(entity.solidArea.intersects(obj.solidArea)){
                            index = game.obj.indexOf(obj);
                            entity.collisionOn = true;
                        }
                        break;

                    case "down":
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(obj.solidArea)){
                            index = game.obj.indexOf(obj);
                            entity.collisionOn = true;
                        }
                        break;

                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(obj.solidArea)){
                            index = game.obj.indexOf(obj);
                            entity.collisionOn = true;
                        }
                        break;

                    case "right":
                        entity.solidArea.x += entity.speed;
                        if(entity.solidArea.intersects(obj.solidArea)){
                            index = game.obj.indexOf(obj);
                            entity.collisionOn = true;
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                obj.solidArea.x = obj.solidAreaDefaultX;
                obj.solidArea.y = obj.solidAreaDefaultY;
            }
        }

        return index;
    }

    private void checkCollision(String tile1, String tile2, Entity entity){
        entity.collisionOn = game.tileManager.isTileCollision(tile1) || game.tileManager.isTileCollision(tile2);
    }

    public int checkEntity(Entity entity, ArrayList<Monster> target){
        int i = 999;

        for(Entity e : target){
            // Get entity's position on the map
            entity.solidArea.x = entity.getX() + entity.solidArea.x;
            entity.solidArea.y = entity.getY() + entity.solidArea.y;

            // Get target's position on the map
            e.solidArea.x = e.getX() + e.solidArea.x;
            e.solidArea.y = e.getY() + e.solidArea.y;

            // Check if the two entities collide
            switch (entity.direction){
                case "up":
                    entity.solidArea.y -= entity.speed;
                    if(entity.solidArea.intersects(e.solidArea)){
                        i = target.indexOf(e);
                        entity.collisionOn = true;
                    }
                    break;

                case "down":
                    entity.solidArea.y += entity.speed;
                    if(entity.solidArea.intersects(e.solidArea)){
                        i = target.indexOf(e);
                        entity.collisionOn = true;
                    }
                    break;

                case "left":
                    entity.solidArea.x -= entity.speed;
                    if(entity.solidArea.intersects(e.solidArea)){
                        i = target.indexOf(e);
                        entity.collisionOn = true;
                    }
                    break;

                case "right":
                    entity.solidArea.x += entity.speed;
                    if(entity.solidArea.intersects(e.solidArea)){
                        i = target.indexOf(e);
                        entity.collisionOn = true;
                    }
                    break;

            }
            entity.solidArea.x = entity.solidAreaDefaultX;
            entity.solidArea.y = entity.solidAreaDefaultY;
            e.solidArea.x = e.solidAreaDefaultX;
            e.solidArea.y = e.solidAreaDefaultY;
        }
        return i;
    }
}
