package org.bigacl.renderEngine.gui.fields.button.addSubtract;

import org.bigacl.renderEngine.gui.fields.button.ClickAbstract;
import org.joml.Vector2d;
import org.joml.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public abstract class AddSubtractBasic extends ClickAbstract {
  // Default Button Size
  Vector2f defaultButtonSize = new Vector2f(25, 25);
  Color defaultButtonBackgroundColor = new Color(.2f,.2f,.2f,.2f);
  Color defaultButtonTextColor = Color.white;
  Color defaultTextColor = new Color(1, 0, 0);

  protected ArrayList<AddSubtractButtonWithText> itemArrayList = new ArrayList<>();
  protected Vector2f guiPosition;
  protected float guiWidth;
  protected int amountOfRows = 0;
  protected float rowSpacing = 10;
  protected float columSpacing = 10;

  float buttonWidth = 20;
  @Override
  public void render() {
    renderAllButtonSet();
  }

  /**
   * Render default to all Buttons
   */
    public void renderAll() {
      renderAllButtonSet();
  }

  /**
   * Render Every Button in class
   */
  public void renderAllButtonSet() {
    for (AddSubtractButtonWithText addSubtractButtonWithText : itemArrayList) {
      addSubtractButtonWithText.render();
    }
  }

  public void addButton(AddSubtractButtonWithText addSubtractButtonWithText) {
    itemArrayList.add(addSubtractButtonWithText);
  }


  public void init() {
    this.initButtons();
  }

  public abstract void initButtons();

  protected void makeAmountOfNewItems(int amountViewAble){
    while (amountViewAble > itemArrayList.size()){
      makeNewItem();
      System.out.println("Amount Of Items: " + itemArrayList.size());
    }
  }

  private void makeNewItem(){
    addButton(new AddSubtractButtonWithText(String.valueOf(itemArrayList.size() + 1), getButtonPos(itemArrayList.size()),new Vector2f(guiWidth,defaultButtonSize.y),columSpacing,buttonWidth));
  }

  private Vector2f getButtonPos(int row) {
    float y = guiPosition.y + (row * (defaultButtonSize.y + rowSpacing));

    return new Vector2f(guiPosition.x, y);
  }

  public Vector2f getSize(){
    if (this.amountOfRows == 0){
      return null;
    }
    float x = this.guiWidth;
    float y = this.amountOfRows * (this.defaultButtonSize.y + this.rowSpacing);
    return new Vector2f(x,y);
  }
  public void setAmountViewAble(int amountViewAble){
    makeAmountOfNewItems(amountViewAble);
    for (AddSubtractButtonWithText item: itemArrayList){
      item.setVisible(false);
    }
    for (int i = 0; i < amountViewAble; i++) {
      itemArrayList.get(i).setVisible(true);
    }
  }
  public float getInfo(int itemNumber, Vector2d mouseLocation, float changeAmout){
    try {
      AddSubtractButtonWithText item = itemArrayList.get(itemNumber);
      return item.getInfo(mouseLocation, changeAmout);
    }catch (IndexOutOfBoundsException e){
      return 0;
    }
  }

  public void setTextWithArray(float[] floatArray) {
    String[] array = new String[floatArray.length];
    for (int i = 0; i < floatArray.length; i++) {
      array[i] = String.valueOf(floatArray[i]);
    }
    setTextWithArray(array);
  }
  public void setTextWithArray(String[] stringArray){
    for (int itemNumber = 0; itemNumber < stringArray.length; itemNumber++) {
      setText(itemNumber, stringArray[itemNumber]);
    }
  }

  public void setText(int itemNumber, String string){
    try{
      itemArrayList.get(itemNumber).setTextLabel(string);
    }catch (IndexOutOfBoundsException e){
    }
  }

}
