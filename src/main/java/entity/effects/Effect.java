package main.java.entity.effects;

import main.java.entity.Player;
import main.java.entity.Point;
import main.java.gui.Game;

import javax.swing.*;
import java.awt.*;

public abstract class Effect {

    public Point position;
    public Game game;
    public Player player;
    public ImageIcon image;



    public final int solidAreaDefaultX = 10;
    public final int solidAreaDefaultY = 20;

    // 48 as the tile size
    private final int width = 48 - 2*solidAreaDefaultX;
    private final int height = 48 - solidAreaDefaultY - 1;
    public Rectangle solidArea = new Rectangle(solidAreaDefaultX, solidAreaDefaultY, width, height);


    public int speedChange = 0;
    public int blastRangeChange = 0;
    public int bombFreezeTime = 0;
    public int bombPlacementTimeLimit = 0;
    public int invincibilityDuration = 0;
    public int ghostDuration = 0;
    public int maxObstacles = 0;
    public boolean hasDetonator = false;




    public String effectType() {
        return this.getClass().getSimpleName();
    }

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

    protected Effect(Point position, Game game){
        this.position = position;
        this.game = game;
        try{
            this.image = new ImageIcon(getClass().getResource("/objects/effect.gif"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void applyEffect(Player player) {
        // Use switch to check the type of effect and apply the effect

        switch (this.getClass().getSimpleName()) {
            case "ShortBlastCurse":
                // Apply ShortBlastCurse effect
                break;
            case "SlowerCurse":
                // Apply SlowerCurse effect
                break;
            case "BombFreezeCurse":
                // Apply BombFreezeCurse effect
                break;
            case "BombPlacementTimeLimitCurse":
                // Apply BombPlacementTimeLimitCurse effect
                break;
            case "GhostPowerUp":
                // Apply GhostPowerUp effect
                break;
            case "RollerSkatePowerUp":
                // Apply RollerSkatePowerUp effect
                break;
            case "InvincibilityPowerUp":
                // Apply InvincibilityPowerUp effect
                break;
            case "BlastRangePowerUp":
                // Apply BlastRangePowerUp effect
                break;
            case "DetonatorPowerUp":
                // Apply DetonatorPowerUp effect
                break;
            case "ObstaclePowerUp":
                // Apply ObstaclePowerUp effect
                break;
            case "BombSlotIncreasePowerUp":
                // Apply BombSlotIncreasePowerUp effect
                break;
        }

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
