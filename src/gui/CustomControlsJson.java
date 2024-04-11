package gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

class CustomControl {
    public String id;
    public int go_up;
    public int go_left;
    public int go_down;
    public int go_right;
    public int place_bomb;

    public CustomControl(String id, int go_up, int go_left, int go_down, int go_right, int place_bomb){
        this.id = id;
        this.go_up = go_up;
        this.go_left = go_left;
        this.go_down = go_down;
        this.go_right = go_right;
        this.place_bomb = place_bomb;
    }

    @Override
    public String toString() {
        return this.id + ": " + this.go_up + ", " + this.go_left + ", " + this.go_down + ", " + this.go_right + ", " + this.place_bomb;
    }

	public void changeAttrValue(String attr, int value){
		if (attr.equals("go_up")) {
			this.go_up = value;
		} else if (attr.equals("go_left")) {
			this.go_left = value;
		} else if (attr.equals("go_down")) {
			this.go_down = value;
		} else if (attr.equals("go_right")) {
			this.go_right = value;
		} else if (attr.equals("place_bomb")) {
			this.place_bomb = value;
		} 
	}
}


public class CustomControlsJson {
    public static CustomControl[] readControlsFromJson(){
        CustomControl[] customControls = new CustomControl[0];
        // Read from the JSON file
        try (Reader reader = new FileReader("player_controls.json")) {
            // Convert JSON to List of Game objects
            Gson gson = new Gson();
            customControls = gson.fromJson(reader, CustomControl[].class);

            return customControls;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customControls;
    }

	// public static CustomControl getPlayerConntrolById(String playerControlId) {
    //     CustomControl[] playerControls = readControlsFromJson();
    //     for (CustomControl playerControl : playerControls) {
    //         if (playerControl.id.equals(playerControlId)) {
    //             return playerControl;
    //         }
    //     }
    //     return null;
    // }

	public static void saveControlsToJson(CustomControl[] customControls) {
        // Create Gson instance with pretty printing
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Convert the array to JSON
        String json = gson.toJson(customControls);

        // Write the JSON to a file
        try (Writer writer = new FileWriter("player_controls.json")) {
            writer.write(json);
            System.out.println("Data written to JSON successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
