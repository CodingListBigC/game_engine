package org.bigacl.renderEngine.gui.fields.button.select;

import org.bigacl.renderEngine.gameItems.item.placeable.BasePlaceableItem;
import org.bigacl.renderEngine.gui.fields.InputInterface;
import org.bigacl.renderEngine.gui.fields.sets.InputSets;
import org.bigacl.renderEngine.gui.menu.hudMenu.HudAbstract;
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


  @Override
  public void newDefaultItem() {
    Vector2f location = new Vector2f(columnWidth, getNextY()).add(this.setPos);
    addItem(new SelectButtonWithText(location, this.rowSize, this.buttonBg, this.buttonOl, this.fontColor));
    itemList.getFirst().setClickedStatus(true);
    System.out.println("Clicked Status First: " + itemList.getFirst().getClickedStatus());
  }

  @Override
  public void checkInput(Vector2d mouseLocation) {
    if (ClassConst.window.getMouseAction() != 1) {
      return;
    }
    int settingValue = selectMousePress(mouseLocation);
    if (settingValue == -1) {
      return;
    }
    int lastedClicked = getLastClicked();

    if (lastedClicked == settingValue) {
      return;
    }
    resetAll();
    try {
      HudAbstract.getViewData().setViewIndex(settingValue);
      itemList.get(settingValue).setClickedStatus(true);
    } catch (IndexOutOfBoundsException e) {
    }


  }

  @Override
  public void updateText() {
    ArrayList<? extends BasePlaceableItem> listToShow;
    int showItemSettings = HudAbstract.getViewData().getViewIndex();
    if (showItemSettings == 1) {
      listToShow = (ArrayList<? extends BasePlaceableItem>) ClassConst.itemManger.getHouseList();
    } else if (showItemSettings == 2) {
      listToShow = ClassConst.itemManger.getApartmentList();
    } else {
      listToShow = (ArrayList<? extends BasePlaceableItem>) ClassConst.itemManger.getAllItems();
    }
    HudAbstract.getViewData().setViewDataList(listToShow);
    setItemsText(listToShow);
  }

  private void updateListText(int index, String text) {
    try {
      checkAmount(index);
      itemList.get(index).setTextLabel(text);

    } catch (IndexOutOfBoundsException e) {
      System.out.checkError();
    }
  }

  private void setItemsText( ArrayList<? extends BasePlaceableItem>  list) {

    viewAmount(list.size());
    for (BasePlaceableItem item : list) {
      String name = item.mainName();
      int index = list.indexOf(item);
      String setString = name + ": " + index;
      updateListText(index, setString);

    }
  }

  private void resetAll() {
    for (InputInterface item : itemList) {
      item.setClickedStatus(false);
    }
  }

  private int getLastClicked() {
    for (int i = 0; i < itemList.size(); i++) {
      if (itemList.get(i).getClickedStatus()){
        return i;
      }
    }
    return -1;
  }

  private int selectMousePress(Vector2d mouseLocation) {
    for (int i = 0; i < itemList.size(); i++) {
      if (itemList.get(i).isHovered(mouseLocation)) {
        return i;
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
