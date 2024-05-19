### **1. In Main Menu**

| **I want to** | **Given** | **When** | **Then** |
|---------------|-----------|----------|----------|
| Start a new game | I'm at the main menu | I select "New game" | I see the new game menu |
| See game info | I'm at the main menu | I select "Game info" | I see the game info window |
| See map settings | I'm at the main menu | I select "Maps Settings" | I see the maps settings |
| Exit the game | I'm at the main menu | I select "Exit" | The game closes |

### **2. In the New Game Menu**

| **I want to** | **Given** | **When** | **Then** |
|---------------|-----------|----------|----------|
| Input players' names | In the New game menu | I fill in the "Player" fields | Player names are recorder |
| Select a map | In the New game menu | I choose a map from the map selection | The chosen map is set for the new game |
| Enable intelligent monsters | In the New game menu | I check "Intelligent Monsters" | Intelligent monsters are enabled |
| Enable advanced powerups | In the New game menu | I check "Advanced powerups" | Advanced powerups are enabled |
| Enable hindering cursor | In the New game menu | I check "Hindering cursor" | Hindering cursor is enabled |
| Exit the menu | In the New game menu | I click "Exit" | I return to the main menu |
| Start playing | In the New game menu | I click "Play" | The game starts with selected settings |

### **3. In-Game Menu**

| **I want to** | **Given** | **When** | **Then** |
|---------------|-----------|----------|----------|
| Pause the game | I'm in a game | I press the pause button | The pause menu appears |
| View my info | I'm in a game | I look at the screen | I see my username and status at the top left |
| View opponent's info | I'm in a game | I look at the screen | I see their username and status at the top right |
| Move | I'm in a game | I press the specific button for my player to move (on the side of the screen) | Player moves |
| Drop a bomb | I'm in a game and bomb is placeable in my position | I press the specific button for my player to place a bomb (on the side of the screen) | A bomb appears on the current position |
| Pick up a Power-Up/Curse | I'm in a game and a Power-Up/Curse is available (visible) | I move to the position where Power-Up/Curse is and step on it | I get the effect of a Power-Up/Curse |
| Blow a box | I'm in a game and the box is within the blast range of a bomb | I place a bomb next to the box | The bomb blows up and will be replaced by Grass area OR Power-Up/Curse will appear instead of the blown-up box |
| Detonate a bomb | I'm in a game and I have the Detonator Power-Up  | I place the bombs within Placeable number of Bombs limit | I detonate the placed bombs when I press the 'Place bomb" key |
| Pass through the wall | I'm in a game and I have the Ghost Power-Up active | I move towards a wall/box | I pass through wall/box without being hindered |

### **4. In the Pause Menu**

| **I want to** | **Given** | **When** | **Then** |
|---------------|-----------|----------|----------|
| Resume the game | In pause menu | I select "Resume" | The game resumes |
| Restart the game | In pause menu | I select "Restart" | The game restarts |
| See game info | In pause menu | I select "Game info" | The game info window appears |
| Exit to the main menu | In pause menu | I select "Exit" | I return to the main menu |

### **5. In Maps Settings**

| **I want to** | **Given** | **When** | **Then** |
|---------------|-----------|----------|----------|
| Create a new map | In Maps menu | I select "Create new" | I am taken to a map editing tool with an empty map |
| Edit an existing map | In Maps menu | I select "Edit" next to a map | I am taken to a map editing tool with a selected map |
| Remove an existing map | In Maps menu | I select "Remove" next to a map | The selected map is deleted |
| Return to the main menu | In Maps menu | I click "Return" | I am taken back to the main menu |

### 6\. Map Editing Menu

| **I want to** | **Given** | **When** | **Then** |
|---------------|-----------|----------|----------|
| Save current map | In the Map Editing Menu | I select "Save" | I am taken back to the "Map settings" menu |
| Change or set the map dimensions | In the Map Editing Menu | I change the values of the dimensions | Maps transforms according to the given dimensions |
| Set a material in the map | In the Map Editing Menu | I select the material and click on the cells I want to place them in | The pressed cells become a chosen material |

### **7. In Game Over Window**

| **I want to** | **Given** | **When** | **Then** |
|---------------|-----------|----------|----------|
| See game results | In the Game Over window | The game has ended | I see the result and the winning user |
| Restart the game | In the Game Over window | I wish to play again | I can start a new game immediately |
| Exit to the main menu | In the Game Over window | I want to stop playing | I return to the main menu |

### **8. In the Game Info Window**

<table>
<tr>
<th>

**I want to**
</th>
<th>

**Given**
</th>
<th>

**When**
</th>
<th>

**Then**
</th>
</tr>
<tr>
<td>Understand game</td>
<td>In the Game info window</td>
<td>I look for control info</td>
<td>I learn the specifics of the game</td>
</tr>
<tr>
<td>

Go back to

Pause Menu
</td>
<td>In the Game info window</td>
<td>I press "Back" button</td>
<td>I get back to the Pause Menu</td>
</tr>
</table>

