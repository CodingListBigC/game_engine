package org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign;

import org.bigacl.renderEngine.gui.drawing.NanoVGUI;
import org.bigacl.renderEngine.gui.fields.Text;
import org.bigacl.renderEngine.gui.fields.button.addSubtract.AddSubtractButtonWithText;
import org.bigacl.renderEngine.gui.fields.button.addSubtract.VectorButton;
import org.bigacl.renderEngine.gui.menu.hudMenu.HudAbstract;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.utils.number.SnapNumbers;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class LeftHud extends ModelDesignAbstractClass {

  float widthPercentage = 0.125f;
  float heightPercentage = 0.90f;

  // These should ideally be updated if the window resizes
  float panelWidth = screenWidth * widthPercentage;
  float panelHeight = screenHeight * heightPercentage;

  int startY = 10;

  Text itemName = new Text("Item Name", 1, new Vector2f(0, 0), new Vector2f(0, 0));

  // Position Control
  VectorButton positionButton = new VectorButton(panelWidth);
  SnapNumbers positionSnap = new SnapNumbers(1, 1);
  AddSubtractButtonWithText editPositionSnapping = new AddSubtractButtonWithText(10);

  // Rotation Control
  VectorButton rotationButton = new VectorButton(panelWidth);
  SnapNumbers rotationSnap = new SnapNumbers(15, 90);
  AddSubtractButtonWithText editRotationSnapping = new AddSubtractButtonWithText(10);

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
    this.editPositionSnapping.setVisible(true);
    this.editPositionSnapping.setItemSize(new Vector2f(usableWidth, 40));
    this.editPositionSnapping.setItemPosition(new Vector2f(leftXOffset, currentY));
    currentY += 40 + ySpacing;

    // 3. Position Vector
    this.positionButton.setLocation(new Vector2f(leftXOffset, currentY));
    this.positionButton.updateLocation();
    currentY += this.positionButton.getSize().y + ySpacing;

    // 4. Rotation Snapping Button (Was missing setLocation!)
    this.editRotationSnapping.setVisible(true);
    this.editRotationSnapping.setItemSize(new Vector2f(usableWidth, 40));
    this.editRotationSnapping.setItemPosition(new Vector2f(leftXOffset, currentY));
    currentY += 40 + ySpacing;

    // 5. Rotation Vector
    this.rotationButton.setLocation(new Vector2f(leftXOffset, currentY));
    this.rotationButton.updateLocation();
  }

  @Override
  public void renderMenu() {
    updateText();
    NanoVGUI nanoVGUI = ClassConst.nanoVGUI;

    drawSide(false, nanoVGUI, widthPercentage, heightPercentage);

    // Text Render
    this.itemName.render();

    // Vector Button Render
    this.positionButton.render();
    this.rotationButton.render();

    // Single Add Subtract Buttons
    this.editPositionSnapping.render();
    this.editRotationSnapping.render();
  }

  @Override
  public void checkHudInputs(Vector2d mouseLocation, int mouseAction) {
    // Tip: Use renderer value from editPositionSnapping instead of hardcoded 1.0f
    float snapValue = 1.0f; // TODO: Pull from editPositionSnapping.getValue()

    Vector3f newPos = this.positionButton.checkButtonInput(
            mouseLocation, mouseAction, HudAbstract.getViewData().getPosition(), positionSnap.getCurrentAmount()
    );
    Vector3f newRot = this.rotationButton.checkButtonInput(
            mouseLocation, mouseAction, HudAbstract.getViewData().getRotation(), rotationSnap.getCurrentAmount()
    );

    // Safety check to ensure index exists
    int index = HudAbstract.getViewData().getViewIndex();
    var items = ClassConst.itemMangerAbstract.getDefaultData();

    try {
      items.get(index).setWorldPosition(newPos);
      items.get(index).setRotation(newRot);
    } catch (IndexOutOfBoundsException e) {
    }

    if (mouseAction == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
      int positionSnapChange = this.editPositionSnapping.getInfo(mouseLocation);
      int rotationSnapChange = this.editRotationSnapping.getInfo(mouseLocation);
      this.positionSnap.changeByInt(positionSnapChange);
      this.rotationSnap.changeByInt(rotationSnapChange);
    }
  }

  @Override
  public void updateText() {
    // Change Vector Buttons
    positionButton.updateText(HudAbstract.getViewData().getPosition());
    rotationButton.updateText(HudAbstract.getViewData().getRotation());

    // Set Text Size
    this.itemName.setText(HudAbstract.getViewData().getName());

    // Set Add Subtract Buttons
    this.editPositionSnapping.setTextLabel(String.valueOf(this.positionSnap.getCurrentAmount()));
    this.editRotationSnapping.setTextLabel(String.valueOf(this.rotationSnap.getCurrentAmount()));

    setPosition();
  }
}