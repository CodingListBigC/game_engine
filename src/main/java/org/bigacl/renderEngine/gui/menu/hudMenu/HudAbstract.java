package org.bigacl.renderEngine.gui.menu.hudMenu;


import org.bigacl.renderEngine.gui.menu.debugMenu.DebugMenu;

public abstract class HudAbstract {
  protected static DebugMenu debugMenu = null;
  protected static boolean debugStatus = false;

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

  protected static void renderHud() {
  }

  public void renderAll() {
  }
  public abstract void checkHudInputs();
}