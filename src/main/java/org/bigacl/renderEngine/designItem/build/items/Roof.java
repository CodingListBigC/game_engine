package org.bigacl.renderEngine.designItem.build.items;

import org.bigacl.renderEngine.designItem.build.BuildItemsAbstract;

public class Roof extends BuildItemsAbstract {
  @Override
  public void defaultSettings() {
    this.folderPath = "modelDesignData/roof";
  }

  public Roof() {
  }

  public Roof(int type) {
    super();
    this.currentType = type;
    init();
  }

  public Roof(Roof copy) {
    super(copy);
  }
}
