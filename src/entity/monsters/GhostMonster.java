package entity.monsters;

import entity.Point;
import gui.Game;

import java.util.Random;


public class GhostMonster extends Monster{
    private final Game game;
    public GhostMonster(Game gp) {
        super(gp);
        this.game = gp;
        position = new Point(7 * gp.tileSize, 8 * gp.tileSize);
        speed = 1;
    }

    @Override
    protected String getMonsterType() {
        return "ghost_monster";
    }

    @Override
    public void update() {
        setAction();
    }

    @Override
    public void setAction() {
        if (shouldChangeDirection()) {
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

        adjustDirectionAtMapEdges();
        move();
        updateSpriteImage();
    }

    private void adjustDirectionAtMapEdges() {
        int mapWidth = game.screenWidth;
        int mapHeight = game.screenHeight;
        int tileSize = game.tileSize;


        // Right edge
        if (position.getX() >= mapWidth - 2 * tileSize && direction.equals("right")) {
            direction = "left";
        }
        // Left edge
        else if (position.getX() <= tileSize && direction.equals("left")) {
            direction = "right";
        }
        // Bottom edge
        if (position.getY() >= mapHeight - 2 * tileSize && direction.equals("down")) {
            direction = "up";
        }
        // Top edge
        else if (position.getY() <= tileSize && direction.equals("up")) {
            direction = "down";
        }
    }


    private boolean shouldChangeDirection() {
        Random random = new Random();
        return random.nextInt(100) < 5;
    }

}
