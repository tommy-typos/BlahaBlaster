package gui;

import custom.Slate;
import entity.Player;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class TimerAndActivePowerUpsPreview extends JPanel {

  private JLabel timerLabel;
  private JPanel[] playerPowerUpsPanels; // Panels for each player's power-ups


  //initialize the array of players
  public TimerAndActivePowerUpsPreview(Player[] players) {



    super();
    this.setLayout(new BorderLayout());
    this.setBackground(Slate._950);
    this.initializeComponents(players.length);
  }

  private void initializeComponents(int numberOfPlayers) {
    // Timer setup at the top
    timerLabel = new JLabel("Time: 00:00", SwingConstants.CENTER);
    timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
    timerLabel.setForeground(Color.WHITE);
    this.add(timerLabel, BorderLayout.NORTH);

    // Creating panels for each player's power-ups
    JPanel playersPanel = new JPanel(new GridLayout(numberOfPlayers, 1));
    playersPanel.setBackground(Slate._950);
    this.add(playersPanel, BorderLayout.CENTER);

    playerPowerUpsPanels = new JPanel[numberOfPlayers];
    for (int i = 0; i < numberOfPlayers; i++) {
      playerPowerUpsPanels[i] = createPlayerPanel(i + 1);
      playersPanel.add(playerPowerUpsPanels[i]);
    }
  }

  private JPanel createPlayerPanel(int playerNumber) {
    JPanel panel = new JPanel();
    panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2),
            "Player " + playerNumber + " Power-ups",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 18),
            Color.WHITE));
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(Slate._950);

    // Example power-ups for initialization
    addPowerUpLabel(panel, "Ghost: Active", new ImageIcon("ghost_icon.png"));
    addPowerUpLabel(panel, "Shield: Active", new ImageIcon("shield_icon.png"));
    addPowerUpLabel(panel, "Speed: Active", new ImageIcon("speed_icon.png"));

    return panel;
  }

  private void addPowerUpLabel(JPanel panel, String text, Icon icon) {
    JLabel label = new JLabel(text, icon, SwingConstants.LEFT);
    label.setFont(new Font("Arial", Font.PLAIN, 16));
    label.setForeground(Color.YELLOW);
    panel.add(label);
  }

  public void updateTimer(String time) {
    timerLabel.setText("Time: " + time);
  }

  public void updatePowerUpStatus(int playerIndex, int powerUpIndex, String status, Icon icon) {
    if (playerIndex >= 0 && playerIndex < playerPowerUpsPanels.length) {
      JPanel panel = playerPowerUpsPanels[playerIndex];
      JLabel label = (JLabel) panel.getComponent(powerUpIndex);
      label.setText(status);
      label.setIcon(icon);
    }
  }
}
