package main.java.entity.monsters;
import main.java.entity.Point;
import main.java.gui.Game;
//package main.java.entity.monsters;

import java.util.Random;

public class BasicMonster extends Monster {
    public BasicMonster(Game gp, int id, Point position) {
        super(gp, id, position);
        speed = 2;
    }

    @Override
    protected String getMonsterType() {
        return "basic_monster";
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
        gp.collisionChecker.checkObject(this);
        gp.collisionChecker.checkEntityToEntity(this);
        gp.collisionChecker.checkMonsterToPlayer(this);

        if (!collisionOn) {
            move();
        }
        updateSpriteImage();
    }
}