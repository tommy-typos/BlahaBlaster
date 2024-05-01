package main.java.gui;

import main.java.custom.Slate;
import main.java.entity.GameMap;

import javax.swing.*;
import java.awt.*;

class MapPreview extends JPanel {
    public MapPreview(GameMap gameMap) {
        super();
        this.setLayout(new GridLayout(10, 10, 1, 1));
        this.setBackground(Slate._950);
        this.setBorder(BorderFactory.createLineBorder(Slate._950, 1));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JLabel tempbtn = new JLabel();
                tempbtn.setForeground(Slate._400);
                tempbtn.setVerticalAlignment(SwingConstants.CENTER);
                tempbtn.setHorizontalAlignment(SwingConstants.CENTER);
                tempbtn.setOpaque(true);
                tempbtn.setSize(new Dimension(10, 10));
//                tempbtn.setBorder(BorderFactory.createLineBorder(custom.Slate._950, 1));

                String value = gameMap.mapCells[i][j];
                switch (value) {
                    case "grass" -> tempbtn.setBackground(new Color(5, 46, 22));
                    case "box" -> {
                        tempbtn.setBackground(new Color(120, 53, 15));
                        tempbtn.setText("box");
                    }
                    case "wall" -> {
                        tempbtn.setBackground(new Color(41, 37, 36));
                        tempbtn.setText("wall");
                    }
                }
                this.add(tempbtn);
            }

        }
    }
}
