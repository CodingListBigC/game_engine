package org.bigacl.renderEngine.gameItems.item.tool;

import org.bigacl.renderEngine.gameItems.item.ItemMangerAbstract;
import org.bigacl.renderEngine.gameItems.item.placeable.BasePlaceableItem;
import org.bigacl.renderEngine.utils.consts.ItemConst;

public class ItemMangerModelDesign extends ItemMangerAbstract<BasePlaceableItem, ItemConst.BasicPlaceableTypes> {
  @Override
  public void copyItemDefaultData(int viewIndex) {
    if (viewIndex >= 0 && viewIndex < this.allItems.size()) {
      BasePlaceableItem origin = this.allItems.get(viewIndex);

      // We cast because the copy() returns BasePlaceableItem
      // but we know it matches renderItem in this context
      BasePlaceableItem copy = (BasePlaceableItem) origin.copy();

      this.addItemAll(copy);
    }
  }
}
