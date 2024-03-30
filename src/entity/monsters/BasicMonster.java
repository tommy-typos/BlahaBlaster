package entity.monsters;
import entity.Point;
import gui.Game;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;


public class BasicMonster extends Monster{
    public BasicMonster(Game gp) {
        super(gp);
        position = new Point(5 * gp.tileSize, 6 * gp.tileSize);
        speed = 2;
    }

    @Override
    public void getMonsterImage() {
        String basePath = "/monsters/basic_monster/bm_";
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

        if (actionLockCounter == 60) {
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
            actionLockCounter = 0;
        }

        collisionOn = false;
        gp.collisionChecker.checkTile(this);

        if (!collisionOn) {
            move();
        }
        updateSpriteImage();
    }

}
