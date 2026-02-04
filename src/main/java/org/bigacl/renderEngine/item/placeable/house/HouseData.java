package org.bigacl.renderEngine.item.placeable.house;

import org.bigacl.renderEngine.item.placeable.BasePlaceableItem;

import java.util.Map;

public class HouseData {
  // These MUST use the static inner classes from BasePlaceableItem
  public BasePlaceableItem.NameData name;
  public Map<String, BasePlaceableItem.BaseModelParts> baseModel;
  public int amount_of_levels;
  public String type;
  public Map<String, BasePlaceableItem.LevelData> level;
}
