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
        this.setBackground(Slate._950);
        GameMap[] gameMaps = MapsController.readGameMapsFromJson();

        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(1, 2, 20, 0));
        grid.setOpaque(true);
        grid.setBackground(Slate._950);

        // left grid
        JPanel leftGrid = new JPanel();
        leftGrid.setLayout(new GridLayout(4, 1));


        // left grid 1
        JPanel labelcombo = new JPanel();
        labelcombo.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 25));
        labelcombo.setOpaque(true);
        labelcombo.setAlignmentY(CENTER_ALIGNMENT);
        labelcombo.setBackground(Slate._950);

        JLabel labelSelectMap = new JLabel("Select Map");
        labelSelectMap.setForeground(Slate._300);
//        labelSelectMap.setOpaque(true);
//        labelSelectMap.setBackground(Color.ORANGE);

        JComboBox combobox = new JComboBox(gameMaps);
        combobox.setEditable(false);

        labelcombo.add(labelSelectMap);
        labelcombo.add(combobox);
        leftGrid.add(labelcombo);
        leftGrid.setOpaque(true);
        leftGrid.setBackground(Slate._950);
        //left grid 2
        JPanel panel_left2 = new JPanel();
        panel_left2.setLayout(new FlowLayout(FlowLayout.LEADING, 25, 25));
        panel_left2.setOpaque(true);
        panel_left2.setBackground(Slate._950);
//

        JLabel difficultyValue = new JLabel("Difficulty: " + gameMaps[combobox.getSelectedIndex()].difficulty);
        difficultyValue.setForeground(Slate._300);

        CustomButton btn_edit = new CustomButton("Edit");
        btn_edit.setPreferredSize(new Dimension(80, 50));
        btn_edit.addActionListener(e -> {
            // TODO: handle edit button
        });
        CustomButton btn_remove = new CustomButton("Remove");
        btn_remove.setBtnType("red_button");
        btn_remove.setPreferredSize(new Dimension(80, 50));
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


//        panel_left2.add(difficultyText);
        panel_left2.add(difficultyValue);
        panel_left2.add(btn_edit);
        panel_left2.add(btn_remove);
        leftGrid.add(panel_left2);
//      // left 3

        JPanel descWrapper = new JPanel();
        descWrapper.setOpaque(true);
        descWrapper.setBackground(Slate._950);
        descWrapper.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 25));

        JTextArea description = new JTextArea("DESCRIPTION:\n\n" + gameMaps[combobox.getSelectedIndex()].description);
        description.setEditable(false);
        description.setLineWrap(true);
        description.setForeground(Slate._300);
//        description.setPreferredSize(new Dimension(300, 100));
        description.setOpaque(true);
        description.setBackground(null);
        description.setBorder(BorderFactory.createLineBorder(Slate._950, 25));

//        descWrapper.add(description);
        leftGrid.add(description);

        // left 4
        JPanel panel_left4 = new JPanel();
        panel_left4.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 25));
        panel_left4.setOpaque(true);
        panel_left4.setBackground(Slate._950);

        CustomButton btn_backToMainMenu = new CustomButton("Back To Main Menu");
        btn_backToMainMenu.setPreferredSize(new Dimension(150, 50));
        btn_backToMainMenu.addActionListener(e -> {
            navigator.goto_screen_mainMenu();
        });

        CustomButton btn_createNewMap = new CustomButton("Create a new map");
        btn_createNewMap.setPreferredSize(new Dimension(150, 50));
        btn_createNewMap.addActionListener(e -> {
            // TODO: handle create new map button
        });



        panel_left4.add(btn_backToMainMenu);
        panel_left4.add(btn_createNewMap);
        leftGrid.add(panel_left4);


        // right grid Map Preview Panel
        MapPreviewWrapper previewWrapper = new MapPreviewWrapper();
//        previewWrapper.setBorder(BorderFactory.createLineBorder(Slate._950, 25));
        previewWrapper.setPreferredSize(new Dimension(450, 450));
        previewWrapper.setMapPreview(new MapPreview(gameMaps[combobox.getSelectedIndex()]));




        combobox.addActionListener(e -> {
            difficultyValue.setText("Difficulty: " + gameMaps[combobox.getSelectedIndex()].difficulty);
            description.setText("DESCRIPTION:\n\n" + gameMaps[combobox.getSelectedIndex()].description);

            previewWrapper.setMapPreview(new MapPreview(gameMaps[combobox.getSelectedIndex()]));
        });

        grid.add(leftGrid);
        grid.add(previewWrapper);
        this.add(grid);
    }
}

