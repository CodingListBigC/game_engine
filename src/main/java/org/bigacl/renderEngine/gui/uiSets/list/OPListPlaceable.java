package org.bigacl.renderEngine.gui.uiSets.list;

import org.bigacl.renderEngine.gameItems.item.placeable.BasePlaceableItem;

import java.util.ArrayList;

/**
 * One Pick List
 */
public class OPListPlaceable {
  ArrayList<Object> basePlaceableItemArrayList = new ArrayList<>();

  public void addItem(BasePlaceableItem basePlaceableItem){
    basePlaceableItemArrayList.add(basePlaceableItem);
  }
}
