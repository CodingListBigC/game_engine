package org.bigacl.renderEngine.gui.menu;

import org.bigacl.renderEngine.gui.font.NanoVGUI;
import org.bigacl.renderEngine.gameManger.logic.IGameLogic;
import org.bigacl.renderEngine.player.PlayerStats;
import org.bigacl.renderEngine.player.level.Level;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.utils.consts.Const;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.awt.*;

public class PlayerStatsGui {
  private final IGameLogic game;
  private final NanoVGUI nanoVGUI;

  public PlayerStatsGui(IGameLogic game, NanoVGUI nanoVGUI) {
    this.game = game;
    this.nanoVGUI = nanoVGUI;
  }

  /*
   * Render Player Stats on top left of the screen
   */
  public void renderStats() {
    PlayerStats stats = ClassConst.iGameLogic.getPlayer().getPlayerStats();

    // Box dimensions
    float boxFromTop = 0;
    float boxWidth = 200.0f;
    int itemCount = 5;
    float itemSize = 20;
    float iconSize = 24;
    float boxHeight = (itemCount * (iconSize + Const.PADDING)) + (Const.PADDING * 4); // Simplified

    // Draw background box
    nanoVGUI.drawRect(0.0f, boxFromTop, boxWidth, boxHeight, 0.0f, 0.0f, 0.0f, 0.5f);

    // TextLimits configuration
    Vector2f startPos = new Vector2f(Const.PADDING, boxFromTop + Const.PADDING + itemSize);
    Color whiteColor = Color.WHITE;

    // Draw items
    nanoVGUI.drawIconWithText("", stats.username(), startPos, 0, iconSize, itemSize, whiteColor);
    nanoVGUI.drawIconWithText("\uF0D6", "Money: " + stats.money(), startPos, 1, iconSize, itemSize, whiteColor);
    nanoVGUI.drawIconWithText("", "Level: " + stats.level().getCurrentLevel(), startPos, 2, iconSize, itemSize, whiteColor);
    nanoVGUI.drawIconWithText("", "XP: " + stats.level().getAmountOfExperience(), startPos, 3, iconSize, itemSize, whiteColor);

    // Render level progress bar
    renderLevelSettings(
            HUDFunctions.hudItemPos(new Vector2f(startPos.x, startPos.y), 4, (int) itemSize),
            new Vector2f(boxWidth - (Const.PADDING * 2), 30),
            stats.level()
    );
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
