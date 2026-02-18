package org.bigacl.renderEngine.player.level;

import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.test.launcher.MainLauncher;

public class Level {
  private int amountOfExperience = 0;
  private int currentLevel = 0;
  private final MainLevel mainLevel;

  public Level() {
    this.mainLevel = ClassConst.mainLevel;
  }

  public int getAmountOfExperience() {
    return amountOfExperience;
  }

  public void addAmountOfExperience(int amountOfExperience) {
    this.amountOfExperience += amountOfExperience;
    if (mainLevel.checkLevelUp(currentLevel,this.amountOfExperience)){
      currentLevel++;
    }
  }

  public int getCurrentLevel() {
    return currentLevel;
  }

  /**
   * To get the next level experience
   * @return -1 if no more level or does not exist
   */
  public int getAmountOfExperienceForNextLevel() {
    return mainLevel.amountOfExperienceForNextLevel(currentLevel);
  }



  public int getExperienceToNextLevel() {
    return mainLevel.amountOfExperienceForNextLevel(currentLevel);
  }

  /**
   * Function to experience in current level to next level
   * @return -1 if not more level or does not exist
   */
  public int getExperienceInNextLevel() {
    return mainLevel.getExperienceInNextLevel(currentLevel);
  }
}