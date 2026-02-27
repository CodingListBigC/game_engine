package org.bigacl.renderEngine.gui.fields.button.addSubtract;

import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.window.WindowMaster;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.awt.*;

public class VectorButton extends AddSubtractBasic {
  public VectorButton(Vector2f guiPosition, float width) {
    this.guiPosition = new Vector2f(guiPosition);
    this.guiWidth = width;

    init();
  }

  @Override
  public void initButtons() {
    makeAmountOfNewItems(3);
  }

  private float getYPos(int row){
    return guiPosition.y + (row * (defaultButtonSize.y + rowSpacing));
  }

  public Vector3f checkButtonInput(Vector2d mouseLocation, int action, Vector3f vector3f, float changeAmount) {
    // 1. Only trigger on the initial PRESS
    if (action != 1) return vector3f;


    int sizeOfVector = 3;

    setAmountViewAble(sizeOfVector);

    float xValue = getInfo(0, mouseLocation, changeAmount);
    float yValue = getInfo(1, mouseLocation, changeAmount);
    float zValue = getInfo(2, mouseLocation, changeAmount);
    Vector3f addVector = new Vector3f(xValue, yValue, zValue);
    vector3f.add(addVector);

    float[] floatArray = new float[3];
    floatArray[0] = vector3f.x;
    floatArray[1] = vector3f.y;
    floatArray[2] = vector3f.z;
    setTextWithArray(floatArray);
    return vector3f;

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

  private void updateText(Vector3f vector3f){
  }

  @Override
  public void rightClick() {

  }

  @Override
  public void leftClick() {

  }

}
