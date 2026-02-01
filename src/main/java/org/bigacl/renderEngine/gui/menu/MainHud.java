package org.bigacl.renderEngine.gui.menu;

import org.bigacl.renderEngine.MainGame;
import org.bigacl.renderEngine.gui.font.NanoVGUI;

public class MainHud {
  // MainGame Variables Get
  private final MainGame mainGame;
  private final NanoVGUI nanoVGUI;

  // Status Variables
  private boolean debugMenuStatus = false;

  // Ui Menu Varaible
  private final PlayerStatsGui playerStatsGui;

  public MainHud(MainGame mainGame, NanoVGUI nanoVGUI) {
    this.mainGame = mainGame;
    this.nanoVGUI = nanoVGUI;
    this.playerStatsGui = new PlayerStatsGui(this.mainGame, this.nanoVGUI);
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