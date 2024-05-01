package main.java.entity;

import main.java.entity.effects.Effect;
import main.java.entity.monsters.Monster;
import main.java.entity.objects.BombObject;
import main.java.entity.objects.BrickObject;
import main.java.entity.objects.SuperObject;
import main.java.gui.Game;

import java.util.ArrayList;
import java.util.List;

public class CollisionChecker {

    Game game;

    public CollisionChecker(Game game) {
        this.game = game;
    }

    public void checkTile(Entity entity) {
        int entityLeftX = entity.getX() + entity.solidArea.x;
        int entityRightX = entityLeftX + entity.solidArea.width;
        int entityTopY = entity.getY() + entity.solidArea.y;
        int entityBottomY = entityTopY + entity.solidArea.height;

        int leftCol = entityLeftX / game.tileSize;
        int rightCol = entityRightX / game.tileSize;
        int topRow = entityTopY / game.tileSize;
        int bottomRow = entityBottomY / game.tileSize;

        String tile1 = null, tile2 = null;

        switch (entity.direction) {
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

    public int checkObject(Entity entity) {
        int index = 999;

        for (SuperObject obj : game.obj) {
            if (obj != null) {
                if (entity instanceof Player && obj instanceof BombObject) {
                    Player player = (Player) entity;
                    BombObject bomb = (BombObject) obj;
                    long leaveTime = bomb.timeToLeave - System.currentTimeMillis();
                    if (leaveTime > 0 && bomb.owner != null && bomb.owner.equals(player.name)) {
                        continue;
                    }
                }

                // Extend the handling to include BrickObject with similar logic as BombObject
                if (entity instanceof Player && obj instanceof BrickObject) {
                    Player player = (Player) entity;
                    BrickObject brick = (BrickObject) obj;
                    if (brick.owner != null && brick.owner.equals(player.name)) {
                        continue; // Skip if the brick is owned by the player
                    }
                }

                // Update solid areas to current position
                entity.solidArea.x = entity.getX() + entity.solidArea.x;
                entity.solidArea.y = entity.getY() + entity.solidArea.y;

                obj.solidArea.x = obj.getX() + obj.solidArea.x;
                obj.solidArea.y = obj.getY() + obj.solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(obj.solidArea)) {
                            index = game.obj.indexOf(obj);
                            entity.collisionOn = true;
                        }  // If block here and in cases below were not here in powerups before the merge with master
                        break;

                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(obj.solidArea)) {
                            index = game.obj.indexOf(obj);
                            entity.collisionOn = true;
                        }
                        break;

                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(obj.solidArea)) {
                            index = game.obj.indexOf(obj);
                            entity.collisionOn = true;
                        }
                        break;

                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(obj.solidArea)) {
                            index = game.obj.indexOf(obj);
                            entity.collisionOn = true;
                        }
                        break;
                }

                // For DRY method we need to move if block from the top cases to here
//                if (entity.solidArea.intersects(obj.solidArea)) {
//                    index = game.obj.indexOf(obj);
//                    entity.collisionOn = true;
//                    break; // Exit on first collision found
//                }


                // Reset solid areas after check
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                obj.solidArea.x = obj.solidAreaDefaultX;
                obj.solidArea.y = obj.solidAreaDefaultY;
            }
        }

        return index; // Return index of the object with which collision is detected
    }

    private void checkCollision(String tile1, String tile2, Entity entity) {
        entity.collisionOn =
                game.tileManager.isTileCollision(tile1) || game.tileManager.isTileCollision(tile2);
    }

    public int checkEntityToMonsters(Entity entity, List<Monster> target){
        int i = 999;

        for (Entity e : target) {
            // Get entity's position on the map
            entity.solidArea.x = entity.getX() + entity.solidArea.x;
            entity.solidArea.y = entity.getY() + entity.solidArea.y;

            // Get target's position on the map
            e.solidArea.x = e.getX() + e.solidArea.x;
            e.solidArea.y = e.getY() + e.solidArea.y;

            // Check if the two entities collide
            switch (entity.direction) {
                case "up":
                    entity.solidArea.y -= entity.speed;
                    if (entity.solidArea.intersects(e.solidArea)) {
                        i = target.indexOf(e);
                        entity.collisionOn = true;
                    }
                    break;

                case "down":
                    entity.solidArea.y += entity.speed;
                    if (entity.solidArea.intersects(e.solidArea)) {
                        i = target.indexOf(e);
                        entity.collisionOn = true;
                    }
                    break;

                case "left":
                    entity.solidArea.x -= entity.speed;
                    if (entity.solidArea.intersects(e.solidArea)) {
                        i = target.indexOf(e);
                        entity.collisionOn = true;
                    }
                    break;

                case "right":
                    entity.solidArea.x += entity.speed;
                    if (entity.solidArea.intersects(e.solidArea)) {
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

    public void checkEntityToEntity(Entity entity) {
        ArrayList<Entity> entities = new ArrayList<>();
        if (entity instanceof Player) {
            entities.addAll(game.players);
        } else if (entity instanceof Monster) {
            entities.addAll(game.monsters);
        }

        for (Entity e : entities) {
            if (e != entity) {
                // Get entity's position on the map
                entity.solidArea.x = entity.getX() + entity.solidArea.x;
                entity.solidArea.y = entity.getY() + entity.solidArea.y;

                // Get target's position on the map
                e.solidArea.x = e.getX() + e.solidArea.x;
                e.solidArea.y = e.getY() + e.solidArea.y;

                // Check if the two entities collide
                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(e.solidArea)) {
                            entity.collisionOn = true;
                        }
                        break;

                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(e.solidArea)) {
                            entity.collisionOn = true;
                        }
                        break;

                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(e.solidArea)) {
                            entity.collisionOn = true;
                        }
                        break;

                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(e.solidArea)) {
                            entity.collisionOn = true;
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                e.solidArea.x = e.solidAreaDefaultX;
                e.solidArea.y = e.solidAreaDefaultY;
            }
        }
    }

    public void checkMonsterToPlayer(Monster monster) {
        for (Player p : game.players) {
            monster.solidArea.x = monster.getX() + monster.solidArea.x;
            monster.solidArea.y = monster.getY() + monster.solidArea.y;

            // Get target's position on the map
            p.solidArea.x = p.getX() + p.solidArea.x;
            p.solidArea.y = p.getY() + p.solidArea.y;

            boolean playerDies = false;
            switch (monster.direction) {
                case "up":
                    monster.solidArea.y -= monster.speed;
                    if (monster.solidArea.intersects(p.solidArea)) {
                        playerDies = true;
                    }
                    break;
                case "down":
                    monster.solidArea.y += monster.speed;
                    if (monster.solidArea.intersects(p.solidArea)) {
                        playerDies = true;
                    }
                    break;
                case "left":
                    monster.solidArea.x -= monster.speed;
                    if (monster.solidArea.intersects(p.solidArea)) {
                        playerDies = true;
                    }
                    break;
                case "right":
                    monster.solidArea.x += monster.speed;
                    if (monster.solidArea.intersects(p.solidArea)) {
                        playerDies = true;
                    }
                    break;
            }


            monster.solidArea.x = monster.solidAreaDefaultX;
            monster.solidArea.y = monster.solidAreaDefaultY;

            p.shouldBeRemoved = playerDies;
            if (!playerDies) {
                p.solidArea.x = p.solidAreaDefaultX;
                p.solidArea.y = p.solidAreaDefaultY;
            }
        }
    }

    public void checkPlayerToEffect(Player player) {
        for (Effect effect : game.effects) {  // Assuming 'game.effects' is your list of all effects on the game map.
            effect.solidArea.x = effect.position.getX() + effect.solidArea.x;
            effect.solidArea.y = effect.position.getY() + effect.solidArea.y;

            player.solidArea.x = player.getX() + player.solidArea.x;
            player.solidArea.y = player.getY() + player.solidArea.y;

            // Check if the player collides with an effect in any direction.
            switch (player.direction) {
                case "up":
                    player.solidArea.y -= player.speed;
                    break;
                case "down":
                    player.solidArea.y += player.speed;
                    break;
                case "left":
                    player.solidArea.x -= player.speed;
                    break;
                case "right":
                    player.solidArea.x += player.speed;
                    break;
            }

            // Apply the effect if there is a collision
            if (player.solidArea.intersects(effect.solidArea)) {
                game.effects.remove(effect);
                effect.applyEffect(player);
                System.out.println("Effect " + effect.effectType() +" applied: " + effect.getClass().getSimpleName());
                System.out.println("ghostDuration: " + player.ghostDuration);
                System.out.println("invincibilityDuration: " + player.invincibilityDuration);
                System.out.println("hasDetonator: " + player.hasDetonator);
                System.out.println("speed: " + player.speed);
                System.out.println("blastRange: " + player.blastRange);
                System.out.println("bombsNum: " + player.bombsNum);

                break;  // Assuming only one effect can be applied at a time.
            }

            // Reset the areas after checking
            player.solidArea.x = player.solidAreaDefaultX;
            player.solidArea.y = player.solidAreaDefaultY;
            effect.solidArea.x = effect.solidAreaDefaultX;
            effect.solidArea.y = effect.solidAreaDefaultY;
        }
    }
}
