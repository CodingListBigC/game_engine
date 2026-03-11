package org.bigacl.renderEngine.designItem.building;


import org.bigacl.renderEngine.designItem.BuildItemsAbstract;

public class Block extends BuildItemsAbstract {
  @Override
  public void defaultSettings() {
    this.folderPath = "modelDesignData/blocks";
  }

  public Block() {
    init();
  }

  public Block(int type) {
    this.currentType = type;
    init();
  }
}
