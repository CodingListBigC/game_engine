package org.bigacl.renderEngine.gui.menu.hudMenu;


import org.bigacl.renderEngine.gui.menu.debugMenu.DebugMenu;

public abstract class HudAbstract {
  public static DebugMenu debugMenu = null;
  public static boolean debugStatus = false;

  public HudAbstract(DebugMenu debugMenu) {
    HudAbstract.debugMenu = debugMenu;
  }

  public static void render(){
    if (debugStatus){
      renderDebug();
    }else{
      renderHud();
    }
  }
  public static void renderDebug(){
    if (debugMenu != null){
      debugMenu.renderMenu();
    }
  }

  public void toogleDebugStatus(){
    debugStatus = !debugStatus;
  }

  public static void renderHud() {
  }
}