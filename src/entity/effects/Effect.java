package entity.effects;

public abstract class Effect {

    protected int speedChange = 0;
    protected int blastRangeChange = 0;
    protected int bombFreezeTime = 0;
    protected int bombPlacementTimeLimit = 0;
    protected int invincibilityDuration = 0;
    protected int ghostDuration = 0;
    protected int maxObstacles = 0;
    protected boolean hasDetonator = false;


    protected Effect(int speedChange, int blastRangeChange) {
        this.speedChange = speedChange;
        this.blastRangeChange = blastRangeChange;
    }

    public boolean isGhostPowerUp() { return false; }
    public boolean isRoolerSkatePowerUp() { return false; }
    public boolean isInvincibilityPowerUp() { return false; }
    public boolean isShortBlastCurse() { return false; }
    public boolean isSlowerCurse() { return false; }

}
