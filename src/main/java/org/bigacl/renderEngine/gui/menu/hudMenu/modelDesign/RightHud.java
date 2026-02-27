package org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign;

import org.bigacl.renderEngine.gui.fields.button.select.SelectButtonSet;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2d;
import org.joml.Vector2f;

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
  public void checkHudInputs(Vector2d mouseLocation) {
    int buttonClicked = selectButtonSet.checkInput(mouseLocation);
    if (buttonClicked != -1){
      // TODO: Set variable to edit data
    }
  }

  @Override
  public void renderMenu() {
    updateText();
    drawSide(true, nanoVGUI, widthPercentage, heightPercentage);
    selectButtonSet.renderAll();
  }

  @Override
  public void updateText() {
    selectButtonSet.updateText();
  }
}
