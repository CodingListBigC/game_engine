package org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign;

import org.bigacl.renderEngine.gui.drawing.NanoVGUI;
import org.bigacl.renderEngine.gui.menu.hudMenu.HudAbstract;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2d;
import org.joml.Vector2f;

public abstract class ModelDesignAbstractClass extends HudAbstract {
  public void drawSide(boolean isRightSide, NanoVGUI nanoVGUI, float widthPercentage, float heightPercentage) {
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
    // Size: (leftPanelWidth, screenHeight)
    Vector2f pos = new Vector2f(startX, 0.0f);
    Vector2f size = new Vector2f(panelWidth, panelHeight);

    nanoVGUI.drawRect(pos, size, mainBackgroundColor);
  }

  public abstract void checkHudInputs(Vector2d mouseLocation);

  public abstract void updateText();
}

