package org.bigacl.renderEngine.gui.menu;

import org.bigacl.renderEngine.MainGame;
import org.bigacl.renderEngine.gui.font.NanoVGUI;
import org.bigacl.renderEngine.player.PlayerStats;

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
  public void renderStats(){
    PlayerStats stats = mainGame.getPlayer().getPlayerStats();
    String playerUsername = stats.getUsername();
    String playerLevel = "Level: "+ stats.getLevel().getCurrentLevel();
    String playerExperience = "XP: " + stats.getLevel().getAmountOfExperience();
    String playerMoney = "Money: " + stats.getMoney();

    nanoVGUI.drawRect(0.0f,0.0f,150.0f,150.0f,0.0f,0.0f,0.0f,0.5f);
    nanoVGUI.drawText(playerUsername, 10.0f, 20.0f, 20,0.5f,0.5f,0.5f);
    nanoVGUI.drawText(playerLevel, 10.0f, 50.0f, 20,0.5f,0.5f,0.5f);
    nanoVGUI.drawText(playerExperience, 10.0f, 80.0f, 20,0.5f,0.5f,0.5f);
    nanoVGUI.drawText(playerMoney, 10.0f, 110.0f, 20,0.5f,0.5f,0.5f);
  }
}
