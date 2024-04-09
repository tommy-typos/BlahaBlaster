package entity.objects;

import entity.Point;
//import entity.effects.Curses.ShortBlastCurse;
import entity.effects.Effect;
//import entity.effects.PowerUps.GhostPowerUp;
import gui.Game;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class ChestObject extends SuperObject{

    public ChestObject(Point position, Game game) {
        super(position, game);
        this.name = "chest";
        try{
            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // This method is called when a bomb explodes next to the chest
//    public void explode() {
//        Effect effect = getRandomEffect();
//        // Assume there's a method to replace the chest with the effect in the game field
//        game.replaceObjectWithEffect(this.position, effect);
//    }

    // Utility method to randomly select and instantiate an effect
//    private Effect getRandomEffect() {
//        Random random = new Random();
//        int effectType = random.nextInt(11); // 7 power-ups + 4 curses
//
//        switch (effectType) {
//            case 0: return new GhostPowerUp();
//            case 1: // Another power-up instantiation
//                // Define other cases for other power-ups and curses
//            case 10: return new ShortBlastCurse();
//            default: return null; // Default case, should not be reached
//        }
//    }
}
