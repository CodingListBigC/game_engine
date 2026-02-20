package org.bigacl.renderEngine.gui.menu.hudMenu;

import org.bigacl.renderEngine.gui.font.NanoVGUI;
import org.bigacl.renderEngine.gui.menu.debugMenu.DebugMenu;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class ModelDesignHud extends HudAbstract {

  Vector4f mainBackgroundColor = new Vector4f(0.5f, 0.5f, 0.5f, 0.25f);

  public ModelDesignHud() {
    debugMenu = new DebugMenu();
  }

  @Override
  public void renderAll() {
    leftMenu();
    //rightMenu();
  }

  public void leftMenu() {
    NanoVGUI nanoVGUI = ClassConst.nanoVGUI;
    drawSide(false, nanoVGUI, 0.25f, 0.75f);
  }

  public void rightMenu() {
    NanoVGUI nanoVGUI = ClassConst.nanoVGUI;
    drawSide(true, nanoVGUI,0.25f, 0.75f);
  }

  public void drawSide(boolean isRightSide, NanoVGUI vgui, float widthPercentage, float heightPercentage) {
    float screenWidth = ClassConst.window.getWidth();
    float screenHeight = ClassConst.window.getHeight();

    // This is how wide the actual panel is
    float panelWidth = screenWidth * widthPercentage;
    float panelHeight = screenHeight * heightPercentage;
    float startX = 0;

    if (isRightSide) {
      // Move the starting point to the far right, minus the panel width
      startX = screenWidth - panelWidth;
    }

    // Position: (startX, 0)
    // Size: (panelWidth, screenHeight)
    Vector2f pos = new Vector2f(startX, 0.0f);
    Vector2f size = new Vector2f(panelWidth, panelHeight);

    vgui.drawRect(pos, size, mainBackgroundColor);
  }
}
