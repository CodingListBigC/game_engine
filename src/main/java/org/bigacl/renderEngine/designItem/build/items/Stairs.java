package org.bigacl.renderEngine.designItem.build.items;

import org.bigacl.renderEngine.designItem.build.BuildItemsAbstract;

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

  @Override
  public Stairs copy() {
    return new Stairs(this); // Now this works because House is not abstract
  }
}
