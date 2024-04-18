package gui;

import java.awt.*;

public class UI {
    Game game;
    Graphics2D g2d;
    Font arial_40, arial_80B;
    public int commandNum = 0;

    public UI(Game game){
        this.game = game;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
    }

    public void draw (Graphics2D g2d){
        this.g2d = g2d;
        g2d.setFont(arial_40);
        g2d.setColor(Color.WHITE);

        if(game.gameState == game.pauseState){
            drawPauseScreen();
        }
        if(game.gameState == game.gameOverState){
            drawGameOverScreen();
        }
    }

    private void drawGameOverScreen() {
        g2d.setColor(new Color(0, 0, 0, 200));
        g2d.fillRect(0, 0, game.screenWidth, game.screenHeight);

        int x;
        int y;
        String text;
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 90f));


        text = "Game Over";
        g2d.setColor(Color.BLACK);
        x = getXforCenteredText(text);
        y = game.tileSize*4;
        g2d.drawString(text, x, y);

        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x-4, y-4);

        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 50f));


        if(game.players.size() == 1){
            text = game.players.get(0).name;
        }else{
            text = "Draw";
        }
        x = getXforCenteredText(text);
        y = game.tileSize*6;
        g2d.drawString(text, x, y);

        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 40f));

        text = "Retry";
        x = getXforCenteredText(text) - 3*game.tileSize;
        y = game.tileSize*8;
        g2d.drawString(text, x, y);
        if(commandNum == 0){
            g2d.drawString(">", x-game.tileSize, y);
            if(game.keyHandler.enterPressed){
                game.restart();
                game.keyHandler.enterPressed = false;
            }
        }

        text = "Exit";
        y = game.tileSize*10;
        g2d.drawString(text, x, y);
        if(commandNum == 1){
            g2d.drawString(">", x-game.tileSize, y);
            if(game.keyHandler.enterPressed){
                game.keyHandler.enterPressed = false;
                game.screenNavigator.goto_screen_mainMenu();
            }
        }
    }

    public void drawPauseScreen(){
        g2d.setColor(Color.WHITE);
        g2d.setFont(g2d.getFont().deriveFont(50F));

        int frameX = game.tileSize*6;
        int frameY = game.tileSize;
        int frameWidth = game.tileSize*8;
        int frameHeight = game.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        optionsTop(frameX, frameY);

        game.keyHandler.enterPressed = false;
    }

    public void optionsTop(int frameX, int frameY){

        int textX;
        int textY;

        // Paused
        String text = "Paused";
        textX = getXforCenteredText(text);
        textY = frameY + game.tileSize;
        g2d.drawString(text, textX, textY);

        g2d.setFont(g2d.getFont().deriveFont(30F));
        textX = frameX + game.tileSize;
        textY += game.tileSize * 2;
        g2d.drawString("Resume", textX, textY);
        if(commandNum == 0){
            g2d.drawString(">", textX-25, textY);
            if(game.keyHandler.enterPressed){
                game.gameState = game.playState;
                game.keyHandler.enterPressed = false;
            }
        }

        textX = frameX + game.tileSize;
        textY += game.tileSize;
        g2d.drawString("Restart", textX, textY);
        if(commandNum == 1){
            g2d.drawString(">", textX-25, textY);
            if(game.keyHandler.enterPressed){
                game.keyHandler.enterPressed = false;
                game.restart();
            }
        }

        textX = frameX + game.tileSize;
        textY += game.tileSize;
        g2d.drawString("Exit", textX, textY);
        if(commandNum == 2){
            g2d.drawString(">", textX-25, textY);
            if(game.keyHandler.enterPressed){
                game.keyHandler.enterPressed = false;
                game.screenNavigator.goto_screen_mainMenu();
            }
        }

    }

    private int getXforCenteredText(String text) {
        int length = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        return (int) (game.screenWidth/1.3 - length/2);
    }

    public void drawSubWindow(int x, int y, int width, int height){
        g2d.setColor(new Color(0, 0, 0, 200));
        g2d.fillRoundRect(x, y, width, height, 35, 35);
        g2d.setColor(Color.white);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRoundRect(x, y, width, height, 35, 35);
    }
}
