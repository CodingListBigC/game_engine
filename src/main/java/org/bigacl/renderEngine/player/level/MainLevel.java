package org.bigacl.renderEngine.player.level;

import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;

public class MainLevel {
  // File and folder paths
  private final String folderPath = "gameData/player";
  private final String jsonName = "level.json";

  // Data Variables - These now match the JSON keys exactly
  private int number_of_levels;
  private Map<String, LevelInfo> levels;


  public MainLevel() {
    loadData();
  }


  private void loadData() {
    String jsonFilePath = folderPath + "/" + jsonName;
    try (InputStream in = getClass().getClassLoader().getResourceAsStream(jsonFilePath)) {
      if (in == null) {
        System.err.println("File not found: " + jsonFilePath);
        return;
      }

      Gson gson = new Gson();
      Reader reader = new InputStreamReader(in);
      MainLevel data = gson.fromJson(reader, MainLevel.class);

      if (data != null) {
        this.number_of_levels = data.number_of_levels;
        this.levels = data.levels;
        System.out.println("Loaded " + this.number_of_levels + " player levels.");

        // Safety: Check if the loaded XP immediately triggers a level up
      }
    } catch (Exception e) {
      System.err.println("Error loading Level JSON: " + e.getMessage());
      e.printStackTrace();
    }
  }

  // Helper classes for GSON to map the nested JSON structure
  public static class LevelInfo {
    public int amount_of_xp;
    public Map<String, ItemLevel> itemAvailable;
  }

  public static class ItemLevel {
    public int level;
  }


  public boolean checkLevelUp(int currentLevel, int amountOfExperience) {
    String nextLevelKey = String.valueOf(currentLevel + 1);
    if (levels.containsKey(nextLevelKey)) {
      if (amountOfExperience >= levels.get(nextLevelKey).amount_of_xp) {
        currentLevel++;
        return true;
      }
    }
    return false;
  }
}