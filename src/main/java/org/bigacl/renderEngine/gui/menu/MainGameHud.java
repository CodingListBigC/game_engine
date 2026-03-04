package org.bigacl.renderEngine.gui.menu;

import org.bigacl.renderEngine.gui.menu.debugMenu.DebugMenu;

public class MainGameHud {

  // Status Variables
  private boolean debugMenuStatus = true;

  // Ui Menu Variable
  private final PlayerStatsGui playerStatsGui;
  private final DebugMenu debugMenu = new DebugMenu();

  public MainGameHud() {
    this.playerStatsGui = new PlayerStatsGui();
  }

  public void renderAll(){
    if (debugMenuStatus){
      debugMenu.renderMenu();
      return;
    }
    playerStatsGui.renderStats();
  }
}