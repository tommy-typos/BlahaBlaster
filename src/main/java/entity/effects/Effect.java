package entity.effects;

import entity.Player;
import entity.Point;
import gui.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public abstract class Effect {

    public Point position;
    public Game game;
    public Player player;
    public ImageIcon image;
    public BufferedImage icon;

    public final int solidAreaDefaultX = 10;
    public final int solidAreaDefaultY = 20;

    // 48 as the tile size
    private final int width = 48 - 2 * solidAreaDefaultX;
    private final int height = 48 - solidAreaDefaultY - 1;
    public Rectangle solidArea = new Rectangle(solidAreaDefaultX, solidAreaDefaultY, width, height);

    public int blastRangeChange = 0;


    public String effectType() {
        return this.getClass().getSimpleName();
    }


    protected Effect(Point position, Game game) {
        this.position = position;
        this.game = game;
        try {
            this.image = new ImageIcon(getClass().getResource("/objects/effect.gif"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void applyEffect(Player player) {

    }

    // get icon method
    public BufferedImage getPowerupIcon() {
        String basePath = "/powerups/" + getPowerUpType() + ".png";
        try {
            return icon = ImageIO.read(getClass().getResourceAsStream(basePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return icon;
    }

    // get status


    protected abstract String getPowerUpType();


    public boolean isGhostPowerUp() {
        return false;
    }

    public boolean isRollerSkatePowerUp() {
        return false;
    }

    public boolean isInvincibilityPowerUp() {
        return false;
    }

    public boolean isBlastRangePowerUp() {
        return false;
    }

    public boolean isDetonatorPowerUp() {
        return false;
    }

    public boolean isObstaclePowerUp() {
        return false;
    }

    public boolean isBombSlotIncreasePowerUp() {
        return false;
    }

    public boolean isShortBlastCurse() {
        return false;
    }

    public boolean isSlowerCurse() {
        return false;
    }

    public boolean isBombFreezeCurse() {
        return false;
    }

    public boolean isBombPlacementTimeLimitCurse() {
        return false;
    }
}
