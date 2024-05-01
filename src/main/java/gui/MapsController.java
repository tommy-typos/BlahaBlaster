package main.java.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.entity.GameMap;

import java.io.*;

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

    public static GameMap getMapById(String mapId) {
        GameMap[] gameMaps = readGameMapsFromJson();
        for (GameMap gameMap : gameMaps) {
            if (gameMap.id.equals(mapId)) {
                return gameMap;
            }
        }
        return null;
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