package entity.effects;

import entity.Point;
import gui.Game;

import javax.swing.*;

public  class Effect {

    public Point position;
    public Game game;
    public ImageIcon image;

    public int speedChange = 0;
    public int blastRangeChange = 0;
    public int bombFreezeTime = 0;
    public int bombPlacementTimeLimit = 0;
    public int invincibilityDuration = 0;
    public int ghostDuration = 0;
    public int maxObstacles = 0;
    public boolean hasDetonator = false;




    /*
    public BombObject(Point position, Game game, String owner) {
        super(position, game);
        this.name = "bomb";
        this.owner = owner;
        try{
            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/bomb.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    * */

    // make a constructor with an image like in BombObject

    public Effect(Point position, Game game){
        this.position = position;
        this.game = game;
        try{
            this.image = new ImageIcon(getClass().getResource("/objects/effect.gif"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void applyEffect() {
        // Apply the effect to the player
        // Virtual method to be overwritten in other sub classes
    }

    public boolean isGhostPowerUp() { return false; }
    public boolean isRollerSkatePowerUp() { return false; }
    public boolean isInvincibilityPowerUp() { return false; }
    public boolean isBlastRangePowerUp() { return false; }
    public boolean isDetonatorPowerUp() { return false; }
    public boolean isObstaclePowerUp() { return false; }
    public boolean isBombSlotIncreasePowerUp() { return false; }

    public boolean isShortBlastCurse() { return false; }
    public boolean isSlowerCurse() { return false; }

    public boolean isBombFreezeCurse() { return false; }

    public boolean isBombPlacementTimeLimitCurse() { return false; }


}
