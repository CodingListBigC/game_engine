package org.bigacl.renderEngine.gui.menu.hudMenu;

import org.bigacl.renderEngine.gui.menu.MainGameHud;
import org.bigacl.renderEngine.gui.menu.debugMenu.DebugMenu;

public class MasterGameHud extends HudAbstract {
  @Override
  public void checkHudInputs() {

  }

  private static MainGameHud mainGameHud = null;

  public MasterGameHud() {
    debugMenu = new DebugMenu();
    mainGameHud = new MainGameHud();
  }

  public static void render() {
    if (debugStatus){
      debugMenu.renderMenu();
    }else{
      mainGameHud.renderAll();
    }
  }
}
