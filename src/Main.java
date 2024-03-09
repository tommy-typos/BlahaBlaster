import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

class MainJFrame extends JFrame {
    HeaderPanel headerPanel;
    public MainJFrame() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Blaha Blaster");
        this.setResizable(false);
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(Slate._950);

        headerPanel = new HeaderPanel();
        this.add(headerPanel, BorderLayout.NORTH);
    }
}

class MainJPanel extends JPanel{
    public MainJPanel() {
        super();
        this.setBackground(Slate._950);
        this.setBorder(BorderFactory.createLineBorder(Slate._950, 25));
    }
}


public class Main {
    public static void main(String[] args) {
        MainJFrame frame = new MainJFrame();

        MainJPanel mainPanel = new MainJPanel();
        frame.add(mainPanel, BorderLayout.CENTER);

        // ********************* Main Menu Screen *************************

        goto_screen_mainMenu(frame, mainPanel);

        // *********************** Game Info Screen **********************

//        goto_screen_gameInfo(frame, mainPanel);




        frame.setVisible(true);
    }

    public static void goto_screen_mainMenu(MainJFrame frame, MainJPanel mainPanel){
        mainPanel.removeAll();

        Screen_MainMenu screen_mainMenu = new Screen_MainMenu();
        screen_mainMenu.btn_newGame.addActionListener(e -> {
            frame.headerPanel.changeHeaderText("New Game");
            // TODO: handle new game screen
        });
        screen_mainMenu.btn_mapsSettings.addActionListener(e -> {
            frame.headerPanel.changeHeaderText("Maps Settings");
            // TODO: handle maps settings screen
        });
        screen_mainMenu.btn_gameInfo.addActionListener(e -> {
            frame.headerPanel.changeHeaderText("Game Info");
            goto_screen_gameInfo(frame, mainPanel);
        });
        screen_mainMenu.btn_exit.addActionListener(e -> frame.dispose());

        mainPanel.add(screen_mainMenu, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void goto_screen_gameInfo(MainJFrame frame, MainJPanel mainPanel){
        mainPanel.removeAll();

        Screen_GameInfo screen_gameInfo = new Screen_GameInfo();
        screen_gameInfo.btn_newGoBack.addActionListener(e -> {
            frame.headerPanel.changeHeaderText("Main Menu");
            goto_screen_mainMenu(frame, mainPanel);
        });

        mainPanel.add(screen_gameInfo, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

}

