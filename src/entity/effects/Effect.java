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


    protected Effect() {
        this.speedChange = speedChange;
        this.blastRangeChange = blastRangeChange;
    }

    protected void applyEffect() {
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
