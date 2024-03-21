package gui;

import java.awt.*;

public class ScreenNavigator {
    MainJFrame frame;
    MainJPanel mainPanel;

    public ScreenNavigator(MainJFrame frame, MainJPanel mainPanel) {
        this.frame = frame;
        this.mainPanel = mainPanel;
    }

    public void goto_screen_mainMenu(){
        mainPanel.removeAll();
        frame.headerPanel.changeHeaderText("Main Menu");
        MainMenu _mainMenu = new MainMenu();
        _mainMenu.btn_newGame.addActionListener(e -> {
            frame.headerPanel.changeHeaderText("New Game");
            // TODO: handle new game screen
        });
        _mainMenu.btn_mapsSettings.addActionListener(e -> {
            goto_screen_mapsMenu();
        });
        _mainMenu.btn_customControls.addActionListener(e -> {
            goto_screen_customControls();
        });
        _mainMenu.btn_gameInfo.addActionListener(e -> {
            goto_screen_gameInfo();
        });
        _mainMenu.btn_exit.addActionListener(e -> frame.dispose());

        mainPanel.add(_mainMenu, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void goto_screen_gameInfo(){
        mainPanel.removeAll();
        frame.headerPanel.changeHeaderText("Game Info");

        GameInfo _gameInfo = new GameInfo();
        _gameInfo.btn_newGoBack.addActionListener(e -> {
            frame.headerPanel.changeHeaderText("Main Menu");
            goto_screen_mainMenu();
        });

        mainPanel.add(_gameInfo, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void goto_screen_mapsMenu(){
        mainPanel.removeAll();
        frame.headerPanel.changeHeaderText("Maps Settings");

        MapsMenu _mapsMenu = new MapsMenu(this);

        mainPanel.add(_mapsMenu, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void goto_screen_mapEditor(int selectedMapIndex){
        mainPanel.removeAll();
        frame.headerPanel.changeHeaderText("Map Editor");

        MapEditor _mapEditor = new MapEditor(this, selectedMapIndex);

        mainPanel.add(_mapEditor, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void goto_screen_customControls(){
        mainPanel.removeAll();
        frame.headerPanel.changeHeaderText("Map Editor");

        CustomControls _customControls = new CustomControls(this);

        mainPanel.add(_customControls, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void goto_screen_ACTUAL_GAME(String player1_name, String player2_name, String selected_map_id,
                                        boolean intelligent_monsters, boolean advanced_powerups, boolean hindering_curses){
        mainPanel.removeAll();
        frame.headerPanel.changeHeaderText("In Game Screen");

        Game game = new Game(this, player1_name, player2_name, selected_map_id, intelligent_monsters, advanced_powerups, hindering_curses);

        mainPanel.add(game, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();

        game.startGameThread();
    }


}