package org.bigacl.renderEngine.gui.menu;

import org.bigacl.renderEngine.GameLogic;
import org.bigacl.renderEngine.gui.font.NanoVGUI;

public class MainHud {
  // GameLogic Variables Get
  private final GameLogic gameLogic;
  private final NanoVGUI nanoVGUI;

  // Status Variables
  private boolean debugMenuStatus = false;

  // Ui Menu Varaible
  private final PlayerStatsGui playerStatsGui;

  public MainHud(GameLogic gameLogic, NanoVGUI nanoVGUI) {
    this.gameLogic = gameLogic;
    this.nanoVGUI = nanoVGUI;
    this.playerStatsGui = new PlayerStatsGui(this.gameLogic, this.nanoVGUI);
  }
  public void toogleDebugMenu(){
    // Flips debugMenuStatus
    debugMenuStatus = !debugMenuStatus;
  }

  public void renderAll(){
    if (debugMenuStatus){
      // TODO: Add debug Menu here
      return;
    }
    playerStatsGui.renderStats();
  }
}