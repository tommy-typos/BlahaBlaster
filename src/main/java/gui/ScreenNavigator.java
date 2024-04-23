package gui;

import custom.CustomButton;
import custom.CustomLabel;
import custom.Slate;

import javax.swing.*;
import java.awt.*;

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
