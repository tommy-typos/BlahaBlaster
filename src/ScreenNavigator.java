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
        Screen_MainMenu screen_mainMenu = new Screen_MainMenu();
        screen_mainMenu.btn_newGame.addActionListener(e -> {
            frame.headerPanel.changeHeaderText("New Game");
            // TODO: handle new game screen
        });
        screen_mainMenu.btn_mapsSettings.addActionListener(e -> {
            goto_screen_mapsMenu();
        });
        screen_mainMenu.btn_customControls.addActionListener(e -> {
            goto_screen_customControls();
        });
        screen_mainMenu.btn_gameInfo.addActionListener(e -> {
            goto_screen_gameInfo();
        });
        screen_mainMenu.btn_exit.addActionListener(e -> frame.dispose());

        mainPanel.add(screen_mainMenu, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void goto_screen_gameInfo(){
        mainPanel.removeAll();
        frame.headerPanel.changeHeaderText("Game Info");

        Screen_GameInfo screen_gameInfo = new Screen_GameInfo();
        screen_gameInfo.btn_newGoBack.addActionListener(e -> {
            frame.headerPanel.changeHeaderText("Main Menu");
            goto_screen_mainMenu();
        });

        mainPanel.add(screen_gameInfo, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void goto_screen_mapsMenu(){
        mainPanel.removeAll();
        frame.headerPanel.changeHeaderText("Maps Settings");

        Screen_MapsMenu screen_mapsMenu = new Screen_MapsMenu(this);

        mainPanel.add(screen_mapsMenu, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void goto_screen_mapEditor(int selectedMapIndex){
        mainPanel.removeAll();
        frame.headerPanel.changeHeaderText("Map Editor");

        Screen_MapEditor screen_mapEditor = new Screen_MapEditor(this, selectedMapIndex);

        mainPanel.add(screen_mapEditor, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void goto_screen_customControls(){
        mainPanel.removeAll();
        frame.headerPanel.changeHeaderText("Map Editor");

        Screen_CustomControls screen_customControls = new Screen_CustomControls(this);

        mainPanel.add(screen_customControls, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void goto_screen_ACTUAL_GAME(String player1_name, String player2_name, String selected_map_id,
                                        boolean intelligent_monsters, boolean advanced_powerups, boolean hindering_curses){
        mainPanel.removeAll();
        frame.headerPanel.changeHeaderText("In Game Screen");

        Screen_ACTUAL_GAME screen_actual_game = new Screen_ACTUAL_GAME(this, player1_name, player2_name, selected_map_id, intelligent_monsters, advanced_powerups, hindering_curses);

        mainPanel.add(screen_actual_game, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }


}
