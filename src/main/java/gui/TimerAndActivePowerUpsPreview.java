package gui;

import custom.Slate;
import entity.Player;
import entity.effects.Effect;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TimerAndActivePowerUpsPreview extends JPanel {

  Game game;
  private JLabel timerLabel;
  private ArrayList<JPanel> playerPanels = new ArrayList<>();
  private Timer timer;

  public TimerAndActivePowerUpsPreview(Game game) {
    super(new BorderLayout());
    this.game = game;
    setBackground(Slate._950);
    ArrayList<Player> players = game.getPlayers();
    initializeComponents(players);
    startTimer();
  }

  private void initializeComponents(ArrayList<Player> players) {
    if (game == null) {
      throw new IllegalStateException("Game object must be initialized before initializing components.");
    }

    long elapsed = System.currentTimeMillis() - game.getStartTime();
    long timeLeft = Math.max(game.gameDuration - elapsed, 0); // Ensure timeLeft is not negative

    timerLabel = new JLabel(formatTime(timeLeft), SwingConstants.LEFT);
    timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
    timerLabel.setForeground(Color.WHITE);
    add(timerLabel, BorderLayout.NORTH);

    JPanel playersPanel = new JPanel(new GridLayout(players.size(), 1));
    playersPanel.setBackground(Slate._950);

    for (Player player : players) {
      JPanel panel = createPlayerPanel(player);
      playersPanel.add(panel);
      playerPanels.add(panel); // Store the panel for dynamic updates


      player.addDeathListener(deceasedPlayer -> {
        SwingUtilities.invokeLater(() -> removePlayerPanel(panel));
      });

      if (!player.isAlive) {
        removePlayerPanel(panel);
      }


    }

    add(playersPanel, BorderLayout.LINE_START);
  }
  private JPanel createPlayerPanel(Player player) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(Slate._950);
    panel.setBorder(new EmptyBorder(10, 5, 10, 5));

    JLabel nameLabel = new JLabel(player.name.toUpperCase(), SwingConstants.LEFT);
    nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
    nameLabel.setForeground(Color.WHITE);
    nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(nameLabel);

    // separator
    JSeparator separator = new JSeparator();
    separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
    separator.setForeground(Color.GRAY);
    panel.add(separator);


    player.addPowerUpChangeListener(newPowerUps -> {
      SwingUtilities.invokeLater(() -> updatePlayerPowerUps(panel, newPowerUps));
    });

    player.addDeathListener(deceasedPlayer -> {
      SwingUtilities.invokeLater(() -> removePlayerPanel(panel));
    });
//    player.removePowerUpChangeListener(removedPowerUps -> {
//      SwingUtilities.invokeLater(() -> updatePlayerPowerUps(panel, removedPowerUps));
//    });

    JLabel speedLabel = addStatusLabel(panel, "SPEED", player.speed);
    JLabel blastRangeLabel = addStatusLabel(panel, "BLAST RANGE", player.blastRange);
    JLabel bombsNumLabel = addStatusLabel(panel, "SLOT", player.bombsNum);

    player.addAttributeChangeListener(() -> {
      SwingUtilities.invokeLater(() -> {
        speedLabel.setText("SPEED: " + player.speed);
        blastRangeLabel.setText("BLAST RANGE: " + player.blastRange);
        bombsNumLabel.setText("SLOT: " + player.bombsNum);
      });
    });

    return panel;
  }


  private void removePlayerPanel(JPanel panel) {
    // Remove panel from UI
    playerPanels.remove(panel);
    this.remove(panel);
    this.revalidate();
    this.repaint();
  }

  private JLabel addStatusLabel(JPanel panel, String attribute, int value) {
    JLabel label = new JLabel(attribute + ": " + value, SwingConstants.LEFT);
    label.setFont(new Font("Arial", Font.PLAIN, 12));
    label.setForeground(Color.LIGHT_GRAY);
    panel.add(label);
    return label;
  }

  // Dynamically update power-ups displayed for a player

  private void updatePlayerPowerUps(JPanel panel, ArrayList<Effect> powerUps) {
    SwingUtilities.invokeLater(() -> {
      // Locate the existing icon panel to update or create a new one if it doesn't exist
      JPanel iconPanel = null;
      if (panel.getComponentCount() > 2) {
        Component possibleIconPanel = panel.getComponent(2);
        if (possibleIconPanel instanceof JPanel) {
          iconPanel = (JPanel) possibleIconPanel;
        }
      }

      // Calculate the number of rows needed based on the number of powerups, allowing up to 4 icons per row
      int rows = (int) Math.ceil(powerUps.size() / 4.0);

      // If no existing icon panel found, create a new one with the appropriate layout
      if (iconPanel == null) {
        iconPanel = new JPanel(new GridLayout(rows, 4, 5, 5)); // 5 pixels padding for better spacing
        iconPanel.setBackground(panel.getBackground());
        if (panel.getComponentCount() > 2) {
          panel.add(iconPanel, 2); // Add in the correct position
        } else {
          panel.add(iconPanel);
        }
      } else {
        iconPanel.removeAll(); // Clear existing icons for refresh
        iconPanel.setLayout(new GridLayout(rows, 4, 5, 5)); // Reset the grid layout with updated rows
      }

      // Populate the icon panel with new icons
      for (int i = 0; i < powerUps.size(); i++) {
        Effect effect = powerUps.get(i);
        ImageIcon icon = new ImageIcon(effect.getPowerupIcon().getScaledInstance(36, 36, Image.SCALE_SMOOTH));
        JLabel label = new JLabel(icon);
        iconPanel.add(label);
      }

      // If there are fewer icons than the capacity of the last row, fill in the gaps with empty labels
      int totalCells = rows * 4;
      for (int i = powerUps.size(); i < totalCells; i++) {
        iconPanel.add(new JLabel()); // Add empty labels to maintain grid structure
      }

      panel.revalidate();
      panel.repaint();
    });
  }

  public void resetTimer() {
    // Stop the current timer if running
    if (timer != null) {
      timer.stop();
    }
    startTimer(); // Restart the timer with updated game start time
  }

  private void startTimer() {
    int delay = 1000; // milliseconds
    ActionListener taskPerformer = new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        long elapsed = System.currentTimeMillis() - game.getStartTime(); // Use updated start time
        long timeLeft = game.gameDuration - elapsed;
        if (timeLeft < 0) {
          timeLeft = 0; // Prevent negative display
          ((Timer) evt.getSource()).stop(); // Stop timer when time runs out
        }
        updateTimer(formatTime(timeLeft));
      }
    };
    timer = new Timer(delay, taskPerformer);
    timer.start();
  }

  private String formatTime(long millis) {
    long seconds = (millis / 1000) % 60;
    long minutes = (millis / (1000 * 60)) % 60;
    long hours = (millis / (1000 * 60 * 60)) % 24;
    // show hours only if it's greater than 0
    if (hours > 0) {
      return String.format("Time: %02d:%02d:%02d", hours, minutes, seconds);
    } else {
      return String.format("Time: %02d:%02d", minutes, seconds);
    }

  }

//  public void resetTimer() {
//    startTimer(); // Restart the timer
//  }


  public void updateTimer(String time) {
    timerLabel.setText("Time: " + time);
  }
}
