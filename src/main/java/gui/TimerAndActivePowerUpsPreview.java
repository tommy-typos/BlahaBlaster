package gui;

import custom.Slate;
import entity.Player;
import entity.effects.Effect;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class TimerAndActivePowerUpsPreview extends JPanel {

  private JLabel timerLabel;
  private ArrayList<JPanel> playerPanels = new ArrayList<>(); // Better control over individual player panels

  public TimerAndActivePowerUpsPreview(Game game) {
    super(new BorderLayout());
    setBackground(Slate._950);
    ArrayList<Player> players = game.getPlayers();
    initializeComponents(players);
  }

  private void initializeComponents(ArrayList<Player> players) {
    timerLabel = new JLabel("Time: 00:00", SwingConstants.LEFT);
    timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
    timerLabel.setForeground(Color.WHITE);
    add(timerLabel, BorderLayout.NORTH);

    JPanel playersPanel = new JPanel(new GridLayout(players.size(), 1));
    playersPanel.setBackground(Slate._950);

    for (Player player : players) {
      JPanel panel = createPlayerPanel(player);
      playersPanel.add(panel);
      playerPanels.add(panel); // Store the panel for dynamic updates
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







//  private void updatePlayerPowerUps(JPanel panel, ArrayList<Effect> powerUps) {
//    // First, remove the old icon panel if it exists
//    if (panel.getComponentCount() > 2) {
//      panel.remove(2); // Assuming the third component is the icon panel
//    }
//
//    // Calculate the number of rows needed (4 icons per row)
//    int rows = (int) Math.ceil(powerUps.size() / 4.0);
//
//    // Create a new panel for icons with grid layout, 4 icons per row
//    JPanel iconPanel = new JPanel(new GridLayout(rows, 4, 0, 0));
//    iconPanel.setBackground(panel.getBackground()); // Match the background color
//
//    for (Effect effect : powerUps) {
//      ImageIcon icon = new ImageIcon(effect.getPowerupIcon().getScaledInstance(36, 36, Image.SCALE_SMOOTH));
//      JLabel label = new JLabel(icon);
//      iconPanel.add(label);
//    }
//
//    // Fill remaining grid cells if there are fewer icons than cells in the last row
//    int remainingCells = (rows * 4) - powerUps.size();
//    for (int i = 0; i < remainingCells; i++) {
//      iconPanel.add(new JLabel()); // Add empty labels to balance the grid if necessary
//    }
//
//    panel.add(iconPanel);
//    panel.revalidate();
//    panel.repaint();
//  }


  public void updateTimer(String time) {
    timerLabel.setText("Time: " + time);
  }
}
