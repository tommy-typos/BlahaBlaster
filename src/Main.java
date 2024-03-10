import com.google.gson.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Random;

class HeaderPanel extends JPanel {
    JLabel headerText;
    public HeaderPanel() {
        super();
        this.setBackground(Slate._900);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.setPreferredSize(new Dimension(0, 40));
        headerText = new JLabel("Main Menu");
        headerText.setForeground(Slate._300);
        headerText.setPreferredSize(new Dimension(300, 40));
        headerText.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(headerText);
    }

    public void changeHeaderText(String text) {
        headerText.setText(text);
    }
}

class MainJFrame extends JFrame {
    HeaderPanel headerPanel;
    public MainJFrame() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Blaha Blaster");
        this.setResizable(false);
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(Slate._950);

        headerPanel = new HeaderPanel();
        this.add(headerPanel, BorderLayout.NORTH);
    }
}

class MainJPanel extends JPanel{
    public MainJPanel() {
        super();
        this.setBackground(Slate._950);
        this.setBorder(BorderFactory.createLineBorder(Slate._950, 25));
    }
}


public class Main {
    public static void main(String[] args) {
        MainJFrame frame = new MainJFrame();

        MainJPanel mainPanel = new MainJPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // TODO: keep this
        frame.add(mainPanel, BorderLayout.CENTER);

        // ---- Screen Navigator ----
        ScreenNavigator navigator = new ScreenNavigator(frame, mainPanel);

        // ********************* Main Menu Screen *************************
//        navigator.goto_screen_mainMenu();

        // *********************** Game Info Screen **********************
//        navigator.goto_screen_gameInfo();

        // *********************** Maps Menu Screen **********************
        navigator.goto_screen_mapsMenu();



        frame.setVisible(true);
    }

}

class Screen_MapEditor extends JPanel {
    GameMap gameMap;
    GameMap[] allMaps;

    private GameMap createNewMap() {
        String map_id = String.valueOf((new Random()).nextInt()) + String.valueOf((new Random()).nextInt());
        String map_name = "New Map " + allMaps.length;
        int[] map_dimensions = new int[]{10, 10};
        String[][] map_cells = new String[10][10];
        for (int i = 0; i < map_dimensions[0]; i++) {
            for (int j = 0; j < map_dimensions[1]; j++) {
                map_cells[i][j] = "___";
            }
        }
        return new GameMap(map_id, map_name, "", "Easy", map_dimensions, map_cells);
    }
    public Screen_MapEditor(ScreenNavigator navigator, int selectedMapIndex) {
        /***
         * selectedMapIndex
         * if -1 ::: create new map
         * if 0 or  more ::: edit existing map
         * */
        super();
        this.setBackground(Color.ORANGE);
        allMaps = MapsController.readGameMapsFromJson();

        if (selectedMapIndex == -1){
            gameMap = createNewMap();
        }else {
            gameMap = allMaps[selectedMapIndex];
        }
        // ***************** Editor ********************

    }
}