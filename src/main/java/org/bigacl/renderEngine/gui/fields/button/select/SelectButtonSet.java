package org.bigacl.renderEngine.gui.fields.button.select;

import org.bigacl.renderEngine.gameItems.item.placeable.BasePlaceableItem;
import org.bigacl.renderEngine.gui.fields.InputInterface;
import org.bigacl.renderEngine.gui.fields.sets.InputSets;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2d;
import org.joml.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public class SelectButtonSet extends InputSets<SelectButtonWithText> {

  float columnWidth = 10;
  final Color buttonBg = Color.DARK_GRAY;
  final Color buttonOl = Color.GRAY;
  final Color fontColor = Color.white;
  final Vector2f setPos;

  public void setShowItemSettings(int showItemSettings) {
    this.showItemSettings = showItemSettings;
  }

  private int showItemSettings = 0;
  @Override
  public void newDefaultItem() {
    Vector2f location = new Vector2f(columnWidth, getNextY()).add(this.setPos);
    addItem(new SelectButtonWithText(location,this.rowSize,this.buttonBg,this.buttonOl, this.fontColor));
    itemList.getFirst().setClickedStatus(true);
    System.out.println("Clicked Status First: " + itemList.getFirst().getClickedStatus());
  }

  @Override
  public int checkInput(Vector2d mouseLocation) {
    if (ClassConst.window.getMouseAction() != 1){
      return -1;
    }
    int settingValue = selectMousePress(mouseLocation);
    if (settingValue == -1){
      return -1;
    }
    int lastedClicked = getLastClicked();

    if (lastedClicked == settingValue){
      return settingValue;
    }
    resetAll();
    try {

      itemList.get(settingValue).setClickedStatus(true);
      return settingValue;

    }catch (IndexOutOfBoundsException e){
      return lastedClicked;
    }


  }

  @Override
  public void updateText() {
    if (showItemSettings == 0){
      this.setItemsText((ArrayList<BasePlaceableItem>) ClassConst.itemManger.getAllItems());
    }
  }

  private void updateListText(int index, String text){
    try {
      checkAmount(index);
      itemList.get(index).setTextLabel(text);

    }catch (IndexOutOfBoundsException e){
      System.out.checkError();
    }
  }

  private void setItemsText(ArrayList<BasePlaceableItem> list){
    viewAmount(list.size());
    for (BasePlaceableItem item: list){
      String name = item.mainName();
      int index =  list.indexOf(item);
      String setString = name + ": " + index;
      updateListText(index, setString);

    }
  }
  private void resetAll(){
    for (InputInterface item: itemList){
      item.setClickedStatus(false);
    }
  }
  private int getLastClicked(){
    for (InputInterface item: itemList){
      if (item.getClickedStatus()){
        return itemList.indexOf(item);
      }
    }
    return -1;
  }
  private int selectMousePress(Vector2d mouseLocation){
    for (InputInterface item: itemList){
      if (item.isHovered(mouseLocation)){
        return itemList.indexOf(item);
      }
    }
    return -1;
  }

  public SelectButtonSet(Vector2f setPos) {
    this.setPos = new Vector2f(setPos);
    checkAmount(10);
    viewAmount(4);
  }
}
