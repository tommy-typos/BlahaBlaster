package main.java.gui;

import main.java.custom.CustomButton;
import main.java.custom.Slate;
import java.awt.*;
import javax.swing.*;

class GameInfo extends JPanel {
  CustomButton btn_newGoBack;

  public GameInfo() {
    super();
    this.setBackground(Slate._950);

    btn_newGoBack = new CustomButton("Go Back");
    btn_newGoBack.setPreferredSize(new Dimension(100, 50));

    JPanel contentWrap = new JPanel();
    contentWrap.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 5));
    contentWrap.setBackground(Slate._950);
    contentWrap.setPreferredSize(new Dimension(900, 500));

    class GameInfoText extends JTextArea {
      public GameInfoText(String text, String textType) {
        super(text);
        this.setOpaque(true);
        this.setForeground(Slate._300);
        this.setBackground(Slate._950);
        this.setEditable(false);
        this.setPreferredSize(new Dimension(900, 50));
        this.setLineWrap(true);
        this.setAlignmentY(TOP_ALIGNMENT);

        this.setSizes(textType);
      }

      private void setSizes(String textType) {
        if (textType == "h2") {
          this.setFont(new Font("Arial", Font.PLAIN, 26));
          this.setPreferredSize(new Dimension(900, 30));
        } else if (textType == "p") {
          this.setFont(new Font("Arial", Font.PLAIN, 14));
          this.setPreferredSize(new Dimension(900, 90));
        }
      }
    }

    String description =
        "Lorem ipsum dolor sit amet consectetur. Tempor urna ut at scelerisque adipiscing elementum leo feugiat.\n"
            + "1. Condimentum nunc urna enim risus tortor a lobortis vestibulum. Eget odio mollis volutpat ipsum. Amet tincidunt vel facilisi. \n"
            + "2. Habitant lorem ac nulla est vestibulum nulla. Sed viverra velit a id ut ac. Malesuada et in placerat egestas eu vitae. \n";

    GameInfoText txt_rules = new GameInfoText("Rules", "header");
    txt_rules.setFont(new Font("Arial", Font.PLAIN, 40));

    GameInfoText txt_h2_1 = new GameInfoText("Monster Types", "h2");

    GameInfoText txt_p_1 = new GameInfoText(description, "p");

    GameInfoText txt_h2_2 = new GameInfoText("Advanced PowerUps", "h2");

    GameInfoText txt_p_2 = new GameInfoText(description, "p");

    GameInfoText txt_h2_3 = new GameInfoText("Hindering Curses", "h2");

    GameInfoText txt_p_3 = new GameInfoText(description, "p");

    contentWrap.add(btn_newGoBack);
    contentWrap.add(txt_rules);
    contentWrap.add(txt_h2_1);
    contentWrap.add(txt_p_1);
    contentWrap.add(txt_h2_2);
    contentWrap.add(txt_p_2);
    contentWrap.add(txt_h2_3);
    contentWrap.add(txt_p_3);

    this.add(contentWrap, BorderLayout.CENTER);
  }
}
