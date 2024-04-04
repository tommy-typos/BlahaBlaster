package gui;

import custom.CustomButton;
import custom.Slate;
import entity.GameMap;

import javax.swing.*;
import java.awt.*;

public class NewGameScreen extends JPanel {
    public NewGameScreen(ScreenNavigator navigator) {
        super();
        this.setBackground(Slate._950);
        GameMap[] gameMaps = MapsController.readGameMapsFromJson();

        JPanel grid = new JPanel();
        GridLayout gridlayout = new GridLayout(9, 2, 20, 0);
        gridlayout.setVgap(20);
        grid.setLayout(gridlayout);
        grid.setOpaque(true);
        grid.setBackground(Slate._950);

        /** MAP SELECTION */

        JPanel map_labelcombo = new JPanel();
        map_labelcombo.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 0));
        map_labelcombo.setOpaque(true);
        map_labelcombo.setAlignmentY(CENTER_ALIGNMENT);
        map_labelcombo.setBackground(Slate._950);

        JLabel map_label = new JLabel("Select Map");
        map_label.setForeground(Slate._300);

        JComboBox map_combobox = new JComboBox(gameMaps);
        map_combobox.setEditable(false);

        map_labelcombo.add(map_label);
        map_labelcombo.add(map_combobox);



        /** PLAYER 1 */

        JPanel p1_labelinput = new JPanel();
        p1_labelinput.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 0));
        p1_labelinput.setOpaque(true);
        p1_labelinput.setAlignmentY(CENTER_ALIGNMENT);
        p1_labelinput.setBackground(Slate._950);

        JLabel p1_label = new JLabel("Player 1 Name:");
        p1_label.setForeground(Slate._300);

        JTextField p1_input = new JTextField();
        p1_input.setText("player1");
        p1_input.setForeground(Slate._300);
        p1_input.setOpaque(true);
        p1_input.setBackground(Slate._800);
        p1_input.setCaretColor(Slate._300);
        p1_input.setPreferredSize(new Dimension(100, 35));

        p1_labelinput.add(p1_label);
        p1_labelinput.add(p1_input);


        /** PLAYER 2 */

        JPanel p2_labelinput = new JPanel();
        p2_labelinput.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 0));
        p2_labelinput.setOpaque(true);
        p2_labelinput.setAlignmentY(CENTER_ALIGNMENT);
        p2_labelinput.setBackground(Slate._950);

        JLabel p2_label = new JLabel("Player 2 Name:");
        p2_label.setForeground(Slate._300);

        JTextField p2_input = new JTextField();
        p2_input.setText("player2");
        p2_input.setForeground(Slate._300);
        p2_input.setOpaque(true);
        p2_input.setBackground(Slate._800);
        p2_input.setCaretColor(Slate._300);
        p2_input.setPreferredSize(new Dimension(100, 35));

        p2_labelinput.add(p2_label);
        p2_labelinput.add(p2_input);


        /*** INTELLIGENT MONSTERS */
        JPanel intelligent_flow = new JPanel();
        intelligent_flow.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 0));
        intelligent_flow.setOpaque(true);
        intelligent_flow.setAlignmentY(CENTER_ALIGNMENT);
        intelligent_flow.setBackground(Slate._950);

        JCheckBox intelligentmonster_checkbox = new JCheckBox("Intelligent Monsters");
        intelligentmonster_checkbox.setForeground(Slate._300);

        intelligent_flow.add(intelligentmonster_checkbox);


        /*** ADVANCED POWERUPS */

        JPanel advanced_flow = new JPanel();
        advanced_flow.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 0));
        advanced_flow.setOpaque(true);
        advanced_flow.setAlignmentY(CENTER_ALIGNMENT);
        advanced_flow.setBackground(Slate._950);

        JCheckBox advancedpowerups_checkbox = new JCheckBox("Advanced Powerups");
        advancedpowerups_checkbox.setForeground(Slate._300);

        advanced_flow.add(advancedpowerups_checkbox);


        /*** HINDERING CURSES */

        JPanel hindering_flow = new JPanel();
        hindering_flow.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 0));
        hindering_flow.setOpaque(true);
        hindering_flow.setAlignmentY(CENTER_ALIGNMENT);
        hindering_flow.setBackground(Slate._950);

        JCheckBox hinderingcurse_checkbox = new JCheckBox("Hindering Curses");
        hinderingcurse_checkbox.setForeground(Slate._300);

        hindering_flow.add(hinderingcurse_checkbox);

        /*** MAIN MENU & START GAME */
        JPanel buttons_flow = new JPanel();
        buttons_flow.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 0));
        buttons_flow.setOpaque(true);
        buttons_flow.setAlignmentY(CENTER_ALIGNMENT);
        buttons_flow.setBackground(Slate._950);

        CustomButton mainmenu_btn = new CustomButton("Main Menu");
        mainmenu_btn.setPreferredSize(new Dimension(100, 30));
        mainmenu_btn.addActionListener(e -> {
            navigator.goto_screen_mainMenu();
        });

        CustomButton startgame_btn = new CustomButton("Start Game");
        startgame_btn.setPreferredSize(new Dimension(100, 30));
        startgame_btn.addActionListener(e -> {
            String p1_name = p1_input.getText();
            String p2_name = p2_input.getText();
            String selected_map_id = gameMaps[map_combobox.getSelectedIndex()].id;
            boolean intelligent_monsters = intelligentmonster_checkbox.isSelected();
            boolean advanced_powerups = advancedpowerups_checkbox.isSelected();
            boolean hindering_curses = hinderingcurse_checkbox.isSelected();

            if(p1_name.length() < 1 || p2_name.length() < 1){
                JOptionPane.showMessageDialog(this, "Player names cannot be empty");
            }else {
                navigator.goto_screen_ACTUAL_GAME(p1_name, p2_name, selected_map_id, intelligent_monsters, advanced_powerups, hindering_curses);
            }

        });

        buttons_flow.add(mainmenu_btn);
        buttons_flow.add(startgame_btn);





        grid.add(p1_labelinput);
        grid.add(p2_labelinput);
        grid.add(map_labelcombo);
        grid.add(intelligent_flow);
        grid.add(advanced_flow);
        grid.add(hindering_flow);
        grid.add(buttons_flow);

        this.add(grid);
    }
}
