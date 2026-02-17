package org.bigacl.renderEngine.gui.menu;

import org.bigacl.renderEngine.logic.IGameLogic;
import org.bigacl.renderEngine.player.Player;

public class MasterHud {
  private static MainHud mainHud = null;
  private static DebugMenu debugMenu = null;

  public MasterHud(IGameLogic gameLogic) {
    mainHud = new MainHud(gameLogic);
    debugMenu = new DebugMenu();
  }

  public static void render(Player player) {
    if (debugMenu.getStatus()){
      debugMenu.renderMenu();
    }else{
      mainHud.renderAll(player);
    }
  }
  public void toggleDebug(){
    debugMenu.toggleStatus();
  }
}
