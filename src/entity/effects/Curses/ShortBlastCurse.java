package entity.effects.Curses;

import entity.effects.Effect;

public class ShortBlastCurse extends Effect {

    @Override
    public boolean isShortBlastCurse() {
        return true;
    }

    protected ShortBlastCurse(int speedChange, int blastRangeChange) {
        super(speedChange, blastRangeChange);
    }
}
