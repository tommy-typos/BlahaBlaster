package entity.monsters;

import entity.Player;
import entity.Point;
import gui.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;


public class TipsyMonster extends Monster{
    private final Game game;
    private final List<Player> players;
    private final Random random;


    public TipsyMonster(Game gp, List<Player> players, int id, Point position) {
        super(gp, id, position);
        this.game = gp;
        this.players = players;
        speed = 2;
        random = new Random();
    }

    @Override
    public void getMonsterImage() {
        String basePath = "/monsters/tipsy_monster/tm_";
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

                if (random.nextInt(100) < 20) { // 20% chance to make a wrong move
                    nextMove = calculateRandomMove(); // Choose a random direction instead
                }

                adjustDirectionBasedOnNextMove(nextMove);
                collisionOn = false;
                gp.collisionChecker.checkTile(this);
                gp.collisionChecker.checkObject(this);
                gp.collisionChecker.checkMonsterToMonster(this);
                gp.collisionChecker.checkMonsterToPlayer(this);

                if (!collisionOn) {
                    movedTowardsPlayer = true;
                }
            }

            if (!movedTowardsPlayer) {
                adjustDirectionBasedOnNextMove(calculateRandomMove());
            }
            actionLockCounter = 0;
        }
        collisionOn = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkObject(this);
        gp.collisionChecker.checkMonsterToMonster(this);
        gp.collisionChecker.checkMonsterToPlayer(this);

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

    private Point calculateRandomMove() {
        int i = random.nextInt(4) + 1;
        switch (i) {
            case 1:
                return new Point(position.getX(), position.getY() - speed); // Move up
            case 2:
                return new Point(position.getX(), position.getY() + speed); // Move down
            case 3:
                return new Point(position.getX() - speed, position.getY()); // Move left
            default:
                return new Point(position.getX() + speed, position.getY()); // Move right
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






