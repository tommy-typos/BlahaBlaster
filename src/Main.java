import com.google.gson.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.List;
import java.util.Objects;

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

class Screen_MapsMenu extends JPanel {

    private static GameMap[] removeMapAtIndexAndReturnNewMap(GameMap[] gameMaps, int indexToRemove){
        GameMap[] newArray = new GameMap[gameMaps.length - 1];

        // Copy the elements before the specified index
        System.arraycopy(gameMaps, 0, newArray, 0, indexToRemove);
        // Copy the elements after the specified index
        System.arraycopy(gameMaps, indexToRemove + 1, newArray, indexToRemove, newArray.length - indexToRemove);

        return newArray;
    }

    public Screen_MapsMenu(ScreenNavigator navigator){
        super();
        this.setBackground(Color.WHITE);
        GameMap[] gameMaps = MapsController.readGameMapsFromJson();

        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(1, 2, 25, 25));

        // left grid
        JPanel leftGrid = new JPanel();
        leftGrid.setLayout(new GridLayout(4, 1, 25, 25));
        leftGrid.setPreferredSize(new Dimension(500, 500));

        // left grid 1
        JPanel labelcombo = new JPanel();
        labelcombo.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));

        JLabel labelSelectMap = new JLabel("Select Map");

        JComboBox combobox = new JComboBox(gameMaps);
        combobox.setEditable(false);

        labelcombo.add(labelSelectMap);
        labelcombo.add(combobox);
        leftGrid.add(labelcombo);
        //left grid 2
        JPanel panel_left2 = new JPanel();
        panel_left2.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
        JLabel difficultyText = new JLabel("Difficulty:");

        JLabel difficultyValue = new JLabel(gameMaps[combobox.getSelectedIndex()].difficulty);

        JButton btn_edit = new JButton("Edit");
        btn_edit.addActionListener(e -> {
            // TODO: handle edit button
        });
        JButton btn_remove = new JButton("Remove");
        btn_remove.addActionListener(e -> {
            int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + combobox.getSelectedItem() + " map?", "Delete " + combobox.getSelectedItem(), JOptionPane.YES_NO_OPTION);
            // yes = 0, no = 1
            if (answer == 0) {
                int indexToRemove = combobox.getSelectedIndex();

                if (indexToRemove >= 0 && indexToRemove < gameMaps.length && gameMaps.length > 1) {
                    GameMap[] newArray = removeMapAtIndexAndReturnNewMap(gameMaps, indexToRemove);

                    MapsController.saveGameMapsToJson(newArray);

//                    goto_screen_mapsMenu(frame, mainPanel);
                    navigator.goto_screen_mapsMenu();
                } else {
                    JOptionPane.showMessageDialog(null, "You cannot delete all maps.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        panel_left2.add(difficultyText);
        panel_left2.add(difficultyValue);
        panel_left2.add(btn_edit);
        panel_left2.add(btn_remove);
        leftGrid.add(panel_left2);
//      // left 3

        JTextArea description = new JTextArea("DESCRIPTION: " + gameMaps[combobox.getSelectedIndex()].description);
        description.setEditable(false);
        description.setLineWrap(true);
        leftGrid.add(description);

        // left 4
        JPanel panel_left4 = new JPanel();
        panel_left4.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));

        JButton btn_backToMainMenu = new JButton("Back To Main Menu");
        btn_backToMainMenu.addActionListener(e -> {
            navigator.goto_screen_mainMenu();
        });

        JButton btn_createNewMap = new JButton("Create a new map");
        btn_createNewMap.addActionListener(e -> {
            // TODO: handle create new map button
        });



        panel_left4.add(btn_backToMainMenu);
        panel_left4.add(btn_createNewMap);
        leftGrid.add(panel_left4);


        // right grid Map Preview Panel
        MapPreviewWrapper previewWrapper = new MapPreviewWrapper();

        previewWrapper.setMapPreview(new MapPreview(gameMaps[combobox.getSelectedIndex()]));




        combobox.addActionListener(e -> {
            difficultyValue.setText(gameMaps[combobox.getSelectedIndex()].difficulty);
            description.setText("DESCRIPTION: " + gameMaps[combobox.getSelectedIndex()].description);

            previewWrapper.setMapPreview(new MapPreview(gameMaps[combobox.getSelectedIndex()]));
        });

        grid.add(leftGrid);
        grid.add(previewWrapper);
        this.add(grid);
    }
}

