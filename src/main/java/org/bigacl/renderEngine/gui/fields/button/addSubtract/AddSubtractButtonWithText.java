package org.bigacl.renderEngine.gui.fields.button.addSubtract;

import org.bigacl.renderEngine.gui.fields.TextWithBackground;
import org.bigacl.renderEngine.gui.fields.button.Button;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.utils.consts.Const;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class AddSubtractButtonWithText {
  private final TextWithBackground text;
  private final Button addBtn;
  private final Button subBtn;
  private boolean viewStatus = true;
  // Size
  private final Vector2f itemPosition;
  private final Vector2f itemSize;
  private final float spacing;
  // Button Sizes
  private final float buttonWidth;
  private Vector2f buttonSize;
  private Vector2f addBtnPosition;
  private Vector2f subBtnPosition;
  // Text Size
  private Vector2f textSize;
  private Vector2f textPosition;

  private int fontType = ClassConst.fontSizing.NORMAL_TEXT_SIZE;
  String mainCode;

  public AddSubtractButtonWithText(String mainCode, Vector2f itemPosition, Vector2f itemSize, float spacing, float buttonWidth) {
    this.mainCode = mainCode;
    this.itemPosition = itemPosition;
    this.itemSize = itemSize;
    this.spacing = spacing;
    this.buttonWidth = buttonWidth;
    // Set ui item sizes
    setSizes();
    // Create Default Items
    this.text = new TextWithBackground("0");
    this.addBtn = new Button("+", mainCode + "-1",buttonSize, Color.darkGray,Color.white);
    this.subBtn = new Button("-", mainCode + "-2",buttonSize, Color.darkGray, Color.white);
    // Set there position
    setItemPosition();
  }

  public void setSizes(){
    this.buttonSize = new Vector2f(this.buttonWidth,this.itemSize.y);
    this.addBtnPosition = new Vector2f(this.spacing, 0);
    this.subBtnPosition = new Vector2f(this.itemSize.x - this.spacing - this.buttonSize.x, 0);
    float sideTextPadding = (this.spacing * 2) + this.buttonSize.x;
    float textWidth = this.itemSize.x - (sideTextPadding * 2);
    this.textPosition = new Vector2f(sideTextPadding, 0.0f);
    this.textSize = new Vector2f(textWidth, this.itemSize.y);
  }

  private void setItemPosition(){
    Vector2f addBtnRenderPosition = new Vector2f(this.addBtnPosition).add(this.itemPosition);
    this.addBtn.setLocation(addBtnRenderPosition);
    Vector2f subBtnRenderPosition = new Vector2f(this.subBtnPosition).add(this.itemPosition);
    this.subBtn.setLocation(subBtnRenderPosition);
    Vector2f textRenderPosition = new Vector2f(this.textPosition).add(this.itemPosition);
    text.setPosition(textRenderPosition);
    Vector2f textLimitsSize = new Vector2f(itemSize.x -  ((this.spacing * 4) + (this.buttonWidth * 2)), itemSize.y);
    text.setSizeLimits(textLimitsSize);

  }

  public void setViewStatus(boolean viewStatus) {
    this.viewStatus = viewStatus;
  }
  public void setTextLabel(String textLabel){
    this.text.setText(textLabel);
  }
  public void render(){
    if (!this.viewStatus)
      return;

    this.addBtn.render();
    this.subBtn.render();
    this.text.renderBg();

  }

  /**
   *
   * @param mouseLocation - Mouse Current Location
   * @param mouseAction - Mouse Current Action
   * @return return null if no click of wrong action, return string with number key
   */
  public String[] checkClicked(Vector2d mouseLocation, int mouseAction){
    if (mouseAction != GLFW.GLFW_MOUSE_BUTTON_LEFT)
      return null;
    if (addBtn.isHovered(mouseLocation)) {
      String[] itemReturn = addBtn.getCode().split("-");
      if (itemReturn.length >= 2){
        return itemReturn;
      }
    }
    if (subBtn.isHovered(mouseLocation)) {
      String[] itemReturn = addBtn.getCode().split("-");
      if (itemReturn.length >= 2){
        return itemReturn;
      }
    }
    return null;
  }

  public boolean isHovered(Vector2d mouseLocation) {
    return addBtn.isHovered(mouseLocation) || subBtn.isHovered(mouseLocation);
  }

  public float getInfo(Vector2d mouseLocation, float changeAmount){
    if (addBtn.isHovered(mouseLocation))
      return changeAmount; // Add Value
    if (subBtn.isHovered(mouseLocation))
      return -changeAmount; // Subtract Value

    return 0; //  Default Value
  }
}

