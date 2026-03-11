package org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign;

import org.bigacl.renderEngine.gui.drawing.NanoVGUI;
import org.bigacl.renderEngine.gui.fields.Text;
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

  Text itemName;
  VectorButton positionButton;
  VectorButton rotationButton;

  public LeftHud() {
    this.widthPercentage = 0.125f;
    this.heightPercentage = 0.90f;

    // This is how wide the actual panel is
    this.panelWidth = screenWidth * widthPercentage;
    this.panelHeight = screenHeight * heightPercentage;
    this.itemName = new Text("Item Name", 1);
    this.itemName.setSizeLimits(new Vector2f(panelWidth - 20, 40));
    this.itemName.setPosition(new Vector2f(10, 5));
    this.positionButton = new VectorButton(new Vector2f(0, 50), panelWidth);
    this.rotationButton = new VectorButton(new Vector2f(0, 200), panelWidth);
  }

  @Override
  public void renderMenu() {
    updateText();
    NanoVGUI nanoVGUI = ClassConst.nanoVGUI;


    drawSide(false, nanoVGUI, widthPercentage, heightPercentage);
    this.itemName.render();
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
            HudAbstract.getViewData().getRotation(),
            90f
    );

    try {
      ClassConst.itemMangerAbstract.getDefaultData().get(HudAbstract.getViewData().getViewIndex()).setWorldPosition(changeLocation);
      ClassConst.itemMangerAbstract.getDefaultData().get(HudAbstract.getViewData().getViewIndex()).setRotation(changeRotation);
    } catch (IndexOutOfBoundsException ignored) {

    }

  }
  @Override
  public void updateText() {
    positionButton.updateText(HudAbstract.getViewData().getPosition());
    rotationButton.updateText(HudAbstract.getViewData().getRotation());
    this.itemName.setText(HudAbstract.getViewData().getName());
  }
}
