package org.bigacl.renderEngine.gui.menu.hudMenu;

import org.bigacl.renderEngine.gui.menu.MainHud;
import org.bigacl.renderEngine.gui.menu.debugMenu.DebugMenu;

public class MasterGameHud extends HudAbstract {
  private static MainHud mainHud = null;
  private static final DebugMenu debugMenu = null;

  public MasterGameHud() {
    super(new DebugMenu());
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
