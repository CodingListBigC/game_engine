package org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign;

import org.bigacl.renderEngine.gui.drawing.NanoVGUI;
import org.bigacl.renderEngine.gui.fields.Text;
import org.bigacl.renderEngine.gui.fields.button.addSubtract.AddSubtractButtonWithText;
import org.bigacl.renderEngine.gui.fields.button.addSubtract.VectorButton;
import org.bigacl.renderEngine.gameItems.item.ItemMangerMainGame;
import org.bigacl.renderEngine.gui.menu.hudMenu.HudAbstract;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class LeftHud extends ModelDesignAbstractClass {

  float widthPercentage = 0.125f;
  float heightPercentage = 0.90f;

  // These should ideally be updated if the window resizes
  float panelWidth = screenWidth * widthPercentage;
  float panelHeight = screenHeight * heightPercentage;

  int startY = 10;

  Text itemName = new Text("Item Name", 1, new Vector2f(0, 0), new Vector2f(0, 0));
  VectorButton positionButton = new VectorButton(panelWidth);
  AddSubtractButtonWithText editPositionSnapping = new AddSubtractButtonWithText(10);
  VectorButton rotationButton = new VectorButton(panelWidth);

  public LeftHud() {
    setSizes();
    setPosition();
  }

  private void setSizes() {
    positionButton.setAmountViewAble(3);
    rotationButton.setAmountViewAble(3);
  }

  private void setPosition() {
    float currentY = this.startY;
    int ySpacing = 10;
    int leftXOffset = 10;
    int usableWidth = (int) (panelWidth - (leftXOffset * 2));

    // 1. Item Name
    this.itemName.setPosition(new Vector2f(leftXOffset, currentY));
    this.itemName.setSizeLimits(new Vector2f(usableWidth, 40));
    currentY += 40 + ySpacing;

    // 2. Snapping Button (Was missing setLocation!)
    this.editPositionSnapping.setItemSize(new Vector2f(usableWidth, 40));
    this.editPositionSnapping.setLocation(new Vector2f(leftXOffset, currentY));
    currentY += 40 + ySpacing;

    // 3. Position Vector
    this.positionButton.setLocation(new Vector2f(leftXOffset, currentY));
    this.positionButton.updateLocation();
    currentY += this.positionButton.getSize().y + ySpacing;

    // 4. Rotation Vector
    this.rotationButton.setLocation(new Vector2f(leftXOffset, currentY));
    this.rotationButton.updateLocation();
  }

  @Override
  public void renderMenu() {
    updateText();
    NanoVGUI nanoVGUI = ClassConst.nanoVGUI;

    drawSide(false, nanoVGUI, widthPercentage, heightPercentage);

    this.itemName.render();
    this.positionButton.render();
    this.rotationButton.render();
    this.editPositionSnapping.render();
  }

  @Override
  public void checkHudInputs(Vector2d mouseLocation, int mouseAction) {
    // Tip: Use the value from editPositionSnapping instead of hardcoded 1.0f
    float snapValue = 1.0f; // TODO: Pull from editPositionSnapping.getValue()

    Vector3f newPos = this.positionButton.checkButtonInput(
            mouseLocation, mouseAction, HudAbstract.getViewData().getPosition(), snapValue
    );
    Vector3f newRot = this.rotationButton.checkButtonInput(
            mouseLocation, mouseAction, HudAbstract.getViewData().getRotation(), 90f
    );

    // Safety check to ensure index exists
    int index = HudAbstract.getViewData().getViewIndex();
    var items = ClassConst.itemMangerAbstract.getDefaultData();

    if (index >= 0 && index < items.size()) {
      items.get(index).setWorldPosition(newPos);
      items.get(index).setRotation(newRot);
    }
  }

  @Override
  public void updateText() {
    positionButton.updateText(HudAbstract.getViewData().getPosition());
    rotationButton.updateText(HudAbstract.getViewData().getRotation());
    this.itemName.setText(HudAbstract.getViewData().getName());
    setPosition();
  }
}