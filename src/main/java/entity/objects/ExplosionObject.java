package main.java.entity.objects;

import main.java.entity.Player;
import main.java.entity.Point;
import main.java.gui.Game;

import javax.swing.*;

public class ExplosionObject {

    public Point position;
    public Game game;
    public Player player;
    public ImageIcon image;
    public ExplosionObject(Point position, Game game){
        this.position = position;
        this.game = game;
        try{
            this.image = new ImageIcon(getClass().getResource("/resources/tiles/explosion.gif"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
