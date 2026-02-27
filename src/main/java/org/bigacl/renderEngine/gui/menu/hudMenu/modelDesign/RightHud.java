package org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign;

import org.bigacl.renderEngine.gui.fields.button.select.SelectButton;
import org.bigacl.renderEngine.gui.fields.button.select.SelectButtonSet;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.awt.*;

import static org.bigacl.renderEngine.utils.consts.ClassConst.nanoVGUI;

public class RightHud extends ModelDesignAbstractClass {

  float widthPercentage;
  float heightPercentage;
  float panelWidth;
  float panelHeight;

SelectButtonSet selectButtonSet;

  public RightHud() {
    this.widthPercentage = 0.125f;
    this.heightPercentage = 0.75f;

    // This is how wide the actual panel is
    this.panelWidth = screenWidth * widthPercentage;
    this.panelHeight = screenHeight * heightPercentage;
    Vector2f selectButtonSetPos = new Vector2f((screenWidth - panelWidth), 20);
    selectButtonSet = new SelectButtonSet(new Vector2f(selectButtonSetPos));
  }


  @Override
  public void checkHudInputs() {
  }

  @Override
  public void renderMenu() {
    drawSide(true, nanoVGUI, widthPercentage, heightPercentage);
    selectButtonSet.renderAll();
  }

  @Override
  public void updateText() {

  }
}
