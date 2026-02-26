package org.bigacl.renderEngine.gui.menu;

import org.bigacl.renderEngine.gui.drawing.NanoVGUI;
import org.bigacl.renderEngine.gui.menu.debugMenu.DebugMenu;
import org.bigacl.renderEngine.gameManger.logic.IGameLogic;
import org.bigacl.renderEngine.utils.consts.ClassConst;

public class MainGameHud {

  // Status Variables
  private boolean debugMenuStatus = true;

  // Ui Menu Variable
  private final PlayerStatsGui playerStatsGui;
  private final DebugMenu debugMenu = new DebugMenu();

  public MainGameHud() {
    // MainGame Variables Get
    IGameLogic game = ClassConst.iGameLogic;
    NanoVGUI nanoVGUI = ClassConst.nanoVGUI;
    this.playerStatsGui = new PlayerStatsGui(game, nanoVGUI);
  }
  public void toogleDebugMenu(){
    // Flips debugMenuStatus
    debugMenuStatus = !debugMenuStatus;
  }

  public void renderAll(){
    if (debugMenuStatus){
      debugMenu.renderMenu();
      return;
    }
    playerStatsGui.renderStats();
  }
}