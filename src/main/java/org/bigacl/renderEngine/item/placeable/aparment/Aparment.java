package org.bigacl.renderEngine.item.placeable.aparment;

import org.bigacl.renderEngine.item.placeable.BasePlaceableItem;
import org.joml.Vector3f;

public class Aparment extends BasePlaceableItem {

  public Aparment() {
    defaultSettings();
  }
  public Aparment(Vector3f pos){
    defaultSettings();
    this.worldPosition = pos;
  }
  @Override
  public void defaultSettings() {
    this.folderPath = "gameData/buildings/aparment";
    this.jsonName = "aparment.json";
    loadData();
  }
}
