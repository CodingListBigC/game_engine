package org.bigacl.renderEngine.gui.fields.sets.addSubtract;

import org.bigacl.renderEngine.gui.fields.Button;
import org.bigacl.renderEngine.gui.fields.TextLimits;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.Objects;

public class VectorButton extends AddSubtractBasic {

  // Default Button Size
  Vector2f defaultButtonSize = new Vector2f(25, 25);
  Vector4f defaultButtonBackgroundColor = new Vector4f(0.5f, 0.5f, 0.5f, 1f);
  Vector3f defaultButtonTextColor = new Vector3f(1, 1, 1);
  Vector3f defaultTextColor = new Vector3f(1, 0, 0);

  public VectorButton(Vector2f guiPosition, float width) {
    this.guiPosition = guiPosition;
    this.guiWidth = width;

    init();
  }

  @Override
  public void initButtons() {
    createButton(-1, 0, "+", "100");
    createButton(1, 0, "-", "200");
    createButton(-1, 1, "+", "010");
    createButton(1, 1, "-", "020");
    createButton(-1, 2, "+", "001");
    createButton(1, 2, "-", "002");
  }

  @Override
  public void initText() {
    this.createText(0, "1");
    this.createText(1, "2");
    this.createText(2, "3");
  }

  @Override
  public Vector2f getSize() {
    if (this.amountOfRows == 0){
      return null;
    }
    float x = this.guiWidth;
    float y = this.amountOfRows * (this.defaultButtonSize.y + this.rowSpacing);
    return new Vector2f(x,y);
  }

  public void createButton(float rowLocation, int column, String label, String code) {
    boolean buttonSide = rowLocation >= 0;
    this.addButton(new Button(label, code, defaultButtonSize, getButtonPos(buttonSide, column), defaultButtonBackgroundColor, defaultButtonTextColor));
    if (this.amountOfRows < column) {
      this.amountOfRows = column;
    }

  }

  private Vector2f getButtonPos(boolean side, int row) {
    float x = side ? guiWidth - defaultButtonSize.x - guiPosition.x - columSpacing : guiPosition.x + columSpacing;
    float y = guiPosition.y + (row * (defaultButtonSize.y + rowSpacing));

    return new Vector2f(x, y);
  }


  private float getYPos(int row){
    return guiPosition.y + (row * (defaultButtonSize.y + rowSpacing));
  }

  public Vector3f checkButtonInput(double mouseX, double mouseY, int action, Vector3f vector3f, float snap) {
    // 1. Only trigger on the initial PRESS
    if (action != 1) return vector3f;

    for (Button button : buttonArrayList) {
      if (!button.isHovered(mouseX, mouseY)) continue;

      char[] chars = button.getCode().toLowerCase().toCharArray();
      if (chars.length < 3) continue;

      // Use a single 'changePosition' call to avoid logic overlap
      float dx = 0, dy = 0, dz = 0;

      if (chars[0] == '1') dx = snap;
      else if (chars[0] == '2') dx = -snap;

      if (chars[1] == '1') dy = snap;
      else if (chars[1] == '2') dy = -snap;

      if (chars[2] == '1') dz = snap;
      else if (chars[2] == '2') dz = -snap;
      vector3f.add(dx, dy, dz);
    }
    updateText(vector3f);
    ClassConst.window.setMouseAction(-1);
    return vector3f;
  }
  private Vector2f getTextStartPos(int row){
    float y = getYPos(row);
    float x = defaultButtonSize.x + (columSpacing * 2);
    return new Vector2f(x,y);
  }
  private Vector2f getTextSize(){
    float x = guiWidth - ((defaultButtonSize.x * 2) + (columSpacing * 4));
    float y = defaultButtonSize.y;
    return new Vector2f(x,y);
  }
  private void createText(int row, String code){
    TextLimits textLimits = new TextLimits("0", code, getTextStartPos(row), getTextSize(), defaultTextColor);
    addText(textLimits);
  }
  private void updateText(Vector3f vector3f){
    for (TextLimits textLimits : textLimitsArrayList){
      System.out.println("Checking code: " + textLimits.getCode());
      if (Objects.equals(textLimits.getCode(), "1")){
        textLimits.setText(String.valueOf(vector3f.x));
      }else if (Objects.equals(textLimits.getCode(), "2")){
        textLimits.setText(String.valueOf(vector3f.y));
      }else if (Objects.equals(textLimits.getCode(), "3")){
        textLimits.setText(String.valueOf(vector3f.z));
      }
    }
  }


}
