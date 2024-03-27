package entity.effects.Curses;

import entity.effects.Effect;

public class SlowerCurse extends Effect {

    @Override
    public boolean isSlowerCurse() {
        return true;
    }

    protected SlowerCurse(int speedChange, int blastRangeChange) {
        super(speedChange, blastRangeChange);
    }
}
