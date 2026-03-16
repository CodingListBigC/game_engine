package org.bigacl.renderEngine.gui.fields.button.addSubtract;

import org.bigacl.renderEngine.gui.fields.TextWithBackground;
import org.bigacl.renderEngine.gui.fields.button.Button;
import org.bigacl.renderEngine.gui.fields.sets.InputWithText;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.utils.consts.Const;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class AddSubtractButtonWithText extends InputWithText {
  private Button addBtn;
  private Button subBtn;
  // Size
  private Vector2f itemPosition;
  private Vector2f itemSize;


  private final float spacing;
  // Button Sizes
  private final float buttonWidth;
  private Vector2f buttonSize;
  private Vector2f addBtnPosition;
  private Vector2f subBtnPosition;
  private float amount = 1;
  String mainCode;

  public AddSubtractButtonWithText(String mainCode, Vector2f itemPosition, Vector2f itemSize, float spacing, float buttonWidth) {
    this.mainCode = mainCode;
    this.itemPosition = itemPosition;
    this.itemSize = itemSize;
    this.spacing = spacing;
    this.buttonWidth = buttonWidth;
    initDefault();
  }

  public AddSubtractButtonWithText(float amount) {
    this.amount = amount;
    this.itemPosition = new Vector2f();
    this.itemSize = new Vector2f();
    this.spacing = 20;
    this.buttonWidth = 20;
    initDefault();
  }

  private void initDefault() {
    // Set ui item sizes
    setSizes();
    // Create Default Items
    this.text = new TextWithBackground("0");
    this.addBtn = new Button("+", mainCode + "-1", buttonSize, Color.darkGray, Color.white);
    this.subBtn = new Button("-", mainCode + "-2", buttonSize, Color.darkGray, Color.white);

    this.addBtn.setSize(buttonSize);
    this.subBtn.setSize(buttonSize);
    // Set there position
    setItemPosition();

  }


  public void setSizes() {
    this.buttonSize = new Vector2f(this.buttonWidth, this.itemSize.y);
    this.addBtnPosition = new Vector2f(this.itemSize.x - this.spacing - this.buttonSize.x, 0);
    this.subBtnPosition = new Vector2f(this.spacing, 0);
    float sideTextPadding = (this.spacing * 2) + this.buttonSize.x;
    float textWidth = this.itemSize.x - (sideTextPadding * 2);
    this.textPosition = new Vector2f(sideTextPadding, 0.0f);
    this.textSize = new Vector2f(textWidth, this.itemSize.y);
  }

  private void setItemPosition() {
    Vector2f addBtnRenderPosition = new Vector2f(this.addBtnPosition).add(this.itemPosition);
    this.addBtn.setLocation(addBtnRenderPosition);
    this.addBtn.setSize(buttonSize);
    Vector2f subBtnRenderPosition = new Vector2f(this.subBtnPosition).add(this.itemPosition);
    this.subBtn.setLocation(subBtnRenderPosition);
    this.subBtn.setSize(buttonSize);
    Vector2f textRenderPosition = new Vector2f(this.textPosition).add(this.itemPosition);
    text.setPosition(textRenderPosition);
    Vector2f textLimitsSize = new Vector2f(itemSize.x - ((this.spacing * 4) + (this.buttonWidth * 2)), itemSize.y);
    text.setSizeLimits(textLimitsSize);
  }

  @Override
  public void renderInput() {
    this.addBtn.render();
    this.subBtn.render();
  }

  @Override
  protected void setText() {
    this.text = new TextWithBackground("hi");
  }

  /**
   *
   * @param mouseLocation - Mouse Current Location
   * @param mouseAction   - Mouse Current Action
   * @return return null if no click of wrong action, return string with number key
   */
  public String[] checkClicked(Vector2d mouseLocation, int mouseAction) {
    if (mouseAction != GLFW.GLFW_MOUSE_BUTTON_LEFT) return null;
    if (addBtn.isHovered(mouseLocation)) {
      String[] itemReturn = addBtn.getCode().split("-");
      if (itemReturn.length >= 2) {
        return itemReturn;
      }
    }
    if (subBtn.isHovered(mouseLocation)) {
      String[] itemReturn = addBtn.getCode().split("-");
      if (itemReturn.length >= 2) {
        return itemReturn;
      }
    }
    return null;
  }

  @Override
  protected void rightClick() {

  }

  @Override
  protected void leftClick() {

  }

  public boolean isHovered(Vector2d mouseLocation) {
    return addBtn.isHovered(mouseLocation) || subBtn.isHovered(mouseLocation);
  }

  /**
   * Gets the calculated change based on a custom multiplier.
   *
   * @param mouseLocation Current mouse location
   * @param changeAmount  The multiplier to apply to the button state
   * @return The final change value (e.g., 1.5, -1.5, or 0.0)
   */
  public float getInfo(Vector2d mouseLocation, float changeAmount) {
    return (float) this.getInfo(mouseLocation) * changeAmount;
  }

  /**
   * Gets the change based on the default internal 'amount' variable.
   *
   * @param mouseLocation Current mouse location
   * @return The change value multiplied by the default amount
   */
  public float getInfoDefault(Vector2d mouseLocation) {
    return (float) this.getInfo(mouseLocation) * this.amount;
  }

  /**
   * Determines if the add or subtract button is hovered.
   *
   * @param mouseLocation Current mouse location
   * @return 1 for Add, -1 for Subtract, or 0 if neither is hovered
   */
  public int getInfo(Vector2d mouseLocation) {
    if (addBtn.isHovered(mouseLocation)) return 1;
    if (subBtn.isHovered(mouseLocation)) return -1;

    return 0;
  }

  public void setItemPosition(Vector2f itemPosition) {
    this.itemPosition = itemPosition;
    setItemPosition();
    setSizes();
  }

  public void setItemSize(Vector2f itemSize) {
    this.itemSize = itemSize;
    setSizes();
    setItemPosition();
  }
}

