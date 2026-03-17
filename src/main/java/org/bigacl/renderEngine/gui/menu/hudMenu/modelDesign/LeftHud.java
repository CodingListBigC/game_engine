package org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign;

import org.bigacl.renderEngine.gui.drawing.NanoVGUI;
import org.bigacl.renderEngine.gui.fields.text.Text;
import org.bigacl.renderEngine.gui.fields.button.Button;
import org.bigacl.renderEngine.gui.fields.button.addSubtract.AddSubtractButtonWithText;
import org.bigacl.renderEngine.gui.fields.button.addSubtract.VectorButton;
import org.bigacl.renderEngine.gui.menu.hudMenu.HudAbstract;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.utils.consts.Const;
import org.bigacl.renderEngine.utils.number.SnapNumbers;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

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
  SnapNumbers positionSnap = new SnapNumbers(0.125f, 1, 0, 1);
  AddSubtractButtonWithText editPositionSnapping = new AddSubtractButtonWithText(10);

  // Rotation Control
  VectorButton rotationButton = new VectorButton(panelWidth);
  SnapNumbers rotationSnap = new SnapNumbers(15, 90, 0, 360);
  AddSubtractButtonWithText editRotationSnapping = new AddSubtractButtonWithText(10);

  // Delete Button
  Button deleteButton = new Button("Delete", Color.DARK_GRAY, Color.BLACK);
  // Copy Button
  Button copyButton = new Button("Copy", Color.DARK_GRAY, Color.BLACK);
  // Change Type Button
  Button changeTypeButton = new Button("Change Type Button", Color.DARK_GRAY, Color.BLACK);

  public LeftHud() {
    setSizes();
    setPosition();
  }

  private void setSizes() {
    int xOffset = 10;
    int usableWidth = (int) (panelWidth - (xOffset * 2));
    positionButton.setAmountViewAble(3);
    rotationButton.setAmountViewAble(3);
    this.editPositionSnapping.setItemSize(new Vector2f(usableWidth, Const.DEFAULT_BUTTON_SIZE.y));
    this.editRotationSnapping.setItemSize(new Vector2f(usableWidth, Const.DEFAULT_BUTTON_SIZE.y));
    this.deleteButton.setSize(new Vector2f((float) (this.panelWidth * .5), Const.DEFAULT_BUTTON_SIZE.y));
    this.copyButton.setSize(new Vector2f((float) (this.panelWidth * .5), Const.DEFAULT_BUTTON_SIZE.y));
    this.changeTypeButton.setSize(new Vector2f((float) (this.panelWidth * .75), Const.DEFAULT_BUTTON_SIZE.y));
  }

  private void setPosition() {
    float currentY = this.startY;
    int ySpacing = 10;
    int xOffset = 10;
    int usableWidth = (int) (panelWidth - (xOffset * 2));

    // 1. Item Name
    this.itemName.setPosition(new Vector2f(xOffset, currentY));
    this.itemName.setSizeLimits(new Vector2f(usableWidth, 40));
    currentY += 40 + ySpacing;

    // 2. Snapping Button (Was missing setLocation!)
    this.editPositionSnapping.setVisible(true);
    this.editPositionSnapping.setItemPosition(new Vector2f(xOffset, currentY));
    currentY += 40 + ySpacing;

    // 3. Position Vector
    this.positionButton.setLocation(new Vector2f(xOffset, currentY));
    this.positionButton.updateLocation();
    currentY += this.positionButton.getSize().y + ySpacing;

    // 4. Rotation Snapping Button (Was missing setLocation!)
    this.editRotationSnapping.setVisible(true);
    this.editRotationSnapping.setItemPosition(new Vector2f(xOffset, currentY));
    currentY += 40 + ySpacing;

    // 5. Rotation Vector
    this.rotationButton.setLocation(new Vector2f(xOffset, currentY));
    this.rotationButton.updateLocation();
    currentY += this.rotationButton.getSize().y + ySpacing;

    // 6. Delete Button
    this.deleteButton.setLocation(new Vector2f(((float) usableWidth / 2) - (this.deleteButton.getSize().x / 2) + xOffset, currentY));
    this.deleteButton.setVisible(true);
    currentY += this.deleteButton.getSize().y + ySpacing;

    // 7. Copy Button
    this.copyButton.setLocation(new Vector2f(((float) usableWidth / 2) - (this.copyButton.getSize().x / 2) + xOffset, currentY));
    this.copyButton.setVisible(true);
    currentY += this.copyButton.getSize().y + ySpacing;

    // 8. Change Type Button
    this.changeTypeButton.setLocation(new Vector2f(((float) usableWidth / 2) - (this.copyButton.getSize().x / 2) + xOffset, currentY));
    this.changeTypeButton.setVisible(true);
  }

  @Override
  public void renderMenu() {
    updateText();
    NanoVGUI nanoVGUI = ClassConst.nanoVGUI;

    drawSide(false, nanoVGUI, widthPercentage, heightPercentage);

    if (ClassConst.itemMangerAbstract.getDefaultData().isEmpty()) {
      return;
    }

    // Text Render
    this.itemName.render();

    // Vector Button Render
    this.positionButton.render();
    this.rotationButton.render();

    // Single Add Subtract Buttons
    this.editPositionSnapping.render();
    this.editRotationSnapping.render();
    this.deleteButton.render();
    this.copyButton.render();
    this.changeTypeButton.render();
  }

  @Override
  public void checkHudInputs(Vector2d mouseLocation, int mouseAction) {
    // Tip: Use renderer value from editPositionSnapping instead of hardcoded 1.0f

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
    } catch (IndexOutOfBoundsException ignored) {
    }

    if (mouseAction == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
      int positionSnapChange = this.editPositionSnapping.getInfo(mouseLocation);
      int rotationSnapChange = this.editRotationSnapping.getInfo(mouseLocation);
      this.positionSnap.changeByInt(positionSnapChange);
      this.rotationSnap.changeByInt(rotationSnapChange);
    }

    if (deleteButton.checkClickable(mouseLocation, mouseAction)) {
      viewData.deleteSelected();
    }

    if (copyButton.checkInput(mouseLocation, mouseAction, GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
      viewData.copySelected();
    }
    if (changeTypeButton.checkInput(mouseLocation, mouseAction, GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
      viewData.changeTypeOfSelected();
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