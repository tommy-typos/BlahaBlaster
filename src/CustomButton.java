import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class CustomButton extends JButton {
    public CustomButton(String text) {
        super(text, null);
        this.setBorder(null);
        this.setOpaque(true);
        this.setBackground(Slate._900);
        this.setForeground(Slate._300);

        CustomButton btnRef = this;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btnRef.setBackground(Slate._700);
                btnRef.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btnRef.setBackground(Slate._900);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                btnRef.setBackground(Slate._900);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                btnRef.setBackground(Slate._700);
            }
        });
    }
}