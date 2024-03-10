import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class CustomButton extends JButton {
    String btnType = "";
    public void setBtnType(String btnType){
        this.btnType = btnType;

        if (this.btnType.equals("red_button")){
            this.setBackground(TwRed._900);
            this.addListeners();
        }
    }
    public CustomButton(String text) {
        super(text, null);
        this.setBorder(null);
        this.setOpaque(true);
        this.setBackground(Slate._900);
        this.setForeground(Slate._300);
        this.addListeners();
    }

    private void addListeners(){
        CustomButton btnRef = this;
        Color color900 = Slate._900;
        Color color700 = Slate._700;

        if (this.btnType.equals("red_button")){
            color900 = TwRed._900;
            color700 = TwRed._700;
        }

        Color finalColor70 = color700;
        Color finalColor90 = color900;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btnRef.setBackground(finalColor70);
                btnRef.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btnRef.setBackground(finalColor90);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                btnRef.setBackground(finalColor90);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                btnRef.setBackground(finalColor70);
            }
        });
    }
}