package org.bigacl.renderEngine.gui.menu.hudMenu;


import org.bigacl.renderEngine.gui.menu.debugMenu.DebugMenu;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector4f;

import java.awt.*;

public abstract class HudAbstract {
  protected static DebugMenu debugMenu = null;
  protected static boolean debugStatus = false;

  protected static Color mainBackgroundColor = new Color(.1f,.1f,.1f,.5f);
  protected float screenWidth = ClassConst.window.getWidth();
  protected float screenHeight= ClassConst.window.getHeight();

  public void render() {
    if (debugStatus) {
      renderDebug();
    } else {
      renderMenu();
    }
  }

  public static void renderDebug() {
    if (debugMenu != null) {
      debugMenu.renderMenu();
    }
  }

  public void toggleDebugStatus() {
    debugStatus = !debugStatus;
  }


  public abstract void checkHudInputs();



  public abstract void renderMenu();

  public boolean getDebugStatus() {
    return debugStatus;
  }
}