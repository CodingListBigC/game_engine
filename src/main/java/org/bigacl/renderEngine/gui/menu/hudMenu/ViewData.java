package org.bigacl.renderEngine.gui.menu.hudMenu;

import org.bigacl.renderEngine.gameItems.item.placeable.BasePlaceableItem;
import org.joml.Vector3f;

import java.util.ArrayList;

public class ViewData {
  Vector3f position;
  ArrayList<? extends BasePlaceableItem> viewDataList;
  int viewIndex;

  public int getViewType() {
    return viewType;
  }

  int viewType = 0;

  public Vector3f getPosition() {
    if (viewDataList != null) {
      try {
        this.position = viewDataList.get(viewIndex).getPosition();
      }catch (IndexOutOfBoundsException e){

      }
    }
    return position;
  }

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
}
