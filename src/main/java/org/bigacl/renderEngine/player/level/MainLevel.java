package org.bigacl.renderEngine.player.level;

import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

public class MainLevel {
  private final String folderPath = "gameData/player";
  private final String jsonName = "level.json";

  // Data Variables
  private int number_of_levels;
  private Map<String, LevelInfo> levels;

  public MainLevel() {
    loadData();
  }

  // This is the "Container" that Gson will use
  private static class LevelDataContainer {
    int number_of_levels;
    Map<String, LevelInfo> levels;
  }

  private void loadData() {
    String jsonFilePath = folderPath + "/" + jsonName;
    try (InputStream in = getClass().getClassLoader().getResourceAsStream(jsonFilePath)) {
      if (in == null) return;

      Gson gson = new Gson();
      Reader reader = new InputStreamReader(in);

      // BREAK THE LOOP: Load into the Container, NOT MainLevel
      LevelDataContainer data = gson.fromJson(reader, LevelDataContainer.class);

      if (data != null) {
        this.number_of_levels = data.number_of_levels;
        this.levels = data.levels;
        System.out.println("Loaded " + this.number_of_levels + " player levels.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static class LevelInfo {
    public int amount_of_xp;
    public Map<String, ItemLevel> itemAvailable;
  }

  public static class ItemLevel {
    public int level;
  }

  public boolean checkLevelUp(int currentLevel, int amountOfExperience) {
    String nextLevelKey = String.valueOf(currentLevel + 1);
    if (levels != null && levels.containsKey(nextLevelKey)) {
      return amountOfExperience >= levels.get(nextLevelKey).amount_of_xp;
    }
    return false;
  }
}