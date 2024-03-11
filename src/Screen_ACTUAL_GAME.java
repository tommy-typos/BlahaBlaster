import javax.swing.*;
import java.awt.*;

public class Screen_ACTUAL_GAME extends JPanel {

    /**
     * navigator.goto_screen_ACTUAL_GAME("player1 name", "player2 name",
     *                 "blaha_map_unique_id",true, true, true);
     * */
    public Screen_ACTUAL_GAME(ScreenNavigator navigator, String player1_name, String player2_name,
                              String selected_map_id, boolean intelligent_monsters,
                              boolean advanced_powerups, boolean hindering_curses) {
        super();
        this.setBackground(Color.ORANGE);
        /**
         * TODO:
         *  Read all maps with MapsController.readGameMapsFromJson(); (it will read from maps.json and return GameMap[] array)
         *  Find the selected map with its id and use.
         * */
    }
}
