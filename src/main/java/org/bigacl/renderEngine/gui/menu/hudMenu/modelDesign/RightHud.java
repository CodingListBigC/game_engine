package org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign;

import static org.bigacl.renderEngine.utils.consts.ClassConst.nanoVGUI;

public class RightHud extends ModelDesignAbstractClass {

  float widthPercentage;
  float heightPercentage;
  float panelWidth;
  float panelHeight;

  public RightHud() {
    this.widthPercentage = 0.125f;
    this.heightPercentage = 0.75f;

    // This is how wide the actual panel is
    this.panelWidth = screenWidth * widthPercentage;
    this.panelHeight = screenHeight * heightPercentage;}


  @Override
  public void checkHudInputs() {

  }

  @Override
  public void renderMenu() {
    drawSide(true, nanoVGUI, widthPercentage, heightPercentage);
  }

  @Override
  public void updateText() {

  }
}
