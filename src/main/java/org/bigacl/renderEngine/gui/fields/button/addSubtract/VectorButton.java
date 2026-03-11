package org.bigacl.renderEngine.gui.fields.button.addSubtract;

import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.window.WindowMaster;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.awt.*;

public class VectorButton extends AddSubtractBasic {
  float[] currentTextArray;
  public VectorButton(Vector2f guiPosition, float width) {
    this.guiPosition = new Vector2f(guiPosition);
    this.guiWidth = width;

    init();
  }

  public VectorButton(float width) {
    this.guiPosition = new Vector2f(0);
    this.guiWidth = width;
    this.size = new Vector2f();
    init();
  }

  @Override
  public void initButtons() {
    makeAmountOfNewItems(3);
  }

  private float getYPos(int row){
    return guiPosition.y + (row * (defaultButtonSize.y + rowSpacing));
  }

  public Vector3f checkButtonInput(Vector2d mouseLocation, int action, Vector3f originalVector, float changeAmount) {
    // 1. Safety Check: If the action isn't a PRESS (1) or the vector is missing, stop.
    if (action != 1 || originalVector == null) {
      return originalVector;
    }

    // 2. Create a copy so we don't modify the original object until we are sure
    Vector3f editVector = new Vector3f(originalVector);

    // 3. Update the UI state
    int sizeOfVector = 3;
    setAmountViewAble(sizeOfVector);

    // 4. Calculate changes.
    // Logic: getInfo should return 0.0f if that specific axis button wasn't clicked.
    float xChange = getInfo(0, mouseLocation, changeAmount);
    float yChange = getInfo(1, mouseLocation, changeAmount);
    float zChange = getInfo(2, mouseLocation, changeAmount);

    // 5. Apply the changes
    editVector.add(xChange, yChange, zChange);

    // 6. Update the display text/array
    float[] floatArray = new float[]{editVector.x, editVector.y, editVector.z};
    this.currentTextArray = floatArray;
    setTextWithArray(floatArray);

    return editVector;
  }

  private Vector2f getTextStartPos(int row){
    float y = getYPos(row);
    float x = guiPosition.x + defaultButtonSize.x + (columSpacing * 2);
    return new Vector2f(x,y);
  }


  private Vector2f getTextSize(){
    float x = guiWidth - ((defaultButtonSize.x * 2) + (columSpacing * 4));
    float y = defaultButtonSize.y;
    return new Vector2f(x,y);
  }

  public void updateText(Vector3f vector3f) {
    if (vector3f == null) return;
    float[] floatArray = new float[]{vector3f.x, vector3f.y, vector3f.z};
    this.currentTextArray = floatArray;
    setTextWithArray(floatArray);
  }

  @Override
  public void rightClick() {

  }

  @Override
  public void leftClick() {

  }

  public void updateText(){
    setTextWithArray(this.currentTextArray);
  }

}
