package gui;

import java.awt.*;
import javax.swing.*;

class MapPreviewWrapper extends JPanel {
  public MapPreviewWrapper() {
    super();
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
  }

  public void setMapPreview(MapPreview mapPreview) {
    this.removeAll();
    this.add(mapPreview);
    this.revalidate();
    this.repaint();
  }
}
