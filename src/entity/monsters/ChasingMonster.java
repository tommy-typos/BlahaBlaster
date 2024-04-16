package entity.monsters;

import entity.Player;
import entity.Point;
import entity.objects.SuperObject;
import entity.objects.BombObject;
import gui.Game;

import java.util.List;
import java.util.Random;


public class ChasingMonster extends Monster{
    private final List<Player> players;
    private final Game game;

    public ChasingMonster(Game gp, List<Player> players, int id, Point position) {
        super(gp, id, position);
        this.game = gp;
        this.players = players;
        speed = 3;
    }

    @Override
    protected String getMonsterType() {
        return "chasing_monster";
    }

    @Override
    public void update() {
        setAction();
    }

    @Override
    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter == 60 || collisionOn) {
            if (!isNearBomb()) {
                Point closestPlayerPosition = findClosestPlayer();
                boolean movedTowardsPlayer = false;

                if (closestPlayerPosition != null) {
                    Point nextMove = calculateNextMoveTowardsPlayer(closestPlayerPosition);
                    adjustDirectionBasedOnNextMove(nextMove);
                    collisionOn = false;
                    gp.collisionChecker.checkTile(this);

                    if (!collisionOn) {
                        movedTowardsPlayer = true;
                    }
                }

                if (!movedTowardsPlayer) {
                    Random random = new Random();
                    int i = random.nextInt(4) + 1;
                    switch (i) {
                        case 1:
                            direction = "up";
                            break;
                        case 2:
                            direction = "down";
                            break;
                        case 3:
                            direction = "left";
                            break;
                        case 4:
                            direction = "right";
                            break;
                    }
                }
                actionLockCounter = 0;
            }
        }
        collisionOn = false;
        gp.collisionChecker.checkTile(this);

        if (!collisionOn) {
            move();
        }
        updateSpriteImage();
    }


    private boolean isNearBomb() {
        for (SuperObject obj : game.getObjects()) {
            if (obj instanceof BombObject) {
                BombObject bomb = (BombObject) obj;
                if (position.distance(bomb.position) <= bomb.blowRadius * game.tileSize) {
                    return true;
                }
            }
        }
        return false;
    }

    private Point calculateNextMoveTowardsPlayer(Point playerPosition) {
        int diffX = playerPosition.getX() - position.getX();
        int diffY = playerPosition.getY() - position.getY();

        int moveX = 0, moveY = 0;

        if (diffX > 0) moveX = speed;
        else if (diffX < 0) moveX = -speed;

        if (diffY > 0) moveY = speed;
        else if (diffY < 0) moveY = -speed;

        if (Math.abs(diffX) > Math.abs(diffY)) {
            return new Point(position.getX() + moveX, position.getY());
        } else {
            // Otherwise, prioritize vertical movement
            return new Point(position.getX(), position.getY() + moveY);
        }
    }

    private void adjustDirectionBasedOnNextMove(Point nextMove) {
        int diffX = nextMove.getX() - position.getX();
        int diffY = nextMove.getY() - position.getY();

        if (Math.abs(diffX) > Math.abs(diffY)) {
            direction = diffX > 0 ? "right" : "left";
        } else if (Math.abs(diffY) > 0) {
            direction = diffY > 0 ? "down" : "up";
        }
    }

    private Point findClosestPlayer() {
        double minDistance = Double.MAX_VALUE;
        Point closestPlayerPosition = null;
        for (Player player : players) {
            Point playerPosition = player.position;
            double distance = this.position.distance(playerPosition);
            if (distance < minDistance) {
                minDistance = distance;
                closestPlayerPosition = playerPosition;
            }
        }
        return closestPlayerPosition;
    }
}


