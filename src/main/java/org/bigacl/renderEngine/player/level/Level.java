package org.bigacl.renderEngine.player.level;

import com.google.gson.Gson;
import org.bigacl.renderEngine.Launcher;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

public class Level {
  private int amountOfExperience = 0;
  private int currentLevel = 1;
  private final MainLevel mainLevel;

  public Level(MainLevel mainLevel) {
    this.mainLevel = Launcher.getMainLevel();
  }

  public int getAmountOfExperience() {
    return amountOfExperience;
  }

  public void addAmountOfExperience(int amountOfExperience) {
    this.amountOfExperience += amountOfExperience;
    if (mainLevel.checkLevelUp(currentLevel,amountOfExperience)){
      currentLevel++;
    }
  }

  public int getCurrentLevel() {
    return currentLevel;
  }
}