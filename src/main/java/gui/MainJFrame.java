package main.java.gui;

import main.java.custom.Slate;
import java.awt.*;
import javax.swing.*;

public class MainJFrame extends JFrame {
  HeaderPanel headerPanel;

  public MainJFrame() {
    super();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Blaha Blaster");
    this.setResizable(false);
    this.setSize(1150, 700);
    this.setLocationRelativeTo(null);
    this.setLayout(new BorderLayout());
    this.getContentPane().setBackground(Slate._950);

    headerPanel = new HeaderPanel();
    this.add(headerPanel, BorderLayout.NORTH);
  }
}
