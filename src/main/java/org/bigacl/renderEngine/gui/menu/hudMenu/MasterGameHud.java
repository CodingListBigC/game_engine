package org.bigacl.renderEngine.gui.menu.hudMenu;

import org.bigacl.renderEngine.gui.menu.MainHud;
import org.bigacl.renderEngine.gui.menu.debugMenu.DebugMenu;

public class MasterGameHud extends HudAbstract {
  private static MainHud mainHud = null;

  public MasterGameHud() {
    debugMenu = new DebugMenu();
    mainHud = new MainHud();
  }

  public static void render() {
    if (debugStatus){
      debugMenu.renderMenu();
    }else{
      mainHud.renderAll();
    }
  }
}
