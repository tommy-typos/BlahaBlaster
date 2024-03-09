import javax.swing.*;
import java.awt.*;

class Screen_MainMenu extends JPanel {
    CustomButton btn_newGame;
    CustomButton btn_mapsSettings;
    CustomButton btn_gameInfo;
    CustomButton btn_exit;

    public Screen_MainMenu(){
        super();
        this.setLayout(new GridLayout(1, 2, 25, 25));
        this.setBackground(Slate._950);

        JPanel mainMenuSidebar = new JPanel();
        mainMenuSidebar.setLayout(new GridLayout(4, 1, 25, 25));
        mainMenuSidebar.setBackground(Slate._950);
        mainMenuSidebar.setBorder(BorderFactory.createLineBorder(Slate._950, 25));

        btn_newGame = new CustomButton("New Game ->");
        btn_newGame.setPreferredSize(new Dimension(100, 100));

        btn_mapsSettings = new CustomButton("Maps Settings");
        btn_mapsSettings.setPreferredSize(new Dimension(100, 100));

        btn_gameInfo = new CustomButton("Game Info");
        btn_gameInfo.setPreferredSize(new Dimension(100, 100));

        btn_exit = new CustomButton("Exit");
        btn_exit.setPreferredSize(new Dimension(100, 100));

        mainMenuSidebar.add(btn_newGame);
        mainMenuSidebar.add(btn_mapsSettings);
        mainMenuSidebar.add(btn_gameInfo);
        mainMenuSidebar.add(btn_exit);

        this.add(mainMenuSidebar);

        JLabel blahaText = new JLabel("Blaha Blaster");
        blahaText.setOpaque(true);
        blahaText.setForeground(Slate._300);
        blahaText.setBackground(Slate._950);
        blahaText.setPreferredSize(new Dimension(450, 100));
        blahaText.setHorizontalAlignment(SwingConstants.CENTER);
        blahaText.setFont(new Font("Bradley Hand", Font.PLAIN, 50));

        this.add(blahaText);
    }
}