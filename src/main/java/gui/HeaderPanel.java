package main.java.gui;

import main.java.custom.Slate;
import java.awt.*;
import javax.swing.*;

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
