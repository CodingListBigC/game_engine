package org.bigacl.renderEngine.player.level;

public class MainLevel {

  // Data Variables

  public MainLevel() {}

  public int getLevelExperience(int level){
    return 50 * level^2;
  }
  public int amountOfExperienceForNextLevel(int currentLevel) {
    return getLevelExperience(currentLevel + 1);
  }
  public int getExperienceInNextLevel(int currentLevel) {
    int currentLevelAmount = getLevelExperience(currentLevel);
    int nextLevelAmount = getLevelExperience(currentLevel + 1);
    return nextLevelAmount-currentLevelAmount;
  }

  public boolean checkLevelUp(int currentLevel, int amountOfExperience) {
    int nextLevel = currentLevel + 1;
    if (getLevelExperience(nextLevel) <= amountOfExperience){
      return true;
    }
    return false;
  }
}