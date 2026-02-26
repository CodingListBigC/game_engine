package org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign;

import org.bigacl.renderEngine.gui.fields.button.select.SelectButton;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.bigacl.renderEngine.utils.consts.ClassConst.nanoVGUI;

public class RightHud extends ModelDesignAbstractClass {

  float widthPercentage;
  float heightPercentage;
  float panelWidth;
  float panelHeight;

  SelectButton selectButton1 = new SelectButton(new Vector2f(1500,20), 20,new Vector4f(1,1,1,1),new Vector4f(1,0,0,1));
  SelectButton selectButton2 = new SelectButton(new Vector2f(1500,60), 20,new Vector4f(1,1,1,1),new Vector4f(1,0,0,1));

  public RightHud() {
    this.widthPercentage = 0.125f;
    this.heightPercentage = 0.75f;

    // This is how wide the actual panel is
    this.panelWidth = screenWidth * widthPercentage;
    this.panelHeight = screenHeight * heightPercentage;
    selectButton1.toggleClickedStatus();
  }


  @Override
  public void checkHudInputs() {

  }

  @Override
  public void renderMenu() {
    drawSide(true, nanoVGUI, widthPercentage, heightPercentage);
    selectButton1.render();
    selectButton2.render();
  }

  @Override
  public void updateText() {

  }
}
