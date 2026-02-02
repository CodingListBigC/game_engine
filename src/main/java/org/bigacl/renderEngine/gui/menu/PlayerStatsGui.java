package org.bigacl.renderEngine.gui.menu;

import org.bigacl.renderEngine.MainGame;
import org.bigacl.renderEngine.gui.font.NanoVGUI;
import org.bigacl.renderEngine.player.PlayerStats;
import org.bigacl.renderEngine.player.level.Level;
import org.joml.Vector2f;

public class PlayerStatsGui {
  private final MainGame mainGame;
  private final NanoVGUI nanoVGUI;

  public PlayerStatsGui(MainGame mainGame, NanoVGUI nanoVGUI) {
    this.mainGame = mainGame;
    this.nanoVGUI = nanoVGUI;
  }

  /*
   * Render Player Stats on top left of the screen
   */
  public void renderStats() {
    PlayerStats stats = mainGame.getPlayer().getPlayerStats();
    String playerUsername = stats.getUsername();
    String playerLevel = "Level: " + stats.getLevel().getCurrentLevel();
    String playerExperience = "XP: " + stats.getLevel().getAmountOfExperience();
    String playerMoney = "Money: " + stats.getMoney();

    nanoVGUI.drawRect(0.0f, 0.0f, 150.0f, 230.0f, 0.0f, 0.0f, 0.0f, 0.5f);
    nanoVGUI.drawText(playerUsername, 10.0f, 20.0f, 20, 0.5f, 0.5f, 0.5f);
    nanoVGUI.drawText(playerLevel, 10.0f, 80.0f, 20, 0.5f, 0.5f, 0.5f);
    nanoVGUI.drawText(playerExperience, 10.0f, 110.0f, 20, 0.5f, 0.5f, 0.5f);

    renderLevelSettings(new Vector2f(10.0f, 140.0f), new Vector2f(100.0f, 30), stats.getLevel());
  }

  public void renderLevelSettings(Vector2f startPositon, Vector2f howBig, Level levelStats) {
    int experienceToNextLevel = levelStats.getExperienceToNextLevel();
    int experienceInNextLevel= levelStats.getExperienceInNextLevel();
    if (experienceToNextLevel == -1) {
      return;
    }
    int gap = (int) Math.floor(howBig.y * .25);
    nanoVGUI.drawRect(startPositon.x, startPositon.y, howBig.x, howBig.y, 0.75f, 0.75f, 0.75f, 1.0f);
    startPositon.x += gap;
    startPositon.y += gap;
    howBig.x -= gap * 2;
    howBig.y -= gap * 2;
    nanoVGUI.drawRect(startPositon.x, startPositon.y, howBig.x, howBig.y, 0.5f, 0.5f, 0.5f, 1.0f);
    float percentageInLevel = (float) (experienceInNextLevel - (experienceToNextLevel - levelStats.getAmountOfExperience())) / experienceInNextLevel;
    int pixelsOfExperienceInLevel = (int) (howBig.x - (howBig.x * (1 - percentageInLevel)));
    if (pixelsOfExperienceInLevel == 0) {
      return;
    }
    if (pixelsOfExperienceInLevel > howBig.x) {
      pixelsOfExperienceInLevel = (int) howBig.x;
    }
    nanoVGUI.drawRect(startPositon.x, startPositon.y, pixelsOfExperienceInLevel, howBig.y, 0.0f, 1.0f, 0.0f, 1.0f);
  }
}
