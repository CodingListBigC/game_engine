package org.bigacl.renderEngine.gui.menu;

import org.bigacl.renderEngine.MainGame;
import org.bigacl.renderEngine.gui.font.NanoVGUI;
import org.bigacl.renderEngine.logic.IGameLogic;
import org.bigacl.renderEngine.player.Player;
import org.bigacl.renderEngine.utils.consts.ClassConst;

public class MainHud {
  // MainGame Variables Get
  private final IGameLogic game;
  private final NanoVGUI nanoVGUI;

  // Status Variables
  private boolean debugMenuStatus = true;

  // Ui Menu Varaible
  private final PlayerStatsGui playerStatsGui;
  private final DebugMenu debugMenu = new DebugMenu();

  public MainHud(IGameLogic thisGame) {
    this.game = thisGame;
    this.nanoVGUI = ClassConst.nanoVGUI;
    this.playerStatsGui = new PlayerStatsGui(this.game, this.nanoVGUI);
  }
  public void toogleDebugMenu(){
    // Flips debugMenuStatus
    debugMenuStatus = !debugMenuStatus;
  }

  public void renderAll(Player player){
    if (debugMenuStatus){
      debugMenu.renderMenu();
      return;
    }
    playerStatsGui.renderStats(player);
  }
}