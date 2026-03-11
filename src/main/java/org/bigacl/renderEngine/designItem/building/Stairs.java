package org.bigacl.renderEngine.designItem.building;

import org.bigacl.renderEngine.designItem.BuildItemsAbstract;

public class Stairs extends BuildItemsAbstract {
  @Override
  public void defaultSettings() {
    this.folderPath = "modelDesignData/stairs";
  }

  public Stairs() {
    super();
    init();
  }

  public Stairs(int type) {
    super();
    this.currentType = type;
    init();
  }

  public Stairs(Stairs copyStair) {
    super(copyStair);
  }
}
