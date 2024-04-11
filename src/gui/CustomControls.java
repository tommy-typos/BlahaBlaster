package gui;

import custom.CustomButton;
import custom.Slate;

import javax.swing.*;
import java.awt.*;

class CustomControls extends JPanel {
    public CustomControls(ScreenNavigator navigator) {
        super();
        this.setBackground(Slate._950);

        JPanel grid = new JPanel();
        GridLayout gridlayout = new GridLayout(9, 1, 20, 20);
        grid.setLayout(gridlayout);
        grid.setOpaque(true);
        grid.setBackground(Slate._950);


        JPanel grid_3players = new JPanel();
        GridLayout gridlayout_3players = new GridLayout(1, 3, 20, 0);
        grid_3players.setLayout(gridlayout_3players);
        grid_3players.setOpaque(true);
        grid_3players.setBackground(Slate._950);

		// player 1 ******************************
		JPanel grid_p1 = new JPanel();
        GridLayout gridlayout_p1 = new GridLayout(9, 2, 20, 0);
        grid_p1.setLayout(gridlayout_p1);
        grid_p1.setOpaque(true);
        grid_p1.setBackground(Slate._950);

		// label
		JPanel p1_label = new JPanel();
        p1_label.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
        p1_label.setOpaque(true);
        p1_label.setAlignmentY(CENTER_ALIGNMENT);
        p1_label.setBackground(Slate._950);

        JLabel label_p1 = new JLabel("Player 1");
        label_p1.setForeground(Color.blue);
        p1_label.add(label_p1);

		// upbutton
		JPanel p1_up = new JPanel();
        p1_up.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
        p1_up.setOpaque(true);
        p1_up.setAlignmentY(CENTER_ALIGNMENT);
        p1_up.setBackground(Slate._950);

        CustomButton p1_button_up = new CustomButton("⬆");
        p1_button_up.setForeground(Color.blue);
        p1_button_up.setPreferredSize(new Dimension(70, 25));
        p1_up.add(p1_button_up);

        // left bottom right
        JPanel p1_left_bottom_right = new JPanel();
        p1_left_bottom_right.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        p1_left_bottom_right.setOpaque(true);
        p1_left_bottom_right.setAlignmentY(CENTER_ALIGNMENT);
        p1_left_bottom_right.setBackground(Slate._950);


        CustomButton p1_button_left = new CustomButton("⬅");
        p1_button_left.setForeground(Color.blue);
        p1_button_left.setPreferredSize(new Dimension(70, 25));

		CustomButton p1_button_bottom = new CustomButton("⬇");
        p1_button_bottom.setForeground(Color.blue);
        p1_button_bottom.setPreferredSize(new Dimension(70, 25));

		CustomButton p1_button_right = new CustomButton("➡");
        p1_button_right.setForeground(Color.blue);
        p1_button_right.setPreferredSize(new Dimension(70, 25));


        p1_left_bottom_right.add(p1_button_left);
        p1_left_bottom_right.add(p1_button_bottom);
        p1_left_bottom_right.add(p1_button_right);

		// bomb button
		JPanel p1_bomb = new JPanel();
        p1_bomb.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
        p1_bomb.setOpaque(true);
        p1_bomb.setAlignmentY(CENTER_ALIGNMENT);
        p1_bomb.setBackground(Slate._950);

        CustomButton p1_button_bomb = new CustomButton("bomb");
        p1_button_bomb.setForeground(Color.blue);
        p1_button_bomb.setPreferredSize(new Dimension(200, 25));
        p1_bomb.add(p1_button_bomb);


        grid_p1.add(p1_label);
        grid_p1.add(p1_up);
        grid_p1.add(p1_left_bottom_right);
        grid_p1.add(p1_bomb);
		



		// player 2 ******************************
		JPanel grid_p2 = new JPanel();
        GridLayout gridlayout_p2 = new GridLayout(9, 2, 20, 0);
        grid_p2.setLayout(gridlayout_p2);
        grid_p2.setOpaque(true);
        grid_p2.setBackground(Slate._950);

		// label
		JPanel p2_label = new JPanel();
        p2_label.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
        p2_label.setOpaque(true);
        p2_label.setAlignmentY(CENTER_ALIGNMENT);
        p2_label.setBackground(Slate._950);

        JLabel label_p2 = new JLabel("Player 1");
        label_p2.setForeground(Color.red);
        p2_label.add(label_p2);

		// upbutton
		JPanel p2_up = new JPanel();
        p2_up.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
        p2_up.setOpaque(true);
        p2_up.setAlignmentY(CENTER_ALIGNMENT);
        p2_up.setBackground(Slate._950);

        CustomButton p2_button_up = new CustomButton("⬆");
        p2_button_up.setForeground(Color.red);
        p2_button_up.setPreferredSize(new Dimension(70, 25));
        p2_up.add(p2_button_up);

        // left bottom right
        JPanel p2_left_bottom_right = new JPanel();
        p2_left_bottom_right.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        p2_left_bottom_right.setOpaque(true);
        p2_left_bottom_right.setAlignmentY(CENTER_ALIGNMENT);
        p2_left_bottom_right.setBackground(Slate._950);


        CustomButton p2_button_left = new CustomButton("⬅");
        p2_button_left.setForeground(Color.red);
        p2_button_left.setPreferredSize(new Dimension(70, 25));

		CustomButton p2_button_bottom = new CustomButton("⬇");
        p2_button_bottom.setForeground(Color.red);
        p2_button_bottom.setPreferredSize(new Dimension(70, 25));

		CustomButton p2_button_right = new CustomButton("➡");
        p2_button_right.setForeground(Color.red);
        p2_button_right.setPreferredSize(new Dimension(70, 25));


        p2_left_bottom_right.add(p2_button_left);
        p2_left_bottom_right.add(p2_button_bottom);
        p2_left_bottom_right.add(p2_button_right);

		// bomb button
		JPanel p2_bomb = new JPanel();
        p2_bomb.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
        p2_bomb.setOpaque(true);
        p2_bomb.setAlignmentY(CENTER_ALIGNMENT);
        p2_bomb.setBackground(Slate._950);

        CustomButton p2_button_bomb = new CustomButton("bomb");
        p2_button_bomb.setForeground(Color.red);
        p2_button_bomb.setPreferredSize(new Dimension(200, 25));
        p2_bomb.add(p2_button_bomb);


        grid_p2.add(p2_label);
        grid_p2.add(p2_up);
        grid_p2.add(p2_left_bottom_right);
        grid_p2.add(p2_bomb);



		// player 3 ******************************
		JPanel grid_p3 = new JPanel();
        GridLayout gridlayout_p3 = new GridLayout(9, 2, 20, 0);
        grid_p3.setLayout(gridlayout_p3);
        grid_p3.setOpaque(true);
        grid_p3.setBackground(Slate._950);

		// label
		JPanel p3_label = new JPanel();
        p3_label.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
        p3_label.setOpaque(true);
        p3_label.setAlignmentY(CENTER_ALIGNMENT);
        p3_label.setBackground(Slate._950);

        JLabel label_p3 = new JLabel("Player 1");
        label_p3.setForeground(Color.green);
        p3_label.add(label_p3);

		// upbutton
		JPanel p3_up = new JPanel();
        p3_up.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
        p3_up.setOpaque(true);
        p3_up.setAlignmentY(CENTER_ALIGNMENT);
        p3_up.setBackground(Slate._950);

        CustomButton p3_button_up = new CustomButton("⬆");
        p3_button_up.setForeground(Color.green);
        p3_button_up.setPreferredSize(new Dimension(70, 25));
        p3_up.add(p3_button_up);

        // left bottom right
        JPanel p3_left_bottom_right = new JPanel();
        p3_left_bottom_right.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        p3_left_bottom_right.setOpaque(true);
        p3_left_bottom_right.setAlignmentY(CENTER_ALIGNMENT);
        p3_left_bottom_right.setBackground(Slate._950);


        CustomButton p3_button_left = new CustomButton("⬅");
        p3_button_left.setForeground(Color.green);
        p3_button_left.setPreferredSize(new Dimension(70, 25));

		CustomButton p3_button_bottom = new CustomButton("⬇");
        p3_button_bottom.setForeground(Color.green);
        p3_button_bottom.setPreferredSize(new Dimension(70, 25));

		CustomButton p3_button_right = new CustomButton("➡");
        p3_button_right.setForeground(Color.green);
        p3_button_right.setPreferredSize(new Dimension(70, 25));


        p3_left_bottom_right.add(p3_button_left);
        p3_left_bottom_right.add(p3_button_bottom);
        p3_left_bottom_right.add(p3_button_right);

		// bomb button
		JPanel p3_bomb = new JPanel();
        p3_bomb.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
        p3_bomb.setOpaque(true);
        p3_bomb.setAlignmentY(CENTER_ALIGNMENT);
        // p3_bomb.setAlignmentX(CENTER_ALIGNMENT);
        p3_bomb.setBackground(Slate._950);

        CustomButton p3_button_bomb = new CustomButton("bomb");
        p3_button_bomb.setForeground(Color.green);
        p3_button_bomb.setPreferredSize(new Dimension(200, 25));
        p3_bomb.add(p3_button_bomb);


        grid_p3.add(p3_label);
        grid_p3.add(p3_up);
        grid_p3.add(p3_left_bottom_right);
        grid_p3.add(p3_bomb);


		grid_3players.add(grid_p1);
        grid_3players.add(grid_p2);
        grid_3players.add(grid_p3);

		// other stuff *************************************************


        

		grid.add(grid_3players);

        this.add(grid);
    }
}
