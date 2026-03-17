package org.bigacl.renderEngine.gui.menu.hudMenu;

import org.bigacl.renderEngine.gameItems.item.ItemMangerAbstract;
import org.bigacl.renderEngine.gameItems.item.placeable.BasePlaceableItem;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.utils.consts.ItemConst;
import org.joml.Vector3f;

import java.util.ArrayList;

public class ViewData {
  Vector3f position;
  ArrayList<? extends BasePlaceableItem> viewDataList;
  /**
   * Get current item viewing in menu
   */
  int viewIndex;
  ItemConst.BasicPlaceableTypes lastViewType;
  ItemConst.BasicPlaceableTypes viewType = ItemConst.BasicPlaceableTypes.ALL;

  public ItemConst.BasicPlaceableTypes getLastViewType() {
    return lastViewType;
  }

  public ItemConst.BasicPlaceableTypes getViewType() {
    return viewType;
  }


  public Vector3f getPosition() {
    if (viewDataList != null) {
      try {
        this.position = viewDataList.get(viewIndex).getPosition();
      }catch (IndexOutOfBoundsException e){

      }
    }
    return position;
  }

  public Vector3f getRotation() {
    if (viewDataList == null) return null;
    try {
      return viewDataList.get(viewIndex).getRotation();
    } catch (IndexOutOfBoundsException e) {
    }
    return null;
  }

  ;

  public void setPosition(Vector3f position) {
    this.position = position;
  }

  public void setViewDataList( ArrayList<? extends BasePlaceableItem> viewDataList) {
    this.viewDataList = viewDataList;
  }

  public int getViewIndex() {
    return viewIndex;
  }

  public void setViewIndex(int viewIndex) {
    this.viewIndex = viewIndex;
  }

  public String getName() {
    if (viewDataList == null) return null;
    try {
      return viewDataList.get(viewIndex).getMainName();
    } catch (IndexOutOfBoundsException e) {
    }
    return null;
  }

  public void deleteSelected() {
    ClassConst.itemMangerAbstract.removeItemDefaultData(viewIndex);
  }

  public void copySelected() {
    ClassConst.itemMangerAbstract.copyItemDefaultData(viewIndex);
  }
}
