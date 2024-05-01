package main.java.entity;

public class GameMap {
    public String id;
    public String name;
    public String description;
    public String difficulty; // Easy, Medium, Hard
    public int[] mapDimensions;
    public String[][] mapCells; // grass, box, wall

    public GameMap(String id, String name, String description, String difficulty,
                   int[] mapDimensions, String[][] mapCells) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.mapDimensions = mapDimensions;
        this.mapCells = mapCells;
    }

    @Override
    public String toString() {
        return name;
    }
}
