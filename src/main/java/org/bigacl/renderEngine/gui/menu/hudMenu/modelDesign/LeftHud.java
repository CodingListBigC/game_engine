package org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign;

import org.bigacl.renderEngine.gui.drawing.NanoVGUI;
import org.bigacl.renderEngine.gui.fields.button.addSubtract.VectorButton;
import org.bigacl.renderEngine.gameItems.item.ItemManger;
import org.bigacl.renderEngine.gameItems.item.placeable.house.House;
import org.bigacl.renderEngine.gui.menu.hudMenu.HudAbstract;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class LeftHud extends ModelDesignAbstractClass {


  float widthPercentage;
  float heightPercentage;
  float panelWidth;
  float panelHeight;

  VectorButton vectorButton;


  public LeftHud() {
    this.widthPercentage = 0.125f;
    this.heightPercentage = 0.75f;

    // This is how wide the actual panel is
    this.panelWidth = screenWidth * widthPercentage;
    this.panelHeight = screenHeight * heightPercentage;

    this.vectorButton = new VectorButton(new Vector2f(0, 50), panelWidth);
  }

  @Override
  public void renderMenu() {
    NanoVGUI nanoVGUI = ClassConst.nanoVGUI;


    drawSide(false, nanoVGUI, widthPercentage, heightPercentage);
    this.vectorButton.render();
  }


  @Override
  public void checkHudInputs(Vector2d mouseLocation) {
    ItemManger iM = ClassConst.itemManger;
    Vector3f changeLocation = this.vectorButton.checkButtonInput(
            ClassConst.window.getMouseLocation(),
            ClassConst.window.getMouseAction(),
            HudAbstract.getViewData().getPosition(),
            1.0f
    );

    try {
      iM.getHouseList().get(HudAbstract.getViewData().getViewIndex()).setWorldPosition(changeLocation);
    }catch (IndexOutOfBoundsException e){

    }

  }
  @Override
  public void updateText() {
    vectorButton.updateText();
  }
}
