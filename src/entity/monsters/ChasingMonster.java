package entity.monsters;

import entity.Player;
import entity.Point;
import gui.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;


public class ChasingMonster extends Monster{
    private final Game game;
    private final List<Player> players;

    public ChasingMonster(Game gp, List<Player> players) {
        super(gp);
        this.game = gp;
        this.players = players;
        position = new Point(8 * gp.tileSize, 9 * gp.tileSize);
        speed = 3;
    }

    @Override
    public void getMonsterImage() {
        String basePath = "/monsters/chasing_monster/cm_";
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream(basePath + "up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream(basePath + "up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream(basePath + "down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream(basePath + "down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream(basePath + "left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream(basePath + "left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream(basePath + "right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream(basePath + "right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        setAction();
    }

    @Override
    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter == 60 || collisionOn) {
            Point closestPlayerPosition = findClosestPlayer();
            boolean movedTowardsPlayer = false;

            if (closestPlayerPosition != null) {
                Point nextMove = calculateNextMoveTowardsPlayer(closestPlayerPosition);
                adjustDirectionBasedOnNextMove(nextMove);
                collisionOn = false;
                gp.collisionChecker.checkTile(this);
                gp.collisionChecker.checkObject(this);

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
        collisionOn = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkObject(this);

        if (!collisionOn) {
            move();
        }
        updateSpriteImage();
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


