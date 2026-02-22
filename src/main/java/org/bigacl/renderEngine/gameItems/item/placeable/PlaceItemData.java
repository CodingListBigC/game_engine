package org.bigacl.renderEngine.gameItems.item.placeable;

import java.util.Map;

public class PlaceItemData {
  // These MUST use the static inner classes from BasePlaceableItem
  public BasePlaceableItem.NameData name;
  public Map<String, BasePlaceableItem.BaseModelParts> baseModel;
  public int amount_of_levels;
  public String type;
  public Map<String, BasePlaceableItem.LevelData> level;
}
