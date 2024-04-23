package gui;

import custom.CustomButton;
import custom.CustomLabel;
import custom.Slate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class ScreenNavigator {
  MainJFrame frame;
  MainJPanel mainPanel;

  public ScreenNavigator(MainJFrame frame, MainJPanel mainPanel) {
    this.frame = frame;
    this.mainPanel = mainPanel;
  }

  public void goto_screen_mainMenu() {
    mainPanel.removeAll();
    frame.headerPanel.changeHeaderText("Main Menu");
    MainMenu _mainMenu = new MainMenu();
    _mainMenu.btn_newGame.addActionListener(
        e -> {
          frame.headerPanel.changeHeaderText("New Game");
          goto_screen_new_game_screen();
        });
    _mainMenu.btn_mapsSettings.addActionListener(
        e -> {
          goto_screen_mapsMenu();
        });
    _mainMenu.btn_customControls.addActionListener(
        e -> {
          goto_screen_customControls();
        });
    _mainMenu.btn_gameInfo.addActionListener(
        e -> {
          goto_screen_gameInfo();
        });
    _mainMenu.btn_exit.addActionListener(e -> frame.dispose());

    mainPanel.add(_mainMenu, BorderLayout.CENTER);

    mainPanel.revalidate();
    mainPanel.repaint();
  }

  public void goto_screen_gameInfo() {
    mainPanel.removeAll();
    frame.headerPanel.changeHeaderText("Game Info");

    GameInfo _gameInfo = new GameInfo();
    _gameInfo.btn_newGoBack.addActionListener(
        e -> {
          frame.headerPanel.changeHeaderText("Main Menu");
          goto_screen_mainMenu();
        });

    mainPanel.add(_gameInfo, BorderLayout.CENTER);

    mainPanel.revalidate();
    mainPanel.repaint();
  }

  public void goto_screen_mapsMenu() {
    mainPanel.removeAll();
    frame.headerPanel.changeHeaderText("Maps Settings");

    MapsMenu _mapsMenu = new MapsMenu(this);

    mainPanel.add(_mapsMenu, BorderLayout.CENTER);

    mainPanel.revalidate();
    mainPanel.repaint();
  }

  public void goto_screen_mapEditor(int selectedMapIndex) {
    mainPanel.removeAll();
    frame.headerPanel.changeHeaderText("Map Editor");

    MapEditor _mapEditor = new MapEditor(this, selectedMapIndex);

    mainPanel.add(_mapEditor, BorderLayout.CENTER);

    mainPanel.revalidate();
    mainPanel.repaint();
  }

  public void goto_screen_customControls() {
    mainPanel.removeAll();
    frame.headerPanel.changeHeaderText("Custom Controls");

    CustomControls _customControls = new CustomControls(this);

    mainPanel.add(_customControls, BorderLayout.CENTER);

    mainPanel.revalidate();
    mainPanel.repaint();

    mainPanel.transferFocus();
  }

  public void goto_screen_new_game_screen() {
    mainPanel.removeAll();
    frame.headerPanel.changeHeaderText("New Game");

    NewGameScreen newGameScreen = new NewGameScreen(this);

    mainPanel.add(newGameScreen, BorderLayout.CENTER);

    mainPanel.revalidate();
    mainPanel.repaint();
  }

  public void goto_screen_ACTUAL_GAME(
      String player1_name,
      String player2_name,
      boolean threePlayers,
      String player_name3,
      String selected_map_id,
      boolean intelligent_monsters,
      boolean advanced_powerups,
      boolean hindering_curses) {
    mainPanel.removeAll();
    frame.headerPanel.changeHeaderText("In Game Screen");

    // ******************************************
    // ******************************************
    // ******************************************
    JPanel gameWrapper = new JPanel(new GridBagLayout());
    gameWrapper.setBackground(Slate._950);

    GridBagConstraints gbc = new GridBagConstraints();

    //==== left side
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 0; // 1st column width
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JPanel leftSide = new JPanel();
    leftSide.setBackground(Slate._950);
    leftSide.setPreferredSize(new Dimension(237, 575));

    leftSide.setLayout(new GridLayout(3, 1, 0, 55));

    CustomControl[] jsonControls = CustomControlsJson.readControlsFromJson();
    CustomControl p1Controls = jsonControls[0];
    CustomControl p2Controls = jsonControls[1];
    CustomControl p3Controls = jsonControls[2];

    PlayerControl2 p1ControlsPanel = new PlayerControl2(Color.blue, player1_name, p1Controls);
    PlayerControl2 p2ControlsPanel = new PlayerControl2(Color.red, player2_name, p2Controls);
    PlayerControl2 p3ControlsPanel = new PlayerControl2(Color.green, player_name3, p3Controls);



    leftSide.add(p1ControlsPanel);
    leftSide.add(p2ControlsPanel);
    if (threePlayers) {
      leftSide.add(p3ControlsPanel);
    }











    gameWrapper.add(leftSide, gbc);


    //==== game
    gbc.gridx = 1;
    gbc.weightx = 1; // 2nd column width
    gbc.fill = GridBagConstraints.BOTH;

    Game game =
        new Game(
            this,
            player1_name,
            player2_name,
            threePlayers,
            player_name3,
            selected_map_id,
            intelligent_monsters,
            advanced_powerups,
            hindering_curses);
    gameWrapper.add(game, gbc);


    //==== right side
    gbc.gridx = 2;
    gbc.weightx = 0; // 3rd column width
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JPanel rightSide = new JPanel();
    rightSide.setBackground(Slate._950);
    rightSide.setPreferredSize(new Dimension(237, 575));
    gameWrapper.add(rightSide, gbc);




    mainPanel.add(gameWrapper, BorderLayout.CENTER);

    // ******************************************
    // ******************************************
    // ******************************************

    mainPanel.revalidate();
    mainPanel.repaint();

    mainPanel.transferFocus();

    game.startGameThread();
  }
}


class PlayerControl2 extends JPanel {
  public CustomLabel button_up;
  public CustomLabel button_left;
  public CustomLabel button_down;
  public CustomLabel button_right;
  public CustomLabel button_bomb;

  class CustomControlLabel extends CustomLabel {
    CustomControlLabel(String text, Color color){
      super(text);
      this.setBorder(BorderFactory.createLineBorder(Slate._800, 1));
      this.setHorizontalAlignment(SwingConstants.CENTER);
      this.setForeground(color);
    }
  }

  public PlayerControl2(
          Color color, String playerName, CustomControl pControls) {
    super();
    GridLayout gridlayout_p2 = new GridLayout(4, 1, 0, 0);
    this.setLayout(gridlayout_p2);
    this.setOpaque(true);
    this.setBackground(Slate._950);
    this.setBorder(BorderFactory.createLineBorder(Slate._800, 1));

    // label
    JPanel p2_label = new JPanel();
    p2_label.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    p2_label.setOpaque(true);
    p2_label.setAlignmentY(CENTER_ALIGNMENT);
    p2_label.setBackground(Slate._950);

    JLabel label_p2 = new JLabel(playerName);
    label_p2.setForeground(color);
    p2_label.add(label_p2);

    // upbutton
    JPanel p2_up = new JPanel();
    p2_up.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    p2_up.setOpaque(true);
    p2_up.setAlignmentY(CENTER_ALIGNMENT);
    p2_up.setBackground(Slate._950);

    this.button_up = new CustomControlLabel("⬆ (" + KeyEvent.getKeyText(pControls.go_up) + ")", color);
    this.button_up.setPreferredSize(new Dimension(73, 25));
    p2_up.add(this.button_up);

    // left bottom right
    JPanel p2_left_bottom_right = new JPanel();
    p2_left_bottom_right.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
    p2_left_bottom_right.setOpaque(true);
    p2_left_bottom_right.setAlignmentY(CENTER_ALIGNMENT);
    p2_left_bottom_right.setBackground(Slate._950);

    this.button_left = new CustomControlLabel("⬅ ("+ KeyEvent.getKeyText(pControls.go_left) + ")", color);
    this.button_left.setPreferredSize(new Dimension(73, 25));

    this.button_down = new CustomControlLabel("⬇ ("+ KeyEvent.getKeyText(pControls.go_down) + ")", color);
    this.button_down.setPreferredSize(new Dimension(73, 25));

    this.button_right = new CustomControlLabel("➡ ("+ KeyEvent.getKeyText(pControls.go_right) + ")", color);
    this.button_right.setPreferredSize(new Dimension(73, 25));

    p2_left_bottom_right.add(this.button_left);
    p2_left_bottom_right.add(this.button_down);
    p2_left_bottom_right.add(this.button_right);

    // bomb button
    JPanel p2_bomb = new JPanel();
    p2_bomb.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
    p2_bomb.setOpaque(true);
    p2_bomb.setAlignmentY(CENTER_ALIGNMENT);
    p2_bomb.setBackground(Slate._950);

    this.button_bomb = new CustomControlLabel("bomb ("+ KeyEvent.getKeyText(pControls.place_bomb) + ")", color);
    this.button_bomb.setPreferredSize(new Dimension(200, 25));
    p2_bomb.add(this.button_bomb);

    this.add(p2_label);
    this.add(p2_up);
    this.add(p2_left_bottom_right);
    this.add(p2_bomb);
  }
}