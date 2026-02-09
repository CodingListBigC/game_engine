package org.bigacl.renderEngine.item.placeable.house;

import org.bigacl.renderEngine.item.placeable.BasePlaceableItem;
import org.joml.Vector3f;

public class House extends BasePlaceableItem {

  public House() {
    defaultSettings();
  }
  public House(Vector3f pos){
    defaultSettings();
    this.worldPosition = pos;
  }
  @Override
  public void defaultSettings() {
    this.folderPath = "gameData/buildings/home";
    this.jsonName = "home.json";
    this.isPlaced = true;
    loadData();
    System.out.println(name.main + " placed in world at: " + worldPosition.x + ", " + worldPosition.z);
  }
}