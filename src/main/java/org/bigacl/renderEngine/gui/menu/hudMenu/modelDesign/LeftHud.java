package org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign;

import org.bigacl.renderEngine.gui.drawing.NanoVGUI;
import org.bigacl.renderEngine.gui.fields.button.addSubtract.VectorButton;
import org.bigacl.renderEngine.gameItems.item.ItemMangerMainGame;
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

  VectorButton positionButton;
  VectorButton rotationButton;

  public LeftHud() {
    this.widthPercentage = 0.125f;
    this.heightPercentage = 0.90f;

    // This is how wide the actual panel is
    this.panelWidth = screenWidth * widthPercentage;
    this.panelHeight = screenHeight * heightPercentage;

    this.positionButton = new VectorButton(new Vector2f(0, 50), panelWidth);
    this.rotationButton = new VectorButton(new Vector2f(0, 200), panelWidth);
  }

  @Override
  public void renderMenu() {
    updateText();
    NanoVGUI nanoVGUI = ClassConst.nanoVGUI;


    drawSide(false, nanoVGUI, widthPercentage, heightPercentage);
    this.positionButton.render();
    this.rotationButton.render();
  }


  @Override
  public void checkHudInputs(Vector2d mouseLocation, int mouseAction) {
    Vector3f changeLocation = this.positionButton.checkButtonInput(
            mouseLocation,
            mouseAction,
            HudAbstract.getViewData().getPosition(),
            1.0f
    );
    Vector3f changeRotation = this.rotationButton.checkButtonInput(
            mouseLocation,
            mouseAction,
            HudAbstract.getViewData().getPosition(), 90f
    );

    try {
      ClassConst.itemMangerAbstract.getDefaultData().get(HudAbstract.getViewData().getViewIndex()).setWorldPosition(changeLocation);
      //TODO: Add change location function
    } catch (IndexOutOfBoundsException ignored) {

    }

  }
  @Override
  public void updateText() {
    positionButton.updateText(HudAbstract.getViewData().getPosition());
    rotationButton.updateText(new Vector3f());
  }
}
