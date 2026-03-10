package org.bigacl.renderEngine.designItem.building;

import org.bigacl.renderEngine.designItem.BuildItemsAbstract;

public class Stairs extends BuildItemsAbstract {
  @Override
  public void defaultSettings() {
    this.folderPath = "modelDesignData/stairs";
  }

  public Stairs() {
    init();
  }

  public Stairs(int type) {
    this.currentType = type;
    init();
  }
}
