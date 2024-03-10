import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

class GameMap {
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

class MapsController {
    public static GameMap[] readGameMapsFromJson() {
        GameMap[] gameMaps = new GameMap[0];
        // Read from the JSON file
        try (Reader reader = new FileReader("maps.json")) {
            // Convert JSON to List of Game objects
            Gson gson = new Gson();
            gameMaps = gson.fromJson(reader, GameMap[].class);

            return gameMaps;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameMaps;
    }

    public static void saveGameMapsToJson(GameMap[] gameMaps) {
        // Create Gson instance with pretty printing
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Convert the array to JSON
        String json = gson.toJson(gameMaps);

        // Write the JSON to a file
        try (Writer writer = new FileWriter("maps.json")) {
            writer.write(json);
            System.out.println("Data written to JSON successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}