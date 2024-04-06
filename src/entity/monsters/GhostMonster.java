package entity.monsters;

import entity.Point;
import gui.Game;

import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;


public class GhostMonster extends Monster{
    private final Game game;
    public GhostMonster(Game gp, int id, Point position) {
        super(gp, id, position);
        this.game = gp;
        speed = 1;
    }

    @Override
    public void getMonsterImage() {
        String basePath = "/monsters/ghost_monster/gm_";
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
