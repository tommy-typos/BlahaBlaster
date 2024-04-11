package gui;

import com.google.gson.JsonObject;
import custom.CustomButton;
import custom.CustomLabel;
import custom.Slate;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

class CustomControls extends JPanel {
    public CustomControls(ScreenNavigator navigator) {
        super();
        this.setBackground(Slate._950);

        JPanel grid = new JPanel();
        GridLayout gridlayout = new GridLayout(3, 1, 10, 10);
        grid.setLayout(gridlayout);
        grid.setOpaque(true);
        grid.setBackground(Slate._950);


        JPanel grid_3players = new JPanel();
        GridLayout gridlayout_3players = new GridLayout(1, 3, 10, 10);
        grid_3players.setLayout(gridlayout_3players);
        grid_3players.setOpaque(true);
        grid_3players.setBackground(Slate._950);

        // players

        PlayerControl player1 = new PlayerControl(Color.blue, "Player 1");
        PlayerControl player2 = new PlayerControl(Color.red, "Player 2");
        PlayerControl player3 = new PlayerControl(Color.green, "Player 3");

        grid_3players.add(player1);
        grid_3players.add(player2);
        grid_3players.add(player3);

		
		grid.add(grid_3players);
		// other stuff *************************************************

        JPanel grid_bottom = new JPanel();
        grid_bottom.setLayout(new GridLayout(2, 1, 10, 20));
        grid_bottom.setBackground(Slate._950);

        // top of bottom
        JPanel top_info = new JPanel();
//        top_info.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));
        top_info.setLayout(new GridLayout(2, 1, 10, 1));
        top_info.setBackground(Slate._950);

        CustomLabel info = new CustomLabel("Click on control buttons and then press a key to change defined control key.");
        info.setPreferredSize(new Dimension(700, 20));
        info.setHorizontalAlignment(SwingConstants.CENTER);


        String settinupKey = "Setting Key for Player 1 > UP... (Press Esc to Cancel)";
        CustomLabel settingKey = new CustomLabel("");
        settingKey.setFont(new Font("Arial", Font.PLAIN, 26));
        settingKey.setHorizontalAlignment(SwingConstants.CENTER);
        settingKey.setForeground(Color.yellow);

        top_info.add(info);
        top_info.add(settingKey);

        // bottom of bottom

        JPanel bottom_btns = new JPanel();
        FlowLayout flw = new FlowLayout(FlowLayout.CENTER, 100, 1);
        bottom_btns.setLayout(flw);
        bottom_btns.setBackground(Slate._950);

        CustomButton mainMenu = new CustomButton("Back to Main Menu");
        mainMenu.setPreferredSize(new Dimension(200, 50));
        mainMenu.addActionListener(e -> {
            navigator.goto_screen_mainMenu();
        });

        CustomButton saveChanges = new CustomButton("Save Changes");
        saveChanges.setPreferredSize(new Dimension(200, 50));

        CustomLabel changesSaved = new CustomLabel("");
        changesSaved.setPreferredSize(new Dimension(200, 50));
        changesSaved.setHorizontalAlignment(SwingConstants.CENTER);

        saveChanges.addActionListener(e -> {
            changesSaved.setText("Changes are saved ✅");
            new Thread(() -> {
                try {
                    Thread.sleep(500);
                    SwingUtilities.invokeLater(() -> {
                        changesSaved.setText("");
                    });
                } catch (InterruptedException ee) {
                    ee.printStackTrace();
                }
            }).start();
        });




        bottom_btns.add(mainMenu);
        bottom_btns.add(changesSaved);
        bottom_btns.add(saveChanges);

        grid_bottom.add(top_info);
        grid_bottom.add(bottom_btns);



        grid.add(grid_bottom);
        this.add(grid);

    }
}

class PlayerControl extends JPanel{
    public CustomButton button_up;
    public CustomButton button_left;
    public CustomButton button_down;
    public CustomButton button_right;
    public CustomButton button_bomb;
    public PlayerControl(Color color, String playerName) {
        super();
        GridLayout gridlayout_p2 = new GridLayout(4, 1, 0, 0);
        this.setLayout(gridlayout_p2);
        this.setOpaque(true);

        // label
        JPanel p2_label = new JPanel();
        p2_label.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
        p2_label.setOpaque(true);
        p2_label.setAlignmentY(CENTER_ALIGNMENT);
        p2_label.setBackground(Slate._950);

        JLabel label_p2 = new JLabel(playerName);
        label_p2.setForeground(color);
        p2_label.add(label_p2);

        // upbutton
        JPanel p2_up = new JPanel();
        p2_up.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
        p2_up.setOpaque(true);
        p2_up.setAlignmentY(CENTER_ALIGNMENT);
        p2_up.setBackground(Slate._950);

        this.button_up = new CustomButton("⬆");
        this.button_up.setForeground(color);
        this.button_up.setPreferredSize(new Dimension(70, 25));
        p2_up.add(this.button_up);

        // left bottom right
        JPanel p2_left_bottom_right = new JPanel();
        p2_left_bottom_right.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        p2_left_bottom_right.setOpaque(true);
        p2_left_bottom_right.setAlignmentY(CENTER_ALIGNMENT);
        p2_left_bottom_right.setBackground(Slate._950);


        this.button_left = new CustomButton("⬅");
        this.button_left.setForeground(color);
        this.button_left.setPreferredSize(new Dimension(70, 25));

        this.button_down = new CustomButton("⬇");
        this.button_down.setForeground(color);
        this.button_down.setPreferredSize(new Dimension(70, 25));

        this.button_right = new CustomButton("➡");
        this.button_right.setForeground(color);
        this.button_right.setPreferredSize(new Dimension(70, 25));


        p2_left_bottom_right.add(this.button_left);
        p2_left_bottom_right.add(this.button_down);
        p2_left_bottom_right.add(this.button_right);

        // bomb button
        JPanel p2_bomb = new JPanel();
        p2_bomb.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
        p2_bomb.setOpaque(true);
        p2_bomb.setAlignmentY(CENTER_ALIGNMENT);
        p2_bomb.setBackground(Slate._950);

        this.button_bomb = new CustomButton("bomb");
        this.button_bomb.setForeground(color);
        this.button_bomb.setPreferredSize(new Dimension(200, 25));
        p2_bomb.add(this.button_bomb);


        this.add(p2_label);
        this.add(p2_up);
        this.add(p2_left_bottom_right);
        this.add(p2_bomb);
    }
}