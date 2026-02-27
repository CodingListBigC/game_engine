package org.bigacl.renderEngine.gui.fields.button.select;

import org.bigacl.renderEngine.gui.fields.InputInterface;
import org.bigacl.renderEngine.gui.fields.sets.InputSets;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2d;
import org.joml.Vector2f;

import java.awt.*;

public class SelectButtonSet extends InputSets {

  float columnWidth = 10;
  final Color buttonBg = Color.lightGray;
  final Color buttonOl = Color.darkGray;
  final Color fontColor = Color.white;
  final Vector2f setPos;
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
