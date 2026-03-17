package org.bigacl.renderEngine.designItem.build.items;


import org.bigacl.renderEngine.designItem.build.BuildItemsAbstract;

public class Block extends BuildItemsAbstract {
  public Block(Block copyBlock) {
    super(copyBlock);
  }

  @Override
  public void defaultSettings() {
    this.folderPath = "modelDesignData/blocks";
  }

  public Block() {
    super();
  }

  public Block(int type) {
    super();
    this.currentType = type;
  }
}
